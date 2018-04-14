package es.uam.eps.bmi.recsys.recommender;

import es.uam.eps.bmi.recsys.data.Ratings;
import es.uam.eps.bmi.recsys.ranking.RankingElement;
import es.uam.eps.bmi.recsys.ranking.RankingImpl;
import es.uam.eps.bmi.recsys.recommender.similarity.Similarity;

import java.util.HashMap;
import java.util.Map;

public class ItemNNRecommender extends AbstractRecommender {

    protected Map<Integer, RankingImpl> itemVecinos;
    protected Similarity sim;


    public ItemNNRecommender(Ratings ratings, Similarity sim) {
        super(ratings);
        this.sim = sim;
        this.itemVecinos = new HashMap<>();
        this.ratings.getItems().forEach((i)->{
            RankingImpl r = new RankingImpl(Integer.MAX_VALUE);
            this.ratings.getItems().stream().filter((j)->!j.equals(i)).forEach((j)->{
                double s = this.sim.sim(i,j);
                if(s>0)
                    r.add(j,s);
            });
            this.itemVecinos.put(i,r);

        });
    }

    @Override
    public double score(int user, int item) {
        RankingImpl r = this.itemVecinos.get(user);
        if(r==null) return 0;
        double score = 0, C=0;
        for(RankingElement vecino:r){
            Double scoreI = this.ratings.getRating(user,vecino.getID());
            if(scoreI!=null){
                score+=vecino.getScore() * scoreI;
                C+=vecino.getScore();
            }
        }

        return (C==0)?0:score/C;
    }
}
