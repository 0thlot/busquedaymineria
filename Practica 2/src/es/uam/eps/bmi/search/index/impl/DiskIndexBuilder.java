package es.uam.eps.bmi.search.index.impl;

import es.uam.eps.bmi.search.index.AbstractIndexBuilder;
import es.uam.eps.bmi.search.index.Config;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.impl.ImplPostingList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DiskIndexBuilder extends AbstractIndexBuilder {
    private String indexRuta;
    private int docId=0;
    private Map<String,ImplPostingList> postingMap;

    public DiskIndexBuilder() {
        super();
        postingMap = new TreeMap<>();
    }

    @Override
    protected void indexText(String text, String path) throws IOException {

        List<String> terminos = Arrays.asList(text.replaceAll("[^A-Za-z0-9 ]", " ").toLowerCase().split(" "));
        Set<String> terminosSet = new HashSet<>(terminos);

        for(String t: terminosSet){
            addTermPosting(t,Collections.frequency(terminos,t));
        }
        Path ruta = Paths.get(indexRuta+System.lineSeparator()+Config.PATHS_FILE);
        try (BufferedWriter w = Files.newBufferedWriter(ruta)) {
            w.write(path);
            w.newLine();
        }

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
    protected Index getCoreIndex() throws IOException {
        return new DiskIndex(indexRuta);
    }

    @Override
    public void build(String collectionPath, String indexPath) throws IOException {
        indexRuta=indexPath;
        clear(indexPath);
        File collectioFile = new File(collectionPath);

        if (!collectioFile.exists()) {
            throw new IOException();
        }

        File rutasFile = new File(indexRuta+System.lineSeparator()+Config.PATHS_FILE);
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

    private void saveIndex() throws IOException {

        try(
                OutputStream dicF = new FileOutputStream(indexRuta+System.lineSeparator() + Config.DICTIONARY_FILE);
                OutputStream dicP = new FileOutputStream(indexRuta+System.lineSeparator() + Config.POSTINGS_FILE);
        ){
            int offset = 0;
            postingMap.forEach((k,v)->{
                dicF.write(k+" "+offset+"\n");


            });
        }



    }
}
