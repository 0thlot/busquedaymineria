package es.uam.eps.bmi.search.graph;

import es.uam.eps.bmi.search.index.structure.graph.PageRankPosting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Clase de PageRankBuilder
 *
 * @author oscar
 * @author jorge
 */
public class PageRankBuilder {

    private int numDocs=0;
    private String graphPath;
    private List<PageRankPosting> docsInfo;
    private Map<String,Integer> dic;

    /**Constructor de la clase
     *
     * @param graphPath cadena que contiene la ubicacion del grafo
     */
    public PageRankBuilder(String graphPath){
        this.graphPath = graphPath;
        docsInfo = new ArrayList<>();
        dic = new HashMap<>();
    }

    public int getNumDocs(){ return numDocs; }

    /** Constructor del indice
     *
     * @return una lista de postings con la informacion de los documentos
     */
    public List<PageRankPosting> build(){
        try {
            for (java.lang.String line: Files.readAllLines(Paths.get(graphPath))){
                readLine(line.split("[ \t]"));
            }
        } catch(IOException e){
            System.out.print("Error al abrir el fichero de grafos");
            e.printStackTrace();
        }
        /* Construir indice o pasar estructuras */
        return docsInfo;
    }

    public List<PageRankPosting> getDocsInfo() { return docsInfo; }

    /** Procesa la informacion contenida en una linea del fichero
     * @param nodes array de tamaño 2 con el nodo entrante y el nodo saliente
     */
    private void readLine(String[] nodes){
        if (nodes.length != 2) return;
        int id_node1 = addNode(nodes[0]);
        int id_node2 = addNode(nodes[1]);
        docsInfo.get(id_node1).addOut(id_node2);
        docsInfo.get(id_node2).addIn(id_node1);
    }

    /** Añade un nodo al diccionario y lo crea si no existe
     *
     * @param name nombre del nodo del documento
     * @return id del documento
     */
    private int addNode(String name){
        try {
            return dic.get(name);
        }catch (NullPointerException e){
            PageRankPosting post = new PageRankPosting(numDocs, name);
            docsInfo.add(post);
            dic.put(name, numDocs);
            int id = numDocs;
            numDocs++;
            return id;
        }
    }
}
