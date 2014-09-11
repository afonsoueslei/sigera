package br.ufg.inf.sigera.modelo;

import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import br.ufg.inf.sigera.modelo.perfil.EnumPerfil;
import br.ufg.inf.sigera.modelo.perfil.Perfil;
import br.ufg.inf.sigera.modelo.servico.Persistencia;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.persistence.Table;

@Entity
@Table(name = "usuario_perfil")
public class AssociacaoPerfilCurso implements Serializable, Comparable<AssociacaoPerfilCurso> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioSigera usuario;

    public AssociacaoPerfilCurso() {
    }

    public AssociacaoPerfilCurso(Perfil perfil, Curso curso, UsuarioSigera usuario) {
        this.perfil = perfil;
        this.usuario = usuario;
        setCurso(curso);
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        if (perfilExigeCursoAssociado(this.perfil)) {
            this.curso = curso;
        } else {
            this.curso = null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UsuarioSigera getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioSigera usuario) {
        this.usuario = usuario;
    }

    public static boolean perfilExigeCursoAssociado(Perfil perfil) {
        return ((perfil != null)
                && (perfilExigeCursoAssociado(perfil.getId())));
    }

    public static boolean perfilExigeCursoAssociado(int codigoPerfil) {
        return (codigoPerfil == EnumPerfil.ALUNO.getCodigo()
                || codigoPerfil == EnumPerfil.COORDENADOR_CURSO.getCodigo()
                || codigoPerfil == EnumPerfil.COORDENADOR_ESTAGIO.getCodigo()
                || codigoPerfil == EnumPerfil.SECRETARIA.getCodigo());
    }

    public int compareTo(AssociacaoPerfilCurso a) {
        return this.perfil.getNome().compareTo(a.perfil.getNome());
    }

    public static List<UsuarioSigera> obtenhaUsuariosDoPerfilCurso(int codigoPerfil, int codigoCurso, BuscadorLdap buscadorLdap) {
        EntityManager em = Persistencia.obterManager();
        StringBuilder consulta = new StringBuilder();
        Query query;
        List<UsuarioSigera> usuarios = null;

        consulta.append(" SELECT apc.usuario ");
        consulta.append(" FROM AssociacaoPerfilCurso as apc ");
        consulta.append(" WHERE (apc.perfil.id = :idPerfil  AND apc.curso.id = :idCurso ) ");
        if (codigoPerfil == EnumPerfil.COORDENADOR_GERAL.getCodigo()) {
            consulta.append(" OR apc.perfil.id = :idPerfilCoordenadorGeral ");
        }

        try {
            query = em.createQuery(consulta.toString());
            query.setParameter("idPerfil", codigoPerfil);
            query.setParameter("idCurso", codigoCurso);

            if (codigoPerfil == EnumPerfil.COORDENADOR_GERAL.getCodigo()) {
                query.setParameter("idPerfilCoordenadorGeral", EnumPerfil.COORDENADOR_GERAL.getCodigo());
            }

            usuarios = query.getResultList();
            for (UsuarioSigera u : usuarios) {
                u.setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(u.getId()));
            }

        } catch (Exception ex) {
            Logger.getLogger(AssociacaoPerfilCurso.class.getName()).log(Level.WARNING, "Erro ao executar consulta: obtenhaUsuarios", ex);
        }

        em.close();
        return usuarios;
    }

    public static List<UsuarioSigera> obtenhaUsuariosDoPerfil(int codigoPerfil, BuscadorLdap buscadorLdap) {
        EntityManager em = Persistencia.obterManager();
        StringBuilder consulta = new StringBuilder();
        Query query;
        List<UsuarioSigera> usuarios = null;

        consulta.append(" SELECT apc.usuario ");
        consulta.append(" FROM AssociacaoPerfilCurso as apc ");
        consulta.append(" WHERE apc.perfil.id = :idPerfil ");

        try {
            query = em.createQuery(consulta.toString());
            query.setParameter("idPerfil", codigoPerfil);

            usuarios = query.getResultList();
            for (UsuarioSigera u : usuarios) {
                u.setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(u.getId()));
            }

        } catch (Exception e) {
            System.out.println("Erro ao executar consulta: obtenhaUsuario" + AssociacaoPerfilCurso.class
                    .toString());
        }

        em.close();
        return usuarios;
    }

}
