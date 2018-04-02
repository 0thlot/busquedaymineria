package es.uam.eps.bmi.search;

import es.uam.eps.bmi.search.index.DocumentMap;
import es.uam.eps.bmi.search.index.NoIndexException;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import es.uam.eps.bmi.search.ranking.impl.RankingImpl;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CombinedEngine implements SearchEngine {

    private SearchEngine[] searchArray;
    private double[] ponderacion;

    public CombinedEngine(SearchEngine[] searchArray, double[] ponderacion) throws NoIndexException{
        if(searchArray.length != ponderacion.length){
            throw new NoIndexException("El numero de indices y la ponderacion no coinciden");
        }
        this.searchArray = searchArray;
        this.ponderacion = ponderacion;
    }

    @Override
    public SearchRanking search(String query, int cutoff) throws IOException {
        Map<Integer,Double> ranking = new HashMap<>();
        int i=0;
        for(SearchEngine se: searchArray){
            SearchRanking aux = se.search(query, cutoff);
            Iterator<SearchRankingDoc> sourceIteratorMax =aux.iterator();

            Iterable<SearchRankingDoc> iterableMax = () -> sourceIteratorMax;
            Stream<SearchRankingDoc> targetStreammax = StreamSupport.stream(iterableMax.spliterator(), false);
            double max = targetStreammax.max(Comparator.reverseOrder()).orElseThrow(NoSuchElementException::new).getScore();

            Iterator<SearchRankingDoc> sourceIterator =aux.iterator();
            Iterable<SearchRankingDoc> iterable = () -> sourceIterator;
            Stream<SearchRankingDoc> targetStream = StreamSupport.stream(iterable.spliterator(), false);
            double min = targetStream.min(Comparator.reverseOrder()).orElseThrow(NoSuchElementException::new).getScore();
            for(SearchRankingDoc rank:aux){
                ranking.merge(rank.getDocID(),normalizaMinMax(rank.getScore(),min,max)*ponderacion[i],Double::sum);
            }
            i++;
        }
        RankingImpl aux = new RankingImpl(searchArray[0].getDocMap(),cutoff);
        ranking.forEach(aux::add);

        return aux;
    }

    private double normalizaMinMax(double score, double min, double max){
       return (score-min) /(max-min);
    }

    @Override
    public DocumentMap getDocMap() {
        return searchArray[0].getDocMap();
    }

    public String[] parse(String query) {
        return query.toLowerCase().split("\\P{Alpha}+");
    }
}
