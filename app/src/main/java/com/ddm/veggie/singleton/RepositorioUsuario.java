package com.ddm.veggie.singleton;

import com.ddm.veggie.modelo.Usuario;

public class RepositorioUsuario {
    private static RepositorioUsuario instance;
    private Usuario usuario;

    private RepositorioUsuario() {
        this.usuario = null;
    }

    public static RepositorioUsuario getInstance() {
        if (instance == null) {
            instance = new RepositorioUsuario();
        }
        return instance;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
