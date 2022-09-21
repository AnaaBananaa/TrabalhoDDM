package com.example.t1.DAO;

import com.example.t1.modelo.Receita;

import java.util.ArrayList;
import java.util.List;

public class ManterReceita {
    private static ManterReceita instance;
    private List<Receita> receitas = new ArrayList<>();

    public static ManterReceita getInstance() {
        if (instance == null) {
            instance = new ManterReceita();
        }
        return instance;
    }

    public void insertReceita(Receita receita){
        receitas.add(receita);
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public List<Receita> buscaReceitaPorTitulo(String titulo){
        List<Receita> rAux = new ArrayList<>();
        for(Receita r : receitas){
            if(r.getTitulo().contains(titulo)){
                rAux.add(r);
            }
        }
        return rAux;
    }
}
