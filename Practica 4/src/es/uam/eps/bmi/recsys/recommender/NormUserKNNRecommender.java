package es.uam.eps.bmi.recsys.recommender;

import es.uam.eps.bmi.recsys.data.Ratings;
import es.uam.eps.bmi.recsys.recommender.similarity.Similarity;

public class NormUserKNNRecommender extends AbstractUKNNRecommender {
    private int minRK;
    public NormUserKNNRecommender(Ratings ratings, Similarity sim, int k, int minRK) {
        super(ratings, sim, k);
        this.minRK=minRK;
    }

    @Override
    protected double scoreKNN(double score, double C, int numKwithRant) {
        return (double) ((numKwithRant>=minRK)?score/C:0);
    }


    @Override
    public String toString() {
        return "NormUserKNNRecommender - " + sim;
    }
}
