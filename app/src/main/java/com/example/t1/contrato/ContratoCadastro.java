package com.example.t1.contrato;

public interface ContratoCadastro {
    interface ContratoCadastroView {
        void onNavToActivity(Class classe);
        void onShowToast(String mensage);
    }
    interface ContratoCadastroPresenter {
        void onInsertUser(String email, String senha, String nome);
        void onNavToLogin();
        void setView(ContratoCadastroView view);
        void destroyView();
    }
}
