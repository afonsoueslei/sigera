package br.ufg.inf.sigera.modelo.requerimento;

import br.ufg.inf.sigera.modelo.Curso;
import br.ufg.inf.sigera.modelo.Plano;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import br.ufg.inf.sigera.modelo.perfil.EnumPerfil;
import br.ufg.inf.sigera.modelo.perfil.Perfil;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "req_plano")
@DiscriminatorValue(value = "8")
@PrimaryKeyJoinColumn(name = "Requerimento_id")
public class RequerimentoPlano extends Requerimento {

    @OneToOne
    @JoinColumn(name = "plano_id")
    private Plano plano;

    public RequerimentoPlano() {
    }

    public RequerimentoPlano(UsuarioSigera usuario, Plano plano) {
        super(usuario);
        this.setTipo(EnumTipoRequerimento.PLANO.getCodigo());
        this.setPlano(plano);
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    @Override
    public String getDescricaoTipo() {
        return EnumTipoRequerimento.PLANO.getNome();
    }

    private static EntityManager criarManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("br.ufg.inf.sigera");
        EntityManager em = emf.createEntityManager();
        return em;
    }

    public static RequerimentoPlano obtenhaRequerimentoPlano(BuscadorLdap buscadorLdap, Integer id) {
        EntityManager em = criarManager();
        RequerimentoPlano req = em.find(RequerimentoPlano.class, id);
        if (req != null) {
            req.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(req.getUsuario().getId()));
        }

        return em.find(RequerimentoPlano.class, id);
    }

    @Override
    public boolean perfilPermiteDarParecer(UsuarioSigera usuario) {

        // Só os usuários autenticados com perfil de coordenador de curso do mesmo curso 
        // do plano ou Coordenador Geral podem dar parecer sobre requerimentos de avaliacao de plano de disciplina.
        Perfil perfilUsuario = usuario.getPerfilAtual().getPerfil();
        Integer idCursoUsuario = usuario.getPerfilAtual().getCurso().getId();

        return (perfilUsuario.getId() == EnumPerfil.COORDENADOR_GERAL.getCodigo()
                || (perfilUsuario.getId() == EnumPerfil.COORDENADOR_CURSO.getCodigo() && idCursoUsuario == this.getPlano().getTurma().getDisciplina().getCurso().getId()));
    }

    @Override
    public boolean perfilPermiteEditarPlano(UsuarioSigera usuario) {

        // Só o próprio professor da turma pode editar o Plano de Ensino dessa turma.
        Perfil perfilUsuario = usuario.getPerfilAtual().getPerfil();
        
        return (perfilUsuario.getId() == EnumPerfil.PROFESSOR.getCodigo()
                && usuario.getId() == this.getPlano().getTurma().getProfessor().getUsuario().getId());
    }
}
