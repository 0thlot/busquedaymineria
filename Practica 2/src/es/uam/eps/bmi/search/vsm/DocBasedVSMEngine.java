package es.uam.eps.bmi.search.vsm;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.PostingsList;
import es.uam.eps.bmi.search.index.structure.PostingsListIterator;
import es.uam.eps.bmi.search.index.structure.impl.ImplPosting;
import es.uam.eps.bmi.search.index.structure.lucene.LucenePostingsList;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.impl.RankingImpl;
import es.uam.eps.bmi.search.ranking.impl.RankingImplDoc;

import java.io.IOException;
import java.util.*;

public class DocBasedVSMEngine extends AbstractVSMEngine {

    private List<RankingImplDoc> vectorDoc;
    public DocBasedVSMEngine(Index index) {
        super(index);
    }

    @Override
    public SearchRanking search(String query, int cutoff) throws IOException {
        String[] terms = parse(query);
        RankingImpl ranking = new RankingImpl(index, cutoff);

        PriorityQueue<ImplPosting> heapDocId = new PriorityQueue<>(terms.length);
        HashMap<String,PostingsListIterator> listPostings = new HashMap<>();
        HashMap<Integer,Double> mapDocScore = new HashMap<>();
        vectorDoc = new ArrayList<>();

        for(String t: terms){
            PostingsListIterator pl = (PostingsListIterator) index.getPostings(t).iterator();
            if(pl!=null && pl.hasNext()){
                Posting p = pl.next();
                heapDocId.add(new ImplPosting(p.getDocID(),p.getFreq(),t));
                listPostings.put(t,pl);
            }
        }
        int beforeDocId = -1;
        do{
            if(heapDocId.isEmpty()) break;
            ImplPosting head=heapDocId.poll();

            if(beforeDocId != -1 && beforeDocId!=head.getDocID()){
                ranking.add(beforeDocId,mapDocScore.get(beforeDocId)/index.getDocNorm(beforeDocId));
                mapDocScore.remove(beforeDocId);
            }
            beforeDocId = head.getDocID();
            if(!mapDocScore.containsKey(head.getDocID())){
                mapDocScore.put(head.getDocID(), (double) 0);
            }
            mapDocScore.replace(head.getDocID(),mapDocScore.get(head.getDocID())+tfidf(head.getFreq(),index.getDocFreq(head.getTerm()),index.numDocs()));
            PostingsListIterator pl = listPostings.get(head.getTerm());
            if(pl!=null && pl.hasNext()){
                Posting p = pl.next();
                heapDocId.add(new ImplPosting(p.getDocID(),p.getFreq(),head.getTerm()));
            }else{
                listPostings.remove(head.getTerm());
            }
        }while(!listPostings.isEmpty());

        return ranking;
    }

}
