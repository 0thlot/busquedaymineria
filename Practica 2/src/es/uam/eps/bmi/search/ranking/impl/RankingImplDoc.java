package es.uam.eps.bmi.search.ranking.impl;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import java.io.IOException;

/**
 *
 * @author jorge
 * @author oscar
 *
 * ES VALIDA NO BORRAR
 */
public class RankingImplDoc extends SearchRankingDoc {

    private int docID;
    private String path;
    private double score;

    RankingImplDoc(int docID, double score) {
        this.docID = docID;
        this.score = score;
    }

    RankingImplDoc(int docID, double score, String path) {
        this.docID = docID;
        this.score = score;
        this.path = path;
    }

    @Override
    public double getScore() { return score; }

    @Override
    public int getDocID() { return docID; }

    @Override
    public String getPath() throws IOException { return path; }

    /** Añade la puntuacion de una palabra al documento
     * @param scoreWord puntuacion de la palabra
     */
    public void addWord(double scoreWord){
        score += scoreWord;
    }

}
