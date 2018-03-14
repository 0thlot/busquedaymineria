package es.uam.eps.bmi.search.index.structure.impl;

import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.PostingsList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImplPostingList implements PostingsList, Serializable {

    private List<Posting> pList;

    public ImplPostingList(){
        pList = new ArrayList<>();
    }

    public int size(){ return pList.size(); }

    public void add(Posting posting){ pList.add(posting); }

    public void add(int docId){
        if(!pList.isEmpty() && docId == pList.get(pList.size()-1).getDocID()){
            pList.get(pList.size()-1).add1();
        }else{
            pList.add(new Posting(docId,1));
        }
    }

    public boolean contains(Posting post){ return pList.contains(post); }

    @Override
    public Iterator<Posting> iterator() {

        return new ImplPostingListIterator(pList);
    }

    @Override
    public String toString() {
        StringBuilder aux = new StringBuilder();

        for(Posting p:pList){
            aux.append(p.getDocID()).append(" ").append(p.getFreq()).append(" ");
        }
        aux.deleteCharAt(aux.length()-1);

        return aux.toString();
    }

    public static PostingsList toList(String postingList) {
        ImplPostingList aux = new ImplPostingList();
        String[] postings = postingList.split(" ");
        for(int i = 0;i<postings.length;i+=2){
            aux.add(new Posting(Integer.parseInt(postings[i]),Long.parseLong(postings[i+1])));
        }
        return aux;
    }

}
