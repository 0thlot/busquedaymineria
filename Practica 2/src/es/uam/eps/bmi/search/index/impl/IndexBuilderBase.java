package es.uam.eps.bmi.search.index.impl;

import es.uam.eps.bmi.search.index.AbstractIndexBuilder;
import es.uam.eps.bmi.search.index.Config;
import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.impl.ImplPostingList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardOpenOption.APPEND;

public abstract class IndexBuilderBase extends AbstractIndexBuilder{

    protected String indexRuta;
    protected int docId=0;
    protected Map<String,ImplPostingList> postingMap = new TreeMap<>();

    @Override
    protected void indexText(String text, String path) throws IOException {

        List<String> terminos = Arrays.asList(text.toLowerCase().split("\\P{Alpha}+"));
        Set<String> terminosSet = new HashSet<>(terminos);

        for(String t: terminosSet){
            if(!t.equals("") && !t.equals("-"))
                addTermPosting(t,Collections.frequency(terminos,t));
        }
        Path ruta = Paths.get(indexRuta+File.separator+Config.PATHS_FILE);
        Files.write(ruta,(path+"\n").getBytes(),APPEND);


        docId++;
    }

    private void addTermPosting(String t, int frequency) {

        if(!postingMap.containsKey(t)){
            postingMap.put(t,new ImplPostingList());
        }

        ImplPostingList iPL = postingMap.get(t);
        iPL.add(new Posting(docId,frequency));

    }

    @Override
    public void build(String collectionPath, String indexPath) throws IOException {
        indexRuta=indexPath;
        clear(indexPath);
        File collectioFile = new File(collectionPath);

        if (!collectioFile.exists()) {
            throw new IOException();
        }

        File rutasFile = new File(indexRuta+File.separator+ Config.PATHS_FILE);
        rutasFile.createNewFile();

        if(collectioFile.isDirectory()){
            this.indexFolder(collectioFile);
        }else if(collectioFile.getName().endsWith(".zip")){
            this.indexZip(collectioFile);
        }else if(collectioFile.getName().endsWith(".txt")){
            this.indexURLs(collectioFile);
        }else {
            throw new IOException("Ruta de la coleccion no valida");
        }

        this.saveIndex();
        this.saveDocNorms(indexPath);

    }

    protected abstract void saveIndex() throws IOException;
}
