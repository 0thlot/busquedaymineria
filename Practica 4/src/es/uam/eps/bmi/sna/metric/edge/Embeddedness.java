package es.uam.eps.bmi.sna.metric.edge;

import es.uam.eps.bmi.sna.metric.LocalMetric;
import es.uam.eps.bmi.sna.ranking.Ranking;
import es.uam.eps.bmi.sna.ranking.RankingImpl;
import es.uam.eps.bmi.sna.structure.Edge;
import es.uam.eps.bmi.sna.structure.UndirectedSocialNetwork;

import java.util.HashSet;
import java.util.Set;

public class Embeddedness <U extends Comparable<U>> implements LocalMetric<Edge<U>,U> {

    private Ranking<Edge<U>> ranking;
    private int cont;
    Edge<U> edge;
    Edge<U> edge_mirror;
    boolean flag;

    public Embeddedness(int topk){
        ranking = new RankingImpl<>(topk);
    }

    @Override
    public Ranking<Edge<U>> compute(UndirectedSocialNetwork<U> network) {
        Set<Edge<U>> setNotRepeated = new HashSet<>();

        network.getUsers().forEach((u)->{
            network.getUsers().forEach((v)-> {
                if (!u.equals(v)){
                    edge = new Edge<U>(u, v);
                    edge_mirror = new Edge<U>(v, u);
                    flag = true;

                    //Buscamos si la arista ya ha sido vista
                    setNotRepeated.forEach((e) -> {
                        if (e.compareTo(edge) == 0 || e.compareTo(edge_mirror) == 0)
                            flag = false;
                    });

                    if (flag) {
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
        cont = 0;

        network.getContacts(element.getFirst()).forEach((n)->{
            if (!n.equals(element.getSecond()))
                if (network.connected(element.getSecond(),n))
                    cont++;
        });

        return (double) cont/(u_size+v_size-cont);
    }


    @Override
    public String toString() {
        return "Embeddedness";
    }
}
