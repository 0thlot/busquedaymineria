package es.uam.eps.bmi.search.ranking.lucene;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import org.apache.lucene.search.ScoreDoc;

import java.util.Iterator;

public class LuceneRanking implements SearchRanking{

    private LuceneRankingIterator rankingIterator;

    public LuceneRanking(Index index, ScoreDoc[] scores) {
        this.rankingIterator = new LuceneRankingIterator(index,scores);
    }

    @Override
    public int size() {
        return rankingIterator.results.length;
    }

    @Override
    public Iterator<SearchRankingDoc> iterator() {
        return rankingIterator;
    }
}
