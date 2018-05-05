package es.uam.eps.bmi.recsys.test;

import es.uam.eps.bmi.recsys.data.*;
import es.uam.eps.bmi.recsys.metric.Metric;
import es.uam.eps.bmi.recsys.metric.Precision;
import es.uam.eps.bmi.recsys.metric.Recall;
import es.uam.eps.bmi.recsys.metric.Rmse;
import es.uam.eps.bmi.recsys.recommender.UserKNNRecommender;
import es.uam.eps.bmi.recsys.recommender.similarity.PearsonCorrelacionSimilarity;
import es.uam.eps.bmi.recsys.recommender.similarity.Similarity;
import es.uam.eps.bmi.recsys.util.Timer;

import java.io.FileNotFoundException;

public class StudentTest {

    public static void main (String a[]) throws FileNotFoundException {

    }

    static <F>void testDataset(String ratingsFile, String featuresFile, String separator, Parser<F> featureParser, int user, int item)
            throws FileNotFoundException {
        int n = 100;
        int cutoff = 10;
        int k = 50;
        double threshold = 3;

        Ratings ratings = new RatingsImpl(ratingsFile, separator);
        Features<F> features = new FeaturesImpl<F>(featuresFile, separator, featureParser);

        Test.testData(ratings, features, user, item);

        testRecommenders(ratings, features, k, n, 3, 4);

        Ratings folds[] = ratings.randomSplit(0.8);
        Ratings train = folds[0];
        Ratings test = folds[1];

        Metric metrics[] = new Metric [] {
                new Rmse(test),
                new Precision(test, threshold, cutoff),
                new Recall(test, threshold, cutoff),
        };

        evaluateRecommenders(train, features, k, n, metrics);
    }

    static <F> void testRecommenders(Ratings ratings, Features<F> features, int k, int n, int nUsers, int nItems) throws FileNotFoundException {


        Timer.reset();
        Similarity sim = new PearsonCorrelacionSimilarity(ratings);
        Test.testRecommender(new UserKNNRecommender(ratings, sim, k), n, nUsers, nItems);


    }

    static <U extends Comparable<U>,I extends Comparable<I>,F> void evaluateRecommenders(Ratings ratings, Features<F> features, int k, int n, Metric metrics[]) {

        Timer.reset();
        Similarity sim = new PearsonCorrelacionSimilarity(ratings);
        Test.evaluateRecommender(new UserKNNRecommender(ratings, sim, k), n, metrics);
    }
}
