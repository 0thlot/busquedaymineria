package es.uam.eps.bmi.recsys.recommender.similarity;

import es.uam.eps.bmi.recsys.data.Ratings;

import java.util.HashMap;
import java.util.Map;

public abstract class CosineAbstractSimilarity implements Similarity {

    protected Map<Integer,Map<Integer,Double>> vecinos;
    protected Ratings ratings;

    public CosineAbstractSimilarity(Ratings r){
        this.ratings = r;
        this.vecinos = new HashMap<>();
    }

    @Override
    public double sim(int x, int y) {
       Map<Integer, Double> aux = vecinos.get(x);
       if(aux != null){
           Double s = aux.get(y);
           if(s!=null)
            return s;
       }
       return 0;
    }
}
