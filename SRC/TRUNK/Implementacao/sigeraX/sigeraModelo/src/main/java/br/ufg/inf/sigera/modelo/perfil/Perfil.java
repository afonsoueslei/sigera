package br.ufg.inf.sigera.modelo.perfil;

import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoPlano;
import br.ufg.inf.sigera.modelo.servico.Persistencia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Table;

@Entity
@Table(name = "perfil")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "id", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Perfil implements Serializable {

    @Id
    private int id;
    private String nome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    protected static EntityManager obtenhaEntityManager() {
        return Persistencia.obterManager();
    }

    public static boolean usuarioTemPerfil(UsuarioSigera usuario, Integer idPerfil) {
        EntityManager em = obtenhaEntityManager();
        Query query;
        StringBuilder consulta = new StringBuilder();
        consulta.append(" SELECT apc.perfil.id ");
        consulta.append(" FROM AssociacaoPerfilCurso as apc ");
        consulta.append(" WHERE (apc.perfil.id = :idPerfil  AND apc.usuario.id = :idUsuario ) ");

        query = em.createQuery(consulta.toString());
        query.setParameter("idPerfil", idPerfil);
        query.setParameter("idUsuario", usuario.getId());
        try {
            Integer codigoPerfil = (Integer) query.getSingleResult();
            if (codigoPerfil == idPerfil) {
                return true;
            }
        } catch (NoResultException nre) {
            Logger.getLogger(Perfil.class.getName()).log(Level.WARNING, "Erro ao executar consulta: usuarioTemPerfil", nre);
            return false;
        }
        return false;
    }

    public static boolean usuarioTemPerfilDoCurso(UsuarioSigera usuario, Integer idPerfil, Integer idCurso) {
        EntityManager em = obtenhaEntityManager();
        Query query;
        StringBuilder consulta = new StringBuilder();
        consulta.append(" SELECT apc.perfil.id ");
        consulta.append(" FROM AssociacaoPerfilCurso as apc ");
        consulta.append(" WHERE (apc.perfil.id = :idPerfil AND apc.curso.id = :idCurso AND apc.usuario.id = :idUsuario ) ");

        query = em.createQuery(consulta.toString());
        query.setParameter("idPerfil", idPerfil);
        query.setParameter("idUsuario", usuario.getId());
        query.setParameter("idCurso", idCurso);

        List<String> resultados = new ArrayList<String>();

        resultados = query.getResultList();

        if ( (!resultados.isEmpty()) && resultados.size()>0 ) {
            try {
                Integer codigoPerfil = (Integer) query.getSingleResult();
                if (codigoPerfil == idPerfil) {
                    return true;
                }
            } catch (NoResultException nre) {
                Logger.getLogger(Perfil.class.getName()).log(Level.WARNING, "Erro ao executar consulta: usuarioTemPerfil", nre);
                return false;
            }
        }
        return false;
    }

    public abstract boolean permiteFazerRequerimento();

    public boolean permiteFazerRequerimentoDaPos() {
        return false;
    }

    public abstract boolean permiteConfigurarSistema();

    public abstract boolean permitePlanoDeAula();

    public abstract boolean permiteEditarPlanoDeAula();

    public abstract boolean permiteEditarTurma();

    public abstract List<Requerimento> obtenhaRequerimentos(UsuarioSigera usuario);

    public abstract List<RequerimentoPlano> obtenhaRequerimentosPlanos(UsuarioSigera usuario);
    
    public abstract List<Requerimento> obtenhaRequerimentosDoCurso(UsuarioSigera usuario);

    public abstract boolean permiteManterUsuarios();

    public abstract boolean permiteImprimirEmenta();

    public abstract boolean permiteCancelarRequerimento();
}
