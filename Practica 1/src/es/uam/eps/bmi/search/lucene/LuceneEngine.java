package es.uam.eps.bmi.search.lucene;

import es.uam.eps.bmi.search.AbstractEngine;
import es.uam.eps.bmi.search.index.lucene.LuceneIndex;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.lucene.LuceneRanking;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;

public class LuceneEngine extends AbstractEngine {

    private IndexSearcher is;

    public LuceneEngine(String indexPath) throws IOException {
        super(new LuceneIndex(indexPath));
        is = new IndexSearcher(this.index.getIndexReader());
    }

    @Override
    public SearchRanking search(String query, int cutoff) throws IOException {
        if (this.index == null)
            throw new IOException();

        QueryParser parser = new QueryParser("texto", new StandardAnalyzer());
        TopDocs topDocs = null;
        try {
            topDocs = is.search(parser.parse(query),cutoff);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return new LuceneRanking(this.index,topDocs.scoreDocs);
    }
}
