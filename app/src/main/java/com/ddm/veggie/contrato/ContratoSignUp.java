package com.ddm.veggie.contrato;

public interface ContratoSignUp {
    interface ContratoSignUpView {
        void closeLoadingDialog();
        void emptyEmail();
        void emptyName();
        void emptyPassword();
        void signUpFailed();
        void signUpSucess();
    }
    interface ContratoSignUpPresenter {
        void signUp(String email, String senha, String nome);
        void setView(ContratoSignUpView view);
        void destroyView();
    }
}
