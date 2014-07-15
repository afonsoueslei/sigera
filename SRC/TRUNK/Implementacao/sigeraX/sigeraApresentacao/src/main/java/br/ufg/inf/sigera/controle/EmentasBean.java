package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.controle.datamodels.DisciplinaDataModel;
import br.ufg.inf.sigera.controle.tela.DisciplinaTela;
import br.ufg.inf.sigera.controle.adaptador.AdaptadorDisciplinaTela;
import br.ufg.inf.sigera.modelo.AssociacaoPerfilCurso;
import br.ufg.inf.sigera.modelo.Disciplina;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.email.GerenciadorEmail;
import br.ufg.inf.sigera.modelo.perfil.EnumPerfil;
import br.ufg.inf.sigera.modelo.requerimento.EnumTipoRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoEmenta;
import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class EmentasBean implements Serializable {

    private DisciplinaDataModel dataModelDisciplinas;
    private List<DisciplinaTela> disciplinasTela;
    private List<DisciplinaTela> disciplinasFiltradas;
    private List<DisciplinaTela> disciplinasSelecionadas;
    private Map<Integer, DisciplinaTela> disciplinasTelaEscolhidas;
    private DisciplinaDataModel dataModelEscolhidas;
    private DisciplinaTela disciplinaExcluir;
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    boolean disciplinasEscolhidasDesatualizadasNaTela;
    private final MensagensTela mensagemDeTela = new MensagensTela();
    private String justificativa;

    public EmentasBean() {
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    
    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public Map<Integer, DisciplinaTela> getDisciplinasTelaEscolhidas() {
        if (this.disciplinasTelaEscolhidas == null) {
            this.disciplinasTelaEscolhidas = new HashMap<Integer, DisciplinaTela>();
        }
        return disciplinasTelaEscolhidas;
    }

    public List<DisciplinaTela> getDisciplinasSelecionadas() {
        return disciplinasSelecionadas;
    }

    public void setDisciplinasSelecionadas(List<DisciplinaTela> disciplinasSelecionadas) {
        this.disciplinasSelecionadas = disciplinasSelecionadas;
    }

    public List<DisciplinaTela> getDisciplinasTela() {
        return disciplinasTela;
    }

    public void setDisciplinasTela(List<DisciplinaTela> disciplinasTela) {
        this.disciplinasTela = disciplinasTela;
    }

    public DisciplinaTela getDisciplinaExcluir() {
        return disciplinaExcluir;
    }

    public void setDisciplinaExcluir(DisciplinaTela disciplinaExcluir) {
        this.disciplinaExcluir = disciplinaExcluir;
    }

    public String salvar() {
        if (!validarRequerimento()) {
            return Paginas.getEmentas();
        }

        Requerimento requerimento = new RequerimentoEmenta(loginBean.getUsuario(), obtenhaDisciplinasEscolhidas(), this.getJustificativa());
        requerimento.salvar();

        List<UsuarioSigera> destinatarios
                = AssociacaoPerfilCurso.obtenhaUsuarios(EnumPerfil.SECRETARIA.getCodigo(),
                        requerimento.getCurso().getId(),
                        loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap());

        if (loginBean.getConfiguracao().isEnviarEmail()) {
            GerenciadorEmail gerenciadorEmail = new GerenciadorEmail();
            gerenciadorEmail.adicionarEmailRequerimento(requerimento, destinatarios);
            gerenciadorEmail.enviarEmails();
        }
        mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.002", EnumTipoRequerimento.EMENTAS.getNome()), Paginas.getEmentas());
        return Paginas.getAbrirRequerimentoID() + requerimento.getId();
    }

    public void remover() {
        this.getDisciplinasTelaEscolhidas().remove(this.disciplinaExcluir.getNumero());
        disciplinasEscolhidasDesatualizadasNaTela = true;
        getDataModelEscolhidas();
    }

    public String voltar() {
        return Paginas.getPrincipal();
    }

    public DisciplinaDataModel getDataModelDisciplinas() {
        if (dataModelDisciplinas == null) {
            List<Disciplina> disciplinasbuscados = Disciplina.buscaTodosDisciplinas();
            this.disciplinasTela = new ArrayList<DisciplinaTela>();
            for (Disciplina d : disciplinasbuscados) {
                this.disciplinasTela.add(new AdaptadorDisciplinaTela(d));
            }
            this.dataModelDisciplinas = new DisciplinaDataModel(this.disciplinasTela);
        }
        return dataModelDisciplinas;
    }

    public DisciplinaDataModel getDataModelEscolhidas() {
        if (this.dataModelEscolhidas == null || disciplinasEscolhidasDesatualizadasNaTela) {
            List<DisciplinaTela> listaEscolhidas = new ArrayList<DisciplinaTela>();
            for (DisciplinaTela disciplina : this.getDisciplinasTelaEscolhidas().values()) {
                listaEscolhidas.add(disciplina);
            }
            this.dataModelEscolhidas = new DisciplinaDataModel(listaEscolhidas);
        }
        disciplinasEscolhidasDesatualizadasNaTela = false;
        return dataModelEscolhidas;
    }

    public Collection<Disciplina> obtenhaDisciplinasEscolhidas() {
        Collection<Disciplina> listaEscolhidas = new ArrayList<Disciplina>();
        for (DisciplinaTela disciplinaTela : this.getDisciplinasTelaEscolhidas().values()) {
            listaEscolhidas.add(disciplinaTela.getDisciplina());
        }
        return listaEscolhidas;
    }

    public List<DisciplinaTela> getDisciplinasFiltradas() {
        return disciplinasFiltradas;
    }

    public void setDisciplinasFiltradas(List<DisciplinaTela> disciplinasFiltrados) {
        this.disciplinasFiltradas = disciplinasFiltrados;
    }

    public void limparSelecao() {
        this.disciplinasSelecionadas = null;
    }

    public void adicionar() {
        for (DisciplinaTela disciplina : this.disciplinasSelecionadas) {
            this.getDisciplinasTelaEscolhidas().put(disciplina.getNumero(), disciplina);
        }
        disciplinasEscolhidasDesatualizadasNaTela = true;
        getDataModelEscolhidas();
        limparSelecao();
    }

    private boolean validarRequerimento() {
        if (this.disciplinasTelaEscolhidas == null
                || this.disciplinasTelaEscolhidas.size() < 1) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.402"), Paginas.getEmentas());
            return false;
        }
        return true;
    }
}
