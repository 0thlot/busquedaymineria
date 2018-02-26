package es.uam.eps.bmi.search.ranking.impl;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import java.io.IOException;

/**
 *
 * @author jorge
 * @author oscar
 */
public class IMPLSearchRankingDoc extends SearchRankingDoc {
    Index index;
    IMPLDocVector rankedDoc;

    IMPLSearchRankingDoc (Index idx, IMPLDocVector r) {
        super();
        index = idx;
        rankedDoc = r;
    }

    @Override
    public double getScore() { return rankedDoc.getScore();  }

    @Override
    public int getDocID() {
        return rankedDoc.getDocID();
    }

    @Override
    public String getPath() throws IOException {
        return rankedDoc.getPath();
    }
}
