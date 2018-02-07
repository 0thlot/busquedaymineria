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

        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        Directory directory = FSDirectory.open(Paths.get(indexPath));
        this.m_indexWriter = new IndexWriter(directory,config);


        FieldType typeText = new FieldType();
        FieldType typeUrl = new FieldType();
        typeText.setStored(true);
        typeText.setIndexOptions(IndexOptions.DOCS_AND_FREQS);

        if(collectionPath.contains(".txt")){

        }else if(collectionPath.contains(".zip")){

        }//caso carpeta
        else{
            File dir = new File(collectionPath);
            File[] files = dir.listFiles();

            for(File f: files){
                Document doc = new Document();
                Field urlField = new Field("path",f.getCanonicalPath(),typeUrl);
                org.jsoup.nodes.Document d = Jsoup.parse(f,"UTF-8");
                Field contField = new Field("texto",d.outerHtml(),typeText);
                doc.add(urlField);
                doc.add(contField);
                this.m_indexWriter.addDocument(doc);

            }
        }




















        this.m_indexWriter.close();



    }
}
