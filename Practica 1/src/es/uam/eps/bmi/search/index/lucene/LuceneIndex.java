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
import java.util.Vector;

public class LuceneIndex implements Index{

    private IndexReader m_indexReader;
    private String path;

    public LuceneIndex(String indexPath) throws IOException {
        /* Variables de inicializacion para IndexReader */
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        Directory directory = FSDirectory.open(Paths.get(indexPath));
        IndexWriter indexWriter = new IndexWriter(directory,config);
        /* Asignacion indexReader */
        m_indexReader = DirectoryReader.open(indexWriter);
        path=indexPath;
    }

    @Override
    public ArrayList<String> getAllTerms() {
        ArrayList<String> terminos = new ArrayList<String>();
        try{
            /* Iteramos los documentos del indice (De verdad estamos visitando todos los docs??)*/
            for(int i=0; i<m_indexReader.numDocs();i++) {
                /*Itermaos los terminos de los documentos*/
                for (Iterator<String> it = m_indexReader.getTermVectors(i).iterator(); it.hasNext(); )
                    terminos.add(it.next());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return terminos;

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
        return  m_indexReader.getTermVector(docID, "path");
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
