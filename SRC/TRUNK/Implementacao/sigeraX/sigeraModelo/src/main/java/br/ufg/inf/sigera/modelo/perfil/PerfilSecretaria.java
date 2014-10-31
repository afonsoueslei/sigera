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

        consulta.append("SELECT * FROM requerimento AS r WHERE r.tipo IN ( ");
        consulta.append(EnumTipoRequerimento.DECLARACAO_MATRICULA.getCodigo()).append(" , ");
        consulta.append(EnumTipoRequerimento.EXTRATO_ACADEMICO.getCodigo()).append(" , ");
        consulta.append(EnumTipoRequerimento.EMENTAS.getCodigo()).append(" , ");
        consulta.append(EnumTipoRequerimento.ASSINATURA.getCodigo()).append(" ) ");
        consulta.append("AND r.usuario_id IN ( SELECT apc.usuario_id FROM usuario_perfil AS apc WHERE  apc.curso_id IN ( ");
        if (usuarioAutenticadoEhSecretarioDaPos) {
            consulta.append(Curso.obtenhaCursoPorPrefixo("msc").getId()).append(" , ");
            consulta.append(Curso.obtenhaCursoPorPrefixo("dsc").getId()).append(" , ");
            consulta.append(idCursoPos).append(" ) ");
        } else {
            consulta.append(usuarioAutenticado.getPerfilAtual().getCurso().getId()).append(" ) ");
        }
        consulta.append("AND apc.perfil_id  IN ( ");
        consulta.append(EnumPerfil.ALUNO.getCodigo()).append(" , ");
        consulta.append(EnumPerfil.ALUNO_POS_STRICTO_SENSU.getCodigo()).append(" ) ) ");

        if (usuarioAutenticadoEhSecretarioDaPos) {
            consulta.append("UNION ");
            consulta.append("SELECT * FROM requerimento AS r WHERE r.tipo IN ( ");
            consulta.append(EnumTipoRequerimento.ACRESCIMO_DISCIPLINAS.getCodigo()).append(" , ");
            consulta.append(EnumTipoRequerimento.CANCELAMENTO_DISCIPLINAS.getCodigo()).append(" ) ");
            consulta.append("AND r.usuario_id IN ( SELECT apc.usuario_id FROM usuario_perfil AS apc WHERE apc.curso_id IN ( ");
            consulta.append(Curso.obtenhaCursoPorPrefixo("msc").getId()).append(" , ");
            consulta.append(Curso.obtenhaCursoPorPrefixo("dsc").getId()).append(" , ");
            consulta.append(idCursoPos).append(" ) ");
            consulta.append("AND apc.perfil_id = ");
            consulta.append(EnumPerfil.ALUNO_POS_STRICTO_SENSU.getCodigo()).append(" ) ");
            consulta.append("AND r.status_req != ");
            consulta.append(EnumStatusRequerimento.ABERTO.getCodigo());
        }
        consulta.append(" ORDER BY status_req, id DESC");

        Query query2 = em.createNativeQuery(consulta.toString(), Requerimento.class);

        List<Requerimento> requerimentos = query2.getResultList();
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
