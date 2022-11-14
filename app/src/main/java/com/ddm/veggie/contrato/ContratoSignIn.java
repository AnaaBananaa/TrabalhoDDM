package com.ddm.veggie.contrato;

public interface ContratoSignIn {
    interface ContratoSignInView {
        void closeLoadingDialog();
        void emptyEmail();
        void emptyPassword();
        void signInFailed();
        void signInSucess();
    }
    interface ContratoSignInPresenter {
        void signIn(String email, String senha);
        void setView(ContratoSignInView view);
        void destroyView();
    }
}
