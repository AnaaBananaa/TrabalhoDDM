package com.example.t1.contrato;

public interface ContratoSignIn {
    interface ContratoSignInView {
        void onShowToast(String mesage);
        void onNavToSignUp();
        void onNavToApp();
    }
    interface ContratoSignInPresenter {
        void onVerifyUser(String email, String senha);
        void setView(ContratoSignInView view);
        void destroyView();
    }
}
