package br.ufg.inf.sigera.modelo.requerimento;

import br.ufg.inf.sigera.modelo.AssociacaoPerfilCurso;
import br.ufg.inf.sigera.modelo.Curso;
import br.ufg.inf.sigera.modelo.Disciplina;
import br.ufg.inf.sigera.modelo.Parecer;
import br.ufg.inf.sigera.modelo.Plano;
import br.ufg.inf.sigera.modelo.Turma;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import br.ufg.inf.sigera.modelo.perfil.EnumPerfil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

@Entity
@Table(name = "requerimento")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Tipo", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Requerimento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioSigera usuario;
    @Column(name = "data_abertura")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataAbertura;
    @Column(name = "status_req")
    private int status;
    @Column(name = "data_fechamento")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataFechamento;
    @Column(name = "justificativa")
    private String justificativa;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "requerimento_id")
    private Collection<Parecer> pareceres;
    @Column(name = "Tipo")
    private int tipo;
    @Transient
    private Curso curso;
    @Transient
    private String codigoMensagemConfirmacao;
    @Transient
    private Plano plano;

    public Requerimento() {
        this.codigoMensagemConfirmacao = "MT.210";
    }

    public Requerimento(UsuarioSigera usuario) {
        this.usuario = usuario;
        this.dataAbertura = new Date();
        this.status = EnumStatusRequerimento.ABERTO.getCodigo();
        this.codigoMensagemConfirmacao = "MT.210";
    }

    public String getCodigoMensagemConfirmacao() {
        return codigoMensagemConfirmacao;
    }

    public void setCodigoMensagemConfirmacao(String codigoMensagemConfirmacao) {
        this.codigoMensagemConfirmacao = codigoMensagemConfirmacao;
    }

    public Curso getCurso() {
        if (curso == null) {
            descobrirCurso();
        }

        return curso;
    }

    //para requerimento do tipo Plano
    public Plano getPlano() {
        if (this.tipo == EnumTipoRequerimento.PLANO.getCodigo()) {
            BuscadorLdap buscadorLdap = this.getUsuario().getUsuarioLdap().getBuscadorLdap();
            RequerimentoPlano rp = RequerimentoPlano.obtenhaRequerimentoPlano(buscadorLdap, this.id);
            return rp.getPlano();
        }
        return null;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int Tipo) {
        this.tipo = Tipo;
    }

    public UsuarioSigera getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioSigera usuario) {
        this.usuario = usuario;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(Date dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public Collection<Parecer> getPareceres() {
        return pareceres;
    }

    public void setPareceres(Collection<Parecer> pareceres) {
        this.pareceres = pareceres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public static Requerimento obtenha(BuscadorLdap buscadorLdap, Integer id) {
        EntityManager em = criarManager();

        Requerimento req = em.find(Requerimento.class, id);
        if (req != null) {
            req.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(req.getUsuario().getId()));
        }

        return req;

    }

    public void salvar() {
        EntityManager em = criarManager();
        em.getTransaction().begin();
        if (this.getId() > 0) {
            em.merge(this);
        } else {
            em.persist(this);
        }
        em.getTransaction().commit();
    }

    private static EntityManager criarManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("br.ufg.inf.sigera");
        EntityManager em = emf.createEntityManager();
        return em;
    }

    public abstract String getDescricaoTipo();

    private void descobrirCurso() {

        Collection<AssociacaoPerfilCurso> perfisUsuario = this.usuario.getPerfis();
        if (perfisUsuario != null) {
            for (AssociacaoPerfilCurso infoPerfil : perfisUsuario) {
                if (infoPerfil.getPerfil().getId() == EnumPerfil.ALUNO.getCodigo()) {
                    curso = infoPerfil.getCurso();
                    break;
                }
            }
        }
    }

    public Collection<Disciplina> getDisciplinas() {
        return null;
    }

    public Collection<Turma> getTurmasAcertoMatricula(BuscadorLdap buscadorLdap) {
        return null;
    }

    public Collection<Anexo> getAnexos() {
        return null;
    }

    public String getDescricaoStatus() {
        return EnumStatusRequerimento.obtenha(this.getStatus()).getNome();
    }

    public boolean estaAberto() {
        return (this.status == EnumStatusRequerimento.ABERTO.getCodigo());
    }

    public boolean autorizaDarParecer(UsuarioSigera usuario) {
        if (this.status != codigoStatusQuePermiteParecer()) {
            return false;
        } else {
            return perfilPermiteDarParecer(usuario);
        }
    }

    public boolean autorizaReabrirPlano(UsuarioSigera usuario) {
        return perfilPermiteDarParecer(usuario);
    }

    public boolean autorizaDarParecerAcertoMatricula(UsuarioSigera usuario) {
        return false;
    }

    public boolean autorizaEditarPlano(UsuarioSigera usuario) {
        if (!StatusDoRequerimentoPermiteEditarPlano()) {
            return false;
        } else {
            return perfilPermiteEditarPlano(usuario);
        }
    }

    public boolean StatusDoRequerimentoPermiteEditarPlano() {
        if (this.status == EnumStatusRequerimento.ABERTO.getCodigo()) {
            return true;
        }
        return false;
    }

    public int codigoStatusQuePermiteParecer() {

        if (this.getTipo() == EnumTipoRequerimento.PLANO.getCodigo()) {
            return EnumStatusRequerimento.CONCLUIDO.getCodigo();
        }
        return EnumStatusRequerimento.ABERTO.getCodigo();
    }

    public boolean autorizaVisualizarParecer(UsuarioSigera usuario) {
        //Quem pode visualizar: o Requerente, o Destinatário do requerimento e o Administrador
        boolean usuarioEhAdministrador = (usuario.getPerfilAtual().getPerfil().getId() == EnumPerfil.ADMINISTRADOR_SISTEMA.getCodigo());

        if (this.getPareceres() != null && this.getPareceres().size() > 0) {
            if (this.getUsuario().getId() == usuario.getId()
                    || perfilPermiteDarParecer(usuario) || usuarioEhAdministrador) {
                return true;
            }
        }
        return false;
    }

    public boolean autorizaVisualizarAnexos(UsuarioSigera usuario) {
        if (this.getAnexos() != null && this.getAnexos().size() > 0) {
            if (this.getUsuario().getId() == usuario.getId()
                    || perfilPermiteDarParecer(usuario)) {
                return true;
            }
        }
        return false;
    }

    public abstract boolean perfilPermiteDarParecer(UsuarioSigera usuario);

    public abstract boolean perfilPermiteEditarPlano(UsuarioSigera usuario);

    public boolean podeVisualizarTelefone(UsuarioSigera usuario) {
        if (getUsuario().getId() == usuario.getId()) {
            return true;
        }

        if (perfilPermiteDarParecer(usuario)) {
            return true;
        }

        return false;
    }

    public void adicionarParecer(Parecer parecer) {
        if (this.pareceres == null) {
            this.pareceres = new ArrayList<Parecer>();
        }
        this.pareceres.add(parecer);
        this.status = parecer.getStatus();
        if (parecer.getStatus() == EnumStatusRequerimento.DEFERIDO.getCodigo()
                || parecer.getStatus() == EnumStatusRequerimento.INDEFERIDO.getCodigo()) {
            this.dataFechamento = parecer.getDataParecer();
        }
    }

    public Collection<Parecer> obtenhaPareceres(BuscadorLdap buscadorLdap) {
        for (Parecer parecer : this.pareceres) {
            parecer.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(parecer.getUsuario().getId()));
        }
        return this.pareceres;
    }

    public boolean usuarioPodeConferirDocumentos(UsuarioSigera usuarioLogado) {
        return false;
    }

    public List<TurmaComStatus> getTurmasComStatus(BuscadorLdap buscadorLdap) {
        return null;
    }

    public TurmaComStatus copiarTurmaComStatus(TurmaComStatus turma) {
        return null;
    }

    public void setTurmasComStatus(List<TurmaComStatus> turmasComStatus) {
    }

}