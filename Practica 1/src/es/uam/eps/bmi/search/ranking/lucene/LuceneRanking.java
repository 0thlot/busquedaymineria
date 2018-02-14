package es.uam.eps.bmi.search.ranking.lucene;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import org.apache.lucene.search.ScoreDoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class LuceneRanking implements SearchRanking{

    List<SearchRankingDoc> docs;

    public LuceneRanking(Index index, ScoreDoc[] scores) throws IOException {
        docs = new ArrayList<>();
        for(ScoreDoc score: scores){
            docs.add(new SearchRankingDoc(score,index.getDocPath(score.doc)));
        }
    }

    @Override
    public int size() {
        return docs.size();
    }

    @Override
    public Iterator<SearchRankingDoc> iterator() {
        return docs.iterator();
    }

    @Override
    public void forEach(Consumer<? super SearchRankingDoc> action) {
        docs.forEach(action);
    }

    @Override
    public Spliterator<SearchRankingDoc> spliterator() {
        return docs.spliterator();
    }
}
