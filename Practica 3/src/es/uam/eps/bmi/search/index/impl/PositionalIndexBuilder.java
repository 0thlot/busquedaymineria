package es.uam.eps.bmi.search.index.impl;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.structure.impl.PositionalDictionary;

import java.io.IOException;
import java.util.ArrayList;

/** Clase PositionalIndexBuilder
 *
 * @author oscar
 * @author jorge
 */
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
