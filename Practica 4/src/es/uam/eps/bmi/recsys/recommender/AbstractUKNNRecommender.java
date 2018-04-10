package es.uam.eps.bmi.recsys.recommender;

import es.uam.eps.bmi.recsys.data.Ratings;
import es.uam.eps.bmi.recsys.ranking.RankingElement;
import es.uam.eps.bmi.recsys.ranking.RankingImpl;
import es.uam.eps.bmi.recsys.recommender.similarity.Similarity;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractUKNNRecommender extends AbstractRecommender{

    protected Map<Integer, RankingImpl> userVecinos;
    protected Similarity sim;
    protected int k;

    public AbstractUKNNRecommender(Ratings ratings, Similarity sim, int k) {
        super(ratings);
        userVecinos = new HashMap<>();
        this.sim = sim;
        this.k = k;

        this.ratings.getUsers().forEach((u)->{
                RankingImpl r = new RankingImpl(k);
                this.ratings.getUsers().stream().filter((v)->!v.equals(u)).forEach((v)->{
                    r.add(v,this.sim.sim(u, v));
                });
                this.userVecinos.put(u,r);
        });
    }

    @Override
    public double score(int user, int item) {

        RankingImpl r = this.userVecinos.get(user);
        if(r==null) return 0;
        double score = 0, C=0;
        int numKwR = 0;
        for(RankingElement vecino:r){
            Double scoreV = this.ratings.getRating(vecino.getID(),item);
            if(scoreV!=null){
                //la sim(u,v) * rating(v,i)
                score+=vecino.getScore() * scoreV;
                C+=vecino.getScore();
                numKwR++;
            }
        }

        return scoreKNN(score,C,numKwR);
    }


    protected abstract double scoreKNN(double score,double C, int numKwithRant);


}
