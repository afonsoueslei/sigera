package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.controle.adaptador.AdaptadorTurmaTela;
import br.ufg.inf.sigera.controle.datamodels.TurmaDataModel;
import br.ufg.inf.sigera.controle.tela.TurmaTela;
import br.ufg.inf.sigera.modelo.AssociacaoPerfilCurso;
import br.ufg.inf.sigera.modelo.Turma;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.email.GerenciadorEmail;
import br.ufg.inf.sigera.modelo.perfil.EnumPerfil;
import br.ufg.inf.sigera.modelo.requerimento.EnumTipoRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoAcrescimoDisciplina;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoCancelamentoDisciplina;
import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import br.ufg.inf.sigera.modelo.perfil.PerfilAlunoPosStrictoSensu;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class AcertoMatriculaBean implements Serializable {

    private TurmaDataModel dataModelTurmas;
    private List<TurmaTela> turmasTela;
    private List<TurmaTela> turmasFiltradas;
    private List<TurmaTela> turmasSelecionadas;
    private Map<Integer, TurmaTela> turmasTelaEscolhidas;
    private TurmaDataModel dataModelEscolhidas;
    private TurmaTela turmaExcluir;
    private String justificativa;
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    boolean turmasEscolhidasDesatualizadasNaTela;
    private String tipoRequerimento;
    private String tipoAcrescimo;
    private String tipoCancelamento;
    private final MensagensTela mensagemDeTela = new MensagensTela();
    private static final String MT002 = "MT.002";

    public AcertoMatriculaBean() {
        this.tipoAcrescimo = Paginas.getACrescimo();
        this.tipoCancelamento = Paginas.getCancelamento();
    }

    public String getTipoAcrescimo() {
        return tipoAcrescimo;
    }

    public void setTipoAcrescimo(String tipoAcrescimo) {
        this.tipoAcrescimo = tipoAcrescimo;
    }

    public String getTipoCancelamento() {
        return tipoCancelamento;
    }

    public void setTipoCancelamento(String tipoCancelamento) {
        this.tipoCancelamento = tipoCancelamento;
    }

    public String getTipoRequerimento() {
        return tipoRequerimento;
    }

    public void setTipoRequerimento(String tipoRequerimento) {
        this.tipoRequerimento = tipoRequerimento;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public TurmaTela getTurmaExcluir() {
        return turmaExcluir;
    }

    public void setTurmaExcluir(TurmaTela turmaExcluir) {
        this.turmaExcluir = turmaExcluir;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public Map<Integer, TurmaTela> getTurmasTelaEscolhidas() {
        if (this.turmasTelaEscolhidas == null) {
            this.turmasTelaEscolhidas = new HashMap<Integer, TurmaTela>();
        }
        return turmasTelaEscolhidas;
    }

    public List<TurmaTela> getTurmasSelecionadas() {
        return turmasSelecionadas;
    }

    public void setTurmasSelecionadas(List<TurmaTela> turmasSelecionadas) {
        this.turmasSelecionadas = turmasSelecionadas;
    }

    public List<TurmaTela> getTurmasTela() {
        return turmasTela;
    }

    public void setTurmasTela(List<TurmaTela> turmasTela) {
        this.turmasTela = turmasTela;
    }

    public TurmaTela getDisciplinaExcluir() {
        return turmaExcluir;
    }

    public void setDisciplinaExcluir(TurmaTela turmaExcluir) {
        this.turmaExcluir = turmaExcluir;
    }

    public String salvar() {
        String msg = null;
        BuscadorLdap buscadorLdap = loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap();

        if (!validarRequerimento()) {
            return this.tipoRequerimento;
        }
        Requerimento requerimentoAcerto = null;

        if (this.tipoRequerimento.equalsIgnoreCase(Paginas.getACrescimo())) {
            msg = Mensagens.obtenha(MT002, EnumTipoRequerimento.ACRESCIMO_DISCIPLINAS.getNome());
            requerimentoAcerto = new RequerimentoAcrescimoDisciplina(loginBean.getUsuario(), obtenhaTurmasEscolhidas(), justificativa);
        }

        if (this.tipoRequerimento.equalsIgnoreCase(Paginas.getCancelamento())) {
            msg = Mensagens.obtenha(MT002, EnumTipoRequerimento.CANCELAMENTO_DISCIPLINAS.getNome());
            requerimentoAcerto = new RequerimentoCancelamentoDisciplina(loginBean.getUsuario(), obtenhaTurmasEscolhidas(), justificativa);
        }

        //persiste requerimento no banco                
        if (requerimentoAcerto != null && requerimentoAcerto.salvar()) {

            List<UsuarioSigera> destinatarios = new ArrayList<UsuarioSigera>();

            //Se o requerimento for de um aluno regular da POS então o requerimento deve ser validado 
            //pelo orientador antes de ir para o coordenador
            if (loginBean.getUsuario().getPerfilAtual().getPerfil().getId() == EnumPerfil.ALUNO_POS_STRICTO_SENSU.getCodigo()) {
                UsuarioSigera orientador = new PerfilAlunoPosStrictoSensu().obtenhaOrientador(loginBean.getUsuario(), buscadorLdap);
                destinatarios.add(orientador);            
            } else {
                destinatarios.addAll(AssociacaoPerfilCurso.obtenhaUsuariosDoPerfilCurso(EnumPerfil.COORDENADOR_CURSO.getCodigo(),
                        requerimentoAcerto.getCurso().getId(), buscadorLdap));
            }

            if (loginBean.getConfiguracao().isEnviarEmail() && destinatarios.size()>0) {
                GerenciadorEmail gerenciadorEmail = new GerenciadorEmail();
                gerenciadorEmail.adicionarEmailRequerimento(requerimentoAcerto, destinatarios);
                gerenciadorEmail.enviarEmails();
            }

            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, msg, Paginas.getDetalheRequerimento());

            return Paginas.getAbrirRequerimentoID() + requerimentoAcerto.getId();
        }
        if (requerimentoAcerto != null) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha("MT.002.Falha", EnumTipoRequerimento.obtenha(requerimentoAcerto.getTipo()).getNome().toUpperCase()), Paginas.getPrincipal());
        }
        return Paginas.getPrincipal();
    }

    public void remover() {
        this.getTurmasTelaEscolhidas().remove(this.turmaExcluir.getNumero());
        turmasEscolhidasDesatualizadasNaTela = true;
        getDataModelEscolhidas();
    }

    public String voltar() {
        return Paginas.getPrincipal();
    }

    public TurmaDataModel getDataModelTurmas() {
        if (dataModelTurmas == null) {
            try {
                List<Turma> turmasbuscadas
                        = Turma.buscaTurmas(loginBean.getConfiguracao().getAnoCorrente(),
                                loginBean.getConfiguracao().getSemestreCorrente(),
                                loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap(),
                                loginBean.getUsuario().getPerfilAtual().getCurso().getId());

                this.turmasTela = new ArrayList<TurmaTela>();
                for (Turma d : turmasbuscadas) {
                    this.turmasTela.add(new AdaptadorTurmaTela(d));
                }
                this.dataModelTurmas = new TurmaDataModel(this.turmasTela);
            } catch (ExceptionInInitializerError ie) {
                Paginas.redirecionePaginaErro();
                Logger.getLogger(AcertoMatriculaBean.class.getName()).log(Level.SEVERE, null, ie);
            }
        }
        return dataModelTurmas;
    }

    public TurmaDataModel getDataModelEscolhidas() {
        if (this.dataModelEscolhidas == null || turmasEscolhidasDesatualizadasNaTela) {
            try {
                List<TurmaTela> listaEscolhidas = new ArrayList<TurmaTela>();
                for (TurmaTela turma : this.getTurmasTelaEscolhidas().values()) {
                    listaEscolhidas.add(turma);
                }
                this.dataModelEscolhidas = new TurmaDataModel(listaEscolhidas);
            } catch (Exception ie) {
                Paginas.redirecionePaginaErro();
                Logger.getLogger(AcertoMatriculaBean.class.getName()).log(Level.SEVERE, null, ie);
            }
        }

        turmasEscolhidasDesatualizadasNaTela = false;
        return dataModelEscolhidas;
    }

    public Collection<Turma> obtenhaTurmasEscolhidas() {
        Collection<Turma> listaEscolhidas = new ArrayList<Turma>();
        for (TurmaTela turmaTela : this.getTurmasTelaEscolhidas().values()) {
            listaEscolhidas.add(turmaTela.getTurma());
        }
        return listaEscolhidas;
    }

    public List<TurmaTela> getTurmasFiltradas() {
        return turmasFiltradas;
    }

    public void setTurmasFiltradas(List<TurmaTela> turmasFiltrados) {
        this.turmasFiltradas = turmasFiltrados;
    }

    public void limparSelecao() {
        this.turmasSelecionadas = null;
    }

    public void adicionar() {
        if (!validarTurmasSelecionadas()) {
            return;
        }

        for (TurmaTela turma : this.turmasSelecionadas) {
            this.getTurmasTelaEscolhidas().put(turma.getNumero(), turma);
        }
        turmasEscolhidasDesatualizadasNaTela = true;
        getDataModelEscolhidas();
        limparSelecao();
    }

    private boolean validarRequerimento() {
        if (this.turmasTelaEscolhidas == null
                || this.turmasTelaEscolhidas.size() < 1) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.403"), tipoRequerimento);
            return false;
        }
        return true;
    }

    private boolean validarTurmasSelecionadas() {
        int qtdeEscolhidas = getTurmasTelaEscolhidas().size();
        int qtdeNovasSelecionadas = this.turmasSelecionadas.size();

        if (qtdeEscolhidas + qtdeNovasSelecionadas > 3) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.214"), tipoRequerimento);
            return false;
        }
        return true;
    }
}
