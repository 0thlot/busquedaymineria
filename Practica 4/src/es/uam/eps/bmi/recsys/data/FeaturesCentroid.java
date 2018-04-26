package es.uam.eps.bmi.recsys.data;

import java.util.HashMap;
import java.util.Map;



/** Clase FeaturesCentroid para mantener las puntuaciones de cada usuario respecto a cada item.
 *
 * @author oscar
 * @author jorge
 */
public class FeaturesCentroid<F> extends FeaturesImpl<F>{

    /** Constructor de la clase
     * @param ratings de los usuarios
     * @param feature caracteristicas de cada item
     */
    public FeaturesCentroid(Ratings ratings, Features<F> feature){
        super(null,null,null);
        Double mul;
        double score;

        //Recorremos usuarios de ratigns
        for(Integer user: ratings.getItems()){
            Map<F, Double> map = new HashMap<>();

            //Recorremos cada item del usuario
            for(int item: ratings.getItems(user)) {
                mul = ratings.getRating(user, item);
                //Recorremos cada F de cada item
                // if(mul != null)
                    for (F f : feature.getFeatures(item)) {
                        // score = 0.0;
                        // if (feature.getFeature(item, f) != null)
                            score = mul * feature.getFeature(item, f);
                        setFeature(user, f, score);
                    }
            }
            // features.put(user, map);
        }
    }
}
