package com.example.t1.apresentador;

import com.example.t1.CadastroActivity;
import com.example.t1.HomeActivity;
import com.example.t1.SingletonUser;
import com.example.t1.Visao;
import com.example.t1.modelo.Usuario;

public class MainActivityApresentador {

    private Visao view;
    private Usuario usuario;

    public MainActivityApresentador(Visao view) {
        this.view = view;
    }

    public void onVerifyUser(String email, String senha){
        usuario = SingletonUser.getInstance().getUsuarios().get(email);
        if((usuario != null) && (usuario.getSenha().equals(senha))) {
            view.onShowToast("Login efetuado com sucesso!");
            view.onNavToActivity(HomeActivity.class);
        }
        else {
            view.onShowToast("Login e/ou senha inválidos!");
        }
    }

    public void onNavToSingUp(){
        view.onNavToActivity(CadastroActivity.class);
    }
}
