package es.uam.eps.bmi.search.index;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class WebCrawler {

    private final int numMaxDoc;
    private PriorityQueue<DocCrawler> colaURL;
    private IndexBuilder index;


    public WebCrawler(IndexBuilder index, int numMaxDoc, String rutaSemilla) throws IOException {
        this.index = index;
        this.numMaxDoc = numMaxDoc;
        this.colaURL = new PriorityQueue<>();

        File filePath = new File(rutaSemilla);


        if (!filePath.exists()) {
            throw new IOException();
        }

        List<String> urls = Files.readAllLines(filePath.toPath());
        urls.forEach(s -> {
            this.colaURL.add(new DocCrawler(s,1));
        });
    }

    public void run(){

        for(int numDoc=0;numDoc<this.numMaxDoc;numDoc++){
            colaURL.addAll(explorar(colaURL.poll()));
        }

    }

    private List<DocCrawler> explorar(DocCrawler doc){
        List<DocCrawler> encontrados = new ArrayList<>();




        return encontrados;
    }


    private class DocCrawler implements Comparator<DocCrawler>{

        private String url;
        private double priority;

        DocCrawler(String url, double priority){
            this.url = url;
            this.priority = priority;
        }

        @Override
        public int compare(DocCrawler o1, DocCrawler o2) {
            return Double.compare(o2.priority,o1.priority);
        }

        public void setPriority(double priority){
            this.priority = priority;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DocCrawler that = (DocCrawler) o;

            if (Double.compare(that.priority, priority) != 0) return false;
            return url.equals(that.url);
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = url.hashCode();
            temp = Double.doubleToLongBits(priority);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
    }
}


