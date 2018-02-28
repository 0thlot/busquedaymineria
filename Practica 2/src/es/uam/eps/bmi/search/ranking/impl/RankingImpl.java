package es.uam.eps.bmi.search.ranking.impl;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;

import java.io.IOException;
import java.util.*;

/**
 *
 * @author jorge
 * @author oscar
 */
public class RankingImpl implements SearchRanking {

    Index index;
    PriorityQueue<SearchRankingDoc> ranking;
    int cutoff;

    public RankingImpl(Index index, int cutoff){
        this.index = index;
        ranking = new PriorityQueue<>(Math.min(cutoff,index.numDocs()));
        this.cutoff = cutoff;
    }

    @Override
    public int size() { return ranking.size(); }

    @Override
    public Iterator<SearchRankingDoc> iterator() {
        SearchRankingDoc[] aux = (SearchRankingDoc[]) ranking.toArray();
        List<SearchRankingDoc> auxL =Arrays.asList(aux);
        auxL.sort(SearchRankingDoc::compareTo);
        return auxL.iterator();

    }

    /** Añade un nuevo documento al ranking
     * @param docId id del documento
     * @param score puntuacion
     */
    public void add(int docId, double score) throws IOException {

        add(docId, score,index.getDocPath(docId));
    }

    /** Añade un nuevo documento al ranking
     * @param docId id del documento
     * @param score puntuacion
     * @param path directorio del documento
     */
    public void add(int docId, double score, String path){
        RankingImplDoc newR=new RankingImplDoc(docId, score, path);
        if(!ranking.offer(newR)){
            RankingImplDoc aux = (RankingImplDoc) ranking.peek();
            if(aux.getScore()<score){
                ranking.remove(aux);
                ranking.offer(newR);
            }
        }
    }

}
