package es.uam.eps.bmi.recsys.recommender.similarity;

import es.uam.eps.bmi.recsys.data.Features;

public class JaccardFeatureSimilarity<F> extends FeatureSimilarity<F> {

    public JaccardFeatureSimilarity(Features<F> features) {
        super(features);
    }

    @Override
    public double sim(int x, int y) {
        return 0;
    }
}
