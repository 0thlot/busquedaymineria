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
            //Recorremos cada item del usuario
            for(int item: ratings.getItems(user)) {
                mul = ratings.getRating(user, item);
                //Recorremos cada F de cada item
                for (F f : feature.getFeatures(item)) {
                    score = 0.0;
                    if (getFeatures(user)!=null)
                        if (getFeature(user, f)!=null)
                            score = getFeature(user, f);
                    score += mul * feature.getFeature(item, f);
                    setFeature(user, f, score);
                }
            }
        }
    }
}
