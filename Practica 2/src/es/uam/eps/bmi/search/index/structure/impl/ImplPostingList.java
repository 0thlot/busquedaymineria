package es.uam.eps.bmi.search.index.structure.impl;

import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.PostingsList;

import java.io.Serializable;
import java.util.Iterator;

public class ImplPostingList implements PostingsList, Serializable {

    private ImplPostingListIterator iterator;

    public ImplPostingList(){
        iterator = new ImplPostingListIterator();
    }

    public int size(){ return iterator.postings.size(); }

    public void add(Posting posting){ iterator.add(posting); }

    public boolean contains(Posting post){ return iterator.contains(post); }

    @Override
    public Iterator<Posting> iterator() { return iterator; }
}
