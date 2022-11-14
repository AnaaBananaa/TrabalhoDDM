package com.ddm.veggie.apresentador;

import com.ddm.veggie.DAO.ManterUsuario;
import com.ddm.veggie.contrato.ContratoSplash;
import com.ddm.veggie.modelo.Usuario;
import com.ddm.veggie.singleton.RepositorioUsuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivityApresentador implements ContratoSplash.ContratoSplashPresenter {

    private ContratoSplash.ContratoSplashView view;

    public SplashActivityApresentador(ContratoSplash.ContratoSplashView view) {
        setView(view);
    }

    @Override
    public void verifyCurrentUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null) {
            Usuario usuario = ManterUsuario.getUsuario(user.getUid());
            if (usuario != null && usuario.getFirebaseUserId() != null
                    && !usuario.getFirebaseUserId().isEmpty() && usuario.getEmail() != null
                    && !usuario.getEmail().isEmpty() && usuario.getNome() != null
                    && !usuario.getNome().isEmpty() && usuario.getFavoriteRecipes() != null) {
                RepositorioUsuario.getInstance().setUsuario(usuario);
                view.onNavToApp();
            } else {
                view.onNavToSignIn();
            }
        } else {
            view.onNavToSignIn();
        }
    }

    @Override
    public void setView(ContratoSplash.ContratoSplashView view) {
        this.view = view;
    }

    @Override
    public void destroyView() {
        view = null;
    }
}
