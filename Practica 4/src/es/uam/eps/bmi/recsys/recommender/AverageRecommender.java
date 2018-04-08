package es.uam.eps.bmi.recsys.recommender;

import es.uam.eps.bmi.recsys.data.Ratings;

import java.util.*;

public class AverageRecommender extends AbstractRecommender {

    Map<Integer,Double> ratingSum;

    public AverageRecommender(Ratings ratings, int minRating) {
        super(ratings);
        ratingSum = new HashMap<>();
        for (int item : ratings.getItems()) {
            Set<Integer> user=ratings.getUsers(item);
            if(user.size()>minRating){
                for (int u : user)
            }

                score(ratings.getRating(u, item);
            ratingSum.put(item, sum);
        }
    }

    @Override
    public double score (int user, int item) {
        return ratingSum.get(item);
    }


}
