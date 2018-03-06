package es.uam.eps.bmi.search.index.impl;


import es.uam.eps.bmi.search.index.Config;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.structure.impl.ImplPostingList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


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
            final int[] offset = {0};
            postingMap.forEach((String k, ImplPostingList v) ->{
                try {
                    dicF.write(String.format("%s %d\n", k, offset[0]).getBytes());
                    String auxListP =v+"\n";
                    // +1 para el \n
                    offset[0] += auxListP.length();
                    dicP.write(auxListP.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
        }



    }
}
