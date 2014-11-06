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
import br.ufg.inf.sigera.modelo.perfil.PerfilAlunoPosStrictoSensu;
import br.ufg.inf.sigera.modelo.servico.Conexoes;
import br.ufg.inf.sigera.modelo.servico.Persistencia;
import com.mysql.jdbc.Connection;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "requerimento_id")
    private Collection<Anexo> anexos;

    @Column(name = "Tipo")
    private int tipo;
    @Transient
    private Curso curso;
    @Transient
    private Integer prazoRequeridoProrrogacaoDefesa;
    @Transient
    private String codigoMensagemConfirmacao;
    @Transient
    private Plano plano;
    @Column(name = "versao")
    private int versao;

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

    public Integer getPrazoRequeridoProrrogacaoDefesa() {
        EntityManager em = criarManager();

        if (this.tipo != EnumTipoRequerimento.PRORROGACAO_DEFESA.getCodigo()) {
            return 0;
        } else {
            RequerimentoProrrogacaoDefesa reqProrrogacao = em.find(RequerimentoProrrogacaoDefesa.class, id);
            prazoRequeridoProrrogacaoDefesa = reqProrrogacao.getPrazoEmMeses();
        }

        return prazoRequeridoProrrogacaoDefesa;
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

    public Collection<Anexo> getAnexos() {
        return anexos;
    }

    public void setAnexos(Collection<Anexo> anexos) {
        for (Anexo a : anexos) {
            a.setRequerimento(this);
        }
        this.anexos = anexos;

        this.anexos = anexos;
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

    public int getVersao() {
        return versao;
    }

    public void setVersao(int versao) {
        this.versao = versao;
    }

    public static Requerimento obtenha(BuscadorLdap buscadorLdap, Integer id) {
        EntityManager em = criarManager();
        Requerimento req = em.find(Requerimento.class, id);       
        if (req != null && req.getUsuario().getUsuarioLdap() == null) {
            req.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(req.getUsuario().getId()));
        }
        return req;

    }

    public Boolean salvar() {
        EntityManager em = Persistencia.obterManager();
        if (Persistencia.versaoValida(this)) {
            em.getTransaction().begin();
            this.setVersao(this.getVersao() + 1);
            if (this.getId() > 0) {
                em.merge(this);
            } else {
                em.persist(this);
            }
            em.getTransaction().commit();
            em.close();
            return true;
        }
        return false;
    }

    public abstract String getDescricaoTipo();

    private void descobrirCurso() {

        Collection<AssociacaoPerfilCurso> perfisUsuario = this.usuario.getPerfis();
        if (perfisUsuario != null) {
            for (AssociacaoPerfilCurso infoPerfil : perfisUsuario) {
                if (infoPerfil.getPerfil().getId() == EnumPerfil.ALUNO.getCodigo()
                        || infoPerfil.getPerfil().getId() == EnumPerfil.ALUNO_POS_STRICTO_SENSU.getCodigo()) {
                    curso = infoPerfil.getCurso();
                    break;
                }
            }
        }

        if (this.getTipo() == EnumTipoRequerimento.PLANO.getCodigo()) {
            curso = this.getPlano().getTurma().getDisciplina().getCurso();
        }

        if (curso == null) {
            curso = new Curso();
            curso.setNome("Usuário sem curso definido!");
            curso.setPrefixo("X");
        }

    }

    public Collection<Disciplina> getDisciplinas() {
        return null;
    }

    public Collection<Turma> getTurmasAcertoMatricula(BuscadorLdap buscadorLdap) {
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
                    || perfilPermiteDarParecer(usuario)
                    || usuarioEhDaSecretariaDoCurso(usuario)
                    || usuarioEhCoordenadorDoCursoDoRequerente(usuario)
                    || usuarioEhAdministrador) {
                return true;
            }
        }
        return false;
    }

    public boolean usuarioEhDaSecretariaDoCurso(UsuarioSigera usuarioLogado) {
        // 1 caso) Usuários com perfil de secretaria do mesmo curso do requerimento
        // 2 caso) Usuários com perfil de secretaria de Graduaçao        

        Integer perfilUsuarioLogado = usuarioLogado.getPerfilAtual().getPerfil().getId();
        boolean cursoRequerimentoEhCursoPos = getCurso().getPrefixo().endsWith("SC") || getCurso().getPrefixo().endsWith("sc");
        boolean usuarioLogadoEhSecretarioCursoPos = false;

        if (usuarioLogado.getPerfilAtual().getCurso() != null) {
            Integer cursoUsuarioLogado = usuarioLogado.getPerfilAtual().getCurso().getId();
            if (cursoUsuarioLogado == Curso.obtenhaCursoPorPrefixo("POS").getId()) {
                usuarioLogadoEhSecretarioCursoPos = true;
            }

            // 1 caso
            if (perfilUsuarioLogado == EnumPerfil.SECRETARIA.getCodigo() && cursoUsuarioLogado == this.getCurso().getId()) {
                return true;
            }
        }

        // 2 caso
        if (perfilUsuarioLogado == EnumPerfil.SECRETARIA_GRADUACAO.getCodigo()) {
            return true;
        }

        //caso excepcional Qdo e secretario Pos e curso do requerimento e de aluno do MSC ou do DSC
        if (usuarioLogadoEhSecretarioCursoPos && cursoRequerimentoEhCursoPos) {
            return true;
        }
        return false;
    }

    public boolean autorizaVisualizarAnexos(UsuarioSigera usuario) {
        if (this.getAnexos() != null && this.getAnexos().size() > 0) {
            if (this.getUsuario().getId() == usuario.getId()
                    || perfilPermiteDarParecer(usuario)
                    || usuarioEhCoordenadorDoCursoDoRequerente(usuario)) {
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

    public boolean usuarioEhCoordenadorDoCursoDoRequerente(UsuarioSigera usuarioLogado) {
        return usuarioLogado.getPerfilAtual().getCurso() != null
                && this.getCurso().getId() == usuarioLogado.getPerfilAtual().getCurso().getId()
                && usuarioLogado.getPerfilAtual().getPerfil().getId() == EnumPerfil.COORDENADOR_CURSO.getCodigo();
    }

    public boolean usuarioEhOrientadorDoRequerente(UsuarioSigera usuarioLogado) {
        BuscadorLdap buscadorLdap = usuarioLogado.getUsuarioLdap().getBuscadorLdap();
        UsuarioSigera orientador = new PerfilAlunoPosStrictoSensu().obtenhaOrientador(this.getUsuario(), buscadorLdap);
        if (orientador != null) {
            return orientador.getId() == usuarioLogado.getId();
        } else {
            return false;
        }
    }

    public List<TurmaComStatus> getTurmasComStatus(BuscadorLdap buscadorLdap) {
        return null;
    }

    public TurmaComStatus copiarTurmaComStatus(TurmaComStatus turma) {
        return null;
    }

    public void setTurmasComStatus(List<TurmaComStatus> turmasComStatus) {
    }

    private static EntityManager criarManager() {

        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("br.ufg.inf.sigera");
            return emf.createEntityManager();
        } catch (Exception e) {
            System.out.println("Não foi possível criar Entidade de Persistência");
        }
        return null;
    }

    public void imprimir(UsuarioSigera usuarioAutenticado) throws IOException {
        Map parametrosImpressao = obterParametrosParaImpressao(usuarioAutenticado);

        FacesContext contexto = FacesContext.getCurrentInstance();
        ExternalContext contextoExterno = contexto.getExternalContext();
        HttpServletResponse resp = (HttpServletResponse) contextoExterno.getResponse();

        ServletContext servletContext = (ServletContext) contextoExterno.getContext();
        String realPath = servletContext.getRealPath("") + "/resources/relatorios";
        String dataHora = Long.toString(new Date().getTime());

        String caminhoArquivoPDF = Conexoes.getPASTA_REQUERIMENTOS() + dataHora + "-REQ-" + String.valueOf(this.id) + "-" + this.getUsuario().getNome().trim() + ".pdf";

        File arquivoPdf = new File(caminhoArquivoPDF);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Conexoes.lerParametros();
            Connection conn = (Connection) DriverManager.getConnection(Conexoes.getCONEXAO_BANCO(), Conexoes.getUSUARIO_BANCO(), Conexoes.getCHAVE_BANCO());

            File arquivo = new File(realPath + "/requerimento.jasper");

            JasperReport relatorioJasper = (JasperReport) JRLoader.loadObject(arquivo);

            JasperPrint jasperPrint = JasperFillManager.fillReport(relatorioJasper, parametrosImpressao, conn);

            JasperExportManager.exportReportToPdfFile(jasperPrint, caminhoArquivoPDF);

            byte[] bytes = JasperRunManager.runReportToPdf(relatorioJasper, parametrosImpressao);
            resp.reset();
            resp.setContentType("application/pdf");
            resp.setContentLength(bytes.length);
            resp.setHeader("Content-Disposition", "inline; filename=\"" + arquivoPdf.getName() + "\"");
            ServletOutputStream ouputStream = resp.getOutputStream();
            ouputStream.write(bytes, 0, bytes.length);
            ouputStream.flush();
            ouputStream.close();

            // Informar ao  JSF que não precisa lidar com a resposta, caso contrário, terá a seguinte exceção nos logs:
            // java.lang.IllegalStateException: Cannot forward after response has been committed.
            contexto.responseComplete();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private Map obterParametrosParaImpressao(UsuarioSigera usuarioAutenticado) {
        String dataProvaSegundaChamada = null;
        String turmaSegundaChamada = null;
        StringBuilder listaDisciplinasAcerto = new StringBuilder();
        StringBuilder listaDisciplinasEmenta = new StringBuilder();
        Collection<Turma> turmas = null;
        Collection<Disciplina> disciplinas = null;

        //Se o requerimento for de ajuste de matricula (Acrescimo / Cancelamento de Disciplinas)
        if (this instanceof RequerimentoAcrescimoDisciplina) {
            turmas = ((RequerimentoAcrescimoDisciplina) this).getTurmasAcertoMatricula(usuarioAutenticado.getUsuarioLdap().getBuscadorLdap());
        }
        if (this instanceof RequerimentoCancelamentoDisciplina) {
            turmas = ((RequerimentoCancelamentoDisciplina) this).getTurmasAcertoMatricula(usuarioAutenticado.getUsuarioLdap().getBuscadorLdap());
        }
        if (turmas != null && turmas.size() > 0) {
            for (Turma t : turmas) {
                listaDisciplinasAcerto.append("* ").append(t.getDisciplina().getNome());
                listaDisciplinasAcerto.append(" - ").append(t.getProfessor().getNome());
                listaDisciplinasAcerto.append(" - Turma: [").append(t.getNome()).append("] \n");
            }
        }
        //Se o requerimento for de Segunda Chamada
        if (this instanceof RequerimentoSegundaChamada) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dataProvaSegundaChamada = dateFormat.format(((RequerimentoSegundaChamada) this).getDataProva());
            turmaSegundaChamada = ((RequerimentoSegundaChamada) this).getTurma().getDisciplina().getNome() + " - "
                    + ((RequerimentoSegundaChamada) this).getTurma().getProfessor().getNome() + " - Turma: [ "
                    + ((RequerimentoSegundaChamada) this).getTurma().getNome() + " ] ";
        }

        //Se o requerimento for de Ementa de Disciplinas
        if (this instanceof RequerimentoEmenta) {
            disciplinas = ((RequerimentoEmenta) this).getDisciplinas();
        }
        if (disciplinas != null && disciplinas.size() > 0) {
            for (Disciplina d : disciplinas) {
                listaDisciplinasEmenta.append("* ").append(d.getNome());
                listaDisciplinasEmenta.append(" - ").append(d.getCurso().getNome()).append(" \n");
            }
        }

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = servletContext.getRealPath("") + "/resources/relatorios";

        String caminhoImagemSigera = realPath + "/marca-inf.jpg";
        String caminhoImagemUFG = realPath + "/marca-ufg.jpg";

        Map parametros = new HashMap();
        parametros.put("tipoRequerimento", EnumTipoRequerimento.obtenha(this.tipo).getNome().toUpperCase());
        parametros.put("requerimentoId", this.id);
        parametros.put("nomeRequerente", this.getUsuario().getNome().trim());
        parametros.put("matriculaRequerente", this.getUsuario().getMatricula());
        parametros.put("nomeCurso", this.getCurso().getNome().trim());
        parametros.put("caminhoImagemSigera", caminhoImagemSigera);
        parametros.put("caminhoImagemUFG", caminhoImagemUFG);
        parametros.put("email", this.getUsuario().getUsuarioLdap().getEmail());
        parametros.put("dataProvaSegundaChamada", dataProvaSegundaChamada);
        parametros.put("turmaSegundaChamada", turmaSegundaChamada);
        parametros.put("listaDisciplinasAcerto", listaDisciplinasAcerto.toString().trim());
        parametros.put("listaDisciplinasEmenta", listaDisciplinasEmenta.toString().trim());

        return parametros;
    }
}
