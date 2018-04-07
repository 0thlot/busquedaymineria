package es.uam.eps.bmi.recsys.recommender;

import es.uam.eps.bmi.recsys.Recommendation;
import es.uam.eps.bmi.recsys.data.Ratings;
import es.uam.eps.bmi.recsys.ranking.Ranking;
import es.uam.eps.bmi.recsys.ranking.RankingImpl;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AverageRecommender implements  Recommender {

    int cutoff;
    Ratings ratings;

    public AverageRecommender(Ratings ratings, int cutoff){
        this.ratings=ratings;
        this.cutoff=cutoff;
    }

    @Override
    public Recommendation recommend(int cutoff) {

        ARRecommendation recommendation = new ARRecommendation();

        for (int user : ratings.getUsers()){
            RankingImpl ranking = new RankingImpl(cutoff);
            for(int item : ratings.getItems(user))
                if (ratings.getRating(user, item)<=0.0)
                    ranking.add(item, score(user, item));
            recommendation.add(user,ranking);
        }
        return recommendation;
    }

    @Override
    public double score(int user, int item) {
        int num=0;
        Double score=0.0;

        for(int userI : ratings.getUsers()){
            Double scoreI = ratings.getRating(userI, item);
            if (scoreI>0.0)
                num++;
            score += scoreI;
        }
        return score/num;
    }

    public class ARRecommendation implements Recommendation{

        private Map<Integer, Ranking> recomendations;

        public ARRecommendation(){
            recomendations = new HashMap<>();
        }

        @Override
        public Set<Integer> getUsers() {
            return recomendations.keySet();
        }

        @Override
        public Ranking getRecommendation(int user) {
            return recomendations.get(user);
        }

        @Override
        public void add(int user, Ranking ranking) {
            recomendations.put(user, ranking);
        }

        @Override
        public void print(PrintStream out) {
            print(out);
        }

        @Override
        public void print(PrintStream out, int userCutoff, int itemCutoff) {
            //print(out + String.valueOf(userCutoff) + String.valueOf(itemCutoff));
        }
    }
}
