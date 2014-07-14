package br.ufg.inf.sigera.modelo.ldap;

import java.util.List;

public interface BuscadorLdap {
    
    public List<UsuarioLdap> obtenhaTodosUsuariosLdap();
    public UsuarioLdap obtenhaUsuarioLdap(int id);
    public UsuarioLdap obtenhaUsuarioLdap(String nomeUsuario);
    
}
