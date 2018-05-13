package es.uam.eps.bmi.sna.metric.edge;

import es.uam.eps.bmi.sna.metric.LocalMetric;
import es.uam.eps.bmi.sna.ranking.Ranking;
import es.uam.eps.bmi.sna.ranking.RankingImpl;
import es.uam.eps.bmi.sna.structure.Edge;
import es.uam.eps.bmi.sna.structure.EdgeImpl;
import es.uam.eps.bmi.sna.structure.UndirectedSocialNetwork;

import java.util.HashSet;
import java.util.Set;

public class Embeddedness <U extends Comparable<U>> implements LocalMetric<Edge<U>,U> {

    private Ranking<Edge<U>> ranking;

    public Embeddedness(int topk){
        ranking = new RankingImpl<>(topk);
    }

    @Override
    public Ranking<Edge<U>> compute(UndirectedSocialNetwork<U> network) {
        Set<Edge<U>> setNotRepeated = new HashSet<>();

        network.getUsers().forEach((u)->{
            network.getUsers().forEach((v)-> {
                if (!u.equals(v)){
                    Edge<U> edge = new EdgeImpl<U>(u, v);

                   if (!setNotRepeated.contains(new EdgeImpl<U>(v, u))) {
                        ranking.add(edge, compute(network, edge));
                        setNotRepeated.add(edge);
                    }
                }
            });
        });
        return ranking;
    }

    @Override
    public double compute(UndirectedSocialNetwork<U> network, Edge<U> element) {
        double u_size = network.getContacts(element.getFirst()).size();
        double v_size = network.getContacts(element.getSecond()).size();

        if (network.connected(element.getSecond(), element.getFirst())) {
            u_size--;
            v_size--;
        }

        long cont = network.getContacts(element.getFirst()).stream().filter((n)->!n.equals(element.getSecond())&&network.connected(element.getSecond(),n)).count();

        return (double) cont/(u_size+v_size-cont);
    }


    @Override
    public String toString() {
        return "Embeddedness";
    }
}
