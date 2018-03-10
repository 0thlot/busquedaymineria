package es.uam.eps.bmi.search.index.structure.impl;

import es.uam.eps.bmi.search.index.structure.Posting;

import java.io.Serializable;

public class ImplPosting extends Posting implements Serializable {

    private int pos;

    public ImplPosting(int id, long f, int pos) {
        super(id, f);
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }
}
