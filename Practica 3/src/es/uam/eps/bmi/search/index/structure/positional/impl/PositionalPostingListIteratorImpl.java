package es.uam.eps.bmi.search.index.structure.positional.impl;

import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.PostingsListIterator;
import es.uam.eps.bmi.search.index.structure.positional.PositionalPostingImpl;

public class PositionalPostingListIteratorImpl implements PostingsListIterator {

    private PositionalPostingImpl[] aux;
    private int i=0;

    public PositionalPostingListIteratorImpl(PositionalPostingImpl[] aux){
        this.aux=aux;
    }

    @Override
    public boolean hasNext() {
        return i<aux.length;
    }

    @Override
    public Posting next() {
        return aux[i++];
    }
}
