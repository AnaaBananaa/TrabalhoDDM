package com.example.t1;

import com.example.t1.DAO.ManterUsuario;
import com.example.t1.apresentador.CadastroActivityApresentador;
import com.example.t1.contrato.ContratoCadastro;
import com.example.t1.modelo.Usuario;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CadastroActivityApresentadorTest {
    private ContratoCadastro.ContratoCadastroPresenter apresentador;
    private ViewTest view;
    private static ManterUsuario manterUsuario;

    @BeforeClass
    public static void inicializacaoClasse() {
        manterUsuario = ManterUsuario.getInstance();
    }

    @Before
    public void inicializacaoTeste() {
        view = new ViewTest();
        apresentador = new CadastroActivityApresentador(view);
    }

    @Test
    public void testeCadastroUsuarioInserido() {
        Usuario usuario = manterUsuario.getUsuarios().get("teste@teste.com");
        if (usuario != null) {
            manterUsuario.getUsuarios().remove("teste@teste.com", usuario);
        }
        apresentador.onInsertUser("teste@teste.com", "teste123", "teste");
        Boolean retornoUsuarioEsperado = true;
        Boolean retornoUsuarioObtido = (manterUsuario.getUsuarios().get("teste@teste.com") != null);
        String retornoMensagemEsperado = "Usuário cadastrado com sucesso";
        String retornoMensagemObtido = view.getMensage();
        Boolean retornoActionEsperado = true;
        Boolean retrnoActionRecebido = view.getRealizouAcaoNav();
        assertEquals(retornoUsuarioEsperado,retornoUsuarioObtido);
        assertEquals(retornoMensagemEsperado, retornoMensagemObtido);
        assertEquals(retornoActionEsperado, retrnoActionRecebido);
    }

    @Test
    public void testeCadastroUsuarioInseridoExistente() {
        Usuario usuario = manterUsuario.getUsuarios().get("teste@teste.com");
        if (usuario == null) {
            manterUsuario.insertUsuario("teste","teste123", "teste@teste.com");
        }
        apresentador.onInsertUser("teste@teste.com", "teste123", "teste");
        String retornoMensagemEsperado = "Usuário já cadastrado no sistema";
        String retornoMensagemObtido = view.getMensage();
        Boolean retornoActionEsperado = false;
        Boolean retrnoActionRecebido = view.getRealizouAcaoNav();
        assertEquals(retornoMensagemEsperado, retornoMensagemObtido);
        assertEquals(retornoActionEsperado, retrnoActionRecebido);
    }

    static class ViewTest implements ContratoCadastro.ContratoCadastroView {
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
