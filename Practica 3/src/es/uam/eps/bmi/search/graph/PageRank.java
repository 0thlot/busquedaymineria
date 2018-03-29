package es.uam.eps.bmi.search.graph;

import es.uam.eps.bmi.search.SearchEngine;
import es.uam.eps.bmi.search.index.DocumentMap;
import es.uam.eps.bmi.search.index.structure.graph.PageRankPosting;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.impl.RankingImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class PageRank implements SearchEngine, DocumentMap{

    private PageRankBuilder builder;
    private double r_value;
    private double ir_value;
    private int num_iterations;
    private List<Double> scoresDocs;
    private List<PageRankPosting> docsInfo;

    /**
     *
     * @param graphPath
     * @param r
     * @param num_iterations
     */
    public PageRank(String  graphPath, double r, int num_iterations) {
        r_value = r;
        ir_value = 1-r;
        this.num_iterations = num_iterations;
        builder = new PageRankBuilder(graphPath); //O this.graphPath = graphPath
    }

    @Override
    public SearchRanking search(String query, int cutoff) throws IOException {
        /* Generar scores */
        docsInfo=builder.build();
        scoresDocs = makeScores();
        RankingImpl ranking = new RankingImpl(this, cutoff);
        for(int i = 0; i< scoresDocs.size(); i++)
            ranking.add(i, scoresDocs.get(i));
        return ranking;
    }

    @Override
    public DocumentMap getDocMap() {
        return this;
    }

    @Override
    public String getDocPath(int docID) throws IOException { return builder.getDocsInfo().get(docID).getDocName(); }

    @Override
    public double getDocNorm(int docID) throws IOException { return 0; }

    /**
     * TODO
     * @return
     */
    private List<Double> makeScores(){
        scoresDocs = new ArrayList<>(Collections.nCopies(builder.getNumDocs(),r_value/builder.getNumDocs()));
        /* Condicion de converguencia */
        for (int i=0; i<num_iterations; i++){
            /* Inicializacion de la tabla auxiliar de puntuaciones y la informacion de sumideros*/
            List<Double> score_aux = new ArrayList<>(Collections.nCopies(builder.getNumDocs(),r_value/builder.getNumDocs()));
            double add_sink= sink(scoresDocs);
            /*  Recorremos todos los nodos del indice para puntuarlos*/
            for(PageRankPosting doc: docsInfo)
                score_aux.set(doc.getDocID(), docScore(doc, add_sink, score_aux));
            Collections.copy(scoresDocs,score_aux); /* Guardamos los nuevos resultados */
        }
        return scoresDocs;
    }

    /**
     *
     * @param doc
     * @param sink
     * @param scores
     * @return
     */
    private double docScore(PageRankPosting doc, double sink, List<Double> scores){
        double neighborFrom_value=0;
        for (Integer docOut : doc.getIn())
            neighborFrom_value += scoresDocs.get(docOut)/docsInfo.get(docOut).numOut();
        return scores.get(doc.getDocID()) + ir_value*neighborFrom_value + sink;
    }

    /**
     * TODO
     * @param scores
     * @return
     */
    private double sink(List<Double> scores){
        double sink = 0;
        for (Double s: scores) sink +=s;
        return (1-sink)/builder.getNumDocs();
    }

}
