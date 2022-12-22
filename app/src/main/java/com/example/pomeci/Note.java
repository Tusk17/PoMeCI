package com.example.pomeci;

public class Note {
    private String dir;
    private String titulo;
    public Note(){}

    public Note(String dir){
        this.dir=dir;
    }

    public Note(String titulo, String dir) {
        this.titulo=titulo;
        this.dir=dir;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDir() {
        return dir;
    }

}
