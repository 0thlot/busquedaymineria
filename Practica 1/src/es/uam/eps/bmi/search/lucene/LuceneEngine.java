package es.uam.eps.bmi.search.lucene;

import es.uam.eps.bmi.search.AbstractEngine;
import es.uam.eps.bmi.search.ranking.SearchRanking;

import java.io.IOException;

public class LuceneEngine extends AbstractEngine {



    public LuceneEngine(String indexPath) {

        super(new LuceneIndex(indexPath));

    }

    @Override
    public SearchRanking search(String query, int cutoff) throws IOException {
        if (this.index == null)
            throw new IOException(this.index.toString());

        return null;
    }
}
