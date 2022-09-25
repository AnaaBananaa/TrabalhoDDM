package com.example.t1.apresentador;

import com.example.t1.contrato.ContratoCadastro;
import com.example.t1.contrato.ContratoMain;
import com.example.t1.view.CadastroActivity;
import com.example.t1.view.HomeActivity;
import com.example.t1.view.MainActivity;
import com.example.t1.DAO.ManterUsuario;
import com.example.t1.modelo.Usuario;

public class MainActivityApresentador implements ContratoMain.ContratoMainPresenter {

    private ContratoMain.ContratoMainView view;
    private Usuario usuario;

    public MainActivityApresentador(ContratoMain.ContratoMainView view) {
        setView(view);
    }

    @Override
    public void onVerifyUser(String email, String senha){
        usuario = ManterUsuario.getInstance().getUsuarios().get(email);
        if((usuario != null) && (usuario.getSenha().equals(senha))) {
            view.onShowToast("Login efetuado com sucesso!");
            view.onNavToActivity(HomeActivity.class);
        }
        else {
            view.onShowToast("Login e/ou senha inválidos!");
        }
    }

    @Override
    public void onNavToSingUp(){
        view.onNavToActivity(CadastroActivity.class);
    }

    @Override
    public void setView(ContratoMain.ContratoMainView view) {
        this.view = view;
    }

    @Override
    public void destroyView() {
        view = null;
    }
}
