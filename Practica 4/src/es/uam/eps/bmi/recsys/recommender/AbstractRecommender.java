package es.uam.eps.bmi.recsys.recommender;

import es.uam.eps.bmi.recsys.Recommendation;
import es.uam.eps.bmi.recsys.RecommendationImpl;
import es.uam.eps.bmi.recsys.data.Ratings;
import es.uam.eps.bmi.recsys.ranking.Ranking;
import es.uam.eps.bmi.recsys.ranking.RankingImpl;

public abstract class AbstractRecommender implements Recommender {

    private Ratings ratings;
    public AbstractRecommender(Ratings ratings) {
        this.ratings = ratings;
    }
    public Recommendation recommend(int cutoff) {
        Recommendation rec = new RecommendationImpl();

        ratings.getUsers().forEach((u)->{
            Ranking ranking = new RankingImpl(cutoff);
            ratings.getItems(u).forEach((i)->{
                ranking.add(i,score(u,i));
            });
            rec.add(u,ranking);
        });

        return rec;
    }
}
