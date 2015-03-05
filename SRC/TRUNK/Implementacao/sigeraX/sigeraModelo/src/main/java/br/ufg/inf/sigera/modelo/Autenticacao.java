package br.ufg.inf.sigera.modelo;

import br.ufg.inf.sigera.modelo.perfil.GerenciadorPerfil;
import br.ufg.inf.sigera.modelo.ldap.AutenticacaoLdap;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import br.ufg.inf.sigera.modelo.ldap.EnumGrupo;
import br.ufg.inf.sigera.modelo.ldap.UsuarioLdap;
import br.ufg.inf.sigera.modelo.servico.Persistencia;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

public class Autenticacao {

    private UsuarioSigera usuario;
    private UsuarioLdap usuarioLdap;

    public Autenticacao() {
    }

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
            EntityManager em = Persistencia.obterManager();

            for (UsuarioLdap usuarioLdap : usuariosLdap) {
                UsuarioSigera usuario = null;
                try {
                    usuario = em.find(UsuarioSigera.class, Integer.valueOf(usuarioLdap.getUidNumber()));
                } catch (Exception x) {
                    System.out.println("O Registro do Usuario de uidNumber: " + usuarioLdap.getUidNumber() + " é inválido!");
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

    public static UsuarioSigera obtenhaUsuarioPorUidNumber(BuscadorLdap buscadorLdap, UsuarioLdap userLdap) {
        UsuarioSigera usuarioEncontrado;
        EntityManager em = Persistencia.obterManager();
        try {
            usuarioEncontrado = em.find(UsuarioSigera.class, Integer.parseInt(userLdap.getUidNumber()));
            usuarioEncontrado.setUsuarioLdap(userLdap);
            return usuarioEncontrado;

        } catch (javax.persistence.PersistenceException ex) {
            Logger.getLogger(Autenticacao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
        return null;
    }

    private UsuarioSigera efetueLogin(String nomeUsuario, String senha) {
        AutenticacaoLdap autenticadorLdap = new AutenticacaoLdap();
        usuarioLdap = autenticadorLdap.efetuaLogin(nomeUsuario, senha);

        if (usuarioLdap == null) {
            return null;
        }
        if(usuarioLdap.getUid().equalsIgnoreCase("FalhaConexaoLDAP")){
            return crieUsuarioFalhaConexaoLdap();
        }
        try {
            EntityManager em = Persistencia.obterManager();

            if (em == null) {
                return crieUsuarioFalhaBanco();
            }

            usuario = em.find(UsuarioSigera.class, Integer.valueOf(usuarioLdap.getUidNumber()));

            if (usuario == null) {
                //crie um novo usuarioSigera se ainda não existe no banco
                usuario = new UsuarioSigera();
                usuario.setPrimeiroAcesso(new Date()); //Grava primeiro acesso no Sigera 2.0
                usuario.setUsuarioLdap(usuarioLdap);
                usuario.setId(Integer.valueOf(usuarioLdap.getUidNumber()));

                if (usuarioLdap.getGrupo().equals(EnumGrupo.ALUNO)) {
                    Collection<AssociacaoPerfilCurso> perfis = new ArrayList<AssociacaoPerfilCurso>();

                    //se for aluno regular de Pos Stricto Sensu, cria perfil proprio e associa ao orientador (Administrador = uidNumber 1786)
                    if (usuarioLdap.isAlunoRegularPosStrictoSensu()) {
                         AssociacaoPerfilCurso perfilEstudantePosStrictoSensu = GerenciadorPerfil.criePerfilAlunoPosStrictoSensu(usuario,Professor.obtenhaProfessorPorIdUsuario(1786));
                         perfis.add(perfilEstudantePosStrictoSensu);
                    } else {                     
                        AssociacaoPerfilCurso perfilEstudante = GerenciadorPerfil.criePerfilAluno(usuario);
                        perfis.add(perfilEstudante);
                    }
                    usuario.setPerfis(perfis);
                }
            }

            //Grava primeiro acesso no Sigera 2.0
            if (usuario.getPrimeiroAcesso() == null) {
                usuario.setPrimeiroAcesso(new Date());
            }

            usuario.setUsuarioLdap(usuarioLdap);
            usuario.salvar();
            System.out.println(" >>>>>> Usuario " + usuario.getNome().toUpperCase() + " acabou de ENTRAR no sistema! <<<<<<" );
            return usuario;

        } catch (javax.persistence.PersistenceException ex) {
            Logger.getLogger(Autenticacao.class.getName()).log(Level.SEVERE, null, ex);
            return crieUsuarioFalhaBanco();
        }
    }

    //Usuario temporário apenas para informar que não há conexão com o banco
    public UsuarioSigera crieUsuarioFalhaBanco() {
        usuario = new UsuarioSigera();
        usuario.setTelefoneComercial("falha-banco");
        return usuario;
    }
   
    public UsuarioSigera crieUsuarioFalhaConexaoLdap() {
        usuario = new UsuarioSigera();
        usuario.setTelefoneComercial("falha-conexao-ldap");
        return usuario;
    }
}
