package es.uam.eps.bmi.search.index.impl;

import es.uam.eps.bmi.search.index.AbstractIndexBuilder;
import es.uam.eps.bmi.search.index.Config;
import es.uam.eps.bmi.search.index.structure.impl.ImplPostingList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class IndexBuilderBase extends AbstractIndexBuilder{

    protected String indexRuta;
    protected int docId=0;
    protected Map<String,ImplPostingList> postingMap = new HashMap<>();
    private OutputStream outRutas;

    @Override
    protected void indexText(String text, String path) throws IOException {

        for(String t: text.toLowerCase().split("\\P{Alpha}+")){
            if(!t.equals("") && !t.equals("-"))
                addTermPosting(t);
        }
        outRutas.write((path+"\n").getBytes());

        docId++;
    }

    private void addTermPosting(String t) {

        postingMap.putIfAbsent(t,new ImplPostingList());
        postingMap.get(t).add(docId) ;
    }

    @Override
    public void build(String collectionPath, String indexPath) throws IOException {
        indexRuta=indexPath;
        clear(indexPath);
        File collectioFile = new File(collectionPath);

        if (!collectioFile.exists()) {
            throw new IOException();
        }
        outRutas = new FileOutputStream(indexRuta+File.separator+ Config.PATHS_FILE);
        if (collectioFile.isDirectory()) {
           this.indexFolder(collectioFile);
        } else if (collectioFile.getName().endsWith(".zip")) {
           this.indexZip(collectioFile);
        } else if (collectioFile.getName().endsWith(".txt")) {
           this.indexURLs(collectioFile);
        } else {
           throw new IOException("Ruta de la coleccion no valida");
        }
        outRutas.close();

        this.saveIndex();
        this.saveDocNorms(indexPath);

    }

    protected abstract void saveIndex() throws IOException;
}
