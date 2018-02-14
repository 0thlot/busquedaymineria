package es.uam.eps.bmi.search.index.lucene;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.freq.FreqVector;
import es.uam.eps.bmi.search.index.freq.lucene.LuceneFreqVector;
import es.uam.eps.bmi.search.index.freq.lucene.LuceneTermFreq;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class LuceneIndex implements Index{

    private IndexReader m_indexReader;
    private ArrayList<String> termList;

    /**
     *
     * @param indexPath
     * @throws IOException
     */
    public LuceneIndex(String indexPath) throws IOException {
        /* Inicializacion para IndexReader */
        m_indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));

        /* Inicializacion de la lista de terminos y del iterador de terminos */
        termList = new ArrayList<String>();
        TermsEnum terms = MultiFields.getFields(m_indexReader).terms("texto").iterator();

        /* Construimos la lista de terminos iterando el indice*/
        while (terms.next() != null){
            termList.add(terms.term().utf8ToString());
        }
    }

    @Override
    public ArrayList<String> getAllTerms() {
        return termList;
    }

    @Override
    public int getTotalFreq(String palabra) {
        int freq=0;
        try{
            freq = (int) m_indexReader.getSumTotalTermFreq(palabra);
        }catch (IOException e){
            e.printStackTrace();
        }
        return freq;
    }

    @Override
    public FreqVector getDocVector(int docID){
        LuceneFreqVector vector=null;
        try {
            vector = new LuceneFreqVector(m_indexReader.getTermVector(docID, "texto"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vector;
    }

    @Override
    public String getDocPath(int docID){
        try {
            return m_indexReader.getTermVector(docID, "path").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public float getTermFreq(String palabra, int docID) {
        Terms term = null;
        float freq=0;
        try{
            term = m_indexReader.getTermVector(docID,palabra);
            freq = (float) term.getSumTotalTermFreq();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return freq;

    }

    @Override
    public float getDocFreq(String palabra) {
        float freq=0;
        try {
            freq =  (float) m_indexReader.getDocCount(palabra);
        }catch (IOException e){
            e.printStackTrace();
        }
        return freq;
    }
}
