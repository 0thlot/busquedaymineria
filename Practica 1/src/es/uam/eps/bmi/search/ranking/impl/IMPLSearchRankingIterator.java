package es.uam.eps.bmi.search.ranking.impl;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import es.uam.eps.bmi.search.ranking.SearchRankingIterator;
import org.apache.lucene.search.ScoreDoc;

public class IMPLSearchRankingIterator implements SearchRankingIterator{

    ScoreDoc results[];
    Index index;
    int n = 0;

    public IMPLSearchRankingIterator (Index idx, ScoreDoc r[]) {
        index = idx;
        results = r;
    }

    // Empty result list
    public IMPLSearchRankingIterator () {
        index = null;
        results = new ScoreDoc[0];
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public SearchRankingDoc next() {
        return null;
    }
}
