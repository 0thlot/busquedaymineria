package es.uam.eps.bmi.recsys.recommender.similarity;

import es.uam.eps.bmi.recsys.data.Features;
import es.uam.eps.bmi.recsys.data.FeaturesCentroid;
import es.uam.eps.bmi.recsys.data.Ratings;

import java.util.*;

/**
  * @author oscar
  * @author jorge
*/
public class CosineFeatureSimilarity<F> extends FeatureSimilarity<F>{

    public CosineFeatureSimilarity(Features<F> features) {
        super(features);
    }

    @Override
    public double sim(int x, int y) {
        //Aqui se implementa la funcionalidad de coseno

        double modX = getMod(x, xFeatures);
        double modY = getMod(y, yFeatures);
        double numer = 0.0;

        for (F f : xFeatures.getFeatures(x)) {
            Double aux1 = xFeatures.getFeature(x, f);
            Double aux2 = yFeatures.getFeature(y, f);
            if (aux1!=null && aux2!=null)
                numer += aux1 * aux2 ;
        }

        return numer/(modX*modY);
    }

    /**
     *
     * @param ind
     * @return
     */
    private double getMod(int ind, Features<F> features){
        double mod = 0.0;

        if (features.getFeatures(ind).size()<=0)
            return 0.0;

        for (F f: features.getFeatures(ind)) {
            //Dentro de cada F
            Double aux = features.getFeature(ind, f);
            if (aux!=null)
                mod += aux * aux;
        }
        return Math.sqrt(mod);
    }

    /** Establece el eje X en funcion de unos ratings
     *
     * @param ratings de los usuarios
     */
    public void setXFeatures(Ratings ratings) {
        xFeatures = new FeaturesCentroid<>(ratings, yFeatures);
    }
}
