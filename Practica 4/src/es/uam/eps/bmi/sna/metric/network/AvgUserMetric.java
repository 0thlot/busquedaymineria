package es.uam.eps.bmi.sna.metric.network;

import es.uam.eps.bmi.sna.metric.GlobalMetric;
import es.uam.eps.bmi.sna.metric.user.UserClusteringCoefficient;
import es.uam.eps.bmi.sna.structure.UndirectedSocialNetwork;

public class AvgUserMetric<U extends Comparable<U>> implements GlobalMetric<U>{

    private  UserClusteringCoefficient<U> cluster;
    double avg;

    public AvgUserMetric(UserClusteringCoefficient<U> cluster){
        this.cluster = cluster;
    }

    @Override
    public double compute(UndirectedSocialNetwork<U> network) {
        avg = 0.0;
        network.getUsers().forEach((u)->{
            avg += cluster.compute(network,u);
        });
        return avg/network.getUsers().size();
    }

    public String toString(){
        return "Avg(UserClusteringCoefficient)";
    }
}
