package es.uam.eps.bmi.search.index.lucene;

import es.uam.eps.bmi.search.index.IndexBuilder;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

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


        FieldType typeText = new FieldType();
        typeText.setStored(true);
        typeText.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
        FieldType typeUrl = new FieldType();
        typeUrl.setStored(true);


        if(collectionPath.endsWith(".txt")){

        }else if(collectionPath.endsWith(".zip")){

        }else if(filePath.isDirectory()){

            File[] files = filePath.listFiles();
            Arrays.stream(files).parallel().forEach((f) -> {

                try{
                    Document doc = new Document();
                    org.jsoup.nodes.Document d = Jsoup.parse(f, "UTF-8");
                    Field urlField = new Field("path", d.baseUri(), typeUrl);
                    Field contField = new Field("texto", d.normalise().text(), typeText);
                    doc.add(urlField);
                    doc.add(contField);
                    this.m_indexWriter.addDocument(doc);

                }catch (IOException e){
                    throw new RuntimeException(e);
                }

            });


        }else{
            throw new IOException();
        }

        this.m_indexWriter.close();

    }

    private void descomprimir(){

    }
}
