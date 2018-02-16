package es.uam.eps.bmi.search.ranking.impl;
import java.lang.Math;

import static java.lang.Math.log;
import static java.lang.Math.sqrt;

public class IMPLDocVector {
    private int docID;
    private int tamanio;
    private int numIndexDocs;
    private double[] puntuaciones;

    public IMPLDocVector(int tamanio, int docID, int numIndexDocs){
        this.tamanio = tamanio;
        this.docID = docID;
        puntuaciones = new double[tamanio];
    }

    public void añadirPalabra(int posPalabra, float freqDoc, float freqIndex){
        puntuaciones[posPalabra] = td(freqDoc)-idf(freqIndex);
    }

    public double modulo(){
        double modulo=0;
        for (int i=0; i<tamanio;i++){
            modulo += puntuaciones[i];
        }
        return sqrt(modulo);
    }

    public int getDocID(){
        return docID;
    }

    private double td(float freqDoc){
        return 1+log(freqDoc);
    }

    private double idf(float freqIndex){
        return log((double) numIndexDocs/freqIndex);

    }
}