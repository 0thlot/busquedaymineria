package es.uam.eps.bmi.search.ranking.impl;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.freq.FreqVector;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import org.apache.lucene.search.ScoreDoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IMPLSearchRanking implements SearchRanking {

    private IMPLSearchRankingIterator rankingIterator;

    public  IMPLSearchRanking(Index index, ScoreDoc[] scores){
        /* Aqui ordenamos la lista de documentos */
    }

    @Override
    public int size() { return rankingIterator.results.length; }

    @Override
    public Iterator<SearchRankingDoc> iterator() { return rankingIterator; }

}
