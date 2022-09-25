package com.example.t1.contrato;

public interface ContratoMain {
    interface ContratoMainView {
        void onNavToActivity(Class classe);
        void onShowToast(String mesage);
    }
    interface ContratoMainPresenter {
        void onVerifyUser(String email, String senha);
        void onNavToSingUp();
        void setView(ContratoMain.ContratoMainView view);
        void destroyView();
    }
}
