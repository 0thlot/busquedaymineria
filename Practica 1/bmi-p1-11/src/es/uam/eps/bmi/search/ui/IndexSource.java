package es.uam.eps.bmi.search.ui;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum IndexSource {
    SRC("index/src"),
    DOCS("index/docs"),
    URLS("index/urls");

    private final String route;

    IndexSource(String s) {
        route = s;
    }

    public String route() {
        return route;
    }

    public static String routeIndex(int i){
        List<String> s = Arrays.stream(values()).filter((r)-> r.ordinal()==i).map(IndexSource::route).collect(Collectors.toList());

        return (s.isEmpty())? null : s.get(0);
    }
}
