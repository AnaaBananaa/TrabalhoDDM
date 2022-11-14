package com.ddm.veggie.apresentador;

import com.ddm.veggie.DAO.ManterReceita;
import com.ddm.veggie.contrato.ContratoRecipes;
import com.ddm.veggie.modelo.Receita;

import java.util.List;
import java.util.Map;

public class RecipesFragmentApresentador implements ContratoRecipes.ContratoRecipesPresenter {

    private ContratoRecipes.ContratoRecipesView view;
    private static List<Receita> receitas;

    public RecipesFragmentApresentador(ContratoRecipes.ContratoRecipesView view){
        setView(view);
    }

    @Override
    public void buscaReceitas(String nome, Map<String, Object> filters){
        receitas = ManterReceita.listReceitas(nome, filters);
    }

    @Override
    public List<Receita> getReceitas() {
        return receitas;
    }

    @Override
    public void setView(ContratoRecipes.ContratoRecipesView view) {
        this.view = view;
    }

    @Override
    public void destroyView() {
        view = null;
    }
}
