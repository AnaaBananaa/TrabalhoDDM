package com.example.t1;

import com.example.t1.modelo.Usuario;

import java.util.HashMap;
import java.util.Map;

public class SingletonUser {
    private static SingletonUser instance;
    private Map<String, Usuario> usuarios = new HashMap<>();

    public static SingletonUser getInstance() {
        if (instance == null) {
            instance = new SingletonUser();
        }
        return instance;
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
