package es.uam.eps.bmi.search.vsm;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.PostingsList;
import es.uam.eps.bmi.search.index.structure.PostingsListIterator;
import es.uam.eps.bmi.search.index.structure.impl.ImplPosting;
import es.uam.eps.bmi.search.index.structure.lucene.LucenePostingsList;
import es.uam.eps.bmi.search.ranking.SearchRanking;

import java.io.IOException;
import java.util.*;

public class DocBasedVSMEngine extends AbstractVSMEngine {
    public DocBasedVSMEngine(Index index) {
        super(index);
    }

    @Override
    public SearchRanking search(String query, int cutoff) throws IOException {
        String[] terms = parse(query);

        PriorityQueue<ImplPosting> heapDocId = new PriorityQueue<>(terms.length);
        HashMap<String,PostingsListIterator> listPostings = new HashMap<>();
        HashMap<Integer,Long> mapDocScore = new HashMap<>();

        for(String t: terms){
            PostingsListIterator pl = (PostingsListIterator) index.getPostings(t).iterator();
            if(pl!=null && pl.hasNext()){
                Posting p = pl.next();
                heapDocId.add(new ImplPosting(p.getDocID(),p.getFreq(),t));
                listPostings.put(t,pl);
            }
        }
        do{
            if(heapDocId.isEmpty()) break;
            ImplPosting head=heapDocId.poll();
            if(head==null)break;
            if(!mapDocScore.containsKey(head.getDocID())){
                mapDocScore.put(head.getDocID(), (long) 0);
            }
            mapDocScore.replace(head.getDocID(),mapDocScore.get(head.getDocID())+tfidf(head.getFreq(),index.getDocFreq(head.getTerm()),index.numDocs());
            PostingsListIterator pl = listPostings.get(head.getTerm());
            if(pl!=null && pl.hasNext()){
                Posting p = pl.next();
                heapDocId.add(new ImplPosting(p.getDocID(),p.getFreq(),head.getTerm()));
            }
        }while(true);














    }
}
