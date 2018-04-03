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
/** Clase que combina varios motores de busqueda
 *
 * @author oscar
 * @author jorge
 */
public class CombinedEngine implements SearchEngine {

    private SearchEngine[] searchArray;
    private double[] ponderacion;

    /**
     * Es necesario que los dos parametros tengan el mismo tamaño
     * @param searchArray Array de motores de busqueda
     * @param ponderacion Array con la ponderacion de cada motor
     * @throws NoIndexException Cuando no tienen el mismo tamaño
     */
    public CombinedEngine(SearchEngine[] searchArray, double[] ponderacion) throws NoIndexException{
        if(searchArray.length != ponderacion.length){
            throw new NoIndexException("El numero de indices y la ponderacion no coinciden");
        }
        this.searchArray = searchArray;
        this.ponderacion = ponderacion;
    }

    @Override
    public SearchRanking search(String query, int cutoff) throws IOException {
        Map<String,Double> ranking = new HashMap<>();
        DocumentMapImpl index = new DocumentMapImpl();
        Map<String,Integer> mapPath = new HashMap<>();

        int i=0,j=0;
        //Calculamos el resultado de cada motor
        for(SearchEngine se: searchArray){
            SearchRanking aux = se.search(query, Integer.MAX_VALUE);
            Iterator<SearchRankingDoc> sourceIteratorMax =aux.iterator();

            Iterable<SearchRankingDoc> iterableMax = () -> sourceIteratorMax;
            Stream<SearchRankingDoc> targetStreammax = StreamSupport.stream(iterableMax.spliterator(), false);
            double max = targetStreammax.max(Comparator.reverseOrder()).orElseThrow(NoSuchElementException::new).getScore();

            Iterator<SearchRankingDoc> sourceIterator =aux.iterator();
            Iterable<SearchRankingDoc> iterable = () -> sourceIterator;
            Stream<SearchRankingDoc> targetStream = StreamSupport.stream(iterable.spliterator(), false);
            double min = targetStream.min(Comparator.reverseOrder()).orElseThrow(NoSuchElementException::new).getScore();

            //Agrupamos los distintos resultados de los motores
            for(SearchRankingDoc rank:aux){
                if(!ranking.containsKey(rank.getPath())){
                    index.put(j,rank.getPath());
                    mapPath.put(rank.getPath(),j);
                    j++;
                }
                //Realizamos la combinacion lineal
                ranking.merge(rank.getPath(),normalizaMinMax(rank.getScore(),min,max)*ponderacion[i],Double::sum);
            }
            i++;
        }

        RankingImpl aux = new RankingImpl(index,cutoff);
        ranking.forEach((k,v)->{
            aux.add(mapPath.get(k),v);
        });

        return aux;
    }

    /**
     *
     * @param score puntutacion del Doc
     * @param min puntuacion mas baja en la busqueda
     * @param max puntuacion mas alta en la busqueda
     * @return puntuacion normalizada
     */
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

    /**
     * Clase interna que simula un indice, necesario para el correcto funcionamiento
     */
    private class DocumentMapImpl implements DocumentMap{

        private Map<Integer,String> map;

        public DocumentMapImpl() {
            this.map = new HashMap<>();
        }

        /**
         * Añade un nuevo documento
         * @param docId del documento
         * @param path Ruta
         */
        public void put(Integer docId,String path){
            map.put(docId,path);
        }

        @Override
        public String getDocPath(int docID) throws IOException {
            return map.get(docID);
        }

        @Override
        public double getDocNorm(int docID) throws IOException {
            return 0;
        }
    }
}
