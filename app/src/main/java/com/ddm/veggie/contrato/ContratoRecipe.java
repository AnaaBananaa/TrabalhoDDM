package com.ddm.veggie.contrato;

import com.ddm.veggie.modelo.Receita;

import java.util.List;
import java.util.Map;

public interface ContratoRecipe {
    interface ContratoRecipeView {
        void enableFavorite();
        void disableFavorite();
    }
    interface ContratoRecipePresenter {
        void changeFavorite(String recipeId);
        void setView(ContratoRecipeView view);
        void destroyView();
    }
}
