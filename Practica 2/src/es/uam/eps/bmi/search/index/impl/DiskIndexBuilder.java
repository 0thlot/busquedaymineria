package es.uam.eps.bmi.search.index.impl;


import es.uam.eps.bmi.search.index.Config;
import es.uam.eps.bmi.search.index.Index;


import java.io.*;
import java.nio.file.FileSystem;


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
            postingMap.forEach((k,v)->{
                try {
                    dicF.write((k+" "+ offset[0] +"\n").getBytes());
                    String auxListP = v.size() +" "+v+" ";
                    offset[0] += auxListP.length();
                    dicP.write((auxListP).getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
        }



    }
}
