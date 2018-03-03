package es.uam.eps.bmi.search.index.impl;

public class ImplDoc {

    private String content;
    private String path;

    public ImplDoc(String text, String path){
        this.content=text;
        this.path=path;
    }

    public String getContent() { return content; }

    public String getPath() { return path; }
}
