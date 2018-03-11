package es.uam.eps.bmi.search.index.impl;

import es.uam.eps.bmi.search.index.Config;
import es.uam.eps.bmi.search.index.Index;

import java.io.*;

public class SerializedRAMIndexBuilder extends IndexBuilderBase implements Serializable{

    public SerializedRAMIndexBuilder(){}

    @Override
    protected Index getCoreIndex() throws IOException {
        return new SerializedRAMIndex(indexRuta,postingMap);
    }

    @Override
    protected void saveIndex() throws IOException{
        try( ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(indexRuta+File.separator+Config.INDEX_FILE))
        ){
            out.writeObject(postingMap); //Guardamos las listas de postings
        }
    }
}
