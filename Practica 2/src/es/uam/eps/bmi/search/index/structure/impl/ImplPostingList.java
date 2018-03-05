package es.uam.eps.bmi.search.index.structure.impl;

import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.PostingsList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImplPostingList implements PostingsList, Serializable {

    private List<Posting> postings;

    public ImplPostingList(){
        postings = new ArrayList<>();
    }

    public int size(){ return postings.size(); }

    public void add(Posting posting){ postings.add(posting); }

    public boolean contains(Posting post){ return postings.contains(post); }

    @Override
    public Iterator<Posting> iterator() { return postings.iterator(); }

    @Override
    public String toString() {
        StringBuilder aux = new StringBuilder();

        for(Posting p:postings){
            aux.append(p.getDocID()).append(" ").append(p.getFreq()).append(" ");
        }
        aux.deleteCharAt(aux.length()-1);

        return aux.toString();
    }
}
