package es.uam.eps.bmi.search.index.impl;

import es.uam.eps.bmi.search.index.Config;
import es.uam.eps.bmi.search.index.NoIndexException;
import es.uam.eps.bmi.search.index.structure.PostingsList;
import es.uam.eps.bmi.search.index.structure.impl.ImplPostingList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

public class SerializedRAMIndex extends IndexBase<ImplPostingList> {


    public SerializedRAMIndex(String ruta) throws IOException{
        if(ruta==null || ruta.equals(""))
            throw new NoIndexException("Ruta esta vacia");
        load(ruta);
    }

    @Override
    public PostingsList getPostings(String term) {
        return listasPosting.get(term);
    }

    @Override
    public void loadIndex(String ruta) {
        try(ObjectInputStream in = new ObjectInputStream( new FileInputStream(ruta+File.separator+Config.INDEX_FILE))){
                listasPosting = (Map<String,ImplPostingList>) in.readObject();
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
