package com.ddm.veggie.contrato;

public interface ContratoDeleteAccount {
    interface ContratoDeleteAccountView {
        void deleteSucess();
        void deleteFailed();
    }
    interface ContratoDeleteAccountPresenter {
        void deleteAccount();
        void setView(ContratoDeleteAccountView view);
        void destroyView();
    }
}
