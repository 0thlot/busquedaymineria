package es.uam.eps.bmi.recsys.metric;

import es.uam.eps.bmi.recsys.Recommendation;
import es.uam.eps.bmi.recsys.data.Ratings;
import es.uam.eps.bmi.recsys.ranking.RankingElement;

import java.util.Set;

public class Recall implements Metric {
    private Ratings ratings;
    private double umbral;
    private int cutoff;

    public Recall(Ratings test, double umbral, int cutoff) {
        this.ratings = test;
        this.umbral = umbral;
        this.cutoff = cutoff;
    }
    @Override
    public double compute(Recommendation rec) {
        double recall = 0.0;
        int nUsers = 0;

        for(Integer u: rec.getUsers()){
            int nUmbral = 0;

            Set<Integer> items = ratings.getItems(u);

            if(items!=null && items.size()>0){
                int k=0;
                int tUmbral;

                tUmbral = (int) items.stream().filter((i)-> {
                    Double s=this.ratings.getRating(u, i);
                    return s!=null && s>umbral;
                }).count();

                for (RankingElement re : rec.getRecommendation(u)) {
                    if(k==cutoff){
                        break;
                    }
                    Double score = ratings.getRating(u,re.getID());

                    if(score!=null && score>umbral){
                        nUmbral++;
                    }
                    k++;
                }

                recall+=(double)nUmbral/tUmbral;
                nUsers++;
            }


        }

        return recall/nUsers;
    }

    @Override
    public String toString() {
        return "Recall@"+cutoff;
    }
}
