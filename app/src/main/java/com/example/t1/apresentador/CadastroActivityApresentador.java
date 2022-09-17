package com.example.t1.apresentador;

import com.example.t1.CadastroActivity;
import com.example.t1.MainActivity;
import com.example.t1.SingletonUser;
import com.example.t1.Visao;
import com.example.t1.modelo.Usuario;

public class CadastroActivityApresentador {

    private Visao view;
    private Usuario usuario;

    public CadastroActivityApresentador(Visao view) {
        this.view = view;
    }

    public void onInsertUser(String email, String senha, String nome){
        Usuario user = SingletonUser.getInstance().getUsuarios().get(email);
        if(user == null){
            usuario = new Usuario(nome, senha, email);
            SingletonUser.getInstance().getUsuarios().put(email, usuario);
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
