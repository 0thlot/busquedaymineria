package es.uam.eps.bmi.search.index.impl;


import es.uam.eps.bmi.search.index.Config;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.structure.impl.ImplPostingList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;


public class DiskIndexBuilder extends IndexBuilderBase {


    public DiskIndexBuilder() {
        super();
    }

    @Override
    protected Index getCoreIndex() throws IOException {
        return new DiskIndex(indexRuta);
    }

    @Override
    protected void saveIndex() throws IOException {

        try(
                OutputStream dicF = new FileOutputStream(indexRuta+ File.separator + Config.DICTIONARY_FILE);
                OutputStream dicP = new FileOutputStream(indexRuta+File.separator + Config.POSTINGS_FILE);
        ){
            int offset = 0;
            String auxListP;
            for(Map.Entry entry:postingMap.entrySet()){
                dicF.write(String.format("%s %d\n", entry.getKey(), offset).getBytes());
                auxListP =entry.getValue()+"\n";
                offset += auxListP.length();
                dicP.write(auxListP.getBytes());
            }
        }



    }
}
