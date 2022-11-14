package com.ddm.veggie.contrato;

import com.ddm.veggie.modelo.Receita;

import java.util.List;
import java.util.Map;

public interface ContratoRecipes {
    interface ContratoRecipesView {

    }
    interface ContratoRecipesPresenter {
        void buscaReceitas(String nome, Map<String, Object> filters);
        List<Receita> getReceitas();
        void setView(ContratoRecipesView view);
        void destroyView();
    }
}
