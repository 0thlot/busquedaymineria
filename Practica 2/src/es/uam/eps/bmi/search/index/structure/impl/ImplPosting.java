package es.uam.eps.bmi.search.index.structure.impl;

import es.uam.eps.bmi.search.index.structure.Posting;

import java.io.Serializable;

public class ImplPosting extends Posting implements Serializable {

    private String term;

    public ImplPosting(int id, long f, String t) {
        super(id, f);
        term = t;
    }

    public String getTerm() {
        return term;
    }
}
