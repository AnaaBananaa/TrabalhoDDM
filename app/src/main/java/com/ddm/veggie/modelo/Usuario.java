package com.ddm.veggie.modelo;

import java.util.List;

public class Usuario {

    private String firebaseUserId;
    private String email;
    private String nome;
    private List<Receita> favoriteRecipes;

    public Usuario(String firebaseUserId, String email, String nome, List<Receita> favoriteRecipes) {
        this.firebaseUserId = firebaseUserId;
        this.email = email;
        this.nome = nome;
        this.favoriteRecipes = favoriteRecipes;
    }

    public String getFirebaseUserId() {
        return firebaseUserId;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Receita> getFavoriteRecipes() {
        return favoriteRecipes;
    }

}
