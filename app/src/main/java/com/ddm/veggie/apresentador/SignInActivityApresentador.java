package com.ddm.veggie.apresentador;

import com.ddm.veggie.DAO.ManterUsuario;
import com.ddm.veggie.contrato.ContratoSignIn;
import com.ddm.veggie.singleton.RepositorioUsuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivityApresentador implements ContratoSignIn.ContratoSignInPresenter {

    private ContratoSignIn.ContratoSignInView view;

    public SignInActivityApresentador(ContratoSignIn.ContratoSignInView view) {
        setView(view);
    }

    @Override
    public void signIn(String email, String senha){
        if (email != null && !email.isEmpty() && senha != null && !senha.isEmpty()) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        String fireBaseUserId = user.getUid();
                        RepositorioUsuario.getInstance().setUsuario(ManterUsuario.getUsuario(fireBaseUserId));
                        view.closeLoadingDialog();
                        view.signInSucess();
                    } else {
                        view.closeLoadingDialog();
                        view.signInFailed();
                    }
                } else {
                    view.closeLoadingDialog();
                    view.signInFailed();
                }
            });
        } else {
            if (email == null || email.isEmpty()) {
                view.emptyEmail();
            }
            if (senha == null || senha.isEmpty()) {
                view.emptyPassword();
            }
            view.closeLoadingDialog();
        }
    }

    @Override
    public void setView(ContratoSignIn.ContratoSignInView view) {
        this.view = view;
    }

    @Override
    public void destroyView() {
        view = null;
    }
}
