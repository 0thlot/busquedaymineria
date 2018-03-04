package es.uam.eps.bmi.search.vsm;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.PostingsListIterator;
import es.uam.eps.bmi.search.index.structure.impl.ImplPosting;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.impl.RankingImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class DocBasedVSMEngine extends AbstractVSMEngine {

    public DocBasedVSMEngine(Index index) {
        super(index);
    }

    @Override
    public SearchRanking search(String query, int cutoff) throws IOException {
        String[] terms = parse(query);
        RankingImpl ranking = new RankingImpl(index, cutoff);

        PriorityQueue<ImplPosting> heapDocId = new PriorityQueue<>(terms.length);
        Map<String,PostingsListIterator> listPosting = new HashMap<>();
        Map<Integer,Double> mapDocScore = new HashMap<>();

        for(String t: terms){
            PostingsListIterator pl = (PostingsListIterator) index.getPostings(t).iterator();
            if(pl!=null && pl.hasNext()){
                Posting p = pl.next();
                heapDocId.add(new ImplPosting(p.getDocID(),p.getFreq(),t));
                listPosting.put(t,pl);
            }
        }
        int beforeDocId = heapDocId.peek().getDocID();
        do{
            if(heapDocId.isEmpty()){
                if(!mapDocScore.isEmpty()){
                    ranking.add(beforeDocId,mapDocScore.get(beforeDocId)/index.getDocNorm(beforeDocId));
                    mapDocScore.remove(beforeDocId);
                }
                break;
            }
            ImplPosting head=heapDocId.poll();

            if(beforeDocId!=head.getDocID()){
                ranking.add(beforeDocId,mapDocScore.get(beforeDocId)/index.getDocNorm(beforeDocId));
                mapDocScore.remove(beforeDocId);
            }

            beforeDocId = head.getDocID();

            if(!mapDocScore.containsKey(head.getDocID())){
                mapDocScore.put(head.getDocID(), (double) 0);
            }

            mapDocScore.replace(head.getDocID(),mapDocScore.get(head.getDocID())+tfidf(head.getFreq(),index.getDocFreq(head.getTerm()),index.numDocs()));
            PostingsListIterator pl = listPosting.get(head.getTerm());

            if(pl!=null && pl.hasNext()){
                Posting p = pl.next();
                heapDocId.add(new ImplPosting(p.getDocID(),p.getFreq(),head.getTerm()));
            }else{
                listPosting.remove(head.getTerm());
            }

        }while(true);

        return ranking;
    }

}
