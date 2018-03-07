package es.uam.eps.bmi.search.vsm;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.PostingsList;
import es.uam.eps.bmi.search.index.structure.PostingsListIterator;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.impl.*;
import java.io.IOException;
import java.util.*;

public class TermBasedVSMEngine extends AbstractVSMEngine{

    public TermBasedVSMEngine(Index index) {
        super(index);
    }

    @Override
    public SearchRanking search(String query, int cutoff) throws IOException {
        int numeroDocumentos = index.numDocs();
        RankingImpl ranking = new RankingImpl(index, cutoff);
        HashMap<Integer, Double> hashMapDocs = new HashMap<>(); //Asocia documento y puntuacion
        String[] palabras = parse(query);

        for (String pa : palabras) {
            PostingsList pList = index.getPostings(pa); // Recorremos la lista de posting de cada palabra

            for(Posting post: pList) {
                //Posting post = pList.iterator().next();
                double puntuacion = tfidf(post.getFreq(), index.getDocFreq(pa), numeroDocumentos);
                if(!hashMapDocs.containsKey(post.getDocID())){
                    hashMapDocs.put(post.getDocID(), (double) 0);
                }
                hashMapDocs.replace(post.getDocID(),hashMapDocs.get(post.getDocID())+puntuacion);

            }
        }

        for (Integer docID: hashMapDocs.keySet()) {
            ranking.add(docID,hashMapDocs.get(docID)/index.getDocNorm(docID));
        }
        return ranking;
    }

}
