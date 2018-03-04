package es.uam.eps.bmi.search.index.freq.impl;

import es.uam.eps.bmi.search.index.freq.FreqVectorIterator;
import es.uam.eps.bmi.search.index.freq.TermFreq;

public class ImplFreqVectorIterator implements FreqVectorIterator {
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public TermFreq next() {
        return null;
    }
}
