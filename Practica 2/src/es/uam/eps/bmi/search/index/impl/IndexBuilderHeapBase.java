package es.uam.eps.bmi.search.index.impl;

import es.uam.eps.bmi.search.index.AbstractIndexBuilder;
import es.uam.eps.bmi.search.index.Config;
import es.uam.eps.bmi.search.index.structure.impl.ImplPostingList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/** Clase abtracta base para SerializedRAMIndexBuilder y DiskIndexBuilder.
 * Concentra las funciones y variables comunes de las clases donde se implementan.
 * Ademas permite contruir un fichero heap.data donde se guarda la ley Heap del indice
 *
 * Variables:
 *   -indexRuta: directorio del indice
 *   -docId: id del documento (sirve para la construccion del indice)
 *   -postingMap: diccionario de terminos y lista de postings
 *   -outRutas: Fichero donde guardar las rutas de documentos
 *   -outHeap: Fichero donde guardar la ley de Heap
 *   -tam: Suma de tamaños de los documentos
 *
 * Funciones:
 *  - indexText
 *      Propia de la AbstractIndexBuilder.
 *      Añade todos los terminos del documento al diccionario y guarda la ruta del documento
 *
 *  - addTermPosting:
 *      (tiene documentacion propia)
 *
 * @author oscar
 * @author jorge
 */
public abstract class IndexBuilderHeapBase extends AbstractIndexBuilder {

    protected String indexRuta;
    protected int docId=0;
    protected Map<String,ImplPostingList> postingMap = new HashMap<>();
    private OutputStream outRutas;
    private OutputStream outHeap;
    private long tam =0;

    @Override
    protected void indexText(String text, String path) throws IOException {
        String[] split=text.toLowerCase().split("\\P{Alpha}+");
        for(String t: split){
            if(!t.equals("") && !t.equals("-"))
                addTermPosting(t);
        }
        outRutas.write((path+"\n").getBytes());
        tam+=split.length;
        outHeap.write((tam+"\t"+postingMap.keySet().size()+"\n").getBytes());

        docId++;
    }

    /** Añade un nuevo termino al diccionario, comprobando que
     * no este ya.
     * @param t termino en string
     */
    private void addTermPosting(String t) {

        postingMap.putIfAbsent(t,new ImplPostingList());
        postingMap.get(t).add(docId) ;
    }

    @Override
    public void build(String collectionPath, String indexPath) throws IOException {
        indexRuta=indexPath;
        clear(indexPath);
        File collectioFile = new File(collectionPath);

        if (!collectioFile.exists()) {
            throw new IOException();
        }
        outRutas = new FileOutputStream(indexRuta+File.separator+ Config.PATHS_FILE);
        outHeap = new FileOutputStream(indexRuta+File.separator+ "heap.dat");
        if (collectioFile.isDirectory()) {
            this.indexFolder(collectioFile);
        } else if (collectioFile.getName().endsWith(".zip")) {
            this.indexZip(collectioFile);
        } else if (collectioFile.getName().endsWith(".txt")) {
            this.indexURLs(collectioFile);
        } else {
            throw new IOException("Ruta de la coleccion no valida");
        }
        outRutas.close();
        outHeap.close();

        this.saveIndex();
        this.saveDocNorms(indexPath);

    }

    /** Guarda el indice en disco.
     * El formato del fichero depende de la implementacion.
     *
     * @throws IOException error en lectura
     */
    protected abstract void saveIndex() throws IOException;
}
