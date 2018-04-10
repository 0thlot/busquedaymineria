package es.uam.eps.bmi.recsys.recommender;

import es.uam.eps.bmi.recsys.data.Ratings;
import es.uam.eps.bmi.recsys.ranking.RankingElement;
import es.uam.eps.bmi.recsys.ranking.RankingImpl;
import es.uam.eps.bmi.recsys.recommender.similarity.Similarity;

public class UserKNNRecommender extends AbstractUKNNRecommender {


    public UserKNNRecommender(Ratings ratings, Similarity sim, int k) {
        super(ratings,sim,k);
    }

    @Override
    protected double scoreKNN(double score, double C, int numKwithRant) {
        return score;
    }

    @Override
    public String toString() {
        return "UserKNNRecommender";
    }
}
