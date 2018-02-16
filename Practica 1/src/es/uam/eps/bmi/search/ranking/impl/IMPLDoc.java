package es.uam.eps.bmi.search.ranking.impl;

/** Clase que representa un documento y su evaluacion
 * en la busqueda
 *
 * @author jorge
 * @author oscar
 */
public class IMPLDoc {

    int docID;
    float score;

    public IMPLDoc(int docID, float score){
        this.docID = docID;
        this.score = score;
    }

    public float getScore(){  return score; }

    public int getDocID(){  return docID; }
}
