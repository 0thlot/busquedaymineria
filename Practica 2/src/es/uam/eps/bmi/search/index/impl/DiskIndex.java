package es.uam.eps.bmi.search.index.impl;

import es.uam.eps.bmi.search.index.AbstractIndex;
import es.uam.eps.bmi.search.index.structure.PostingsList;

import java.util.Collection;

public class DiskIndex extends AbstractIndex {


    @Override
    public int numDocs() {
        return 0;
    }

    @Override
    public PostingsList getPostings(String term) {
        return null;
    }

    @Override
    public Collection<String> getAllTerms() {
        return null;
    }

    @Override
    public long getTotalFreq(String term) {
        return 0;
    }

    @Override
    public long getDocFreq(String term) {
        return 0;
    }

    @Override
    public String getDocPath(int docID) {
        return null;
    }
}
