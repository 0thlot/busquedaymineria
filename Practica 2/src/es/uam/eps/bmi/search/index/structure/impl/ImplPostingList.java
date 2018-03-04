package es.uam.eps.bmi.search.index.structure.impl;

import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.PostingsList;
import org.apache.lucene.index.PostingsEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImplPostingList implements PostingsList, Serializable {

    private ImplPostingListIterator iterator;
    private String term;

    public ImplPostingList(String term){
        this.term=term;
        iterator = new ImplPostingListIterator();
    }

    public int size(){ return iterator.postings.size(); }

    public void add(ImplPosting posting){ iterator.add(posting); }

    public boolean contains(Posting post){ return iterator.contains(post); }

    public ImplPosting get(Posting posting){ return iterator.get(posting); }

    @Override
    public Iterator<Posting> iterator() { return iterator; }
}
