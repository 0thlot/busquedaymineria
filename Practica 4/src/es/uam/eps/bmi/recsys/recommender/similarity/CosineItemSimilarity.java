package es.uam.eps.bmi.recsys.recommender.similarity;

import es.uam.eps.bmi.recsys.data.Ratings;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CosineItemSimilarity implements Similarity {

    Map<Integer,Double>modItem=new HashMap<>();
    protected Map<Integer,Map<Integer,Double>> vecinos;
    protected Ratings ratings;

    public CosineItemSimilarity(Ratings r) {
        this.ratings = r;
        this.vecinos = new HashMap<>();
        Set<Integer> items = this.ratings.getItems();
        for(Integer i:items){

            Map<Integer,Double> neigh = new HashMap<>();
            for(Integer j:items){
                if(!i.equals(j)){
                    if(vecinos.containsKey(j) && vecinos.get(j).containsKey(i)){

                    }else{
                        neigh.put(j,scoreSim(i,j));
                    }

                }
            }

            this.vecinos.put(i,neigh);
        }


    }

    @Override
    public double sim(int x, int y) {

        if(vecinos.containsKey(x) && vecinos.get(x).containsKey(y))
            return vecinos.get(x).get(y);

        if(vecinos.containsKey(y) && vecinos.get(y).containsKey(x))
            return vecinos.get(y).get(x);


        return 0;
    }

    private double scoreSim(Integer i, Integer j){
        Set<Integer> usersIaux = this.ratings.getUsers(i);
        Set<Integer> usersJaux = this.ratings.getUsers(j);

        if(usersIaux.size()==0||usersJaux.size()==0){
            return 0;
        }

        double modI =0, modJ=0;

        if(modItem.containsKey(i)){
            modI=modItem.get(i);
        }else{
            for(Integer u:usersIaux){
                Double s=this.ratings.getRating(u,i);
                if(s!=null){
                    modI+=s*s;
                }
            }

            modItem.put(i,modI);
        }

        if(modItem.containsKey(j)){
            modJ=modItem.get(j);
        }else{
            for(Integer u:usersJaux){
                Double s=this.ratings.getRating(u,j);
                if(s!=null){
                    modJ+=s*s;
                }
            }

            modItem.put(j,modJ);
        }

        usersJaux.retainAll(usersIaux);
        double scoreComun =0;
        for(Integer u:usersJaux){
            scoreComun+=this.ratings.getRating(u,i)*this.ratings.getRating(u,j);
        }

        return scoreComun/Math.sqrt(modI*modJ);
    }

    @Override
    public String toString() {
        return "CosineItemSimilarity";
    }
}
