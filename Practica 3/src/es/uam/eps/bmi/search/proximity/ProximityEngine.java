package es.uam.eps.bmi.search.proximity;

import es.uam.eps.bmi.search.AbstractEngine;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.structure.PostingsListIterator;
import es.uam.eps.bmi.search.index.structure.positional.impl.PositionalPostingImpl;

import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.impl.RankingImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProximityEngine extends AbstractEngine{
    public ProximityEngine(Index idx) {
        super(idx);
    }

    @Override
    public SearchRanking search(String query, int cutoff) throws IOException {

        boolean literal=false;
        String[] parsed;
        RankingImpl ranking = new RankingImpl(index,cutoff);
        if(query.startsWith("\"") && query.endsWith("\"")){
            literal=true;
            parsed = parse(query.replaceAll("\"",""));
        }else{
            parsed = parse(query);
        }

        PostingsListIterator[] dicIterator = new PostingsListIterator[parsed.length];
        List<PositionalPostingImpl> postingList = new ArrayList<>();
        for(int i=0;i<parsed.length;i++){
            String t=parsed[i];
            dicIterator[i]=(PostingsListIterator) index.getPostings(t).iterator();
            if(dicIterator[i].hasNext()){

            }
        }



        return ranking;
    }
}
