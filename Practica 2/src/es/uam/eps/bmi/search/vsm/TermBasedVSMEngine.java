package es.uam.eps.bmi.search.vsm;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.PostingsList;
import es.uam.eps.bmi.search.index.structure.lucene.LucenePostingsList;
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
        List<IMPLDocVector> documents = new ArrayList<>();
        int numeroDocumentos = index.numDocs();
        String[] palabras =  parse(query);

        for(String pa : palabras){
            PostingsList pList = index.getPostings(pa); // Recorremos la lista de posting de cada palabra
            while (pList.iterator().hasNext()){
                Posting post = pList.iterator().next();
                double puntuacion = tfidf(index.getTotalFreq(pa), post.getFreq(),numeroDocumentos);

                if (docOnList(post.getDocID(),documents)){ // El documento ya esta creado, añadimos la puntuacion
                    documents.get(post.getDocID()).anyadirPalabra(puntuacion);
                }else{  // Creamos el documento y lo añadimos a la lista
                    IMPLDocVector dv = new IMPLDocVector(post.getDocID(), index.getDocPath(post.getDocID()));
                    dv.anyadirPalabra(puntuacion);
                    documents.add(dv);
                }
            }
        }
        return new IMPLSearchRanking(index, listScores(documents, cutoff));
    }

    /**
     *
     * @param vectorDoc
     * @param cutoff
     * @return
     */
    private IMPLDocVector[] listScores(List<IMPLDocVector> vectorDoc, int cutoff){
        int minimo = Math.min(cutoff,vectorDoc.size());
        IMPLDocVector[] scores = new IMPLDocVector[minimo];
        int i,num;

        vectorDoc.sort((v1,v2)-> Double.compare(v2.getScore(),v1.getScore()));

        for (i= 0,num=0; i<minimo;i++){
            double score=vectorDoc.get(i).getScore();
            if(score>0){
                scores[i]=vectorDoc.get(i);
                num++;
            }
        }
        return  Arrays.copyOf(scores,num);
    }

    /**
     *
     * @param docID
     * @param documents
     * @return
     */
    private boolean docOnList(int docID, List<IMPLDocVector> documents){
        while (documents.iterator().hasNext()){
            if (documents.iterator().next().getDocID()==docID) return true;
        }
        return false;
    }
}
