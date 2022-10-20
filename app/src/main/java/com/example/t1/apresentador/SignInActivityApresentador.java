package com.example.t1.apresentador;

import com.example.t1.contrato.ContratoSignIn;
import com.example.t1.DAO.ManterUsuario;
import com.example.t1.modelo.Usuario;

public class SignInActivityApresentador implements ContratoSignIn.ContratoSignInPresenter {

    private ContratoSignIn.ContratoSignInView view;

    public SignInActivityApresentador(ContratoSignIn.ContratoSignInView view) {
        setView(view);
    }

    @Override
    public void onVerifyUser(String email, String senha){
        Usuario usuario = ManterUsuario.getInstance().getUsuarios().get(email);
        if((usuario != null) && (usuario.getSenha().equals(senha))) {
            view.onShowToast("Login efetuado com sucesso!");
            view.onNavToApp();
        }
        else {
            view.onShowToast("Login e/ou senha inválidos!");
        }
    }

    @Override
    public void setView(ContratoSignIn.ContratoSignInView view) {
        this.view = view;
    }

    @Override
    public void destroyView() {
        view = null;
    }
}
