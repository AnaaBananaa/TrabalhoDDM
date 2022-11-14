package com.ddm.veggie.apresentador;

import com.ddm.veggie.DAO.ManterUsuario;
import com.ddm.veggie.contrato.ContratoChangeName;
import com.ddm.veggie.contrato.ContratoSignIn;
import com.ddm.veggie.singleton.RepositorioUsuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeNameActivityApresentador implements ContratoChangeName.ContratoChangeNamePresenter {

    private ContratoChangeName.ContratoChangeNameView view;

    public ChangeNameActivityApresentador(ContratoChangeName.ContratoChangeNameView view) {
        setView(view);
    }


    @Override
    public void changeName(String name) {
        if (name != null && !name.isEmpty()) {
            boolean result = ManterUsuario.updateUsuarioName(RepositorioUsuario.getInstance().getUsuario().getFirebaseUserId(), name);
            if (result) {
                view.saveSucess();
            } else {
                view.saveFailed();
            }
        } else {
            view.saveFailed();
        }
    }

    @Override
    public void setView(ContratoChangeName.ContratoChangeNameView view) {
        this.view = view;
    }

    @Override
    public void destroyView() {
        view = null;
    }
}
