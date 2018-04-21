package es.uam.eps.bmi.recsys.recommender.similarity;

import es.uam.eps.bmi.recsys.data.Features;
import es.uam.eps.bmi.recsys.data.Ratings;
import es.uam.eps.bmi.recsys.ranking.RankingImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CosineUserSimilarity extends CosineAbstractSimilarity {
    public CosineUserSimilarity(Ratings ratings) {
        super(ratings);
        this.ratings.getUsers().forEach((u)->{
            Set<Integer> itemsU = this.ratings.getItems(u);

            if(itemsU.size()>0){

                double modU = itemsU.stream().mapToDouble((i)->Math.pow(this.ratings.getRating(u,i),2)).sum();
                Map<Integer,Double> neigh = new HashMap<>();

                this.ratings.getUsers().stream().filter((v)->!v.equals(u)).forEach((v)->{

                   Set<Integer> itemsV = this.ratings.getItems(v);
                   if(itemsV.size()>0){
                       double modV = itemsV.stream().mapToDouble((i)->Math.pow(this.ratings.getRating(v,i),2)).sum();
                       itemsV.retainAll(itemsU);
                       double scoreComun =(itemsV.size()==0)?0:itemsV.stream().mapToDouble((i)->this.ratings.getRating(u,i)*this.ratings.getRating(v,i)).sum();
                       neigh.putIfAbsent(v,scoreComun/Math.sqrt(modU*modV));
                   }

                });
                this.vecinos.putIfAbsent(u,neigh);
            }
        });
    }

}
