package es.uam.eps.bmi.search.index.structure.graph;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PageRankPosting {

    private int docID;
    private String docName;
    private List<Integer> in;
    private List<Integer> out;

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

    /**
     * TODO
     * @param docID
     */
    public void addIn(int docID){ in.add(docID); }

    /**
     * TODO
     * @param docID
     */
    public void addOut(int docID){ out.add(docID); }

    /**
     * TODO
     * @return
     */
    public int numIn(){ return in.size(); }

    /**
     * TODO
     * @return
     */
    public int numOut(){ return out.size(); }
}
