package com.ddm.veggie.contrato;

public interface ContratoSettings {
    interface ContratoSettingsView {
        void onNavToSignIn();
    }
    interface ContratoSettingsPresenter {
        void signOut();
        void setView(ContratoSettingsView view);
        void destroyView();
    }
}
