package es.uam.eps.bmi.search.index.lucene;

import es.uam.eps.bmi.search.index.IndexBuilder;
import es.uam.eps.bmi.search.index.freq.TermFreq;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jsoup.Jsoup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.apache.lucene.search.similarities.SimilarityBase.log2;
/** Permite generar un indice desde:
 *
 * - Una carpeta (html, pdf, texto plano)
 * - .txt con direcciones
 * - .zip (html)
 *
 * @version 1.0
 * @author jorge
 * @author oscar
 */
public class LuceneBuilder implements IndexBuilder{

    private IndexWriter m_indexWriter;


    public LuceneBuilder() {
    }

    @Override
    public void build(String collectionPath, String indexPath) throws IOException {

        File filePath = new File(collectionPath);

        if (!filePath.exists()) {
            throw new IOException();
        }

        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        Directory directory = FSDirectory.open(Paths.get(indexPath));
        this.m_indexWriter = new IndexWriter(directory,config);


        if(collectionPath.endsWith(".txt")){
            List<String> strings = Files.readAllLines(filePath.toPath());
            strings.forEach(s -> {
                try {
                    org.jsoup.nodes.Document d =Jsoup.connect(s).validateTLSCertificates(false).get();
                    addDocumento(d);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        }else if(collectionPath.endsWith(".zip")){
            ZipFile zipFile = new ZipFile(collectionPath);
            List<? extends ZipEntry> files = zipFile.stream().filter((f)-> !f.isDirectory()).collect(Collectors.toList());

            files.parallelStream().forEach(f -> {
                InputStream is;
                try {
                    is = zipFile.getInputStream(f);
                    org.jsoup.nodes.Document d = Jsoup.parse(is,"UTF-8",filePath.getAbsolutePath()+"\\"+f.getName());
                    if(is != null)is.close();
                    addDocumento(d);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        }else if(filePath.isDirectory()){
            java.util.logging.Logger.getLogger("org.apache.pdfbox")
                    .setLevel(java.util.logging.Level.OFF);
            File[] files = filePath.listFiles();
            Arrays.stream(files).filter((f)-> !f.isDirectory()).forEach((f) -> {
                try{
                    org.jsoup.nodes.Document d=null;
                    if(f.getName().endsWith(".pdf")){
                        PDDocument pdf = PDDocument.load(f);
                        PDFTextStripper pdfParse = new PDFTextStripper();
                        String pdfText = pdfParse.getText(pdf);
                        d = Jsoup.parse(pdfText,f.getAbsolutePath());
                        pdf.close();
                    }else{
                        d = Jsoup.parse(f, "UTF-8");
                    }
                    addDocumento(d);

                }catch (IOException e){
                    throw new RuntimeException(e);
                }

            });
        }else{
            throw new IOException();
        }
        this.m_indexWriter.close();

        writeModulo(indexPath);
    }

    private void writeModulo(String indexRuta) throws IOException {
        LuceneIndex i = new LuceneIndex(indexRuta);
        int numeroDocumentos = i.getIndexReader().numDocs();
        Path ruta = Paths.get(indexRuta+"/modulo.txt");
        double sum=0, tf=0,idf=0;


        try (BufferedWriter w = Files.newBufferedWriter(ruta)) {
            for (int docID = 0; docID < numeroDocumentos; docID++) {
                sum=0;
                for(TermFreq term:i.getDocVector(docID)){
                    tf=(term.getFreq()>0)?1+log2(term.getFreq()):0;
                    idf = (log2((double)1+(numeroDocumentos/(1+i.getTotalFreq(term.getTerm())))));
                    sum+= Math.pow(tf,2)* Math.pow(idf,2);
                }
                w.write(docID+"\t"+Math.sqrt(sum));
                w.newLine();
            }
        }
    }

    private void addDocumento(org.jsoup.nodes.Document d ) throws IOException {

        if(d==null)return;

        Document doc = new Document();

        Field urlField = new StringField("path", d.baseUri(), Field.Store.YES);
        doc.add(urlField);

        FieldType typeText  = new FieldType();
        typeText.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
        typeText.setStoreTermVectors(true);

        Field contField = new Field("texto", d.text(), typeText);
        doc.add(contField);

        this.m_indexWriter.addDocument(doc);
    }
}
