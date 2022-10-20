package com.example.t1;

import static org.junit.Assert.assertEquals;

import com.example.t1.DAO.ManterUsuario;
import com.example.t1.apresentador.SignInActivityApresentador;
import com.example.t1.contrato.ContratoSignIn;
import com.example.t1.modelo.Usuario;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SignInActivityApresentadorTest {
    private ContratoSignIn.ContratoSignInPresenter apresentador;
    private ViewTest view;
    private static ManterUsuario manterUsuario;

    @BeforeClass
    public static void inicializacaoClasse() {
        manterUsuario = ManterUsuario.getInstance();
    }

    @Before
    public void inicializacaoTeste() {
        view = new ViewTest();
        apresentador = new SignInActivityApresentador(view);
    }

    @Test
    public void testeLoginUsuarioInseridoInexistente() {
        Usuario usuario = manterUsuario.getUsuarios().get("teste@teste.com");
        if (usuario != null) {
            manterUsuario.getUsuarios().remove("teste@teste.com", usuario);
        }
        apresentador.onVerifyUser("teste@teste.com", "teste123");
        String retornoMensagemEsperado = "Login e/ou senha inválidos!";
        String retornoMensagemObtido = view.getMensage();
        Boolean retornoActionEsperado = false;
        Boolean retrnoActionRecebido = view.getRealizouNavToApp();
        assertEquals(retornoMensagemEsperado, retornoMensagemObtido);
        assertEquals(retornoActionEsperado, retrnoActionRecebido);
    }

    @Test
    public void testeLoginUsuarioInseridoExistente() {
        Usuario usuario = manterUsuario.getUsuarios().get("teste@teste.com");
        if (usuario == null) {
            manterUsuario.insertUsuario("teste","teste123", "teste@teste.com");
        } else {
            usuario.setSenha("teste123");
        }
        apresentador.onVerifyUser("teste@teste.com", "teste123");
        String retornoMensagemEsperado = "Login efetuado com sucesso!";
        String retornoMensagemObtido = view.getMensage();
        Boolean retornoActionEsperado = true;
        Boolean retrnoActionRecebido = view.getRealizouNavToApp();
        assertEquals(retornoMensagemEsperado, retornoMensagemObtido);
        assertEquals(retornoActionEsperado, retrnoActionRecebido);
    }

    @Test
    public void testeLoginUsuarioInseridoSenhaIncorreta() {
        Usuario usuario = manterUsuario.getUsuarios().get("teste@teste.com");
        if (usuario == null) {
            manterUsuario.insertUsuario("teste","teste123", "teste@teste.com");
        } else {
            usuario.setSenha("teste123");
        }
        apresentador.onVerifyUser("teste@teste.com", "teste124");
        String retornoMensagemEsperado = "Login e/ou senha inválidos!";
        String retornoMensagemObtido = view.getMensage();
        Boolean retornoActionEsperado = false;
        Boolean retrnoActionRecebido = view.getRealizouNavToApp();
        assertEquals(retornoMensagemEsperado, retornoMensagemObtido);
        assertEquals(retornoActionEsperado, retrnoActionRecebido);
    }

    static class ViewTest implements ContratoSignIn.ContratoSignInView {
        private String mensage;
        private boolean realizouNavToSignUp;
        private boolean isRealizouNavToApp;

        public ViewTest() {
            mensage = "";
            realizouNavToSignUp = false;
            isRealizouNavToApp = false;
        }

        @Override
        public void onShowToast(String mensage) {
            this.mensage = mensage;
        }

        @Override
        public void onNavToSignUp() {
            realizouNavToSignUp = true;
        }

        @Override
        public void onNavToApp() {
            isRealizouNavToApp = true;
        }

        public String getMensage() {
            return mensage;
        }

        public boolean getRealizouNavToSignUp() {
            return realizouNavToSignUp;
        }

        public boolean getRealizouNavToApp() {
            return isRealizouNavToApp;
        }
    }
}
