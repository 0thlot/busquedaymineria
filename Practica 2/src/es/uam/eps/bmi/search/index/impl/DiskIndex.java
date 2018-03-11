package es.uam.eps.bmi.search.index.impl;

import es.uam.eps.bmi.search.index.Config;
import es.uam.eps.bmi.search.index.NoIndexException;
import es.uam.eps.bmi.search.index.structure.PostingsList;
import es.uam.eps.bmi.search.index.structure.impl.ImplPostingList;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DiskIndex extends IndexBase<Integer> {

    private RandomAccessFile postingFile;

    public DiskIndex(String ruta) throws IOException {
        if(ruta==null || ruta.equals(""))
            throw new NoIndexException("Ruta esta vacia");
        load(ruta);
    }

    @Override
    public PostingsList getPostings(String term) throws IOException {

        postingFile.seek( this.listasPosting.get(term));
        return ImplPostingList.toList(postingFile.readLine());

    }

    @Override
    public void loadIndex(String ruta) throws IOException {

        try (BufferedReader br = Files.newBufferedReader(Paths.get(ruta + File.separator + Config.DICTIONARY_FILE))) {
            String[] aux;
            String s;
            while ((s=br.readLine())!=null){
                aux = s.split(" ");
                this.listasPosting.put(aux[0],Integer.parseInt(aux[1]));
            }
        }

        postingFile = new RandomAccessFile(ruta + File.separator + Config.POSTINGS_FILE,"r");

    }
}
