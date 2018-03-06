package es.uam.eps.bmi.search.index.impl;

import es.uam.eps.bmi.search.index.AbstractIndexBuilder;
import es.uam.eps.bmi.search.index.Config;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.structure.Posting;
import es.uam.eps.bmi.search.index.structure.impl.ImplPostingList;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SerializedRAMIndexBuilder extends AbstractIndexBuilder implements Serializable{

    private String indexPath;
    private HashMap<String, ImplPostingList> diccionario;
    private List<ImplDoc> documents;
    private int numDocs;

    public SerializedRAMIndexBuilder(){}

    public int getNumDocs() { return numDocs; }

    public List<ImplDoc> getDocuments() { return documents; }

    public HashMap<String, ImplPostingList> getDiccionario() { return diccionario; }

    @Override
    protected void indexText(String text, String path) throws IOException {
        //Generar un nuevo 'documento'
        ImplDoc doc = new ImplDoc(text,path);
        documents.add(doc);
        //Recorremos el documento para crear la lista de postings
        for (String term: text.toLowerCase().split(" ")){
            addTerm(term);
        }
        numDocs++;
    }

    @Override
    protected Index getCoreIndex() throws IOException {
        return new SerializedRAMIndex(indexPath);
    }

    @Override
    public void build(String collectionPath, String indexPath) throws IOException {
        /*A partir de collectionPat, abrimos los f*/
        this.indexPath = indexPath;
        clear(indexPath); //Borramos cualquier fichero que puediera contener el directorio

        //inicializar la estructura para guardar los documentos
        numDocs=0;
        diccionario = new HashMap<>();
        documents = new ArrayList<>();

        File f = new File(collectionPath);
        if (f.exists()) {
            if (f.isDirectory()) indexFolder(f);                // A directory containing text files.
            else if (f.getName().endsWith(".zip")) indexZip(f); // A zip file containing compressed text files.
            else indexURLs(f);                                  // A file containing a list of URLs.
        }else
            System.out.println("El path no vale: "+collectionPath);

        serialize(indexPath);
        saveDocNorms(indexPath);
    }

    private void serialize(String indexPath){
        try{
            FileOutputStream fileOut = new FileOutputStream(indexPath+ Config.INDEX_FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void addTerm(String term){
        Posting posting = new Posting(numDocs,1);
        //El termino se encuentra en el diccionario
        if (diccionario.containsKey(term)){
            //El documento ya esta en la lista de postings del termino
            if (diccionario.get(term).contains(posting)){
               // diccionario.get(term).get(posting).add1();
            }else {
                diccionario.get(term).add(posting);
            }
        }else{
            ImplPostingList postingsList = new ImplPostingList();
            postingsList.add(posting);
            diccionario.put(term, postingsList);
        }
    }
}
