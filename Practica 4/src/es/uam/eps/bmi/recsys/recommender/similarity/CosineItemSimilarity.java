package es.uam.eps.bmi.recsys.recommender.similarity;

import es.uam.eps.bmi.recsys.data.Ratings;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CosineItemSimilarity extends CosineAbstractSimilarity {

    public CosineItemSimilarity(Ratings r) {
        super(r);
        this.ratings.getItems().forEach((i)->{
            Set<Integer> usersIaux = this.ratings.getUsers(i);

            if(usersIaux.size()>0){
                Set<Integer> usersI=usersIaux.stream().filter((u)->this.ratings.getRating(u,i)!=null).collect(Collectors.toSet());
                double modI = usersI.stream().mapToDouble((u)->Math.pow(this.ratings.getRating(u,i),2)).sum();
                Map<Integer,Double> neigh = new HashMap<>();

                this.ratings.getItems().stream().filter((j)->!j.equals(i)).forEach((j)->{

                    Set<Integer> usersJaux = this.ratings.getUsers(j);
                    if(usersJaux.size()>0){
                        Set<Integer>usersJ=usersJaux.stream().filter((v)->this.ratings.getRating(v,j)!=null).collect(Collectors.toSet());
                        double modJ = usersJ.stream().mapToDouble((v)->Math.pow(this.ratings.getRating(v,j),2)).sum();
                        usersJ.retainAll(usersI);
                        double scoreComun =(usersJ.size()==0)?0:usersJ.stream().mapToDouble((u)->this.ratings.getRating(u,i)*this.ratings.getRating(u,j)).sum();
                        neigh.putIfAbsent(j,scoreComun/Math.sqrt(modI*modJ));
                    }

                });
                this.vecinos.putIfAbsent(i,neigh);
            }
        });
    }
}
