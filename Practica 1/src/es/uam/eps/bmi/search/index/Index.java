package es.uam.eps.bmi.search.index;

import es.uam.eps.bmi.search.index.freq.FreqVector;
import org.apache.lucene.index.IndexReader;

import java.io.IOException;
import java.util.List;
/** Interfaz Index
 *
 * @version 1.0
 * @author jorge
 * @author oscar
 */
public interface Index {
    /**
     *
     * @return
     */
    List<String> getAllTerms();

    /**
     *
     * @param palabra
     * @return
     */
    long getTotalFreq(String palabra) throws IOException;

    /**
     *
     * @param docIS
     * @return
     */
    FreqVector getDocVector(int docIS) throws IOException;

    /**
     * Guarda la relacion asociada a un documento.
     *
     * @param docID ID del documento.
     * @return direccion del documento.
     */
    String getDocPath(int docID) throws IOException;

    /**
     *
     * @param palabra
     * @param docID
     * @return
     */
    long getTermFreq(String palabra, int docID) throws IOException;

    /**
     *
     * @param palabra
     * @return
     */
    int getDocFreq(String palabra) throws IOException;

    IndexReader getIndexReader();

    Double getModuloDoc(int docId);


}
