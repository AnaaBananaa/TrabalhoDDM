package com.example.t1.apresentador;

import com.example.t1.CadastroActivity;
import com.example.t1.MainActivity;
import com.example.t1.DAO.ManterUsuario;
import com.example.t1.modelo.Usuario;

public class CadastroActivityApresentador {

    private CadastroActivity view;
    private Usuario usuario;

    public CadastroActivityApresentador(CadastroActivity view) {
        this.view = view;
    }

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

    public void onNavToLogin(){
        view.onNavToActivity(MainActivity.class);
    }

}
