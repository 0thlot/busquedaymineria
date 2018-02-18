package es.uam.eps.bmi.search.vsm;

import es.uam.eps.bmi.search.AbstractEngine;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.impl.IMPLDoc;
import es.uam.eps.bmi.search.ranking.impl.IMPLDocVector;
import es.uam.eps.bmi.search.ranking.impl.IMPLSearchRanking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 *
 * @author jorge
 * @author oscar
 */
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
        String[] palabras =  query.toLowerCase().split(" ");

        for(int docID = 0; docID < numeroDocumentos; docID++){
            IMPLDocVector doc = new IMPLDocVector(docID, numeroDocumentos,index.getModuloDoc(docID));
            for(String palabra:palabras){
                doc.añadirPalabra(index.getTermFreq(palabra, docID),index.getDocFreq(palabra));
            }
            vectorDoc.add(doc);
        }

        return new IMPLSearchRanking(index, listScores(cutoff));
    }



    /**
     *
     *
     * @return
     */
    private IMPLDoc[] listScores(int cutoff){

        int minimo = Math.min(cutoff,vectorDoc.size());
        IMPLDoc[] scores = new IMPLDoc[minimo];
        int i,num;

        vectorDoc.sort((v1,v2)-> Double.compare(v2.sumPuntuaciones(),v1.sumPuntuaciones()));

        for (i= 0,num=0; i<minimo;i++){
            double score=vectorDoc.get(i).sumPuntuaciones();
            if(score>0){
                scores[i]=new IMPLDoc(vectorDoc.get(i).getDocID(), score);
                num++;
            }
        }
        return  Arrays.copyOf(scores,num);
    }

}

