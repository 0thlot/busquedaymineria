package es.uam.eps.bmi.search.index.impl;

import java.io.Serializable;

public class ImplDoc implements Serializable {

    private String content;
    private String path;

    public ImplDoc(String text, String path){
        this.content=text;
        this.path=path;
    }

    public String getContent() { return content; }

    public String getPath() { return path; }
}
