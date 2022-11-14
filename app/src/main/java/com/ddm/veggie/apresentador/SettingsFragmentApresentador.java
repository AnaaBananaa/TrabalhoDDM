package com.ddm.veggie.apresentador;

import com.ddm.veggie.contrato.ContratoSettings;
import com.ddm.veggie.singleton.RepositorioUsuario;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragmentApresentador implements ContratoSettings.ContratoSettingsPresenter {

    private ContratoSettings.ContratoSettingsView view;

    public SettingsFragmentApresentador(ContratoSettings.ContratoSettingsView view){
        setView(view);
    }

    @Override
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        RepositorioUsuario.getInstance().setUsuario(null);
        view.onNavToSignIn();
    }

    @Override
    public void setView(ContratoSettings.ContratoSettingsView view) {
        this.view = view;
    }

    @Override
    public void destroyView() {
        view = null;
    }
}
