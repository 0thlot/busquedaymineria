package es.uam.eps.bmi.search.ranking.impl;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import es.uam.eps.bmi.search.ranking.SearchRankingIterator;
import es.uam.eps.bmi.search.ranking.lucene.LuceneRankingDoc;
import org.apache.lucene.search.ScoreDoc;

/**
 *
 * @author jorge
 * @author oscar
 */
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
        return n < results.length;
    }

    @Override
    public SearchRankingDoc next() { return new IMPLSearchRankingDoc(index, results[n++]);  }
}
