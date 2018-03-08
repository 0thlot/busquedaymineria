package es.uam.eps.bmi.search.index.impl;

import es.uam.eps.bmi.search.index.AbstractIndexBuilder;
import es.uam.eps.bmi.search.index.Config;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.impl.ImplPostingList;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerializedRAMIndexBuilder extends IndexBuilderBase implements Serializable{

    public SerializedRAMIndexBuilder(){}

    @Override
    protected Index getCoreIndex() throws IOException {
        return new SerializedRAMIndex(indexRuta);
    }

    @Override
    protected void saveIndex() throws IOException{
        try( ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(indexRuta+File.separator+Config.INDEX_FILE));
        ){
            out.writeObject(postingMap); //Guardamos las listas de postings
        }
    }
}
