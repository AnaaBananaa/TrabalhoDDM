package com.ddm.veggie.contrato;

public interface ContratoChangeName {
    interface ContratoChangeNameView {
        void saveSucess();
        void saveFailed();
    }
    interface ContratoChangeNamePresenter {
        void changeName(String name);
        void setView(ContratoChangeNameView view);
        void destroyView();
    }
}
