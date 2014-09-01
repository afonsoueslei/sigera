package br.ufg.inf.sigera.modelo.requerimento;

import br.ufg.inf.sigera.modelo.Curso;
import br.ufg.inf.sigera.modelo.Turma;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import br.ufg.inf.sigera.modelo.perfil.EnumPerfil;
import br.ufg.inf.sigera.modelo.perfil.Perfil;
import br.ufg.inf.sigera.modelo.servico.Persistencia;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Query;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.PrivateOwned;

@Entity
@Table(name = "req_cancelamento_disciplina")
@DiscriminatorValue(value = "2")
@PrimaryKeyJoinColumn(name = "Requerimento_id")
public class RequerimentoCancelamentoDisciplina extends Requerimento {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Req_Cancelamento_Disciplina_Requerimento_id")
    @PrivateOwned
    private Collection<AssociacaoReqCancelamentoTurma> turmas;

    public RequerimentoCancelamentoDisciplina() {
    }

    public RequerimentoCancelamentoDisciplina(UsuarioSigera usuario, Collection<Turma> turmas, String justificativa) {
        super(usuario);
        this.setTipo(EnumTipoRequerimento.ACRESCIMO_DISCIPLINAS.getCodigo());
        this.setJustificativa(justificativa);
        this.definaTurmas(turmas);
    }

    public Collection<AssociacaoReqCancelamentoTurma> getTurmas() {
        return turmas;
    }

    public void setTurmas(Collection<AssociacaoReqCancelamentoTurma> turmas) {
        this.turmas = turmas;
    }

    public void definaTurmas(Collection<Turma> turmas) {
        Collection<AssociacaoReqCancelamentoTurma> assocTurmas = new ArrayList<AssociacaoReqCancelamentoTurma>();
        for (Turma turma : turmas) {
            assocTurmas.add(new AssociacaoReqCancelamentoTurma(turma, this));
        }
        setTurmas(assocTurmas);
    }

    @Override
    public String getDescricaoTipo() {
        return EnumTipoRequerimento.CANCELAMENTO_DISCIPLINAS.getNome();
    }

    @Override
    public Collection<Turma> getTurmasAcertoMatricula(BuscadorLdap buscadorLdap) {
        Collection<Turma> turmasAcerto = new ArrayList<Turma>();
        for (AssociacaoReqCancelamentoTurma infoTurma : turmas) {
            UsuarioSigera usuarioProfessor = infoTurma.getTurma().getProfessor().getUsuario();
            usuarioProfessor.setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(usuarioProfessor.getId()));
            turmasAcerto.add(infoTurma.getTurma());
        }
        return turmasAcerto;
    }

    @Override
    public boolean autorizaDarParecer(UsuarioSigera usuario) {
        return false;
    }

    @Override
    public boolean autorizaDarParecerAcertoMatricula(UsuarioSigera usuario) {
        if (this.getStatus() != codigoStatusQuePermiteParecer()) {
            return false;
        } else {
            return perfilPermiteDarParecer(usuario);
        }
    }

    @Override
    public boolean perfilPermiteDarParecer(UsuarioSigera usuario) {
        // Só os usuários autenticados com perfil de coordenador de curso do mesmo curso 
        // do estudante requerente podem dar parecer sobre requerimentos de acréscimo/cancelamento de disciplina.

        Perfil perfilUsuario = usuario.getPerfilAtual().getPerfil();
        Curso cursoUsuario = usuario.getPerfilAtual().getCurso();

        if (perfilUsuario.getId() == EnumPerfil.COORDENADOR_CURSO.getCodigo()
                && cursoUsuario.getId() == getCurso().getId()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<TurmaComStatus> getTurmasComStatus(BuscadorLdap buscadorLdap) {
        List<TurmaComStatus> turmasComStatus = new ArrayList<TurmaComStatus>();
        for (AssociacaoReqCancelamentoTurma infoTurma : turmas) {
            UsuarioSigera usuarioProfessor = infoTurma.getTurma().getProfessor().getUsuario();
            usuarioProfessor.setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(usuarioProfessor.getId()));
            turmasComStatus.add(infoTurma);
        }
        return turmasComStatus;
    }

    @Override
    public TurmaComStatus copiarTurmaComStatus(TurmaComStatus turma) {
        return new AssociacaoReqCancelamentoTurma(turma);
    }

    @Override
    public void setTurmasComStatus(List<TurmaComStatus> turmasComStatus) {
        Collection<AssociacaoReqCancelamentoTurma> turmasAtualizadas = new ArrayList<AssociacaoReqCancelamentoTurma>();
        for (TurmaComStatus t : turmasComStatus) {
            turmasAtualizadas.add((AssociacaoReqCancelamentoTurma) t);
        }
        setTurmas(turmasAtualizadas);
    }

    @Override
    public boolean perfilPermiteEditarPlano(UsuarioSigera usuario) {
        return false;
    }

    public static Integer consultarPedidosAberto(Integer idTurma) {
        EntityManager em = Persistencia.obterManager();
        StringBuilder consulta = new StringBuilder();
        Integer statusAberto = 1;

        consulta.append(" SELECT COUNT (r.id) FROM Requerimento as r WHERE r.status = :aberto AND r.id IN ( ");
        consulta.append(" SELECT rt.reqCancelamento.id ");
        consulta.append(" FROM AssociacaoReqCancelamentoTurma as rt");
        consulta.append(" WHERE rt.turma.id = :idTurma )");

        Query query = em.createQuery(consulta.toString());
        query.setParameter("idTurma", idTurma);
        query.setParameter("aberto", statusAberto);
        Long x = (Long) query.getSingleResult();
        Integer y = x.intValue();
        return y;
    }
}
