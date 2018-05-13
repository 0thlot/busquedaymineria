package es.uam.eps.bmi.recsys.recommender.similarity;

import es.uam.eps.bmi.recsys.data.Features;

import java.util.Set;

public class JaccardFeatureSimilarity<F> extends FeatureSimilarity<F> {

    public JaccardFeatureSimilarity(Features<F> features) {
        super(features);
    }

    @Override
    public double sim(int x, int y) {

        Set<F> xSet = getFeatures().getFeatures(x);
        Set<F> ySet = getFeatures().getFeatures(y);

        if(xSet==null || xSet.size() == 0 || ySet==null || ySet.size() == 0){
            return 0;
        }

        int xSize = xSet.size(), ySize = ySet.size();

        //Obtengo los comunes
        xSet.retainAll(ySet);

        return (double) xSet.size() / (xSize + ySize - xSet.size());
    }

    @Override
    public String toString() {
        return "JaccardFeatureSimilarity";
    }
}
