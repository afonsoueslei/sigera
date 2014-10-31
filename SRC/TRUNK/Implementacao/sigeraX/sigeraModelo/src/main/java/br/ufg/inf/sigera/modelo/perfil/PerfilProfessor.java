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
        Integer idProfessor = Professor.obtenhaProfessorPorIdUsuario(usuarioAutenticado.getId()).getId();
        //Busca todos os req de segunda chamada cujo professor da turma é o usuario autenticado
        consulta.append("SELECT * FROM Requerimento AS r WHERE r.id IN ");
        consulta.append("(SELECT requerimento_id FROM Req_Segunda_Chamada AS rsq WHERE rsq.turma_id IN ");
        consulta.append("(SELECT id FROM turma AS t WHERE t.professor_id = ");
        consulta.append(idProfessor).append(" ) ) ");
        //Busca todos os req de plano cujo professor da turma associada ao plano é o usuario autenticado
        consulta.append(" UNION ");
        consulta.append("SELECT * FROM Requerimento AS r WHERE r.id IN ");
        consulta.append("(SELECT requerimento_id FROM Req_plano AS rp WHERE rp.plano_id IN ");
        consulta.append("(SELECT id FROM plano AS p WHERE p.turma_id IN ");
        consulta.append("(SELECT id FROM turma AS t WHERE t.professor_id = ");
        consulta.append(idProfessor).append(" ) ) ) ");
        //Busca todos os req de acerto e prorrogacao cujo orientador seja o usuario autenticado
        consulta.append(" UNION ");
        consulta.append("SELECT * FROM Requerimento AS r WHERE r.tipo IN ( ");
        consulta.append(EnumTipoRequerimento.ACRESCIMO_DISCIPLINAS.getCodigo()).append(" , ");
        consulta.append(EnumTipoRequerimento.CANCELAMENTO_DISCIPLINAS.getCodigo()).append(" , ");
        consulta.append(EnumTipoRequerimento.PRORROGACAO_DEFESA.getCodigo()).append(" ) ");
        consulta.append("AND r.usuario_id IN (SELECT usuario_id FROM ");
        consulta.append("Usuario_perfil AS apc WHERE apc.professor_id = ");
        consulta.append(idProfessor).append(" ) ");
        consulta.append("ORDER BY status_req, id DESC ");

        Query query = em.createNativeQuery(consulta.toString(), Requerimento.class);
        List<Requerimento> requerimentosResposta = query.getResultList();

        //Seta usuario Ldap em cada requerimento
        for (Requerimento r : requerimentosResposta) {
            r.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(r.getUsuario().getId()));
        }
        return requerimentosResposta;
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
