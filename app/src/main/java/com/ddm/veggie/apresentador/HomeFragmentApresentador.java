package com.ddm.veggie.apresentador;

import com.ddm.veggie.DAO.ManterUsuario;
import com.ddm.veggie.contrato.ContratoHome;
import com.ddm.veggie.modelo.Receita;
import com.ddm.veggie.singleton.RepositorioUsuario;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentApresentador implements ContratoHome.ContratoHomePresenter {

    private ContratoHome.ContratoHomeView view;
    private List<Receita> favoriteRecipes;

    public HomeFragmentApresentador(ContratoHome.ContratoHomeView view){
        setView(view);
        favoriteRecipes = new ArrayList<>();
    }

    @Override
    public String getUsername() {
        return RepositorioUsuario.getInstance().getUsuario().getNome();
    }

    @Override
    public List<Receita> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    @Override
    public void updateFavoriteRecipesContent() {
        favoriteRecipes = ManterUsuario.getUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid()).getFavoriteRecipes();
    }

    @Override
    public void updateFrameContent() {
        if (favoriteRecipes.size() > 0) {
            view.showWithContent();
        } else {
            view.showWithoutContent();
        }
    }

    @Override
    public void setView(ContratoHome.ContratoHomeView view) {
        this.view = view;
    }

    @Override
    public void destroyView() {
        view = null;
    }
}
