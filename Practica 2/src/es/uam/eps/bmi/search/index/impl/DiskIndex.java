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

    private String ruta;

    public DiskIndex(String ruta) throws IOException {
        if(ruta==null || ruta.equals(""))
            throw new NoIndexException("Ruta esta vacia");
        this.ruta = ruta;
        load(ruta);
    }



    @Override
    public PostingsList getPostings(String term) throws IOException {

        try(RandomAccessFile postingFile = new RandomAccessFile(ruta + File.separator + Config.POSTINGS_FILE,"r")) {
            postingFile.seek( this.listasPosting.get(term));
            return ImplPostingList.toList(postingFile.readLine());
        }
    }

    @Override
    public void loadIndex(String ruta) throws IOException {

        try (BufferedReader br = Files.newBufferedReader(Paths.get(ruta + File.separator + Config.DICTIONARY_FILE))) {
            br.lines().forEach(s->{
                String[] aux = s.split(" ");
               this.listasPosting.put(aux[0],Integer.parseInt(aux[1]));
            });
        }

    }
}
