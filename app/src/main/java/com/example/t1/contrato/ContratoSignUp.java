package com.example.t1.contrato;

public interface ContratoSignUp {
    interface ContratoSignUpView {
        void onShowToast(String mensage);
        void onNavToSignIn();
    }
    interface ContratoSignUpPresenter {
        void onInsertUser(String email, String senha, String nome);
        void setView(ContratoSignUpView view);
        void destroyView();
    }
}
