package com.example.t1.apresentador;

import com.example.t1.CadastroActivity;
import com.example.t1.HomeActivity;
import com.example.t1.MainActivity;
import com.example.t1.ManterUsuario;
import com.example.t1.modelo.Usuario;

public class MainActivityApresentador {

    private MainActivity view;
    private Usuario usuario;

    public MainActivityApresentador(MainActivity view) {
        this.view = view;
    }

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

    public void onNavToSingUp(){
        view.onNavToActivity(CadastroActivity.class);
    }
}
