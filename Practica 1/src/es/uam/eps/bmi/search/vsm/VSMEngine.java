package es.uam.eps.bmi.search.vsm;

import es.uam.eps.bmi.search.AbstractEngine;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.impl.IMPLDocVector;
import es.uam.eps.bmi.search.ranking.impl.IMPLSearchRanking;
import org.apache.lucene.search.ScoreDoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class VSMEngine extends AbstractEngine{

    private List<IMPLDocVector> vectorDoc;

    public VSMEngine(Index idx) {
        super(idx);
        vectorDoc = new ArrayList<>();
    }

    /**
     *
     * @param query
     * @param cutoff
     * @return
     * @throws IOException
     */
    @Override
    public IMPLSearchRanking search(String query, int cutoff) throws IOException {
        int numeroDocumentos = index.getIndexReader().numDocs();
        int palabrasConsulta = query.split(" ").length;
        int posPalabra = 0;

        //Generamos los vectores para cada documento
        for (String palabra : query.toLowerCase().split(" ")) {
            for (int docID = 0; docID < numeroDocumentos; docID++) { // Recorriendo la lista de postings de cada palabra
                long freq = index.getTermFreq(palabra, docID);
                if (freq > 0) {
                    int docIDexistente = isDocOnList(docID);    // Si Esta el documento en la lista de vectores
                    if (docIDexistente > -1) {                  // Actualizamos los datos del documento
                        vectorDoc.get(docIDexistente).añadirPalabra(posPalabra, freq, index.getDocFreq(palabra));
                    } else {                                    // Si no esta el documento, lo creamos de nuevo
                        IMPLDocVector doc = new IMPLDocVector(palabrasConsulta, docID, numeroDocumentos);
                        doc.añadirPalabra(posPalabra, freq, index.getDocFreq(palabra));
                        vectorDoc.add(doc);
                    }
                }
            }
            posPalabra += 1;
        }

        return new IMPLSearchRanking(index, listScores(cutoff));
    }

    /**
     *  Comprueba que el documento se encuentra en la lista
     *  de puntuaciones
     *
     * @param docID
     * @return posicion del documento, -1 si el documento no esta en la lista
     */
    private int isDocOnList(int docID){
        for (int i=0; i<vectorDoc.size();i++)
            if (vectorDoc.get(i).getDocID() == docID)
                return i;
        return -1;

    }

    /**
     *
     *
     * @return
     */
    private ScoreDoc[] listScores(int cutoff){

        int minimo = Math.min(cutoff,vectorDoc.size());
        ScoreDoc[] scores = new ScoreDoc[minimo];

        vectorDoc.sort((v1,v2)-> Float.compare(v2.modulo(),v1.modulo()));

        for (int i= 0; i<minimo;i++){
           scores[i]=new ScoreDoc(vectorDoc.get(i).getDocID(), vectorDoc.get(i).modulo());
        }
        return scores;
    }

}

