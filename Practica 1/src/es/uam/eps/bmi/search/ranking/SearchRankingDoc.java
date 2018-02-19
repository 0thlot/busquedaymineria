package es.uam.eps.bmi.search.ranking;

import org.apache.lucene.search.ScoreDoc;

import java.io.IOException;

public abstract class SearchRankingDoc implements Comparable<SearchRankingDoc> {

    private double score;


    public SearchRankingDoc() {
    }

    public SearchRankingDoc(ScoreDoc score) {
        this.score = score.score;
    }

    @Override
    public int compareTo(SearchRankingDoc o) {

        return Double.compare(this.score, o.score);
    }

     public abstract double getScore() ;


    public abstract String getPath() throws IOException;

}
