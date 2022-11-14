package com.ddm.veggie.modelo;

public class Receita {

    private String firebaseId;
    private String nome;
    private String descricao;
    private int favoriteCount;

    public Receita(String firebaseId, String nome, String descricao, int favoriteCount) {
        this.firebaseId = firebaseId;
        this.nome = nome;
        this.descricao = descricao;
        this.favoriteCount = favoriteCount;
    }

    public String getNome() {
        return nome;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }
}
