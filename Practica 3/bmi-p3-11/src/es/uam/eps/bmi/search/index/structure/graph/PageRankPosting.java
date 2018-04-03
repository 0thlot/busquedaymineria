package es.uam.eps.bmi.search.index.structure.graph;

import java.util.ArrayList;
import java.util.List;

/** Clase PageRankPosting
 *
 * @author oscar
 * @author jorge
 */
public class PageRankPosting {

    private int docID;
    private String docName;
    private List<Integer> in;
    private List<Integer> out;

    /** Constructor de la clase
     *
     * @param docID id del documento
     * @param docName nombre del documento
     */
    public PageRankPosting(int docID, String docName){
        this.docName = docName;
        this.docID = docID;
        in = new ArrayList<>();
        out = new ArrayList<>();
    }
    public List<Integer> getIn(){ return in; }

    public List<Integer> getOut(){ return out; }

    public String getDocName() { return docName; }

    public int getDocID(){ return docID; }

    public boolean compare(PageRankPosting post){ return post.getDocName().equals(docName); }

    /** Añade un documento a lista de entrantes
     * @param docID ide del documento
     */
    public void addIn(int docID){ in.add(docID); }

    /** Añade un documento a lista de salientes
     * @param docID ide del documento
     */
    public void addOut(int docID){ out.add(docID); }

    /**
     * @return numero de documentos entrantes
     */
    public int numIn(){ return in.size(); }

    /**
     * @return numero de documentos salientes
     */
    public int numOut(){ return out.size(); }
}
