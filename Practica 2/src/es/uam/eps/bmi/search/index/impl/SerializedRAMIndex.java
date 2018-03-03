package es.uam.eps.bmi.search.index.impl;

import es.uam.eps.bmi.search.index.AbstractIndex;
import es.uam.eps.bmi.search.index.Config;
import es.uam.eps.bmi.search.index.NoIndexException;
import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.PostingsList;
import es.uam.eps.bmi.search.index.structure.impl.ImplPosting;
import es.uam.eps.bmi.search.index.structure.impl.ImplPostingList;


import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SerializedRAMIndex extends AbstractIndex {
    private String indexPath; //Directorio de construccion del indice
    private HashMap<String, ImplPostingList> diccionario;
    private List<ImplDoc> documents;
    private int numDocs;


    public SerializedRAMIndex(String indexPath) throws IOException{

        //Leer el fichero serializado de Builder
        initFromFile(indexPath);
        loadNorms(indexPath);
    }

    @Override
    public int numDocs() {
        return numDocs;
    }

    @Override
    public PostingsList getPostings(String term) throws IOException {
        return diccionario.get(term);
    }

    @Override
    public Collection<String> getAllTerms() throws IOException {
        return diccionario.keySet();
    }

    @Override
    public long getTotalFreq(String term) throws IOException {
        long freq=0;

        for (Iterator<Posting> it = diccionario.get(term).iterator(); it.hasNext(); ) { freq += it.next().getFreq(); }

        return freq;
    }

    @Override
    public long getDocFreq(String term) throws IOException {
        return (long) diccionario.get(term).size();
    }

    @Override
    public String getDocPath(int docID) throws IOException {
        return documents.get(docID).getPath();
    }

    private void initFromFile(String indexPath){
        SerializedRAMIndexBuilder builder;
        try{
            FileInputStream fileIn = new FileInputStream(indexPath+Config.INDEX_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            builder = (SerializedRAMIndexBuilder) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        diccionario = builder.getDiccionario();
        documents = builder.getDocuments();
        numDocs = builder.getNumDocs();
    }
}
