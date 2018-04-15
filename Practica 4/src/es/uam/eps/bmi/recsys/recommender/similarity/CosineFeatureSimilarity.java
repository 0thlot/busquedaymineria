package es.uam.eps.bmi.recsys.recommender.similarity;

import es.uam.eps.bmi.recsys.data.Features;
import es.uam.eps.bmi.recsys.data.FeaturesImpl;
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
        Vector<Double> uX = new Vector(Arrays.asList(xFeatures.getFeatures(x)));
        Vector<Double> uY = new Vector(Arrays.asList(xFeatures.getFeatures(y)));
        Vector<Double> num = new Vector<>(uX.size());

        double modX = uX.stream().mapToDouble((f)->Math.pow(f,2)).sum();
        double modY = uY.stream().mapToDouble((f)->Math.pow(f,2)).sum();

        for (int i=0; i<uY.size(); i++)
            num.add(uX.get(i)*uY.get(i));

        return (num.stream().mapToDouble((i)->i).sum())/Math.sqrt(modX*modY);
    }

    /** Establece el eje X en funcion de unos ratings
     *
     * @param ratings de los usuarios
     */
    public void setXFeatures(Ratings ratings) {
        xFeatures = new FeaturesCentroid(ratings, yFeatures);
    }

    /** Clase FeaturesCentroid para mantener las puntuaciones de cada usuario respecto a cada item.      */
    public class FeaturesCentroid extends FeaturesImpl{

        /** Constructor de la clase
         * @param ratings de los usuarios
         * @param feature caracteristicas de cada item
         */
        public FeaturesCentroid(Ratings ratings, Features<F> feature){
            super(null,null,null);
            Double mul;
            Double score;

            //Recorremos usuarios de ratigns
            for(Integer user: ratings.getItems()){
                Map<Integer, Double> map = new HashMap<>();

                //Recorremos cada item del usuario
                for(int item: ratings.getItems(user)) {
                    mul = ratings.getRating(user, item);
                    score = 0.0;

                    //Recorremos cada F de cada item
                    if(mul > 0)
                        for (F f : feature.getFeatures(item))
                            score += mul*feature.getFeature(item, f);

                    map.put(item, score);
                    features.put(user, map);
                }
            }
        }
    }
}
