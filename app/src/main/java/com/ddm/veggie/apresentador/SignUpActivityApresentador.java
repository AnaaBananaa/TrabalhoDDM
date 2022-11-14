package com.ddm.veggie.apresentador;

import com.ddm.veggie.DAO.ManterUsuario;
import com.ddm.veggie.contrato.ContratoSignUp;
import com.ddm.veggie.singleton.RepositorioUsuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivityApresentador implements ContratoSignUp.ContratoSignUpPresenter {

    private ContratoSignUp.ContratoSignUpView view;

    public SignUpActivityApresentador(ContratoSignUp.ContratoSignUpView view) {
        setView(view);
    }

    @Override
    public void signUp(String email, String senha, String nome){
        if (email != null && !email.isEmpty() && senha != null && !senha.isEmpty() && nome != null && !nome.isEmpty()) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        String fireBaseUserId = user.getUid();
                        ManterUsuario.createUsuario(fireBaseUserId, email, nome);
                        RepositorioUsuario.getInstance().setUsuario(ManterUsuario.getUsuario(fireBaseUserId));
                        view.closeLoadingDialog();
                        view.signUpSucess();
                    } else {
                        view.closeLoadingDialog();
                        view.signUpFailed();
                    }
                } else {
                    view.closeLoadingDialog();
                    view.signUpFailed();
                }
            });
        }  else {
            if (email == null || email.isEmpty()) {
                view.emptyEmail();
            }
            if (senha == null || senha.isEmpty()) {
                view.emptyPassword();
            }
            if (nome == null || nome.isEmpty()) {
                view.emptyName();
            }
            view.closeLoadingDialog();
        }
    }

    @Override
    public void setView(ContratoSignUp.ContratoSignUpView view) {
        this.view = view;
    }

    @Override
    public void destroyView() {
        view = null;
    }

}
