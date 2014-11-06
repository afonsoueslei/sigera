package br.ufg.inf.sigera.modelo.perfil;

import br.ufg.inf.sigera.modelo.Curso;
import br.ufg.inf.sigera.modelo.requerimento.EnumTipoRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import static br.ufg.inf.sigera.modelo.perfil.Perfil.obtenhaEntityManager;
import br.ufg.inf.sigera.modelo.requerimento.EnumStatusRequerimento;
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
        BuscadorLdap buscadorLdap = usuarioAutenticado.getUsuarioLdap().getBuscadorLdap();
        StringBuilder consulta = new StringBuilder();
        Integer idCurso = usuarioAutenticado.getPerfilAtual().getCurso().getId();
        //Se for requerimento de Plano e esse for do curso do coordenador. Esse req é feito pelo Administrador do sistema         
        consulta.append(" SELECT r FROM Requerimento AS r WHERE r.id IN ( ");
        consulta.append("        SELECT rp.id FROM RequerimentoPlano AS rp ");
        consulta.append("               WHERE rp.plano.turma.disciplina.curso.id = :idCursoUsuarioAutenticado )");
        //Se for requerimento de Acerto só interessa os que não são dos Alunos regulares de pos
        consulta.append(" OR r.id IN (  ");
        consulta.append("         SELECT r.id FROM Requerimento AS r ");
        consulta.append("                WHERE r.tipo IN ( :tipo1, :tipo2 ) AND r.usuario.id IN ( ");
        consulta.append("                      SELECT apc.usuario.id FROM AssociacaoPerfilCurso AS apc ");
        consulta.append("                             WHERE apc.perfil.id = :idPerfilAluno  AND apc.curso.id = :idCursoUsuarioAutenticado ) )");
        //Se for requerimento de Prorrogação só interessa ao coordenador se ele já estiver autorizado pelo orientador           
        consulta.append(" OR r.id IN (  ");
        consulta.append("         SELECT rpd.id FROM RequerimentoProrrogacaoDefesa AS rpd ");
        consulta.append("                WHERE r.status != :statusAberto  AND r.usuario.id IN ( ");
        consulta.append("                      SELECT apc.usuario.id FROM AssociacaoPerfilCurso AS apc ");
        consulta.append("                             WHERE apc.perfil.id = :idPerfilAlunoPos AND apc.curso.id = :idCursoUsuarioAutenticado ) ) ");
        consulta.append(" ORDER BY r.status, r.id DESC ");

        Query query = em.createQuery(consulta.toString());
        query.setParameter("idCursoUsuarioAutenticado", idCurso);
        query.setParameter("tipo1", EnumTipoRequerimento.ACRESCIMO_DISCIPLINAS.getCodigo());
        query.setParameter("tipo2", EnumTipoRequerimento.CANCELAMENTO_DISCIPLINAS.getCodigo());
        query.setParameter("statusAberto", EnumStatusRequerimento.ABERTO.getCodigo());
        query.setParameter("idPerfilAluno", EnumPerfil.ALUNO.getCodigo());
        query.setParameter("idPerfilAlunoPos", EnumPerfil.ALUNO_POS_STRICTO_SENSU.getCodigo());

        List<Requerimento> requerimentosResposta = query.getResultList();

        for (Requerimento r : requerimentosResposta) {
            r.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(r.getUsuario().getId()));
        }
        return requerimentosResposta;
    }

    @Override
    public List<Requerimento> obtenhaRequerimentosDoCurso(UsuarioSigera usuarioAutenticado) {
        EntityManager em = obtenhaEntityManager();
        StringBuilder consulta = new StringBuilder();
        Integer idCurso = usuarioAutenticado.getPerfilAtual().getCurso().getId();
        //Se for requerimento de Plano e esse for do curso do coordenador. Esse req é feito pelo Administrador do sistema         
        consulta.append(" SELECT r FROM Requerimento AS r WHERE r.id IN ( ");
        consulta.append("        SELECT rp.id FROM RequerimentoPlano AS rp ");
        consulta.append("               WHERE rp.plano.turma.disciplina.curso.id = :idCurso )");
        consulta.append(" OR r.id IN (  ");
        consulta.append(" SELECT r.id ");
        consulta.append(" FROM Requerimento as r ");
        consulta.append(" WHERE r.usuario.id IN (SELECT apc.usuario.id ");
        consulta.append("                      FROM AssociacaoPerfilCurso as apc ");
        consulta.append("                      WHERE ((apc.perfil.id = :perfilAluno OR apc.perfil.id = :perfilAlunoPos)  AND apc.curso.id = :idCurso ) ) )");
        consulta.append(" ORDER BY r.status, r.id DESC");

        Query query = em.createQuery(consulta.toString());
        query.setParameter("idCurso", idCurso);
        query.setParameter("perfilAluno", EnumPerfil.ALUNO.getCodigo());
        query.setParameter("perfilAlunoPos", EnumPerfil.ALUNO_POS_STRICTO_SENSU.getCodigo());

        List<Requerimento> requerimentos = query.getResultList();
        List<Requerimento> requerimentos2 = new ArrayList<Requerimento>();

        BuscadorLdap buscadorLdap = usuarioAutenticado.getUsuarioLdap().getBuscadorLdap();

        for (Requerimento r : requerimentos) {
            r.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(r.getUsuario().getId()));
            RequerimentoPlano r1 = RequerimentoPlano.obtenhaRequerimentoPlano(buscadorLdap, r.getId());
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
        Integer idCurso = usuarioAutenticado.getPerfilAtual().getCurso().getId();
        Integer idCursoPos = Curso.obtenhaCursoPorPrefixo("Pos").getId();

        consulta.append("SELECT r");
        consulta.append(" FROM RequerimentoPlano as r  ");
        consulta.append(" WHERE r.plano.turma.disciplina.curso.id IN (SELECT apc.curso.id ");
        consulta.append("                      FROM AssociacaoPerfilCurso as apc ");
        consulta.append("                      WHERE apc.curso.id IN ( :idCurso ");
        if (idCurso == Curso.obtenhaCursoPorPrefixo("msc").getId() || idCurso == Curso.obtenhaCursoPorPrefixo("dsc").getId()) {
            consulta.append(" , ").append(idCursoPos);
        }
        consulta.append(" )) ORDER BY r.status, r.id DESC");

        Query query = em.createQuery(consulta.toString());
        query.setParameter("idCurso", idCurso);

        BuscadorLdap buscadorLdap = usuarioAutenticado.getUsuarioLdap().getBuscadorLdap();
        List<RequerimentoPlano> planos = query.getResultList();

        for (RequerimentoPlano p : planos) {
            p.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(p.getUsuario().getId()));
            UsuarioSigera professor = p.getPlano().getTurma().getProfessor().getUsuario();
            professor.setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(professor.getId()));
        }
        return planos;
    }

    @Override
    public boolean permiteCancelarRequerimento() {
        return false;
    }

}
