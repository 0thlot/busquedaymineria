package es.uam.eps.bmi.search.index;

import es.uam.eps.bmi.search.index.freq.FreqVector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public interface Index {
    /**
     *
     * @return
     */
    public ArrayList<String> getAllTerms();

    /**
     *
     * @param palabra
     * @return
     */
    public int getTotalFreq(String palabra);

    /**
     *
     * @param docIS
     * @return
     */
    public FreqVector getDocVector(int docIS);

    /**
     * Guarda la relacion asociada a un documento.
     *
     * @param docID ID del documento.
     * @return direccion del documento.
     */
    public String getDocPath(int docID);

    /**
     *
     * @param palabra
     * @param docID
     * @return
     */
    public float getTermFreq(String palabra, int docID);

    /**
     *
     * @param palabra
     * @return
     */
    public float getDocFreq(String palabra);


}
