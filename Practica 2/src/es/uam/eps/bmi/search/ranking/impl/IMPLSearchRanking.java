package es.uam.eps.bmi.search.ranking.impl;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import org.apache.lucene.search.ScoreDoc;
import java.util.Iterator;

/**
 *
 * @author jorge
 * @author oscar
 */
public class IMPLSearchRanking implements SearchRanking {

    private IMPLSearchRankingIterator rankingIterator;

    public IMPLSearchRanking(Index index, IMPLDocVector[] scores){
        this.rankingIterator = new IMPLSearchRankingIterator(index, scores);
    }

    @Override
    public int size() { return rankingIterator.results.length; }

    @Override
    public Iterator<SearchRankingDoc> iterator() { return rankingIterator; }

}
