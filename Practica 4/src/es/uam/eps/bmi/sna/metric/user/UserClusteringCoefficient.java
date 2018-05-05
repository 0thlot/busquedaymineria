package es.uam.eps.bmi.sna.metric.user;

import es.uam.eps.bmi.sna.metric.LocalMetric;
import es.uam.eps.bmi.sna.ranking.Ranking;
import es.uam.eps.bmi.sna.ranking.RankingImpl;
import es.uam.eps.bmi.sna.structure.UndirectedSocialNetwork;

import java.util.Set;

public class UserClusteringCoefficient<U extends Comparable<U>> implements LocalMetric<U, U> {

    private Ranking<U> ranking;

    public UserClusteringCoefficient(int topK) {
            ranking = new RankingImpl<>(topK);
    }

    public UserClusteringCoefficient(){
        ranking = new RankingImpl<>();
    }

    @Override
    public Ranking<U> compute(UndirectedSocialNetwork<U> network) {

        network.getUsers().forEach((u)->{
            ranking.add(u,compute(network,u));
        });
        return ranking;
    }

    @Override
    public double compute(UndirectedSocialNetwork<U> network, U element) {

        Set<U> contactos = network.getContacts(element);
        double posible = (double) contactos.size() *(contactos.size()-1)/2;
        int conectados = 0;

        for(U n1:contactos){
            conectados+=contactos.stream().filter((n2)->!n1.equals(n2) && network.connected(n1,n2)).count();
        }

        return (double) (conectados/2)/(posible);//los posibles y eliminamos duplicados
    }

    @Override
    public String toString() {
        return "UserClusteringCoefficient";
    }
}
