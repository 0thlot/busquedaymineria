package es.uam.eps.bmi.search.index.impl;

import es.uam.eps.bmi.search.index.Config;
import es.uam.eps.bmi.search.index.NoIndexException;
import es.uam.eps.bmi.search.index.structure.PostingsList;
import es.uam.eps.bmi.search.index.structure.impl.ImplPostingList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

/** Clase encargada de gestionar el indice generado en memoria RAM
 *
 * @author oscar
 * @author jorge
 */
public class SerializedRAMIndex extends IndexBase<ImplPostingList> {

    /** Constructor de la clase que carga el diccionario dado una ruta
     *
     * @param ruta directorio del indice
     * @throws IOException error en lectura
     */
    public SerializedRAMIndex(String ruta) throws IOException{
        super();
        if(ruta==null || ruta.equals(""))
            throw new NoIndexException("Ruta esta vacia");
        load(ruta);
    }

    /** Constructor de la clase que carga el diccionario dado
     * directamente por paramtero
     *
     * @param ruta directorio a los documentos
     * @param dic diccionario
     * @throws IOException error en lectura
     */
    public SerializedRAMIndex(String ruta, Map<String,ImplPostingList> dic) throws IOException{
        super(ruta,dic);
    }

    @Override
    public PostingsList getPostings(String term) {
        return listasPosting.get(term);
    }

    @Override
    public void loadIndex(String ruta) {
        try(ObjectInputStream in = new ObjectInputStream( new FileInputStream(ruta+File.separator+Config.INDEX_FILE))){
                listasPosting = (Map<String,ImplPostingList>) in.readObject();
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
