package es.uam.eps.bmi.search.ranking.impl;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import org.apache.lucene.search.ScoreDoc;
import java.io.IOException;

/**
 *
 * @author jorge
 * @author oscar
 */
public class IMPLSearchRankingDoc extends SearchRankingDoc {
    Index index;
    IMPLDoc rankedDoc;

    IMPLSearchRankingDoc (Index idx, IMPLDoc r) {
        super();
        index = idx;
        rankedDoc = r;
    }

    @Override
    public double getScore() { return (double) rankedDoc.getScore();  }

    @Override
    public String getPath() throws IOException {
        return index.getDocPath(rankedDoc.getDocID());
    }
}
