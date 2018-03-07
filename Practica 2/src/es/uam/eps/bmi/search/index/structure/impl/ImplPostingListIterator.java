package es.uam.eps.bmi.search.index.structure.impl;

import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.PostingsListIterator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImplPostingListIterator implements PostingsListIterator, Serializable {
    private Posting[] postings;
    private int i=0;

    public ImplPostingListIterator(List<Posting> aux){

        this.postings = aux.toArray(new Posting[0]);
    }

    @Override
    public boolean hasNext() {
        return i<postings.length;
    }

    @Override
    public Posting next() {
        i++;
        return postings[i-1];
    }


}
