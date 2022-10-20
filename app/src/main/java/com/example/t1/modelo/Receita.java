package com.example.t1.modelo;

public class Receita {

    private String titulo;
    private String caminhoImagem;
    private String descricao;

    public Receita(String titulo, String caminhoImagem, String descricao) {
        this.titulo = titulo;
        this.caminhoImagem = caminhoImagem;
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
