package es.uam.eps.bmi.search.index.lucene;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.freq.FreqVector;
import es.uam.eps.bmi.search.index.freq.lucene.LuceneFreqVector;
import org.apache.lucene.index.*;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LuceneIndex implements Index{

    private IndexReader m_indexReader;
    private List<String> termList;

    /**
     *
     * @param indexPath
     * @throws IOException
     */
    public LuceneIndex(String indexPath) throws IOException {
        /* Inicializacion para IndexReader */
        m_indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));

        /* Inicializacion de la lista de terminos y del iterador de terminos */
        termList = new ArrayList<>();
        TermsEnum terms = MultiFields.getFields(m_indexReader).terms("texto").iterator();
        /* Construimos la lista de terminos iterando el indice*/
        while (terms.next() != null) {
            termList.add(terms.term().utf8ToString());
        }

    }

    @Override
    public List<String> getAllTerms() {

        return termList;
    }

    @Override
    public long getTotalFreq(String palabra) throws IOException{
          return  m_indexReader.totalTermFreq(new Term("texto",palabra));
    }

    @Override
    public FreqVector getDocVector(int docID) throws IOException{

        return new LuceneFreqVector(m_indexReader.getTermVector(docID, "texto"));
    }

    @Override
    public String getDocPath(int docID) throws IOException{
        Terms t = m_indexReader.getTermVector(docID, "path");
        return t.iterator().term().utf8ToString();
    }

    @Override
    public long getTermFreq(String palabra, int docID) throws IOException{

        return getDocVector(docID).getFreq(palabra);
    }

    @Override
    public int getDocFreq(String palabra) throws IOException{

        return  m_indexReader.getDocCount(palabra);
    }

    @Override
    public IndexReader getIndexReader() {
        return this.m_indexReader;
    }


}
