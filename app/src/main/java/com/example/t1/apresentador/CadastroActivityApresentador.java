package com.example.t1.apresentador;

import com.example.t1.contrato.ContratoCadastro;
import com.example.t1.view.CadastroActivity;
import com.example.t1.view.MainActivity;
import com.example.t1.DAO.ManterUsuario;
import com.example.t1.modelo.Usuario;

public class CadastroActivityApresentador implements ContratoCadastro.ContratoCadastroPresenter {

    private ContratoCadastro.ContratoCadastroView view;

    public CadastroActivityApresentador(ContratoCadastro.ContratoCadastroView view) {
        setView(view);
    }

    @Override
    public void onInsertUser(String email, String senha, String nome){
        Usuario user = ManterUsuario.getInstance().getUsuarios().get(email);
        if(user == null){
            ManterUsuario.getInstance().insertUsuario(nome,senha, email);
            view.onShowToast("Usuário cadastrado com sucesso");
            view.onNavToActivity(MainActivity.class);
        }else{
            view.onShowToast("Usuário já cadastrado no sistema");
        }
    }

    @Override
    public void onNavToLogin(){
        view.onNavToActivity(MainActivity.class);
    }

    @Override
    public void setView(ContratoCadastro.ContratoCadastroView view) {
        this.view = view;
    }

    @Override
    public void destroyView() {
        view = null;
    }

}
