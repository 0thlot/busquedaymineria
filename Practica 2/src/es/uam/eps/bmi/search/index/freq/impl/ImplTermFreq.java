package es.uam.eps.bmi.search.index.freq.impl;

import es.uam.eps.bmi.search.index.freq.TermFreq;
import org.apache.lucene.index.Term;

import java.io.IOException;

public class ImplTermFreq implements TermFreq{

    String term;
    long freq;

    ImplTermFreq(String term, long freq){
        this.term = term;
        this.freq = freq;
    }

    @Override
    public String getTerm() throws IOException {
        return term;
    }

    @Override
    public long getFreq() throws IOException {
        return freq;
    }
}
