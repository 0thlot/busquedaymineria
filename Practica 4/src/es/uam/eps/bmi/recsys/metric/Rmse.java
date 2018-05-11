package es.uam.eps.bmi.recsys.metric;

import es.uam.eps.bmi.recsys.Recommendation;
import es.uam.eps.bmi.recsys.data.Ratings;
import es.uam.eps.bmi.recsys.ranking.RankingElement;

import java.util.Iterator;

public class Rmse implements Metric {

    private Ratings ratings;
    public Rmse(Ratings test) {
        ratings=test;
    }

    @Override
    public double compute(Recommendation rec) {
        double rmse = 0.0;
        int nT = 0;
        for(Integer u: rec.getUsers()){

            for (RankingElement re : rec.getRecommendation(u)) {
                Double score = ratings.getRating(u,re.getID());

                if(score!=null ){
                    Double cuadrado=Math.pow(re.getScore()-score,2);

                    rmse+= (!cuadrado.isNaN())?cuadrado:0.0;
                    nT++;
                }
            }

        }
        Double result = 0.0;
        if(nT>0){
            result = Math.sqrt(rmse/nT);
        }
        return (!result.isNaN())?result:0.0;
    }

    @Override
    public String toString() {
        return "Rmse";
    }
}
