package es.uam.eps.bmi.search.vsm;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.PostingsList;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.impl.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TermBasedVSMEngine extends AbstractVSMEngine{

    public TermBasedVSMEngine(Index index) {
        super(index);
    }

    @Override
    public SearchRanking search(String query, int cutoff) throws IOException {
        int numeroDocumentos = index.numDocs();
        RankingImpl ranking = new RankingImpl(index, cutoff);
        String[] palabras = parse(query);

        for (String pa : palabras) {
            PostingsList pList = index.getPostings(pa); // Recorremos la lista de posting de cada palabra
            while (pList.iterator().hasNext()) {
                Posting post = pList.iterator().next();
                double puntuacion = tfidf(index.getTotalFreq(pa), post.getFreq(), numeroDocumentos);
                int posDoc = ranking.isDocInRanking(post.getDocID());

                if (posDoc >= 0) { // El documento ya esta creado, añadimos la puntuacion
                    ranking.addWordDoc(posDoc, puntuacion);
                } else {  // Creamos el documento y lo añadimos a la lista
                    ranking.add(post.getDocID(), puntuacion, index.getDocPath(post.getDocID()));
                }
            }
        }
        //Preparar ranking
        ranking.prepare();
        return ranking;
    }

}
