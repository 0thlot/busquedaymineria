package es.uam.eps.bmi.search.index.lucene;

import org.apache.lucene.index.*;

import java.io.IOException;

public class LuceneIndexReader extends IndexReader {

    public LuceneIndexReader(){

    }

    @Override
    public Fields getTermVectors(int i) throws IOException {
        return null;
    }

    @Override
    public int numDocs() {
        return 0;
    }

    @Override
    public int maxDoc() {
        return 0;
    }

    @Override
    public void document(int i, StoredFieldVisitor storedFieldVisitor) throws IOException {

    }

    @Override
    protected void doClose() throws IOException {

    }

    @Override
    public IndexReaderContext getContext() {
        return null;
    }

    @Override
    public CacheHelper getReaderCacheHelper() {
        return null;
    }

    @Override
    public int docFreq(Term term) throws IOException {
        return 0;
    }

    @Override
    public long totalTermFreq(Term term) throws IOException {
        return 0;
    }

    @Override
    public long getSumDocFreq(String s) throws IOException {
        return 0;
    }

    @Override
    public int getDocCount(String s) throws IOException {
        return 0;
    }

    @Override
    public long getSumTotalTermFreq(String s) throws IOException {
        return 0;
    }
}
