package es.uam.eps.bmi.search.index.impl;

import es.uam.eps.bmi.search.index.Config;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.structure.EditableDictionary;
import es.uam.eps.bmi.search.index.structure.impl.HashDictionary;
import es.uam.eps.bmi.search.index.structure.impl.PositionalDictionary;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PositionalIndexBuilder extends SerializedRAMIndexBuilder {

     /** Nuevo tipo de diccionario*/

    public PositionalIndexBuilder(){}

    @Override
    public void init(String indexPath) throws IOException {
        clear(indexPath);
        nDocs = 0;
        dictionary = new PositionalDictionary();
        docPaths = new ArrayList<>();
    }

    @Override
    public void indexText(String text, String path) throws IOException {
        int pos = 0;
        for (String term : text.toLowerCase().split("\\P{Alpha}+")) {
            ((PositionalDictionary) dictionary).add(term, nDocs, pos);
            pos++;
        }
        docPaths.add(path);
        nDocs++;
    }

    @Override
    protected Index getCoreIndex() {
        return new PositionalIndex(dictionary, nDocs);
    }


}
