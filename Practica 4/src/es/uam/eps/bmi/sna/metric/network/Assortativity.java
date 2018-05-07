package es.uam.eps.bmi.sna.metric.network;

import es.uam.eps.bmi.sna.metric.GlobalMetric;
import es.uam.eps.bmi.sna.structure.UndirectedSocialNetwork;

import java.util.Set;

public class Assortativity<U extends Comparable<U>> implements GlobalMetric<U> {

    public Assortativity() {}

    @Override
    public double compute(UndirectedSocialNetwork<U> network) {

        int m = network.nEdges();
        double gradoAlCuadrado=0,gradoAlCubo=0,gradosConectados=0;

        for(U u:network.getUsers()){
            Set<U> vecinos = network.getContacts(u);
            gradoAlCuadrado += Math.pow(vecinos.size(),2);
            gradoAlCubo += Math.pow(vecinos.size(),3);
            gradosConectados += vecinos.parallelStream().mapToDouble((u2)->vecinos.size()*network.getContacts(u2).size()).sum();
        }

        gradoAlCuadrado=Math.pow(gradoAlCuadrado,2);
        gradosConectados/=2;


        return (double) (4*m*gradosConectados - gradoAlCuadrado)/(2*m*gradoAlCubo - gradoAlCuadrado);

    }

    @Override
    public String toString() {
        return "Assortativity";
    }
}
