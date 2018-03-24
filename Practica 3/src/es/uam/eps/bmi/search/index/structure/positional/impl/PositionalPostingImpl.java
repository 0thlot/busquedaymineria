package es.uam.eps.bmi.search.index.structure.positional.impl;

import es.uam.eps.bmi.search.index.structure.positional.PositionalPosting;
import es.uam.eps.bmi.search.index.structure.positional.PositionsIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PositionalPostingImpl extends PositionalPosting{

    private int indexPos;

    public PositionalPostingImpl(int id, int pos) {
        super(id,0, new ArrayList<>());
        add(pos);
    }

    public PositionalPostingImpl(int id, int pos, int indexPos) {
        super(id,0, new ArrayList<>());
        add(pos);
        this.indexPos=indexPos;
    }
    public PositionalPostingImpl(PositionalPosting posting,int indexPos) {
        super(posting.getDocID(),posting.getFreq(), StreamSupport.stream(Spliterators.spliteratorUnknownSize(posting.iterator(),
                Spliterator.ORDERED), false).collect(Collectors.toList()));
        this.indexPos=indexPos;
    }

    @Override
    public PositionsIterator iterator(){
        return new PositionsIterator(positions);
    }


    public void add(int pos){
        add1();
        positions.add(pos);
    }

    public int getIndexPos(){
        return indexPos;
    }
}
