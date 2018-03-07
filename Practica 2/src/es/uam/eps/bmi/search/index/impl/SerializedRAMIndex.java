package es.uam.eps.bmi.search.index.impl;

import es.uam.eps.bmi.search.index.Config;
import es.uam.eps.bmi.search.index.NoIndexException;
import es.uam.eps.bmi.search.index.structure.PostingsList;
import es.uam.eps.bmi.search.index.structure.impl.ImplPostingList;


import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class SerializedRAMIndex extends IndexBase<ImplPostingList> {


    public SerializedRAMIndex(String ruta) throws IOException{
        if(ruta==null || ruta.equals(""))
            throw new NoIndexException("Ruta esta vacia");
        load(ruta);
    }

    @Override
    public PostingsList getPostings(String term) throws IOException {
        return listasPosting.get(term);
    }

    @Override
    public void loadIndex(String ruta) throws IOException {
        try(FileInputStream fileIn = new FileInputStream(ruta+File.separator+Config.INDEX_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn)){
                listasPosting = (Map<String,ImplPostingList>) in.readObject();
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
