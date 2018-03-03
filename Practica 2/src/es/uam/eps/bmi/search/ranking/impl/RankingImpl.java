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

    private Index index;
    private PriorityQueue<SearchRankingDoc> ranking;
    private int cutoff;

    public RankingImpl(Index index, int cutoff){
        this.index = index;
        this.cutoff = Math.min(cutoff, index.numDocs());
        ranking = new PriorityQueue<>(this.cutoff, Comparator.reverseOrder());
    }

    @Override
    public int size() { return ranking.size(); }

    @Override
    public Iterator<SearchRankingDoc> iterator() {
        SearchRankingDoc[] aux = ranking.toArray(new SearchRankingDoc[0]);
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
        if (ranking.size() >= this.cutoff) {
            RankingImplDoc aux = (RankingImplDoc) ranking.peek();
            if(aux.getScore()<score){
                ranking.remove(aux);
                ranking.offer(newR);
            }
        } else {
            ranking.offer(newR);
        }
    }

}
