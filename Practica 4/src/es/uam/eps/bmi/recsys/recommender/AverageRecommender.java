package es.uam.eps.bmi.recsys.recommender;

import es.uam.eps.bmi.recsys.Recommendation;
import es.uam.eps.bmi.recsys.data.Ratings;
import es.uam.eps.bmi.recsys.ranking.Ranking;
import es.uam.eps.bmi.recsys.ranking.RankingImpl;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AverageRecommender extends   AbstractRecommender {

    private Map<Integer,Double> ratingSum;

    public AverageRecommender(Ratings ratings, int min){
        super(ratings);
        ratingSum = new HashMap<>();
        for(int item : ratings.getItems()){
            Set<Integer> users = ratings.getUsers(item);
            if(users.size()>=min){
                double score = users.stream().mapToDouble((u)->ratings.getRating(u,item)).sum();
                ratingSum.put(item,score/users.size());
            }

        }
    }

    @Override
    public double score(int user, int item) {
        Double score = ratingSum.get(item);

        return (score==null)?0:score;
    }

    @Override
    public String toString() {
        return "average";
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
