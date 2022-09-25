package com.example.t1.contrato;

import com.example.t1.modelo.Receita;

import java.util.List;

public interface ContratoHome {
    interface ContratoHomeView {

    }
    interface ContratoHomePresenter {
        List<Receita> buscaReceitas(String tituloBusca);
        void setView(ContratoHome.ContratoHomeView view);
        void destroyView();
    }
}
