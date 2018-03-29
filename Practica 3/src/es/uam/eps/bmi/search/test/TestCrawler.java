package es.uam.eps.bmi.search.test;

import es.uam.eps.bmi.search.index.WebCrawler;
import es.uam.eps.bmi.search.index.impl.SerializedRAMIndexBuilder;

import java.io.IOException;

public class TestCrawler {

    public static void main (String a[]) throws IOException {

        SerializedRAMIndexBuilder index= new SerializedRAMIndexBuilder();
        index.init("");
        WebCrawler wc = new WebCrawler(index,1000,"collections/crawler.txt");
        wc.run();
    }
}
