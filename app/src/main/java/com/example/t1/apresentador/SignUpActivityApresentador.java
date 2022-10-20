package com.example.t1.apresentador;

import com.example.t1.contrato.ContratoSignUp;
import com.example.t1.DAO.ManterUsuario;
import com.example.t1.modelo.Usuario;

public class SignUpActivityApresentador implements ContratoSignUp.ContratoSignUpPresenter {

    private ContratoSignUp.ContratoSignUpView view;

    public SignUpActivityApresentador(ContratoSignUp.ContratoSignUpView view) {
        setView(view);
    }

    @Override
    public void onInsertUser(String email, String senha, String nome){
        Usuario user = ManterUsuario.getInstance().getUsuarios().get(email);
        if(user == null){
            ManterUsuario.getInstance().insertUsuario(nome,senha, email);
            view.onShowToast("Usuário cadastrado com sucesso");
            view.onNavToSignIn();
        }else{
            view.onShowToast("Usuário já cadastrado no sistema");
        }
    }

    @Override
    public void setView(ContratoSignUp.ContratoSignUpView view) {
        this.view = view;
    }

    @Override
    public void destroyView() {
        view = null;
    }

}
