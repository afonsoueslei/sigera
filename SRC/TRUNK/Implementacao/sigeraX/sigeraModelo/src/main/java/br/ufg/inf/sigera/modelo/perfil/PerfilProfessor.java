package br.ufg.inf.sigera.modelo.perfil;

import br.ufg.inf.sigera.modelo.Professor;
import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import static br.ufg.inf.sigera.modelo.perfil.Perfil.obtenhaEntityManager;
import br.ufg.inf.sigera.modelo.requerimento.EnumTipoRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoPlano;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Entity
@DiscriminatorValue(value = "5")
public class PerfilProfessor extends Perfil {

    public PerfilProfessor() {
        setId(EnumPerfil.PROFESSOR.getCodigo());
        setNome(EnumPerfil.PROFESSOR.getNome());
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
        return false;
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
        BuscadorLdap buscadorLdap = usuarioAutenticado.getUsuarioLdap().getBuscadorLdap();
        StringBuilder consulta = new StringBuilder();
        //Busca todos os req de segunda chamada cujo professor da turma é o usuario autenticado
        consulta.append(" SELECT r FROM Requerimento AS r WHERE r.id IN ( ");
        consulta.append("        SELECT rsq.id FROM RequerimentoSegundaChamada as rsq  ");
        consulta.append("               WHERE rsq.turma.professor.usuario.id = :id ) ");
        //Busca todos os req de plano cujo professor da turma associada ao plano é o usuario autenticado
        consulta.append(" OR r.id IN ( ");
        consulta.append("        SELECT rp.id FROM RequerimentoPlano as rp  ");
        consulta.append("               WHERE rp.plano.turma.professor.usuario.id = :id ) ");
        //Busca todos os req de acerto e prorrogacao cujo orientador seja o usuario autenticado
        consulta.append(" OR r.id IN ( ");
        consulta.append("        SELECT r.id FROM Requerimento as r ");
        consulta.append("                    WHERE r.tipo IN ( :tipo1, :tipo2, :tipo3 ) ");        
        consulta.append("                    AND r.usuario.id IN (  " );
        consulta.append("                        SELECT apc.usuario.id ");
        consulta.append("                               FROM AssociacaoPerfilCurso as apc ");
        consulta.append("                                     WHERE apc.orientador.id = :idProfessor ) ) ");
        consulta.append(" ORDER BY r.status, r.id DESC");

        Query query = em.createQuery(consulta.toString());
        query.setParameter("id", usuarioAutenticado.getId());
        query.setParameter("idProfessor", Professor.obtenhaProfessorPorIdUsuario(usuarioAutenticado.getId()).getId());
        query.setParameter("tipo1", EnumTipoRequerimento.ACRESCIMO_DISCIPLINAS.getCodigo());
        query.setParameter("tipo2", EnumTipoRequerimento.CANCELAMENTO_DISCIPLINAS.getCodigo());
        query.setParameter("tipo3", EnumTipoRequerimento.PRORROGACAO_DEFESA.getCodigo());
        List<Requerimento> requerimentos = query.getResultList();

        for (Requerimento r : requerimentos) {
            r.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(r.getUsuario().getId()));
        }
        return requerimentos;
    }

    @Override
    public List<RequerimentoPlano> obtenhaRequerimentosPlanos(UsuarioSigera usuarioAutenticado) {
        EntityManager em = obtenhaEntityManager();
        StringBuilder consulta = new StringBuilder();

        consulta.append("SELECT r");
        consulta.append(" FROM RequerimentoPlano as r  ");
        consulta.append(" WHERE r.plano.turma.professor.usuario.id = :idUsuario ORDER BY r.status, r.id DESC");

        Query query = em.createQuery(consulta.toString());
        query.setParameter("idUsuario", usuarioAutenticado.getId());

        BuscadorLdap buscadorLdap = usuarioAutenticado.getUsuarioLdap().getBuscadorLdap();
        List<RequerimentoPlano> planos = query.getResultList();

        for (RequerimentoPlano p : planos) {
            p.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(p.getUsuario().getId()));
            //setar usuário Ldap do professor do plano
            p.getPlano().getTurma().getProfessor().getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(p.getPlano().getTurma().getProfessor().getUsuario().getId()));
        }
        return planos;
    }

    @Override
    public List<Requerimento> obtenhaRequerimentosDoCurso(UsuarioSigera usuario) {
        return null;
    }

    @Override
    public boolean permiteCancelarRequerimento() {
        return false;
    }

}
