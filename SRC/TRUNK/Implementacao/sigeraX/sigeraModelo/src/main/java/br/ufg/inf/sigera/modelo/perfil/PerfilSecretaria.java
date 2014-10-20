package br.ufg.inf.sigera.modelo.perfil;

import br.ufg.inf.sigera.modelo.AssociacaoPerfilCurso;
import br.ufg.inf.sigera.modelo.Curso;
import br.ufg.inf.sigera.modelo.Professor;
import br.ufg.inf.sigera.modelo.requerimento.EnumTipoRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoPlano;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoProrrogacaoDefesa;
import java.util.ArrayList;
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

        if(secretarioEhProfessorMembroDeComissao(usuarioAutenticado)){
            return obtenhaRequerimentosMembroComissao(usuarioAutenticado);
        }
        
        consulta.append(" SELECT r ");
        consulta.append(" FROM Requerimento as r  ");
        consulta.append(" WHERE r.tipo IN (:tipo1, :tipo2, :tipo3, :tipo4) ");
        consulta.append(" AND r.usuario.id IN (SELECT apc.usuario.id ");
        consulta.append("                      FROM AssociacaoPerfilCurso as apc ");
        if (usuarioAutenticado.getPerfilAtual().getCurso().getId() == idCursoPos) {
            consulta.append("                  WHERE apc.curso.id IN ( :idCursoMSC, :idCursoDSC, :idCursoPos )");
        } else {
            consulta.append("                  WHERE apc.curso.id = :idCurso ");
        }
        consulta.append("                      AND ( apc.perfil.id = :perfilAluno OR apc.perfil.id = :perfilAlunoPos ) ) ORDER BY r.status, r.id DESC");

        Query query = em.createQuery(consulta.toString());
        query.setParameter("tipo1", EnumTipoRequerimento.DECLARACAO_MATRICULA.getCodigo());
        query.setParameter("tipo2", EnumTipoRequerimento.EXTRATO_ACADEMICO.getCodigo());
        query.setParameter("tipo3", EnumTipoRequerimento.EMENTAS.getCodigo());
        query.setParameter("tipo4", EnumTipoRequerimento.ASSINATURA.getCodigo());

        if (usuarioAutenticado.getPerfilAtual().getCurso().getId() == idCursoPos) {
            query.setParameter("idCursoMSC", Curso.obtenhaCursoPorPrefixo("msc").getId());
            query.setParameter("idCursoDSC", Curso.obtenhaCursoPorPrefixo("dsc").getId());
            query.setParameter("idCursoPos", idCursoPos);
        } else {
            query.setParameter("idCurso", usuarioAutenticado.getPerfilAtual().getCurso().getId());
        }
        query.setParameter("perfilAluno", EnumPerfil.ALUNO.getCodigo());
        query.setParameter("perfilAlunoPos", EnumPerfil.ALUNO_POS_STRICTO_SENSU.getCodigo());

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
