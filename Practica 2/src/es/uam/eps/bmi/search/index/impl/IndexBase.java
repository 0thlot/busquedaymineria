package es.uam.eps.bmi.search.index.impl;

import es.uam.eps.bmi.search.index.AbstractIndex;
import es.uam.eps.bmi.search.index.Config;
import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.PostingsList;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class IndexBase<V> extends AbstractIndex{

    protected String[] rutas;
    protected Map<String,V> listasPosting;

    @Override
    public int numDocs() {
        return rutas.length;
    }

    @Override
    public String getDocPath(int docID) throws IOException{
        return rutas[docID];
    }

    @Override
    public Collection<String> getAllTerms() throws IOException{
        return Collections.unmodifiableCollection(listasPosting.keySet());
    }

    @Override
    public long getTotalFreq(String term) throws IOException{
        long count=0;
        for (Posting p:getPostings(term)){
            count+=p.getFreq();
        }
        return count;
    }

    @Override
    public long getDocFreq(String term)  throws IOException  {
        PostingsList list = getPostings(term);
        return list.size();
    }

    protected void load(String ruta) throws IOException {

        this.listasPosting = new HashMap<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(ruta + File.separator + Config.PATHS_FILE))) {
            this.rutas = new String[(int)br.lines().parallel().count()];
        }
        try (BufferedReader br = Files.newBufferedReader(Paths.get(ruta + File.separator + Config.PATHS_FILE))) {
            final int[] c = {0};
            br.lines().forEach(s -> {
                this.rutas[c[0]++] = s;
            });
        }

        this.loadIndex(ruta);
        this.loadNorms(ruta);

    }

    public abstract void loadIndex(String ruta) throws IOException;
}
