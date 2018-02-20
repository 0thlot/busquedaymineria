package es.uam.eps.bmi.search.ui;

import es.uam.eps.bmi.search.ranking.SearchRankingDoc;

import java.io.IOException;

public class TextResultDocRenderer extends ResultsRenderer {

    private SearchRankingDoc result;
    public TextResultDocRenderer(SearchRankingDoc result) {
        this.result = result;
    }


    @Override
    public String toString() {
        String s = result.getScore() + "\t";
        try{
           s += result.getPath();
        }catch (IOException e){
            e.printStackTrace();
        }
        return s;
    }
}
