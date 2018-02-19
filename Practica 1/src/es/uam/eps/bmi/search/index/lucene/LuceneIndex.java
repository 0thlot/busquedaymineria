package es.uam.eps.bmi.search.index.lucene;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.freq.FreqVector;
import es.uam.eps.bmi.search.index.freq.lucene.LuceneFreqVector;
import org.apache.lucene.index.*;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LuceneIndex implements Index{

    private IndexReader m_indexReader;
    private List<String> termList;
    private Map<Integer,Double> moduloDoc;

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

        moduloDoc = new HashMap<>();

        readModulo(indexPath);

    }

    private void readModulo(String indexPath) throws IOException {
        File filePath = new File(indexPath+"/modulo.txt");

        if (!filePath.exists()) {
            return;
        }
        List<String> strings = Files.readAllLines(filePath.toPath());
        strings.forEach(s -> {
               String[] datos = s.split("\t");
               moduloDoc.put(Integer.valueOf(datos[0]),Double.valueOf(datos[1]));
        });


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

        return m_indexReader.document(docID).get("path");
    }

    @Override
    public long getTermFreq(String palabra, int docID) throws IOException{

        return getDocVector(docID).getFreq(palabra);
    }

    @Override
    public int getDocFreq(String palabra) throws IOException{

        return  m_indexReader.docFreq(new Term("texto",palabra));
    }

    @Override
    public IndexReader getIndexReader() {
        return this.m_indexReader;
    }

    @Override
    public Double getModuloDoc(int docId) {
        if(moduloDoc.containsKey(docId))
            return moduloDoc.get(docId);
        return null;
    }


}
