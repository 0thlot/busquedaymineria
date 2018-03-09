package es.uam.eps.bmi.search.vsm;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.PostingsListIterator;
import es.uam.eps.bmi.search.index.structure.impl.ImplPosting;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.impl.RankingImpl;

import java.io.IOException;
import java.util.PriorityQueue;

public class DocBasedVSMEngine extends AbstractVSMEngine {

    public DocBasedVSMEngine(Index index) {
        super(index);
    }

    @Override
    public SearchRanking search(String query, int cutoff) throws IOException {
        String[] terms = parse(query);
        RankingImpl ranking = new RankingImpl(index, cutoff);
        int numDocs = index.numDocs();

        PriorityQueue<ImplPosting> heapDocId = new PriorityQueue<>(terms.length);
        PostingsListIterator[] listPosting = new PostingsListIterator[terms.length];
        long[] docFreq = new long[terms.length];

        int count = 0;
        for(String t: terms){
            docFreq[count] = index.getDocFreq(t);
            PostingsListIterator pl = (PostingsListIterator) index.getPostings(t).iterator();
            //si es 0 es que no existe en el index
            if(docFreq[count]>0){
                Posting p = pl.next();
                heapDocId.add(new ImplPosting(p.getDocID(),p.getFreq(),count));
                listPosting[count]=pl;
            }
            count++;
        }

        int beforeDocId = heapDocId.peek().getDocID();
        ImplPosting head;
        PostingsListIterator pl;
        Posting p;
        double puntuacion =0;
        while(!heapDocId.isEmpty()){

            head=heapDocId.poll();
            if(beforeDocId!=head.getDocID()){
                ranking.add(beforeDocId,puntuacion/index.getDocNorm(beforeDocId));
                puntuacion=0;
                beforeDocId = head.getDocID();
            }

            puntuacion+=tfidf(head.getFreq(),docFreq[head.getPos()],numDocs);
            pl = listPosting[head.getPos()];

            if(pl.hasNext()){
                p = pl.next();
                heapDocId.add(new ImplPosting(p.getDocID(),p.getFreq(),head.getPos()));
            }

        }

        ranking.add(beforeDocId,puntuacion/index.getDocNorm(beforeDocId));

        return ranking;
    }

}
