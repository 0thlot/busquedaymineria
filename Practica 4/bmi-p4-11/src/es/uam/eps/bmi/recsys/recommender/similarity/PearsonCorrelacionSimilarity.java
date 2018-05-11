package es.uam.eps.bmi.recsys.recommender.similarity;

import es.uam.eps.bmi.recsys.data.Ratings;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PearsonCorrelacionSimilarity extends CosineAbstractSimilarity {

    public PearsonCorrelacionSimilarity(Ratings r) {
        super(r);
        this.ratings.getUsers().forEach((u)->{
            Set<Integer> itemsU = this.ratings.getItems(u);

            if(itemsU.size()>0){
                double mediaRU = itemsU.stream().mapToDouble((i)->this.ratings.getRating(u,i)).average().orElse(Double.NaN);
                double modU = itemsU.stream().mapToDouble((i)->Math.pow(this.ratings.getRating(u,i)-mediaRU,2)).sum();
                Map<Integer,Double> neigh = new HashMap<>();

                this.ratings.getUsers().stream().filter((v)->!v.equals(u)).forEach((v)->{

                    Set<Integer> itemsV = this.ratings.getItems(v);
                    if(itemsV.size()>0){
                        double mediaRV = itemsV.stream().mapToDouble((i)->this.ratings.getRating(v,i)).average().orElse(Double.NaN);
                        double modV = itemsV.stream().mapToDouble((i)->Math.pow(this.ratings.getRating(v,i)-mediaRV,2)).sum();
                        itemsV.retainAll(itemsU);
                        double scoreComun =(itemsV.size()==0)?0:itemsV.stream().mapToDouble((i)->(this.ratings.getRating(u,i)-mediaRU)*(this.ratings.getRating(v,i)-mediaRV)).sum();
                        neigh.putIfAbsent(v,scoreComun/Math.sqrt(modU*modV));
                    }

                });
                this.vecinos.putIfAbsent(u,neigh);
            }
        });
    }
}
