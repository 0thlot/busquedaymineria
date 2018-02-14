package es.uam.eps.bmi.search.ranking.impl;

import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import es.uam.eps.bmi.search.ranking.SearchRankingIterator;

public class IMPLSearchRankingIterator implements SearchRankingIterator {
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public SearchRankingDoc next() {
        return null;
    }
}
