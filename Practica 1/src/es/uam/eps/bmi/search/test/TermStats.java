package es.uam.eps.bmi.search.test;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.lucene.LuceneIndex;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TermStats {
    public static void main (String a[]) throws IOException {
        freqTerminos  ("index/docs");

    }

    private static void freqTerminos(String pathIndex) throws IOException {

        System.out.println("Creando el indice");
        Index index = new LuceneIndex(pathIndex);

        List<String> terms = new ArrayList<>(index.getAllTerms());

        System.out.println("Ordenando por TotalFreq");
        terms.sort((t1, t2) -> {
            try {
                return (int) Math.signum(index.getTotalFreq(t2) - index.getTotalFreq(t1));
            } catch (IOException ex) {
                ex.printStackTrace();
                return 0;
            }
        });

        System.out.println("Obteniendo la ruta termfreq.txt");
        Path ruta = Paths.get("./termfreq.txt");

        System.out.println("Escribiendo termfreq.txt");
        try (BufferedWriter w = Files.newBufferedWriter(ruta)) {
            terms.forEach((t)->{
                try {
                    w.write(t+"\t"+index.getTotalFreq(t));
                    w.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        System.out.println("Ordenando por DocFreq");
        terms.sort((t1, t2) -> {
            try {
                return (int) Math.signum(index.getDocFreq(t2) - index.getDocFreq(t1));
            } catch (IOException ex) {
                ex.printStackTrace();
                return 0;
            }
        });

        System.out.println("Obteniendo la ruta termdocfreq.txt");
        ruta = Paths.get("./termdocfreq.txt");

        System.out.println("Escribiendo termdocfreq.txt");
        try (BufferedWriter w = Files.newBufferedWriter(ruta)) {
            terms.forEach((t)->{
                try {
                    w.write(t+"\t"+index.getDocFreq(t));
                    w.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
