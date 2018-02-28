package es.uam.eps.bmi.search.ranking.impl;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author jorge
 * @author oscar
 */
public class RankingImpl implements SearchRanking {

    Index index;
    List<RankingImplDoc> ranking;
    int cutoff;

    public RankingImpl(Index index, int cutoff){
        this.index = index;
        ranking = new ArrayList<RankingImplDoc>();
        this.cutoff = cutoff;
    }

    @Override
    public int size() { return ranking.size(); }

    @Override
    public Iterator<SearchRankingDoc> iterator() { return ranking.iterator(); }

    /** Añade un nuevo documento al ranking
     * @param docId id del documento
     * @param score puntuacion
     */
    public void add(int docId, double score){
        ranking.add(new RankingImplDoc(docId, score));
    }

    /** Añade un nuevo documento al ranking
     * @param docId id del documento
     * @param score puntuacion
     * @param path directorio del documento
     */
    public void add(int docId, double score, String path){
        ranking.add(new RankingImplDoc(docId, score, path));
    }

    /** Comprueba si el documento esta en el ranking
     * @param docID id del documento
     * @return posicion del documento, -1 si no esta el documento
     */
    public int isDocInRanking(int docID){
        for(int i=0; i<size(); i++)
            if (ranking.get(i).getDocID()==docID) return i;
        return -1;
    }

    /** Añdade la puntuacion de una palabra a un documento
     * del ranking
     * @param docID id del documento
     * @param score puntuacion
     */
    public void addWordDoc(int docID, double score){
        if(isDocInRanking(docID)>-1)
            ranking.get(docID).addWord(score);
    }

    /** Ordena el ranking con los mejores resultado
     *  y limita el numero de documentos indicado por cutoff
     */
    public void prepare(){
        int minimo = Math.min(cutoff, size());

        //Ordenamos la lista
        ranking.sort((d1,d2)-> Double.compare(d2.getScore(), d1.getScore()));

        //Si el array es mas pequeño que el numero de corte, no es necesario hacer nada
        if (minimo<cutoff) return;

        //Eliminamos los resultados sobrantes
        for(int i = size()-1; i>cutoff; i--) ranking.remove(i);

        return  Arrays.copyOf(scores,num);
    }

}
