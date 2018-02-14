package es.uam.eps.bmi.search.ranking.lucene;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import java.io.IOException;
import org.apache.lucene.search.ScoreDoc;

/**
 *
 * @author pablo
 */
public class LuceneRankingDoc extends SearchRankingDoc {
    Index index;
    ScoreDoc rankedDoc;
    
    public LuceneRankingDoc (Index idx, ScoreDoc r) {
        index = idx;
        rankedDoc = r;
    }

    @Override
    public double getScore() {
        return rankedDoc.score;
    }

    @Override
    public String getPath()  {
        return index.getDocPath(rankedDoc.doc);
    }
}
