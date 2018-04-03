package es.uam.eps.bmi.search.index;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/** Clase que genera un Crawler.
 *
 * @author oscar
 * @author jorge
 */
public class WebCrawler {

    private final int numMaxDoc;
    private final Path pathGrafo;
    private PriorityQueue<DocCrawler> colaURL;
    private IndexBuilder index;
    private int numDoc=0;
    private Set<String> urlsBase = new HashSet<>();
    private Set<String> urlsDisallow = new HashSet<>();

    /**
     *
     * @param index Indice inicializado
     * @param numMaxDoc Numero maximo de documentos
     * @param rutaSemilla ruta del fichero donde estan las semillas
     * @throws IOException
     */
    public WebCrawler(IndexBuilder index, int numMaxDoc, String rutaSemilla) throws IOException {
        this.index = index;
        this.numMaxDoc = numMaxDoc;
        this.colaURL = new PriorityQueue<>();

        File grafoFile= new File("graph/", Config.GRAPH_FILE);
        grafoFile.getParentFile().mkdirs();
        grafoFile.createNewFile();
        this.pathGrafo = grafoFile.toPath();

        File filePath = new File(rutaSemilla);


        if (!filePath.exists()) {
            throw new IOException();
        }

        List<String> urls = Files.readAllLines(filePath.toPath());
        urls.forEach(s -> {
            try {
                this.colaURL.add(new DocCrawler(s,1));
            } catch (MalformedURLException e) {
            }
        });
    }

    /**
     * Metodo que lanza el Crawler
     * @throws IOException
     */
    public void run() throws IOException{

        while(this.numDoc<this.numMaxDoc){
            colaURL.addAll(explorar(colaURL.poll()));
        }


    }

    /**
     * Explora la web sacada de la cola de prioridad
     * @param doc DocCraler donde esta la ruta
     * @return Lista con las webs nuevas a explorar
     */
    private List<DocCrawler> explorar(DocCrawler doc){
        List<DocCrawler> encontrados = new ArrayList<>();
        int prioridad = ThreadLocalRandom.current().nextInt(1, 60 + 1);
        Document d = null;

        //Volvemos a meter la url con otra prioridad
        doc.setPriority(prioridad);
        encontrados.add(doc);

        String url = doc.url.getProtocol() + "://" + doc.url.getHost();
        //System.out.println("[INFO] Procesando la url: "+doc.url);

        if(!urlsBase.contains(url)) {
            //Si la url es nueva, procesamos su robots.txt
            String robotsUrl = url + "/robots.txt";
            urlsBase.add(url);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(robotsUrl).openStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.toLowerCase().startsWith("disallow:") && line.split(" ").length>1) {
                        String aux = line.split(" ")[1];
                        while (aux.length() > 0 && (aux.charAt(aux.length() - 1) == '*' || aux.charAt(aux.length() - 1) == '/')) {
                            aux = aux.substring(0, aux.length() - 1);
                        }
                        urlsDisallow.add(url + aux);
                    }else if(line.toLowerCase().startsWith("allow:") && line.split(" ").length>1){
                        String aux = line.split(" ")[1];
                        while (aux.length() > 0 && (aux.charAt(aux.length() - 1) == '*' || aux.charAt(aux.length() - 1) == '/')) {
                            aux = aux.substring(0, aux.length() - 1);
                        }
                        encontrados.add(new DocCrawler(url+aux,ThreadLocalRandom.current().nextInt(1, prioridad + 1)));
                    }
                }
            } catch (IOException e) {
                //System.out.println("[WARNING] Al leer " + robotsUrl);
            }
        }
           this.numDoc++;

            try {
                d = Jsoup.connect(url).validateTLSCertificates(false).timeout(10000).get();
                this.index.indexHTML(d.body().text(), doc.url.toString());

                for (Element e : d.select("a[href]")) {
                    String enlace = e.attr("abs:href");
                    if(urlsDisallow.stream().noneMatch(s -> s.startsWith(enlace))){
                        encontrados.add(new DocCrawler(enlace,ThreadLocalRandom.current().nextInt(1, prioridad + 1)));
                        Files.write(pathGrafo,(doc.url+"\t"+enlace+"\n").getBytes(), StandardOpenOption.APPEND);
                    }
                }
            } catch (Exception e) {
               // System.out.println("[ERROR] Al conectar con la url(" + url + ") o en el indice");
            }

        return encontrados;
    }

    /**
     * Clase interna para dotar a un Path una prioridad
     */
    private class DocCrawler implements Comparator<DocCrawler>,Comparable<DocCrawler>{

        private URL url;
        private int priority;

        /**
         *
         * @param url Ruta de la web
         * @param priority Su prioridad
         * @throws MalformedURLException
         */
        DocCrawler(String url, int priority) throws MalformedURLException {
            this.url = new URL(url);
            this.priority = priority;
        }

        @Override
        public int compare(DocCrawler o1, DocCrawler o2) {
            return Double.compare(o1.priority,o2.priority);
        }

        void setPriority(int priority){
            this.priority = priority;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DocCrawler that = (DocCrawler) o;

            return priority == that.priority && url.equals(that.url);
        }

        @Override
        public int hashCode() {
            int result = url.hashCode();
            result = 31 * result + priority;
            return result;
        }

        @Override
        public int compareTo(DocCrawler o) {
            return priority-o.priority;
        }
    }
}


