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
        int nUsers = 0;

        for(Integer u: rec.getUsers()){
            int nT = 0;
            double rmseU = 0.0;

            for (RankingElement re : rec.getRecommendation(u)) {
                Double score = ratings.getRating(u,re.getID());

                if(score!=null){
                    Double cuadrado=Math.pow(re.getScore()-score,2);

                    rmseU+=(!cuadrado.isNaN())?cuadrado:0.0;
                    nT++;
                }
            }

            if(nT>0){
                Double raiz=Math.sqrt(rmseU/nT);
                rmse+=(!raiz.isNaN())?raiz:0.0;
                nUsers++;
            }

        }

        return rmse/nUsers;
    }

    @Override
    public String toString() {
        return "Rmse";
    }
}
