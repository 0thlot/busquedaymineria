package es.uam.eps.bmi.search.test;

import es.uam.eps.bmi.search.index.WebCrawler;
import es.uam.eps.bmi.search.index.impl.SerializedRAMIndexBuilder;
import es.uam.eps.bmi.search.util.Timer;

import java.io.IOException;

public class TestCrawler {

    public static void main (String a[]) throws IOException {

        SerializedRAMIndexBuilder index= new SerializedRAMIndexBuilder();
        int maxDoc=1000;
        index.init("index/crawler");
        Timer.reset("Iniciando Crawler");
        WebCrawler wc = new WebCrawler(index,maxDoc,"collections/crawler.txt");
        wc.run();
        Timer.time("Para "+maxDoc+" documentos ha tardado:");
        index.close("index/crawler");
    }
}
