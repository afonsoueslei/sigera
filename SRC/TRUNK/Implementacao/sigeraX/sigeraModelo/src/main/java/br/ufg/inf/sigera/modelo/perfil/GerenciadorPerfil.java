package br.ufg.inf.sigera.modelo.perfil;

import br.ufg.inf.sigera.modelo.AssociacaoPerfilCurso;
import br.ufg.inf.sigera.modelo.Curso;
import br.ufg.inf.sigera.modelo.Professor;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.ldap.UsuarioLdap;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class GerenciadorPerfil {

    private static final List<Perfil> listaPerfis = new ArrayList<Perfil>();

    static {
        listaPerfis.add(new PerfilAluno());
        listaPerfis.add(new PerfilAdministrador());
        listaPerfis.add(new PerfilDiretor());
        listaPerfis.add(new PerfilCoordenadorCurso());
        listaPerfis.add(new PerfilCoordenadorEstagio());
        listaPerfis.add(new PerfilCoordenadorGeral());
        listaPerfis.add(new PerfilProfessor());
        listaPerfis.add(new PerfilSecretaria());
        listaPerfis.add(new PerfilSecretariaGraduacao());
        listaPerfis.add(new PerfilAlunoPosStrictoSensu());
    }

    public static AssociacaoPerfilCurso criePerfil(EnumPerfil tipoPerfil, UsuarioSigera usuario) {

        if (tipoPerfil.equals(EnumPerfil.ADMINISTRADOR_SISTEMA)) {
            return new AssociacaoPerfilCurso(new PerfilAdministrador(), null, usuario);
        } else if (tipoPerfil.equals(EnumPerfil.ALUNO)) {
            return new AssociacaoPerfilCurso(new PerfilAluno(), null, usuario);
        } else if (tipoPerfil.equals(EnumPerfil.COORDENADOR_CURSO)) {
            return new AssociacaoPerfilCurso(new PerfilCoordenadorCurso(), null, usuario);
        } else if (tipoPerfil.equals(EnumPerfil.COORDENADOR_ESTAGIO)) {
            return new AssociacaoPerfilCurso(new PerfilCoordenadorEstagio(), null, usuario);
        } else if (tipoPerfil.equals(EnumPerfil.COORDENADOR_GERAL)) {
            return new AssociacaoPerfilCurso(new PerfilCoordenadorGeral(), null, usuario);
        } else if (tipoPerfil.equals(EnumPerfil.DIRETOR)) {
            return new AssociacaoPerfilCurso(new PerfilDiretor(), null, usuario);
        } else if (tipoPerfil.equals(EnumPerfil.PROFESSOR)) {
            return new AssociacaoPerfilCurso(new PerfilProfessor(), null, usuario);
        } else if (tipoPerfil.equals(EnumPerfil.SECRETARIA)) {
            return new AssociacaoPerfilCurso(new PerfilSecretaria(), null, usuario);
        } else if (tipoPerfil.equals(EnumPerfil.SECRETARIA_GRADUACAO)) {
            return new AssociacaoPerfilCurso(new PerfilSecretariaGraduacao(), null, usuario);
        } else if (tipoPerfil.equals (EnumPerfil.ALUNO_POS_STRICTO_SENSU)){
            return new AssociacaoPerfilCurso(new PerfilAlunoPosStrictoSensu(),null, null, usuario);
        }

        return null;
    }

    public static AssociacaoPerfilCurso criePerfilAdministrador(UsuarioSigera usuario) {
        return new AssociacaoPerfilCurso(new PerfilAdministrador(), null, usuario);
    }

    public static AssociacaoPerfilCurso criePerfilCoordenadorCurso(UsuarioSigera usuario, Curso curso) {
        return new AssociacaoPerfilCurso(new PerfilCoordenadorCurso(), curso, usuario);
    }

    public static AssociacaoPerfilCurso criePerfilAluno(UsuarioSigera usuario) {
        return new AssociacaoPerfilCurso(new PerfilAluno(), obtenhaCurso(usuario.getUsuarioLdap().getUid()), usuario);
    }
    
    public static AssociacaoPerfilCurso criePerfilAlunoPosStrictoSensu(UsuarioSigera usuario, Professor orientador) {
        return new AssociacaoPerfilCurso(new PerfilAluno(), obtenhaCurso(usuario.getUsuarioLdap().getUid()), orientador, usuario);
    }
        

    public static AssociacaoPerfilCurso criePerfilCoordenadorEstagio(UsuarioSigera usuario, Curso curso) {
        return new AssociacaoPerfilCurso(new PerfilCoordenadorEstagio(), curso, usuario);
    }
    
    public static AssociacaoPerfilCurso criePerfilCoordenadorGeral(UsuarioSigera usuario, Curso curso) {
        return new AssociacaoPerfilCurso(new PerfilCoordenadorEstagio(), curso, usuario);
    }

    public static AssociacaoPerfilCurso criePerfilDiretor(UsuarioSigera usuario) {
        return new AssociacaoPerfilCurso(new PerfilDiretor(), null, usuario);
    }

    public static AssociacaoPerfilCurso criePerfilProfessor(UsuarioSigera usuario) {
        return new AssociacaoPerfilCurso(new PerfilProfessor(), null, usuario);
    }

    public static AssociacaoPerfilCurso criePerfilSecretaria(UsuarioSigera usuario) {
        return new AssociacaoPerfilCurso(new PerfilSecretaria(), null, usuario);
    }

    public static AssociacaoPerfilCurso criePerfilSecretariaGraduacao(UsuarioSigera usuario) {
        return new AssociacaoPerfilCurso(new PerfilSecretariaGraduacao(), null, usuario);
    }

    public static AssociacaoPerfilCurso obtenhaAssociacaoPerfil(int idPerfil, int idUsuario) {
        EntityManager em = obtenhaEntityManager();
        String consulta = " SELECT a FROM AssociacaoPerfilCurso as a WHERE a.perfil.id = :idPerfil AND a.usuario.id = :idUsuario ";
        Query query = em.createQuery(consulta);
        query.setParameter("idPerfil", idPerfil);
        query.setParameter("idUsuario", idUsuario);
        AssociacaoPerfilCurso perfil = (AssociacaoPerfilCurso) query.getSingleResult();

        return perfil;
    }

    public static Perfil obtenhaPerfil(int id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("br.ufg.inf.sigera");
        EntityManager em = emf.createEntityManager();
        Perfil perfil = em.find(Perfil.class, id);

        return perfil;
    }

    public static List<Perfil> obtenhaTodosPerfis() {
        return listaPerfis;
    }

    private static Curso obtenhaCurso(String uid) {
        String prefixoCurso = UsuarioLdap.obtenhaPrefixoCurso(uid);

        //Se for aluno de mestrado ou doutorado então seu prefixo/curso será setado como POS      
        if (prefixoCurso.equalsIgnoreCase("msc") || prefixoCurso.equalsIgnoreCase("dsc")) {
            prefixoCurso = "POS";
        }
        
        EntityManager em = obtenhaEntityManager();
        String consulta = " SELECT c FROM Curso as c WHERE c.prefixo = :prefixo ";
        Query query = em.createQuery(consulta);
        query.setParameter("prefixo", prefixoCurso);
        Curso curso = (Curso) query.getSingleResult();

        return curso;
    }

    private static EntityManager obtenhaEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("br.ufg.inf.sigera");
        return emf.createEntityManager();
    }
}
