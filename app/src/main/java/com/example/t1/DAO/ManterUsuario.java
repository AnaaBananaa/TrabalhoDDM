package com.example.t1.DAO;

import com.example.t1.modelo.Receita;
import com.example.t1.modelo.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManterUsuario {
    private static ManterUsuario instance = new ManterUsuario();
    private Map<String, Usuario> usuarios = new HashMap<>();

    public static ManterUsuario getInstance() {
        if (instance == null) {
            instance = new ManterUsuario();
        }
        return instance;
    }

    public void insertUsuario(String nome, String senha, String email){
        ManterUsuario.getInstance().getUsuarios().put(email, new Usuario(nome, senha, email));
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
