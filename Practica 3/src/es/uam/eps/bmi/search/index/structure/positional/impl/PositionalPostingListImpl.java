package es.uam.eps.bmi.search.index.structure.positional.impl;

import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.PostingsList;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PositionalPostingListImpl implements PostingsList, Serializable{
    protected List<PositionalPostingImpl> postings;

    public PositionalPostingListImpl(List<PositionalPostingImpl> postings) {
        this.postings = postings;
    }
    public PositionalPostingListImpl() {
        this.postings = new ArrayList<>();
    }

    public PositionalPostingListImpl(int docId, int pos) {
        this.postings = new ArrayList<>();
        add(docId,pos);
    }

    @Override
    public int size() {
        return postings.size();
    }

    public void add(PositionalPostingImpl p){
        postings.add(p);
    }

    public void add(int docId, int pos){
        if(!postings.isEmpty() && docId == postings.get(postings.size()-1).getDocID()){
            postings.get(postings.size()-1).add(pos);
        }else{
            postings.add(new PositionalPostingImpl(docId,pos));
        }

    }

    @Override
    public Iterator<Posting> iterator() {
        Posting[] aux = postings.toArray(new Posting[0]);
        return Arrays.asList(aux).iterator();
    }
}
