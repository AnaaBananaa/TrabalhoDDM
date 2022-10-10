package com.example.t1;

import static org.junit.Assert.assertEquals;

import com.example.t1.DAO.ManterUsuario;
import com.example.t1.apresentador.CadastroActivityApresentador;
import com.example.t1.apresentador.MainActivityApresentador;
import com.example.t1.contrato.ContratoCadastro;
import com.example.t1.contrato.ContratoMain;
import com.example.t1.modelo.Usuario;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainActivityApresentadorTest {
    private ContratoMain.ContratoMainPresenter apresentador;
    private ViewTest view;
    private static ManterUsuario manterUsuario;

    @BeforeClass
    public static void inicializacaoClasse() {
        manterUsuario = ManterUsuario.getInstance();
    }

    @Before
    public void inicializacaoTeste() {
        view = new ViewTest();
        apresentador = new MainActivityApresentador(view);
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
        Boolean retrnoActionRecebido = view.getRealizouAcaoNav();
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
        Boolean retrnoActionRecebido = view.getRealizouAcaoNav();
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
        Boolean retrnoActionRecebido = view.getRealizouAcaoNav();
        assertEquals(retornoMensagemEsperado, retornoMensagemObtido);
        assertEquals(retornoActionEsperado, retrnoActionRecebido);
    }

    static class ViewTest implements ContratoMain.ContratoMainView {
        private String mensage;
        private boolean realizouAcaoNav;

        public ViewTest() {
            mensage = "";
            realizouAcaoNav = false;
        }

        @Override
        public void onNavToActivity(Class classe) {
            realizouAcaoNav = true;
        }

        @Override
        public void onShowToast(String mensage) {
            this.mensage = mensage;
        }

        public String getMensage() {
            return mensage;
        }

        public boolean getRealizouAcaoNav() {
            return realizouAcaoNav;
        }
    }
}
