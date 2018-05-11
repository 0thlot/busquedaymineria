package es.uam.eps.bmi.recsys.metric;

import es.uam.eps.bmi.recsys.Recommendation;
import es.uam.eps.bmi.recsys.data.Ratings;
import es.uam.eps.bmi.recsys.ranking.RankingElement;

public class Precision implements Metric{

    private Ratings ratings;
    private double umbral;
    private int cutoff;

    public Precision(Ratings test, double umbral, int cutoff) {
        this.ratings = test;
        this.umbral = umbral;
        this.cutoff = cutoff;
    }


    @Override
    public double compute(Recommendation rec) {
        double precision = 0.0;
        int nUsers = 0;

        for(Integer u: rec.getUsers()){
            int nUmbral = 0;
            int k=0;
            for (RankingElement re : rec.getRecommendation(u)) {
                if(k==cutoff){
                    break;
                }
                Double score = ratings.getRating(u,re.getID());

                if(score!=null && !score.isNaN() && score>umbral){
                    nUmbral++;
                }
                k++;
            }

            precision+=(double) nUmbral/cutoff;
            nUsers++;
        }

        return (double) precision/nUsers;

    }

    @Override
    public String toString() {
        return "Precision@"+cutoff;
    }
}
