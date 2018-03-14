package es.uam.eps.bmi.search.proximity;

import es.uam.eps.bmi.search.AbstractEngine;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.structure.PostingsListIterator;
import es.uam.eps.bmi.search.index.structure.positional.impl.PositionalPostingListImpl;
import es.uam.eps.bmi.search.index.structure.positional.impl.PositionalPostingListIteratorImpl;
import es.uam.eps.bmi.search.ranking.SearchRanking;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProximityEngine extends AbstractEngine{
    public ProximityEngine(Index idx) {
        super(idx);
    }

    @Override
    public SearchRanking search(String query, int cutoff) throws IOException {

        String[] parsed = parse(query);
        Map<String,PostingsListIterator> dicIterator= new HashMap<>();
        for(String t: parsed){
            dicIterator.put(t, (PostingsListIterator) index.getPostings(t).iterator());
        }
        if(query.startsWith("\"") && query.endsWith("\"")){

        }else{

            while(true){



            }
        }

    }
}
