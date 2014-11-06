package br.ufg.inf.sigera.modelo.perfil;

import br.ufg.inf.sigera.modelo.Curso;
import br.ufg.inf.sigera.modelo.Professor;
import br.ufg.inf.sigera.modelo.requerimento.EnumTipoRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import br.ufg.inf.sigera.modelo.requerimento.EnumStatusRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoPlano;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Entity
@DiscriminatorValue(value = "6")
public class PerfilSecretaria extends Perfil {

    public PerfilSecretaria() {
        setId(EnumPerfil.SECRETARIA.getCodigo());
        setNome(EnumPerfil.SECRETARIA.getNome());
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
        return false;
    }

    @Override
    public boolean permiteEditarPlanoDeAula() {
        return false;
    }

    @Override
    public boolean permiteEditarTurma() {
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
        Integer idCursoPos = Curso.obtenhaCursoPorPrefixo("POS").getId();
        Boolean usuarioAutenticadoEhSecretarioDaPos = false;

        if (usuarioAutenticado.getPerfilAtual().getCurso().getId() == idCursoPos) {
            usuarioAutenticadoEhSecretarioDaPos = true;
        }

        if (secretarioEhProfessorMembroDeComissao(usuarioAutenticado)) {
            return obtenhaRequerimentosMembroComissao(usuarioAutenticado);
        }
        //busca todos os requerimento de declaração, extrato, ementas e de assinatura que sejam
        //do mesmo curso do usuarioAutenticado
        consulta.append("SELECT r FROM Requerimento AS r ");
        consulta.append("         WHERE r.tipo IN ( :tipo1 , :tipo2, :tipo3, :tipo4 ) ");
        consulta.append("         AND r.usuario.id IN ( ");
        consulta.append("                             SELECT apc.usuario.id FROM AssociacaoPerfilCurso AS apc ");
        consulta.append("                                                   WHERE apc.curso.id IN ( ");
        //Se o usuario logado for secretario da pos busca os requerimento dos curso de pos, msc e dsc
        if (usuarioAutenticadoEhSecretarioDaPos) {
            consulta.append(" :idMSC , :idDSC, :idPos ) ");
        } else {
            consulta.append(" :idCursoUsuarioAutenticado ) ");
        }
        consulta.append("AND apc.perfil.id  IN ( :idAluno , :idAlunoPos ) )");

        //Se o usuario logado for secretario da pos busca ainda os de acrescimo que já foram
        //autorizados pelo orientador
        if (usuarioAutenticadoEhSecretarioDaPos) {
            consulta.append("OR r.tipo IN ( :tipo5 , :tipo6  ) ");
            consulta.append("AND r.usuario.id IN ( SELECT apc.usuario.id FROM AssociacaoPerfilCurso AS apc ");
            consulta.append("WHERE apc.curso.id IN ( :idMSC , :idDSC, :idPos ) ");
            consulta.append("AND apc.perfil.id = :idAlunoPos ) ");
            consulta.append("AND r.status != :statusAberto ");
        }
        consulta.append("ORDER BY r.status, r.id DESC");

        Query query = em.createQuery(consulta.toString());
        query.setParameter("tipo1", EnumTipoRequerimento.DECLARACAO_MATRICULA.getCodigo());
        query.setParameter("tipo2", EnumTipoRequerimento.EXTRATO_ACADEMICO.getCodigo());
        query.setParameter("tipo3", EnumTipoRequerimento.EMENTAS.getCodigo());
        query.setParameter("tipo4", EnumTipoRequerimento.ASSINATURA.getCodigo());
        if (usuarioAutenticadoEhSecretarioDaPos) {
            query.setParameter("tipo5", EnumTipoRequerimento.ACRESCIMO_DISCIPLINAS.getCodigo());
            query.setParameter("tipo6", EnumTipoRequerimento.CANCELAMENTO_DISCIPLINAS.getCodigo());
            query.setParameter("idMSC", Curso.obtenhaCursoPorPrefixo("msc").getId());
            query.setParameter("idDSC", Curso.obtenhaCursoPorPrefixo("dsc").getId());
            query.setParameter("idPos", idCursoPos);
            query.setParameter("statusAberto", EnumStatusRequerimento.ABERTO.getCodigo());
        } else {
            query.setParameter("idCursoUsuarioAutenticado", usuarioAutenticado.getPerfilAtual().getCurso().getId());
        }
        query.setParameter("idAluno", EnumPerfil.ALUNO.getCodigo());
        query.setParameter("idAlunoPos", EnumPerfil.ALUNO_POS_STRICTO_SENSU.getCodigo());

        List<Requerimento> requerimentos = query.getResultList();
        BuscadorLdap buscadorLdap = usuarioAutenticado.getUsuarioLdap().getBuscadorLdap();
        for (Requerimento r : requerimentos) {
            r.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(r.getUsuario().getId()));
        }

        return requerimentos;
    }

    public List<Requerimento> obtenhaRequerimentosMembroComissao(UsuarioSigera usuarioAutenticado) {
        EntityManager em = obtenhaEntityManager();
        StringBuilder consulta = new StringBuilder();

        consulta.append(" SELECT r ");
        consulta.append(" FROM Requerimento as r, RequerimentoProrrogacaoDefesa as rp  ");
        consulta.append(" WHERE r.id = rp.id AND rp.membroAvaliador = :idUsuario");

        Query query = em.createQuery(consulta.toString());
        query.setParameter("idUsuario", usuarioAutenticado.getId());

        List<Requerimento> requerimentos = query.getResultList();
        BuscadorLdap buscadorLdap = usuarioAutenticado.getUsuarioLdap().getBuscadorLdap();

        for (Requerimento r : requerimentos) {
            r.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(r.getUsuario().getId()));
        }

        return requerimentos;
    }

    public Boolean secretarioEhProfessorMembroDeComissao(UsuarioSigera usuarioAutenticado) {
        return (Professor.obtenhaProfessorPorIdUsuario(usuarioAutenticado.getId()) != null
                && (usuarioAutenticado.getPerfilAtual().getCurso().getId() == Curso.obtenhaCursoPorPrefixo("msc").getId()
                || usuarioAutenticado.getPerfilAtual().getCurso().getId() == Curso.obtenhaCursoPorPrefixo("dsc").getId()));
    }

    @Override
    public List<Requerimento> obtenhaRequerimentosDoCurso(UsuarioSigera usuario) {
        return null;
    }

    @Override
    public List<RequerimentoPlano> obtenhaRequerimentosPlanos(UsuarioSigera usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean permiteManterUsuarios() {
        return false;
    }

    @Override
    public boolean permiteCancelarRequerimento() {
        return false;
    }

}
