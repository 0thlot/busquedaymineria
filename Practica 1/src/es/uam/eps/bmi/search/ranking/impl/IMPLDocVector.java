package es.uam.eps.bmi.search.ranking.impl;
import static java.lang.Math.log;
import static java.lang.Math.sqrt;

/**
 *  Clase que funciona como vector de un documento
 *  en una busqueda
 *
 * @author jorge
 * @author oscar
 */
public class IMPLDocVector {
    private int docID;
    private int tamanio;
    private int numIndexDocs;
    private double[] puntuaciones;

    public IMPLDocVector(int tamanio, int docID, int numIndexDocs){
        this.tamanio = tamanio;
        this.docID = docID;
        this.numIndexDocs = numIndexDocs;
        puntuaciones = new double[tamanio];
    }

    /** Añade una nueva palabra encontrada en el documento
     * guardando su valoracion en el vector del documento
     * @param posPalabra Posicion de la palabra en la busqueda
     * @param freqDoc Frecuencia de la palabra en el documento
     * @param freqIndex Frecuencia de la palabra en t0do el indice.
     */
    public void añadirPalabra(int posPalabra, float freqDoc, float freqIndex){
        puntuaciones[posPalabra] = td(freqDoc)-idf(freqIndex);
    }

    /**
     * @return modulo del vector del documento
     */
    public float modulo(){
        float modulo=0;
        for (int i=0; i<tamanio;i++){
            modulo += puntuaciones[i];
        }
        return (float) sqrt(modulo);
    }

    public int getDocID(){
        return docID;
    }

    /** Funcion td
     * @param freqDoc Frecuencia de la palabra en el documento
     * @return resultado de la funcion
     */
    private float td(float freqDoc){
        return  1+(float)log(freqDoc);
    }

    /** Funcion idf
     * @param freqIndex Frecuencia de la palabra en t0do el indice.
     * @return resultad de la funcion
     */
    private float idf(float freqIndex){
        return (float)log((float) numIndexDocs/freqIndex);

    }
}