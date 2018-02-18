package es.uam.eps.bmi.search.ranking.impl;

import java.util.ArrayList;
import java.util.List;

import static org.apache.lucene.search.similarities.SimilarityBase.log2;

/**
 *  Clase que funciona como vector de un documento
 *  en una busqueda
 *
 * @author jorge
 * @author oscar
 */
public class IMPLDocVector {
    private int docID;
    private int numIndexDocs;
    private Double moduloDoc;
    private List<Double> puntuaciones;

    /**
     *
     * @param docID
     * @param numIndexDocs
     */
    public IMPLDocVector( int docID, int numIndexDocs, Double moduloDoc){
        this.docID = docID;
        this.numIndexDocs = numIndexDocs;
        puntuaciones = new ArrayList<>();
        this.moduloDoc = moduloDoc;
    }

    /** Añade una nueva palabra encontrada en el documento
     * guardando su valoracion en el vector del documento
     * @param freqDoc Frecuencia de la palabra en el documento
     * @param freqIndex Frecuencia de la palabra en t0do el indice.
     */
    public void añadirPalabra(float freqDoc, float freqIndex){
        puntuaciones.add(tf(freqDoc)*idf(freqIndex));
    }

    /**
     * @return modulo del vector del documento
     */
    public double sumPuntuaciones(){
        return  (moduloDoc!=null)?puntuaciones.stream().mapToDouble(Double::doubleValue).sum()/moduloDoc:puntuaciones.stream().mapToDouble(Double::doubleValue).sum();
    }

    public int getDocID(){
        return docID;
    }

    /** Funcion tf
     * @param freqDoc Frecuencia de la palabra en el documento
     * @return resultado de la funcion
     */
    private double tf(double freqDoc){
        return  (freqDoc>0)?1+log2(freqDoc):0;
    }

    /** Funcion idf
     * @param freqIndex Frecuencia de la palabra en t0do el indice.
     * @return resultad de la funcion
     */
    private double idf(double freqIndex){
        return  (log2((double)1+(numIndexDocs/(1+freqIndex))));

    }
}