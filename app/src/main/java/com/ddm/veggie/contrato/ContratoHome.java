package com.ddm.veggie.contrato;

import com.ddm.veggie.modelo.Receita;

import java.util.List;

public interface ContratoHome {
    interface ContratoHomeView {
        void showWithContent();
        void showWithoutContent();
    }
    interface ContratoHomePresenter {
        String getUsername();
        List<Receita> getFavoriteRecipes();
        void updateFavoriteRecipesContent();
        void updateFrameContent();
        void setView(ContratoHomeView view);
        void destroyView();
    }
}
