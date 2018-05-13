package es.uam.eps.bmi.sna.test;

import agape.tutorials.UndirectedGraphFactoryForStringInteger;
import agape.visu.Visualization;
import edu.uci.ics.jung.graph.Graph;
import agape.generators.RandGenerator;


import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author oscar
 * @author jorge
 */
public class MakeGraphs {

    private static int nodes = 10; /** Numero de nodos del grafo*/
    private static int edges = 7; /** Numero de enlaces minimos en Barabasi-Albert*/
    private static int steps = 5; /** Numero de iteraciones en Barabasi-Albert*/
    private static double p = 0.5; /** Factor de dispersion en Erdos Renyi*/

    public static void main(String[] args) throws Exception {

        UndirectedGraphFactoryForStringInteger factory = new UndirectedGraphFactoryForStringInteger();
        UndirectedGraphFactoryForStringInteger factory2 = new UndirectedGraphFactoryForStringInteger();
        Graph<String, Integer> erdos = RandGenerator.generateErdosRenyiGraph(
                factory,
                factory.vertexFactory,
                factory.edgeFactory,
                nodes,
                p);
        Graph<String, Integer> barabasi = RandGenerator.generateBarabasiAlbertGraph(
                factory2,
                factory2.vertexFactory,
                factory2.edgeFactory,
                nodes, edges, steps);//nunca menos nodos que enlaces

        //Imprimir los grafos erdos
        printGraph("erdos", erdos);
        printGraphOnScreen("erdos", erdos);
        printGraph("barabasi", barabasi);
        printGraphOnScreen("barabasi", barabasi);
        //Visualization.showGraph(erdos); //Imprpesion por pantalla de los grafos.
        //Visualization.showGraph(barabasi);
    }


    private static void printGraph(String filename, Graph<String, Integer> graph){
        Map<String, Integer> bd = new HashMap<>();
        int cont = 1;

        try {
            PrintWriter writer = new PrintWriter("graph/"+filename+".csv");
            Iterator iter = graph.getVertices().iterator();
            while (iter.hasNext()){
                String node = (String) iter.next();
                if(!bd.keySet().contains(node)){
                    bd.put(node, cont);
                    cont++;
                }
                int id_node = bd.get(node);
                if (graph.getNeighbors(node)!=null)
                    for (String nodeSon: graph.getNeighbors(node)){
                        if(!bd.keySet().contains(nodeSon)) {
                            bd.put(nodeSon, cont);
                            cont++;
                        }
                        int id_nodeSon = bd.get(nodeSon);
                        writer.println(id_node+","+id_nodeSon);

                    }

            }
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void printGraphOnScreen(String filename, Graph<String, Integer> graph){
        System.out.print("Grafo de "+filename+": \n");
        Map<String, Integer> bd = new HashMap<>();
        int cont = 1;

        Iterator b = graph.getVertices().iterator();
        while (b.hasNext()){
            String i = (String) b.next();
            if(!bd.keySet().contains(i)) {
                bd.put(i, cont);
                cont++;
            }

            int identificador_padre = bd.get(i);
            System.out.print("\tNodo "+identificador_padre+ ":\n");
            if (graph.getNeighbors(i)!=null)
                for (String j: graph.getNeighbors(i)) {
                    if(!bd.keySet().contains(j)) {
                        bd.put(j, cont);
                        cont++;
                    }
                    int identificador_hijo = bd.get(j);
                    System.out.print("\t\tP: " + identificador_padre + "\tH: " + identificador_hijo + "\tE: " + (-1) * graph.findEdge(i, j) + "\n");
                }
        }
    }
}
