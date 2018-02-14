package es.uam.eps.bmi.search.index;

import es.uam.eps.bmi.search.index.freq.FreqVector;
import org.apache.lucene.index.IndexReader;

import java.io.IOException;
import java.util.List;

public interface Index {
    /**
     *
     * @return
     */
    public List<String> getAllTerms();

    /**
     *
     * @param palabra
     * @return
     */
    public long getTotalFreq(String palabra) throws IOException;

    /**
     *
     * @param docIS
     * @return
     */
    public FreqVector getDocVector(int docIS) throws IOException;

    /**
     * Guarda la relacion asociada a un documento.
     *
     * @param docID ID del documento.
     * @return direccion del documento.
     */
    public String getDocPath(int docID) throws IOException;

    /**
     *
     * @param palabra
     * @param docID
     * @return
     */
    public long getTermFreq(String palabra, int docID) throws IOException;

    /**
     *
     * @param palabra
     * @return
     */
    public int getDocFreq(String palabra) throws IOException;

    public IndexReader getIndexReader();


}
