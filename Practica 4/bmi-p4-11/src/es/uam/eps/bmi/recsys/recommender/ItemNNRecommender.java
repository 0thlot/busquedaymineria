package es.uam.eps.bmi.recsys.recommender;

import es.uam.eps.bmi.recsys.data.Ratings;
import es.uam.eps.bmi.recsys.ranking.RankingElement;
import es.uam.eps.bmi.recsys.ranking.RankingImpl;
import es.uam.eps.bmi.recsys.recommender.similarity.Similarity;

import java.util.HashMap;
import java.util.Map;

public class ItemNNRecommender extends AbstractRecommender {

    protected Similarity sim;

    public ItemNNRecommender(Ratings ratings, Similarity sim) {
        super(ratings);
        this.sim = sim;

    }

    @Override
    public double score(int user, int item) {

        double score = 0;
        Double s = this.ratings.getRating(user,item);

        if(s!=null)
            return s;

        for(Integer j:ratings.getItems(user)){
            if(!j.equals(item)){
                Double scoreI = this.ratings.getRating(user,j);
                if(scoreI!=null){
                    score+=sim.sim(item,j) * scoreI;
                }
            }

        }

        return score;
    }

    @Override
    public String toString() {
        return "ItemNNRecommender - " +this.sim;
    }

}
