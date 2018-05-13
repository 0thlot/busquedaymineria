package es.uam.eps.bmi.recsys.recommender;

import es.uam.eps.bmi.recsys.data.Ratings;
import es.uam.eps.bmi.recsys.recommender.similarity.CosineFeatureSimilarity;

/**
 * @author oscar
 * @author jorge
 *
 * @param <F>
 */
public class CentroidRecommender<F> extends AbstractRecommender {

    private CosineFeatureSimilarity<F> features;

    public CentroidRecommender(Ratings ratings, CosineFeatureSimilarity<F> features){
        super(ratings);
        this.features = features;
        features.setXFeatures(ratings);
    }

    @Override
    public double score(int user, int item) {
        return features.sim(user, item);
    }

    @Override
    public String toString(){
        return "CentroidRecommender";
    }
}
