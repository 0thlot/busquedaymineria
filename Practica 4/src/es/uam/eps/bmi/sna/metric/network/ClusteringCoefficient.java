package es.uam.eps.bmi.sna.metric.network;

import es.uam.eps.bmi.sna.metric.GlobalMetric;
import es.uam.eps.bmi.sna.structure.UndirectedSocialNetwork;

public class ClusteringCoefficient<U extends Comparable<U>> implements GlobalMetric<U> {

    public ClusteringCoefficient() {}

    @Override
    public double compute(UndirectedSocialNetwork<U> network) {

        double numTriangulos = 0;
        double numCaminos = 0;

        for(U u1:network.getUsers()){
            for(U u2:network.getContacts(u1)){
                numTriangulos+=network.getContacts(u2).parallelStream().filter((u3)->!u1.equals(u3) && network.connected(u1,u3)).count();
            }
        }

        numTriangulos/=2;

        numCaminos=network.getUsers().parallelStream().mapToDouble((u)->
            {
                int s = network.getContacts(u).size();
                return (s*(s-1)/2);

            }).sum();


        return (numCaminos!=0)?numTriangulos/numCaminos:0;

    }


    @Override
    public String toString() {
        return "ClusteringCoefficient";
    }
}
