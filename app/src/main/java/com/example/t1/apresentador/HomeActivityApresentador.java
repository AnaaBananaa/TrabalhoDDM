package com.example.t1.apresentador;

import com.example.t1.DAO.ManterReceita;
import com.example.t1.contrato.ContratoHome;
import com.example.t1.view.HomeActivity;
import com.example.t1.modelo.Receita;

import java.util.List;

public class HomeActivityApresentador implements ContratoHome.ContratoHomePresenter {

    private ContratoHome.ContratoHomeView view;

    public HomeActivityApresentador(ContratoHome.ContratoHomeView view){
        setView(view);
    }

    @Override
    public List<Receita> buscaReceitas(String tituloBusca){
        return ManterReceita.getInstance().buscaReceitaPorTitulo(tituloBusca);
    }

    @Override
    public void setView(ContratoHome.ContratoHomeView view) {
        this.view = view;
    }

    @Override
    public void destroyView() {
        view = null;
    }
}
