package com.example.t1.DAO;

import com.example.t1.modelo.Receita;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        String busca = titulo.toLowerCase(Locale.ROOT);
        List<Receita> rAux = new ArrayList<>();
        for(Receita r : receitas){
            String rTitulo = r.getTitulo().toLowerCase(Locale.ROOT);
            if(rTitulo.contains(busca)){
                rAux.add(r);
            }
        }
        return rAux;
    }
}
