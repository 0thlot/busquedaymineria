package es.uam.eps.bmi.search.index.structure.impl;

import es.uam.eps.bmi.search.index.structure.Posting;

public class ImplPosting extends Posting {

    private String term;

    public ImplPosting(int id, long f, String t) {
        super(id, f);
        term = t;
    }

    public String getTerm() {
        return term;
    }
}
