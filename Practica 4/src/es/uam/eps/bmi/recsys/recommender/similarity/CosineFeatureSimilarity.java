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
        Set<F> setItems = xFeatures.getFeatures(x);
        Set<F> setFeatures = yFeatures.getFeatures(y);
        List<Double> num = new ArrayList<>();

        double modX = getMod(x, setItems, xFeatures);
        double modY = getMod(y, setFeatures, yFeatures);

        for (F f : setItems) {
            Double aux1 = xFeatures.getFeature(x, f);
            Double aux2 = yFeatures.getFeature(y, f);
            if (aux1!=null && aux2!=null)
                num.add(aux1 * aux2);
        }

        return (num.stream().mapToDouble((i)->i).sum())/Math.sqrt(modX*modY);
    }

    /**
     *
     * @param ind
     * @param list
     * @return
     */
    private double getMod(int ind, Set<F> list, Features<F> features){
        double mod = 0.0;
        Double aux;

        if (list.size()<=0)
            return 0.0;

        for (F f: list) {
            //Dentro de cada F
            aux = features.getFeature(ind, f);
            if (aux!=null)
                mod += Math.pow(aux, 2.0);
        }
        return Math.sqrt(mod);
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
