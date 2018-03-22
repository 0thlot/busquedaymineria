package es.uam.eps.bmi.search.index.impl;

import es.uam.eps.bmi.search.index.structure.EditableDictionary;
import es.uam.eps.bmi.search.index.structure.impl.HashDictionary;
import es.uam.eps.bmi.search.index.structure.impl.PositionalDictionary;

import java.io.IOException;
import java.util.ArrayList;

public class PositionalIndexBuilder extends SerializedRAMIndexBuilder {

    PositionalDictionary dictionary; /** Nuevo tipo de diccionario*/

    public PositionalIndexBuilder(){}

    public void init(String indexPath) throws IOException {
        clear(indexPath);
        nDocs = 0;
        dictionary = new PositionalDictionary();
        docPaths = new ArrayList<String>();
    }

    public void indexText(String text, String path) throws IOException {
        int pos = 0;
        for (String term : text.toLowerCase().split("\\P{Alpha}+")) {
            dictionary.add(term, nDocs, pos);
            pos++;
        }
        docPaths.add(path);
        nDocs++;
    }


}
