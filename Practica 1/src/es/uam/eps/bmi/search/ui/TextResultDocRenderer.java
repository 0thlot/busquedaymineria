package es.uam.eps.bmi.search.ui;

import es.uam.eps.bmi.search.ranking.SearchRankingDoc;

public class TextResultDocRenderer extends ResultsRenderer {

    private SearchRankingDoc result;
    public TextResultDocRenderer(SearchRankingDoc result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return result.toString();
    }
}
