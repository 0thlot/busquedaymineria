package es.uam.eps.bmi.search.index.impl;

import es.uam.eps.bmi.search.index.Config;
import es.uam.eps.bmi.search.index.NoIndexException;
import es.uam.eps.bmi.search.index.structure.Dictionary;
import es.uam.eps.bmi.search.index.structure.impl.PositionalDictionary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class PositionalIndex extends SerializedRAMIndex {

    public PositionalIndex(String indexFolder) throws IOException{
        super(null,0);
        loadPaths(indexFolder);
        loadNorms(indexFolder);
        File f = new File(indexFolder + "/" + Config.INDEX_FILE);
        if (!f.exists()) throw new NoIndexException(indexFolder);
        try(  ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
            dictionary = (PositionalDictionary) in.readObject();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public PositionalIndex(Dictionary dic, int nDocs) {
        super(dic, nDocs);
    }

}