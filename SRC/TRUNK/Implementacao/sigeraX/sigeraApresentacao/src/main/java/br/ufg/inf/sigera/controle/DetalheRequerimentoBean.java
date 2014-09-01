package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.modelo.AssociacaoPerfilCurso;
import br.ufg.inf.sigera.modelo.Parecer;
import br.ufg.inf.sigera.modelo.Turma;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.email.GerenciadorEmail;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import br.ufg.inf.sigera.modelo.perfil.EnumPerfil;
import br.ufg.inf.sigera.modelo.requerimento.TurmaComStatus;
import br.ufg.inf.sigera.modelo.requerimento.EnumStatusRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.EnumTipoRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoSegundaChamada;
import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import br.ufg.inf.sigera.modelo.Disciplina;
import br.ufg.inf.sigera.modelo.Plano;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoAcrescimoDisciplina;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoCancelamentoDisciplina;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class DetalheRequerimentoBean {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    @ManagedProperty(value = "#{planoBean}")
    private PlanoBean planoBean;
    private Collection<Turma> turmas;
    private Turma turma;
    private Requerimento requerimento;
    private int statusParecer;
    private List<EnumStatusRequerimento> listaStatus;
    private String justificativaDeferimento;
    // Controles para conferência de documentos de assinatura de estágio.
    private boolean documentosConferidos;
    private boolean coordenadorEstagio;
    private boolean diretor;
    private String justificativaConferencia;
    // Parecer de acréscimo e cancelamento de disciplinas.
    private String justificativaParecerAcerto;
    private List<TurmaComStatus> turmasComStatus;
    private final MensagensTela mensagemDeTela = new MensagensTela();
    private static final String MT004 = "MT.004";
    //Mostrar número de pedidos aberto por disciplina/turma
    private Integer qtdePedidosAcrescimo;
    private Integer qtdePedidosCancelamento;
    

    public DetalheRequerimentoBean() {
        listaStatus = new ArrayList<EnumStatusRequerimento>();
        listaStatus.add(EnumStatusRequerimento.DEFERIDO);
        listaStatus.add(EnumStatusRequerimento.INDEFERIDO);
        limparControlesConferencia();
    }

    @PostConstruct
    public void init() {
        obtenhaRequerimento();        
        if (requerimento == null || !usuarioPodeVerRequerimento()) {
            redirecioneParaPaginaConsulta();
        }
    }

    private void limparControlesConferencia() {
        this.documentosConferidos = false;
        this.coordenadorEstagio = true;
        this.diretor = false;
        this.justificativaConferencia = null;
    }

    private void redirecioneParaPaginaConsulta() {
        try {
            ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
            contexto.redirect("consultar_requerimento.xhtml");           
        } catch (IOException ex) {
            Logger.getLogger(DetalheRequerimentoBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception iex) {
            Logger.getLogger(DetalheRequerimentoBean.class.getName()).log(Level.SEVERE, null, iex);
        }
    }

    public List<TurmaComStatus> getTurmasComStatus() {
        if (this.turmasComStatus == null) {
            this.turmasComStatus = new ArrayList<TurmaComStatus>();

            if (this.requerimento != null) {
                List<TurmaComStatus> turmasRequerimento = this.requerimento.getTurmasComStatus(
                        this.loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap());

                if (turmasRequerimento != null) {
                    for (TurmaComStatus t : turmasRequerimento) {
                        this.turmasComStatus.add(this.requerimento.copiarTurmaComStatus(t));
                    }
                }
            }
        }
        return turmasComStatus;
    }

    public void setTurmasComStatus(List<TurmaComStatus> turmasComStatus) {
        this.turmasComStatus = turmasComStatus;
    }

    public String getJustificativaParecerAcerto() {
        return justificativaParecerAcerto;
    }

    public void setJustificativaParecerAcerto(String justificativaParecerAcerto) {
        this.justificativaParecerAcerto = justificativaParecerAcerto;
    }

    public boolean isDocumentosConferidos() {
        return documentosConferidos;
    }

    public void setDocumentosConferidos(boolean documentosConferidos) {
        this.documentosConferidos = documentosConferidos;
    }

    public boolean isCoordenadorEstagio() {
        return coordenadorEstagio;
    }

    public void setCoordenadorEstagio(boolean coordenadorEstagio) {
        this.coordenadorEstagio = coordenadorEstagio;
    }

    public boolean isDiretor() {
        return diretor;
    }

    public void setDiretor(boolean diretor) {
        this.diretor = diretor;
    }

    public String getJustificativaConferencia() {
        return justificativaConferencia;
    }

    public void setJustificativaConferencia(String justificativaConferencia) {
        this.justificativaConferencia = justificativaConferencia;
    }

    public String getMensagemConfirmacao() {
        return Mensagens.obtenha(this.requerimento.getCodigoMensagemConfirmacao());
    }

    public String getJustificativaDeferimento() {
        return justificativaDeferimento;
    }

    public void setJustificativaDeferimento(String justificativaDeferimento) {
        this.justificativaDeferimento = justificativaDeferimento;
    }

    public List<EnumStatusRequerimento> getListaStatus() {
        return listaStatus;
    }

    public void setListaStatus(List<EnumStatusRequerimento> listaStatus) {
        this.listaStatus = listaStatus;
    }

    public int getStatusParecer() {
        return statusParecer;
    }

    public void setStatusParecer(int statusParecer) {
        this.statusParecer = statusParecer;
    }

    public Requerimento getRequerimento() {
        return requerimento;
    }

    private void obtenhaRequerimento() {
        requerimento = null;
        String param = null;
        Integer id = 0;
        try {
            param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("numero");
        } catch (Exception ex) {
            Logger.getLogger(Requerimento.class.getName()).log(Level.WARNING, "Paramêtro não pode ser recuperado", ex);
        }

        if (param != null) {
            try {
                id = Integer.valueOf(param);
            } catch (NumberFormatException ex) {
                Logger.getLogger(Requerimento.class.getName()).log(Level.WARNING, "Paramêtro passado não é um número válido.", ex);
            }
        }                        
        requerimento = Requerimento.obtenha(loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap(), id);        
    }

    public Boolean usuarioPodeVerRequerimento() {
        UsuarioSigera usuarioLogado = loginBean.getUsuario();
        //somente o próprio usuário, o administrador, o coordenador Geral e aqueles que podem dar parecer podem ver o requerimento
        return usuarioLogado.getId() == requerimento.getUsuario().getId()
                || usuarioLogado.getPerfilAtual().getPerfil().getId() == EnumPerfil.ADMINISTRADOR_SISTEMA.getCodigo()
                || usuarioLogado.getPerfilAtual().getPerfil().getId() == EnumPerfil.COORDENADOR_GERAL.getCodigo()
                || requerimento.perfilPermiteDarParecer(usuarioLogado)
                || requerimento.usuarioPodeConferirDocumentos(usuarioLogado);
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public PlanoBean getPlanoBean() {
        return planoBean;
    }

    public void setPlanoBean(PlanoBean planoBean) {
        this.planoBean = planoBean;
    }

    public Collection<Turma> getTurmas() {
        if (this.turmas == null) {
            this.turmas = this.requerimento.getTurmasAcertoMatricula(this.loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap());
        }
        return this.turmas;
    }

    public void cancelar() {
        if (this.requerimento != null && this.requerimento.getStatus() == EnumStatusRequerimento.ABERTO.getCodigo()) {
            this.requerimento.setStatus(EnumStatusRequerimento.CANCELADO.getCodigo());

            if (this.requerimento.salvar()) {
                mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.509"), Paginas.getDetalheRequerimento());
            }
        }
        mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.510"), Paginas.getDetalheRequerimento());
    }

    public String voltar() {
        return Paginas.getPrincipal();
    }

    public String voltarLista() {
        return Paginas.getConsultarRequerimentos();
    }

    public String editarPlano() {
        return planoBean.editar(this.requerimento.getPlano());
    }

    public boolean usuarioPodeCancelar() {
        boolean usuarioIgual = this.requerimento.getUsuario().getId() == this.loginBean.getUsuario().getId();
        boolean estaAberto = this.requerimento.estaAberto();
        boolean podeCancelar = usuarioIgual && estaAberto;

        return podeCancelar;
    }

    public Date getDataProva() {
        if (this.requerimento instanceof RequerimentoSegundaChamada) {
            return ((RequerimentoSegundaChamada) this.requerimento).getDataProva();
        } else {
            return null;
        }
    }

    public Turma getTurma() {
        if (this.requerimento instanceof RequerimentoSegundaChamada
                && this.turma == null) {
            Turma turmaProva = ((RequerimentoSegundaChamada) this.requerimento).getTurma();
            UsuarioSigera usuarioProfessor = turmaProva.getProfessor().getUsuario();
            BuscadorLdap buscadorLdap = this.loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap();
            usuarioProfessor.setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(usuarioProfessor.getId()));
            this.turma = turmaProva;
        }

        return this.turma;
    }

    public boolean podeVisualizarAnexos() {
        return this.requerimento.autorizaVisualizarAnexos(loginBean.getUsuario());
    }

    public boolean podeVisualizarPareceres() {
        return this.requerimento.autorizaVisualizarParecer(loginBean.getUsuario());
    }

    public boolean podeVisualizarTelefone() {
        return this.requerimento.podeVisualizarTelefone(loginBean.getUsuario());
    }

    public boolean usuarioPodeDarParecer() {
        return this.requerimento.autorizaDarParecer(loginBean.getUsuario());
    }

    public boolean usuarioPodeDarParecerAcertoMatricula() {
        return this.requerimento.autorizaDarParecerAcertoMatricula(loginBean.getUsuario());
    }

    public boolean usuarioPodeEditarPlano() {
        return this.requerimento.autorizaEditarPlano(loginBean.getUsuario());
    }

    public void cancelarParecer() {
        this.statusParecer = 0;
        this.justificativaDeferimento = null;
    }

    public String confirmarParecer() {
        if (!validarParecer()) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.017"), Paginas.getDetalheRequerimento());
            return enderecoRedirecionamento();
        }

        Parecer parecer = new Parecer(this.requerimento, loginBean.getUsuario(), this.justificativaDeferimento, this.statusParecer);
        this.requerimento.adicionarParecer(parecer);
        if (this.requerimento.salvar()) {

            if (loginBean.getConfiguracao().isEnviarEmail()) {
                GerenciadorEmail gerenciadorEmail = new GerenciadorEmail();
                gerenciadorEmail.adicionarEmailParecer(loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap(), requerimento);
                gerenciadorEmail.enviarEmails();
            }
            //Limpar o bean para atualizar a lista de requerimentos
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("consultarRequerimentoBean");
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha(MT004), Paginas.getDetalheRequerimento());
            return enderecoRedirecionamento();
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("consultarRequerimentoBean");
        mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha("MT.709", "REQUERIMENTO"), Paginas.getDetalheRequerimento());
        return Paginas.getConsultarRequerimentos();
    }

    private boolean validarParecer() {
        return this.statusParecer != 0;
    }

    public Collection<Parecer> obtenhaPareceres() {
        return this.requerimento.obtenhaPareceres(loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap());
    }

    public void cancelarConferencia() {
        limparControlesConferencia();
    }

    public boolean usuarioPodeConferirDocumentos() {
        return  this.requerimento.getStatus() == EnumStatusRequerimento.ABERTO.getCodigo() && 
                this.requerimento.usuarioPodeConferirDocumentos(this.loginBean.getUsuario());
    }

    public boolean validarConferencia() {
        return this.documentosConferidos && validarNotificacao();
    }

    public boolean validarNotificacao() {
        return this.coordenadorEstagio || this.diretor;
    }

    public String confirmarConferencia() {
        if (!this.documentosConferidos) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.512"), Paginas.getDetalheRequerimento());
            return enderecoRedirecionamento();
        }

        if (!validarNotificacao()) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.513"), Paginas.getDetalheRequerimento());
            return enderecoRedirecionamento();
        }

        Parecer parecer = new Parecer(this.requerimento, loginBean.getUsuario(), this.justificativaConferencia, EnumStatusRequerimento.CONFERIDO.getCodigo());
        this.requerimento.adicionarParecer(parecer);
        if (this.requerimento.salvar()) {
            List<UsuarioSigera> destinatarios = new ArrayList<UsuarioSigera>();

            if (this.coordenadorEstagio) {
                List<UsuarioSigera> coordenadoresEstagio
                        = AssociacaoPerfilCurso.obtenhaUsuariosDoPerfilCurso(EnumPerfil.COORDENADOR_ESTAGIO.getCodigo(),
                                requerimento.getCurso().getId(),
                                loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap());
                destinatarios.addAll(coordenadoresEstagio);
            }

            if (this.diretor) {
                List<UsuarioSigera> diretores
                        = AssociacaoPerfilCurso.obtenhaUsuariosDoPerfil(EnumPerfil.DIRETOR.getCodigo(),
                                loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap());
                destinatarios.addAll(diretores);
            }
            if (loginBean.getConfiguracao().isEnviarEmail()) {
                GerenciadorEmail gerenciadorEmail = new GerenciadorEmail();
                gerenciadorEmail.adicionarEmailRequerimento(requerimento, destinatarios);
                gerenciadorEmail.enviarEmails();
            }
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("consultarRequerimentoBean");
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.018"), Paginas.getDetalheRequerimento());
            return enderecoRedirecionamento();
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("consultarRequerimentoBean");
        mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha("MT.709", "REQUERIMENTO"), Paginas.getDetalheRequerimento());
        return Paginas.getConsultarRequerimentos();
    }

    private String enderecoRedirecionamento() {
        return Paginas.getAbrirRequerimentoID() + requerimento.getId();
    }

    public boolean validarStatusParecerAcerto() {
        if (this.turmasComStatus == null || this.turmasComStatus.isEmpty()) {
            return false;
        }

        for (TurmaComStatus turma : this.getTurmasComStatus()) {
            if (turma.getStatus() != EnumStatusRequerimento.DEFERIDO.getCodigo()
                    && turma.getStatus() != EnumStatusRequerimento.INDEFERIDO.getCodigo()) {
                return false;
            }
        }

        return true;
    }

    public String confirmarParecerAcerto() {
        if (!validarStatusParecerAcerto()) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.019"), Paginas.getDetalheRequerimento());
            return enderecoRedirecionamento();
        }

        int statusParecerAcerto = obtenhaStatusParecerAcerto();
        this.requerimento.setTurmasComStatus(this.turmasComStatus);

        Parecer parecer = new Parecer(this.requerimento, loginBean.getUsuario(), this.justificativaParecerAcerto, statusParecerAcerto);
        this.requerimento.adicionarParecer(parecer);
        if (this.requerimento.salvar()) {
            if (loginBean.getConfiguracao().isEnviarEmail()) {
                GerenciadorEmail gerenciadorEmail = new GerenciadorEmail();
                gerenciadorEmail.adicionarEmailParecer(null, requerimento);
                gerenciadorEmail.enviarEmails();
            }
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("consultarRequerimentoBean");
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha(MT004), Paginas.getDetalheRequerimento());
            return enderecoRedirecionamento();
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("consultarRequerimentoBean");
        mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha("MT.709", "REQUERIMENTO"), Paginas.getDetalheRequerimento());
        return Paginas.getConsultarRequerimentos();

    }

    public void cancelarParecerAcerto() {
        this.turmasComStatus = null;
        this.justificativaParecerAcerto = null;
    }

    private int obtenhaStatusParecerAcerto() {
        for (TurmaComStatus turma : this.getTurmasComStatus()) {
            if (turma.getStatus() == EnumStatusRequerimento.DEFERIDO.getCodigo()) {
                return EnumStatusRequerimento.DEFERIDO.getCodigo();
            }
        }
        return EnumStatusRequerimento.INDEFERIDO.getCodigo();
    }

    public String visualizarEmenta() {
        return Paginas.getImpressaoEmenta();
    }

    public Integer getQtdePedidosAcrescimo() {
        return qtdePedidosAcrescimo;
    }

    public void setQtdePedidosAcrescimo(Integer qtdePedidosAcrescimo) {
        this.qtdePedidosAcrescimo = qtdePedidosAcrescimo;
    }

    public Integer getQtdePedidosCancelamento() {
        return qtdePedidosCancelamento;
    }

    public void setQtdePedidosCancelamento(Integer qtdePedidosCancelamento) {
        this.qtdePedidosCancelamento = qtdePedidosCancelamento;
    }

    public void visualizarExtrato(String id) {
        //implementar método para exibir extrato com pedidos em aberto para a turma      
        qtdePedidosAcrescimo = RequerimentoAcrescimoDisciplina.consultarPedidosAberto(Integer.parseInt(id));
        qtdePedidosCancelamento = RequerimentoCancelamentoDisciplina.consultarPedidosAberto(Integer.parseInt(id));
    }

    public void imprimirEmenta(String id) throws IOException {
        Integer idDisciplina = Integer.parseInt(id);
        String nomeDisciplina = Disciplina.obtenhaDisciplina(idDisciplina).getNome();
        BuscadorLdap buscadorLdap = loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap();

        Turma turmaDessaEmenta = Turma.obtenhaTurmaDisciplina(idDisciplina, buscadorLdap);
        Plano planoDessaEmenta = Plano.obtenhaPlanoTurma(turmaDessaEmenta);

        if (planoDessaEmenta != null) {
            planoDessaEmenta.imprimir();
            //caso disciplina não tem plano associado
        } else {
            mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha("MT.525", nomeDisciplina), Paginas.getDetalheRequerimento());
        }
    }

    public String cssStatus() {
        if (this.requerimento.getStatus() == EnumStatusRequerimento.CANCELADO.getCodigo()) {
            return "warning";
        }

        if (this.requerimento.getStatus() == EnumStatusRequerimento.CONFERIDO.getCodigo()
                || this.requerimento.getStatus() == EnumStatusRequerimento.CONCLUIDO.getCodigo()) {
            return "info";
        }

        if (this.requerimento.getStatus() == EnumStatusRequerimento.INDEFERIDO.getCodigo()) {
            return "error";
        }

        if (this.requerimento.getStatus() == EnumStatusRequerimento.DEFERIDO.getCodigo()) {
            return "success";
        }

        return "";
    }

    public Boolean requerimentoEhPlano() {
        if (this.requerimento.getTipo() == EnumTipoRequerimento.PLANO.getCodigo()) {
            return true;
        }
        return false;
    }
    
}
