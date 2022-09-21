package com.example.t1.apresentador;

import com.example.t1.DAO.ManterReceita;
import com.example.t1.HomeActivity;
import com.example.t1.DAO.ManterUsuario;
import com.example.t1.modelo.Receita;

import java.util.List;

public class HomeActivityApresentador {

    private HomeActivity homeActivity;

    public HomeActivityApresentador(HomeActivity homeActivity){
        this.homeActivity = homeActivity;
    }

    public List<Receita> buscaReceitas(String tituloBusca){
        return ManterReceita.getInstance().buscaReceitaPorTitulo(tituloBusca);
    }
}
