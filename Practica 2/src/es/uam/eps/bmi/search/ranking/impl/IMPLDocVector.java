package es.uam.eps.bmi.search.ranking.impl;

import java.util.List;

/**
 *  Clase que funciona como vector de un documento
 *  en una busqueda
 *
 * @author jorge
 * @author oscar
 */
public class IMPLDocVector {
    private int docID;
    private String path;
    private double score;

    /**
     *
     * @param id
     * @param path
     */
    public IMPLDocVector(int id, String path){
        this.docID = id;
        this.path = path;
        score = 0;
    }

    public int getDocID(){
        return docID;
    }

    public String getPath() { return path; }

    public double getScore() { return score; }

    /**
     *
     * @param scoreWord
     */
    public void anyadirPalabra(double  scoreWord){ score += scoreWord; }
}