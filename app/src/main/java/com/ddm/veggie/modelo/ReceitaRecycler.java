package com.ddm.veggie.modelo;

public class ReceitaRecycler {

    private String firebaseId;
    private String nome;
    private String descricao;
    private int favoriteCount;
    private boolean isFavorited;

    public ReceitaRecycler(String firebaseId, String nome, String descricao, int favoriteCount, boolean isFavorited) {
        this.firebaseId = firebaseId;
        this.nome = nome;
        this.descricao = descricao;
        this.favoriteCount = favoriteCount;
        this.isFavorited = isFavorited;
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

    public boolean isFavorited() {
        return isFavorited;
    }

    public void setIsFavorited(boolean isFavorited) {
        this.isFavorited = isFavorited;
    }
}
