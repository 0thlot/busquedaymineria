package es.uam.eps.bmi.sna.test;

import agape.tutorials.UndirectedGraphFactoryForStringInteger;
import agape.visu.Visualization;
import edu.uci.ics.jung.algorithms.generators.random.ErdosRenyiGenerator;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import org.apache.commons.collections15.Factory;
import agape.generators.RandGenerator;


import java.io.*;
import java.util.HashSet;
import java.util.Iterator;

public class MakeGraphs {

    private static int nodes = 10;
    private static int edges = 10;
    private static int steps = 10;
    private static double p = 0.5;

    public static void main(String[] args) throws Exception {

        UndirectedGraphFactoryForStringInteger factory = new UndirectedGraphFactoryForStringInteger();
        Graph<String, Integer> erdos = RandGenerator.generateErdosRenyiGraph(
                factory,
                factory.vertexFactory,
                factory.edgeFactory,
                nodes,
                p);
        Graph<String, Integer> barabasi = RandGenerator.generateBarabasiAlbertGraph(
                factory,
                factory.vertexFactory,
                factory.edgeFactory,
                nodes, edges, 7);

        //Imprimir los grafos erdos
        printGraph("erdosrenyi", erdos);
        printGraphOnScreen("erdosrenyi", erdos);
        printGraph("barbasi", barabasi);
        printGraphOnScreen("barbasi", barabasi);
        Visualization.showGraph(erdos);
        Visualization.showGraph(barabasi);
    }

    private static void printGraph(String filename, Graph<String, Integer> graph){
        try {
            //FileWriter file = new FileWriter("graph/"+filename+".csv");
            PrintWriter writer = new PrintWriter("graph/"+filename+".csv");
            Iterator iter = graph.getVertices().iterator();
            while (iter.hasNext()){
                String node = (String) iter.next();
                if (graph.getNeighbors(node)!=null)
                    for (String nodeSon: graph.getNeighbors(node))
                        writer.println(node+","+nodeSon);
            }
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void printGraphOnScreen(String filename, Graph<String, Integer> graph){
        System.out.print("Grafo de "+filename+": \n");
        Iterator b = graph.getVertices().iterator();
        while (b.hasNext()){
            String i = (String) b.next();
            System.out.print("\tNodo "+i+ ":\n");
            if (graph.getNeighbors(i)!=null)
                for (String j: graph.getNeighbors(i))
                    System.out.print("\t\tP: "+i+"\tH: "+j+"\tE: "+(-1)*graph.findEdge(i,j)+"\n");
        }
    }
}
