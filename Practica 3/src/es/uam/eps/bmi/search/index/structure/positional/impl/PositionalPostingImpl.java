package es.uam.eps.bmi.search.index.structure.positional.impl;

import es.uam.eps.bmi.search.index.structure.positional.PositionalPosting;

import java.util.ArrayList;
import java.util.List;

public class PositionalPostingImpl extends PositionalPosting{
    public PositionalPostingImpl(int id, int pos) {
        super(id,0, new ArrayList<>());
        add(pos);
    }

    public void add(int pos){
        add1();
        positions.add(pos);
    }
}
