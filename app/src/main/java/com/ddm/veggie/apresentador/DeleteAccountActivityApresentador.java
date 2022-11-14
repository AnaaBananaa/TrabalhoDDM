package com.ddm.veggie.apresentador;

import androidx.annotation.NonNull;

import com.ddm.veggie.DAO.ManterUsuario;
import com.ddm.veggie.contrato.ContratoDeleteAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DeleteAccountActivityApresentador implements ContratoDeleteAccount.ContratoDeleteAccountPresenter {

    private ContratoDeleteAccount.ContratoDeleteAccountView view;

    public DeleteAccountActivityApresentador(ContratoDeleteAccount.ContratoDeleteAccountView view) {
        setView(view);
    }

    @Override
    public void deleteAccount() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        ManterUsuario.deleteUsuario(auth.getCurrentUser().getUid(), onCompleteListener -> {
            if (onCompleteListener.isSuccessful()) {
                auth.getCurrentUser().delete();
                view.deleteSucess();
            } else {
                view.deleteFailed();
            }
        });
    }

    @Override
    public void setView(ContratoDeleteAccount.ContratoDeleteAccountView view) {
        this.view = view;
    }

    @Override
    public void destroyView() {
        view = null;
    }
}
