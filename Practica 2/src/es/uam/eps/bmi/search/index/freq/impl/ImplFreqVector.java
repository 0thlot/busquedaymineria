package es.uam.eps.bmi.search.index.freq.impl;

import es.uam.eps.bmi.search.index.freq.FreqVector;
import es.uam.eps.bmi.search.index.freq.TermFreq;

import java.io.IOException;
import java.util.Iterator;

public class ImplFreqVector implements FreqVector{
    @Override
    public long size() throws IOException {
        return 0;
    }

    @Override
    public long getFreq(String term) throws IOException {
        return 0;
    }

    @Override
    public Iterator<TermFreq> iterator() {
        return null;
    }
}
