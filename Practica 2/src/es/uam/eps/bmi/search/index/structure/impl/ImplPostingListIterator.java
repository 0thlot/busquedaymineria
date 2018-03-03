package es.uam.eps.bmi.search.index.structure.impl;

import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.PostingsListIterator;

import java.util.ArrayList;
import java.util.List;

public class ImplPostingListIterator implements PostingsListIterator {
    protected List<ImplPosting> postings;

    public ImplPostingListIterator(){
        this.postings = new ArrayList<>();
    }

    public void add(ImplPosting posting) {
        postings.add(posting);
    }

    @Override
    public boolean hasNext() {
        return postings.iterator().hasNext();
    }

    @Override
    public Posting next() {
        return postings.iterator().next();
    }

    public boolean contains(Posting post){ return postings.contains(post); }

    public ImplPosting get(Posting posting){
        for(ImplPosting postingAux: postings)
            if (postingAux.compareTo(posting)==0) return postingAux;
        return null;
    }
}
