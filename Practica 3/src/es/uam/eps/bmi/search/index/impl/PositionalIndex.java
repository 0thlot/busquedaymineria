package es.uam.eps.bmi.search.index.impl;

import es.uam.eps.bmi.search.index.structure.Dictionary;

import java.io.IOException;

public class PositionalIndex extends SerializedRAMIndex {

    public PositionalIndex(String indexFolder) throws IOException{
        super(indexFolder);
    }

    public PositionalIndex(Dictionary dic, int nDocs) {
        super(dic, nDocs);
    }

}
