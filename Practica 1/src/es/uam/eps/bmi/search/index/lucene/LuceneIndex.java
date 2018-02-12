package es.uam.eps.bmi.search.index.lucene;

import es.uam.eps.bmi.search.index.Index;
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
    private int numTerms;

    public LuceneIndex(String indexPath) throws IOException {
        /* Variables de inicializacion para IndexReader */
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        Directory directory = FSDirectory.open(Paths.get(indexPath));
        IndexWriter indexWriter = new IndexWriter(directory,config);
        /* Asignacion indexReader */
        m_indexReader = DirectoryReader.open(indexWriter);
    }

    @Override
    public ArrayList<String> getAllTerms() throws IOException {
        ArrayList<String> terminos = new ArrayList<String>();
        /* Iteramos los documentos del indice */
        for(int i=0; i<m_indexReader.numDocs();i++) {
            /*Itermaos los terminos de los documentos*/
            for (Iterator<String> it = m_indexReader.getTermVectors(i).iterator(); it.hasNext(); )
                terminos.add(it.next());
        }
        return terminos;

    }

    @Override
    public int getTotalFreq(String palabra) {
        int freq=0;
        try{
            freq = (int) m_indexReader.getSumTotalTermFreq(palabra);
        }catch (IOException e){
        }
        return freq;
    }

    @Override
    public Vector<String> getDocVector(int docID){
        Fields vector=null;
        try {
            vector= m_indexReader.getTermVectors(docID);
            m_indexReader.getTermVectors().
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vector;
    }

    @Override
    public int getDocPath(int docIS){

    }

    @Override
    public float getTermFreq(String palabra, int docID) throws IOException {
        Terms term = m_indexReader.getTermVector(docID,palabra);
        return (float) term.getSumTotalTermFreq();

    }

    @Override
    public float getDocFreq(String palabra) {
        float freq;
        try {
            freq =  (float) m_indexReader.getDocCount(palabra);
        }catch (IOException e){
            freq=0;
        }
        return freq;
    }
}
