package com.ddm.veggie.contrato;

import android.content.Context;

import com.ddm.veggie.modelo.Receita;

import java.util.List;

public interface ContratoSplash {
    interface ContratoSplashView {
        void onNavToApp();
        void onNavToSignIn();
    }
    interface ContratoSplashPresenter {
        void verifyCurrentUser();
        void setView(ContratoSplashView view);
        void destroyView();
    }
}
