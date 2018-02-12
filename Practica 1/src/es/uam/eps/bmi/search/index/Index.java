package es.uam.eps.bmi.search.index;

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
    public Vector<String> getDocVector(int docIS);

    /**
     *
     * @param docIS
     * @return
     */
    public int getDocPath(int docIS);

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
    public float getDocFreq(String palabra) throws IOException;


}
