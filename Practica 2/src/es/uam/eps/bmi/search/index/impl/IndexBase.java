package es.uam.eps.bmi.search.index.impl;

import es.uam.eps.bmi.search.index.AbstractIndex;
import es.uam.eps.bmi.search.index.Config;
import es.uam.eps.bmi.search.index.structure.Posting;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/** Clase abtracta base para SerializedRAMIndex y DiskIndex.
 * Concentra las funciones y variables comunes de las clases donde se implementan.
 *
 * Variables:
 *   - rutas:array de rutas de los documentos
 *   - listaPosting: diccionario de terminos y lista de postings

 * Funciones:
 *  - Todas aquellas que deben ser implementadas por AbstractIndex
 *  - load
 *  - cargaRutas
 *
 * @author oscar
 * @author jorge
 */
public abstract class IndexBase<V> extends AbstractIndex{

    protected String[] rutas;
    protected Map<String,V> listasPosting;

    /** Constuctor base de la clase */
    public IndexBase() {
    }

    /** Constructor que admite inicializar el diccionario por paramtro
     * y cargar la lista de rutas de documentos.
     *
     * @param ruta directorio a las rutas de los documentos.
     * @param listasPosting diccionario
     * @throws IOException error en lectura
     */
    public IndexBase(String ruta, Map<String, V> listasPosting) throws IOException {
        this.listasPosting = listasPosting;
        cargarRuta(ruta);
    }

    @Override
    public int numDocs() {
        return rutas.length;
    }

    @Override
    public String getDocPath(int docID) {
        return rutas[docID];
    }

    @Override
    public Collection<String> getAllTerms() {
        return Collections.unmodifiableCollection(listasPosting.keySet());
    }

    @Override
    public long getTotalFreq(String term) throws IOException{
        long count=0;
        for (Posting p:getPostings(term)){
            count+=p.getFreq();
        }
        return count;
    }

    @Override
    public long getDocFreq(String term)  throws IOException  {
        return getPostings(term).size();
    }

    /** Inicializa la lista de postings, el directorio de rutas y
     * la lista de normas del vector
     *
     * @param ruta directorio a los documentos a cargar
     * @throws IOException error en lectura
     */
    protected void load(String ruta) throws IOException {

        this.listasPosting = new HashMap<>();

        cargarRuta(ruta);

        this.loadIndex(ruta);
        this.loadNorms(ruta);

    }

    /** Carga la lista de rutas dado un directorio
     * que cotenga el archivo de informacion de rutas de documentos
     *
     * @param ruta directorio al documento de listas de rutas documentos
     * @throws IOException error en lectura
     */
    protected void cargarRuta(String ruta)  throws IOException{

        try (BufferedReader br = Files.newBufferedReader(Paths.get(ruta + File.separator + Config.PATHS_FILE))) {
            this.rutas = new String[(int)br.lines().parallel().count()];
        }
        try (BufferedReader br = Files.newBufferedReader(Paths.get(ruta + File.separator + Config.PATHS_FILE))) {

            String s;
            int c = 0;
            while ((s=br.readLine())!=null){
                this.rutas[c++] = s;
            }

        }
    }

    /** Carga el diccionario dado un directorio
     * que cotenga el archivo de informacion del diccionario
     *
     * @param ruta
     * @throws IOException error en lectura
     */
    public abstract void loadIndex(String ruta) throws IOException;
}
