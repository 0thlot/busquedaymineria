package es.uam.eps.bmi.search.index.lucene;

import es.uam.eps.bmi.search.index.Index;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Vector;

public class LuceneIndex implements Index{

    private IndexReader m_indexReader;
    private ArrayList<String> terminos;
    private int numTerms;

    public LuceneIndex(String path){
        /*  */
        Directory directory = DirectoryReader.open(FSDirectory.open(Path.get(path)));
        m_indexReader = new LuceneIndexReader();
    }

    @Override
    public ArrayList<String> getAllTerms() {
        return terminos;
    }

    @Override
    public int getTotalFreq(String palabra) {

    }

    @Override
    public Vector<String> getDocVector(int docID){

    }

    @Override
    public int getDocPath(int docIS){

    }

    @Override
    public float getTermFreq(String palabra, int docID){

    }

    @Override
    public float getDocFreq(String palabra){

    }
}
