package es.uam.eps.bmi.recsys.recommender.similarity;

import es.uam.eps.bmi.recsys.data.Features;
import es.uam.eps.bmi.recsys.data.Ratings;
import es.uam.eps.bmi.recsys.ranking.RankingImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CosineUserSimilarity extends CosineAbstractSimilarity {
    public CosineUserSimilarity(Ratings ratings) {
        super(ratings);
        this.ratings.getUsers().forEach((u)->{
            Set<Integer> itemsUAux = this.ratings.getItems(u);

            if(itemsUAux.size()>0){
                Set<Integer> itemsU = itemsUAux.stream().filter((i)->this.ratings.getRating(u,i)!=null).collect(Collectors.toSet());
                double modU = itemsU.stream().mapToDouble((i)->Math.pow(this.ratings.getRating(u,i),2)).sum();
                Map<Integer,Double> neigh = new HashMap<>();

                this.ratings.getUsers().stream().filter((v)->!v.equals(u)).forEach((v)->{

                   Set<Integer> itemsVAux = this.ratings.getItems(v);
                   if(itemsVAux.size()>0){
                       Set<Integer> itemsV = itemsVAux.stream().filter((i)->this.ratings.getRating(v,i)!=null).collect(Collectors.toSet());

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

    @Override
    public String toString() {
        return "CosineUserSimilarity";
    }
}
