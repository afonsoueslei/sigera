package br.ufg.inf.sigera.modelo.perfil;

import br.ufg.inf.sigera.modelo.requerimento.EnumTipoRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import static br.ufg.inf.sigera.modelo.perfil.Perfil.obtenhaEntityManager;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoPlano;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Entity
@DiscriminatorValue(value = "3")
public class PerfilCoordenadorCurso extends Perfil {

    public PerfilCoordenadorCurso() {
        setId(EnumPerfil.COORDENADOR_CURSO.getCodigo());
        setNome(EnumPerfil.COORDENADOR_CURSO.getNome());
    }

    @Override
    public boolean permiteFazerRequerimento() {
        return false;
    }

    @Override
    public boolean permiteConfigurarSistema() {
        return false;
    }

    @Override
    public boolean permitePlanoDeAula() {
        return true;
    }

    @Override
    public boolean permiteEditarPlanoDeAula() {
        return true;
    }

    @Override
    public boolean permiteEditarTurma() {
        return true;
    }

    @Override
    public boolean permiteManterUsuarios() {
        return false;
    }

    @Override
    public boolean permiteImprimirEmenta() {
        return true;
    }

    @Override
    public List<Requerimento> obtenhaRequerimentos(UsuarioSigera usuarioAutenticado) {
        EntityManager em = obtenhaEntityManager();
        StringBuilder consulta = new StringBuilder();
        Integer idCurso = usuarioAutenticado.getPerfilAtual().getCurso().getId();
        //perfilAdmin para exibir os requerimentos de plano, que são solicitados por administradores do sistema
        consulta.append(" SELECT r ");
        consulta.append(" FROM Requerimento as r ");
        consulta.append(" WHERE r.tipo IN (:tipo1, :tipo2, :tipo3 ) ");
        consulta.append(" AND r.usuario.id IN (SELECT apc.usuario.id ");
        consulta.append("                      FROM AssociacaoPerfilCurso as apc ");
        consulta.append("                      WHERE apc.perfil.id = :perfilAdmin ");
        consulta.append("                      OR (apc.perfil.id = :perfilAluno AND apc.curso.id = :idCurso ) ) ORDER BY r.id DESC");

        Query query = em.createQuery(consulta.toString());
        query.setParameter("tipo1", EnumTipoRequerimento.ACRESCIMO_DISCIPLINAS.getCodigo());
        query.setParameter("tipo2", EnumTipoRequerimento.CANCELAMENTO_DISCIPLINAS.getCodigo());
        query.setParameter("tipo3", EnumTipoRequerimento.PLANO.getCodigo());
        query.setParameter("idCurso", idCurso);
        query.setParameter("perfilAluno", EnumPerfil.ALUNO.getCodigo());
        query.setParameter("perfilAdmin", EnumPerfil.ADMINISTRADOR_SISTEMA.getCodigo());

        List<Requerimento> requerimentos = query.getResultList();
        List<Requerimento> requerimentos2 = new ArrayList<Requerimento>();

        BuscadorLdap buscadorLdap = usuarioAutenticado.getUsuarioLdap().getBuscadorLdap();

        for (Requerimento r : requerimentos) {
            r.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(r.getUsuario().getId()));
            RequerimentoPlano r1 = null;
            r1 = RequerimentoPlano.obtenhaRequerimentoPlano(buscadorLdap, r.getId());
            if (r1 != null) {
                if (r1.getPlano().getTurma().getDisciplina().getCurso().getId() == idCurso) {
                    requerimentos2.add(r);
                }
            } else {
                requerimentos2.add(r);
            }
        }
        return requerimentos2;
    }

    @Override
    public List<RequerimentoPlano> obtenhaRequerimentosPlanos(UsuarioSigera usuarioAutenticado) {
        EntityManager em = obtenhaEntityManager();
        StringBuilder consulta = new StringBuilder();

        consulta.append("SELECT r");
        consulta.append(" FROM RequerimentoPlano as r  ");
        consulta.append(" WHERE r.plano.turma.disciplina.curso.id IN (SELECT apc.curso.id ");
        consulta.append("                      FROM AssociacaoPerfilCurso as apc ");
        consulta.append("                      WHERE apc.curso.id = :idCurso) ORDER BY r.id DESC");

        Query query = em.createQuery(consulta.toString());
        query.setParameter("idCurso", usuarioAutenticado.getPerfilAtual().getCurso().getId());

        BuscadorLdap buscadorLdap = usuarioAutenticado.getUsuarioLdap().getBuscadorLdap();
        List<RequerimentoPlano> planos = query.getResultList();

        for (RequerimentoPlano p : planos) {
            p.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(p.getUsuario().getId()));
            UsuarioSigera professor = p.getPlano().getTurma().getProfessor().getUsuario();
            professor.setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(professor.getId()));
        }
        return planos;
    }
}
