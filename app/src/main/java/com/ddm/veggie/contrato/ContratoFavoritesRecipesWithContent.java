package com.ddm.veggie.contrato;

import com.ddm.veggie.modelo.Receita;

import java.util.List;

public interface ContratoFavoritesRecipesWithContent {
    interface ContratoFavoritesRecipesWithContentView {

    }
    interface ContratoFavoritesRecipesWithContentPresenter {
        List<Receita> receitasFavoritadas();
        void setView(ContratoFavoritesRecipesWithContentView view);
        void destroyView();
    }
}
