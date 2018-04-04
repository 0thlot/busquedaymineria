package es.uam.eps.bmi.recsys;

import es.uam.eps.bmi.recsys.data.Features;
import es.uam.eps.bmi.recsys.data.Parser;

import java.util.Set;

public class FeaturesImpl<F extends Parser> implements Features<F>{

    public FeaturesImpl(String dataPath, String separator, Parser<F> parser){

    }

    @Override
    public Set getFeatures(int id) {
        return null;
    }

    @Override
    public Double getFeature(int id, F feature) {
        return null;
    }

    @Override
    public void setFeature(int id, F feature, double value) {

    }


    @Override
    public Set<Integer> getIDs() {
        return null;
    }
}
