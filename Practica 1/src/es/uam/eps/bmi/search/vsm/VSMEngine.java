package es.uam.eps.bmi.search.vsm;

import es.uam.eps.bmi.search.AbstractEngine;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRanking;

import java.io.IOException;

public class VSMEngine extends AbstractEngine{

    public VSMEngine(Index idx) {
        super(idx);
    }

    public SearchRanking search(String query, int cutoff) throws IOException{

    }
}
