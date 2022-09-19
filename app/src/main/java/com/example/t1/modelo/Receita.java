package com.example.t1.modelo;

import android.media.Image;

import java.io.File;

public class Receita {

    private String titulo;
    private File imageReceita;
    private String descricao;

    public Receita(String titulo, File imageReceita, String descricao) {
        this.titulo = titulo;
        this.imageReceita = imageReceita;
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public File getImageReceita() {
        return imageReceita;
    }

    public void setImageReceita(File imageReceita) {
        this.imageReceita = imageReceita;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
