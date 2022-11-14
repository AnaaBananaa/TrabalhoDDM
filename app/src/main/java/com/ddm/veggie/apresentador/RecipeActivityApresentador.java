package com.ddm.veggie.apresentador;

import com.ddm.veggie.DAO.ManterReceita;
import com.ddm.veggie.DAO.ManterUsuario;
import com.ddm.veggie.R;
import com.ddm.veggie.contrato.ContratoRecipe;
import com.ddm.veggie.modelo.Receita;
import com.ddm.veggie.modelo.Usuario;
import com.google.firebase.auth.FirebaseAuth;

public class RecipeActivityApresentador implements ContratoRecipe.ContratoRecipePresenter {

    private ContratoRecipe.ContratoRecipeView view;

    public RecipeActivityApresentador(ContratoRecipe.ContratoRecipeView view){
        setView(view);
    }

    @Override
    public void changeFavorite(String recipeId) {
        boolean userContaisRecipe = false;
        Usuario user = ManterUsuario.getUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid());
        for (Receita recipe: user.getFavoriteRecipes()) {
            if (recipe.getFirebaseId().equals(recipeId)) {
                userContaisRecipe = true;
                break;
            }
        }
        if (userContaisRecipe) {
            ManterUsuario.updateUsuarioRemoveFavoriteRecipe(user.getFirebaseUserId(), recipeId, onCompleteListenerUser -> {
                if(onCompleteListenerUser.isSuccessful()) {
                    ManterReceita.updateReceitaDecreaseFavoriteCount(recipeId, onCompleteListenerRecipe -> {
                        if (onCompleteListenerRecipe.isSuccessful()) {
                            view.disableFavorite();

                        }
                    });
                }
            });
        } else {
            ManterUsuario.updateUsuarioAddFavoriteRecipe(user.getFirebaseUserId(), recipeId, onCompleteListenerUser -> {
                if(onCompleteListenerUser.isSuccessful()) {
                    ManterReceita.updateReceitaIncreaseFavoriteCount(recipeId, onCompleteListenerRecipe -> {
                        if (onCompleteListenerRecipe.isSuccessful()) {
                            view.enableFavorite();
                        }
                    });
                }
            });
        }
    }

    @Override
    public void setView(ContratoRecipe.ContratoRecipeView view) {
        this.view = view;
    }

    @Override
    public void destroyView() {
        view = null;
    }
}
