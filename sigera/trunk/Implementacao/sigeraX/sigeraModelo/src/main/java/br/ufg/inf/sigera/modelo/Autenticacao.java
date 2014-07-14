package br.ufg.inf.sigera.modelo;

import br.ufg.inf.sigera.modelo.perfil.GerenciadorPerfil;
import br.ufg.inf.sigera.modelo.ldap.AutenticacaoLdap;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import br.ufg.inf.sigera.modelo.ldap.EnumGrupo;
import br.ufg.inf.sigera.modelo.ldap.UsuarioLdap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Autenticacao {

    private UsuarioSigera usuario;
    private UsuarioLdap usuarioLdap;

    public Autenticacao(String nomeUsuario, String senha) {
        this.efetueLogin(nomeUsuario, senha);
    }

    public UsuarioSigera getUsuarioSigera() {
        return usuario;
    }

    public UsuarioLdap getUsuarioLdap() {
        return usuarioLdap;
    }

    public static List<UsuarioSigera> obtenhaUsuarios(BuscadorLdap buscadorLdap) {
        List<UsuarioLdap> usuariosLdap = buscadorLdap.obtenhaTodosUsuariosLdap();
        List<UsuarioSigera> usuarios = new ArrayList<UsuarioSigera>();
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("br.ufg.inf.sigera");
            EntityManager em = emf.createEntityManager();

            for (UsuarioLdap usuarioLdap : usuariosLdap) {
                UsuarioSigera usuario = null;
                try {
                    usuario = em.find(UsuarioSigera.class, Integer.valueOf(usuarioLdap.getUidNumber()));
                } catch (Exception x) {
                    System.out.println("Registro de Usuario inválido!");
                }
                if (usuarioLdap.getUidNumber() != null) {
                    if (usuario == null) {
                        usuario = new UsuarioSigera();
                        usuario.setId(Integer.valueOf(usuarioLdap.getUidNumber()));
                    }
                    usuario.setUsuarioLdap(usuarioLdap);
                    usuarios.add(usuario);
                }
            }
            return usuarios;
        } catch (javax.persistence.PersistenceException ex) {
            Logger.getLogger(Autenticacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
    private UsuarioSigera efetueLogin(String nomeUsuario, String senha) {
        AutenticacaoLdap autenticadorLdap = new AutenticacaoLdap();
        usuarioLdap = autenticadorLdap.efetuaLogin(nomeUsuario, senha);

        if (usuarioLdap == null) {
            return null;
        }
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("br.ufg.inf.sigera");
            EntityManager em = emf.createEntityManager();
            usuario = em.find(UsuarioSigera.class, Integer.valueOf(usuarioLdap.getUidNumber()));

            if (usuario == null) {
                //crie um novo usuarioSigera se ainda não existe no banco
                usuario = new UsuarioSigera();
                usuario.setPrimeiroAcesso(new Date()); //Grava primeiro acesso no Sigera 2.0 
                usuario.setId(Integer.valueOf(usuarioLdap.getUidNumber()));
                

                if (usuarioLdap.getGrupo().equals(EnumGrupo.ALUNO)) {
                    Collection<AssociacaoPerfilCurso> perfis = new ArrayList<AssociacaoPerfilCurso>();
                    AssociacaoPerfilCurso perfilEstudante = GerenciadorPerfil.criePerfilAluno(usuario);
                    perfis.add(perfilEstudante);
                    usuario.setPerfis(perfis);                
                }
                usuario.salvar();
            }
            //Grava primeiro acesso no Sigera 2.0 
            if(usuario.getPrimeiroAcesso()==null){
                usuario.setPrimeiroAcesso(new Date());
                usuario.salvar();                
            }

            usuario.setUsuarioLdap(usuarioLdap);
            return usuario;
        } catch (javax.persistence.PersistenceException ex) {
            Logger.getLogger(Autenticacao.class.getName()).log(Level.SEVERE, null, ex);
            usuario = new UsuarioSigera();
            usuario.setTelefoneComercial("falha-banco");
            return usuario;
        }
    }
}