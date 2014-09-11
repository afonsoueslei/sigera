package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.controle.adaptador.AdaptadorTurmaTela;
import br.ufg.inf.sigera.controle.datamodels.TurmaDataModel;
import br.ufg.inf.sigera.controle.tela.TurmaTela;
import br.ufg.inf.sigera.modelo.Disciplina;
import br.ufg.inf.sigera.modelo.Professor;
import br.ufg.inf.sigera.modelo.Turma;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import br.ufg.inf.sigera.controle.servico.Sessoes;
import br.ufg.inf.sigera.modelo.perfil.EnumPerfil;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean
@SessionScoped
public class TurmaBean {

    private TurmaDataModel dataModel;
    private List<TurmaTela> turmasTela;
    private List<TurmaTela> turmasFiltradas;
    private Turma turmaSelecionada;
    private Turma turmaEditavel;
    private Turma turmaExcluir;
    private List<Disciplina> listaDisciplinas;
    private List<Professor> listaProfessores;
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private final MensagensTela mensagemDeTela = new MensagensTela();

    public TurmaBean() {
    }

    public List<TurmaTela> getTurmasTela() {
        return turmasTela;
    }

    public void setTurmasTela(List<TurmaTela> turmasTela) {
        this.turmasTela = turmasTela;
    }

    public Turma getTurmaExcluir() {
        return turmaExcluir;
    }

    public void setTurmaExcluir(Turma turmaExcluir) {
        this.turmaExcluir = turmaExcluir;
    }

    public Integer getCodigoDisciplina() {
        if (getTurmaEditavel().getDisciplina() != null) {
            return this.turmaEditavel.getDisciplina().getId();
        }
        return null;
    }

    public void setCodigoDisciplina(Integer codigoDisciplina) {
        getTurmaEditavel().setDisciplina(Disciplina.obtenhaDisciplina(codigoDisciplina));
    }

    public List<Disciplina> getListaDisciplinas() {
        Integer codCurso;
        if (this.listaDisciplinas == null) {
            if (loginBean.getUsuario().getPerfilAtual().getPerfil().getId() == EnumPerfil.COORDENADOR_CURSO.getCodigo()) {
                codCurso = loginBean.getUsuario().getPerfilAtual().getCurso().getId();
                this.listaDisciplinas = Disciplina.buscaDisciplinasDoCurso(codCurso);
            } else {
                this.listaDisciplinas = Disciplina.buscaTodosDisciplinas();
            }
        }
        return this.listaDisciplinas;
    }

    public Integer getCodigoProfessor() {
        if (getTurmaEditavel().getProfessor() != null) {
            return this.turmaEditavel.getProfessor().getId();
        }
        return null;
    }

    public void setCodigoProfessor(Integer codigoProfessor) {
        getTurmaEditavel().setProfessor(
                Professor.obtenhaProfessor(loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap(), codigoProfessor));
    }

    public List<Professor> getListaProfessores() {
        if (this.listaProfessores == null) {
            this.listaProfessores
                    = Professor.buscaTodosProfessores(loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap());
        }
        return this.listaProfessores;
    }

    public Turma getTurmaSelecionada() {
        return turmaSelecionada;
    }

    public void setTurmaSelecionada(Turma turmaSelecionada) {
        this.turmaSelecionada = turmaSelecionada;
    }

    public Turma getTurmaEditavel() {
        if (turmaEditavel == null) {
            turmaEditavel = new Turma();

            definirNovoSemestre();
        }
        return turmaEditavel;
    }

    private void definirNovoSemestre() {
        Date dataHoje = new Date();

        if (dataHoje.before(loginBean.getConfiguracao().getDataFinalSemestre())) {
            if (loginBean.getConfiguracao().getSemestreCorrente() == 2) {
                turmaEditavel.setAno(loginBean.getConfiguracao().getAnoCorrente() + 1);
                turmaEditavel.setSemestre(loginBean.getConfiguracao().getSemestreCorrente() - 1);
            } else {
                turmaEditavel.setAno(loginBean.getConfiguracao().getAnoCorrente());
                turmaEditavel.setSemestre(loginBean.getConfiguracao().getSemestreCorrente() + 1);
            }
        } else {
            turmaEditavel.setAno(loginBean.getConfiguracao().getAnoCorrente());
            turmaEditavel.setSemestre(loginBean.getConfiguracao().getSemestreCorrente());
        }
    }

    public void setTurmaEditavel(Turma turmaEditavel) {
        this.turmaEditavel = turmaEditavel;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String salvar() {
        if (Turma.salvar(this.turmaEditavel)) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.508"), Paginas.getManterTurma());
        } else {
            mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha("MT.709", "TURMA"), Paginas.getManterTurma());
        }
        return voltarListaTurmas();
    }

    public void remover() {
        if (this.turmaExcluir != null) {
            Boolean removido = Turma.remover(this.turmaExcluir);
            if (removido) {
                mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.507"), Paginas.getManterTurma());
                Sessoes.limparBeans();
            } else {
                mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.500"), Paginas.getManterTurma());
                Sessoes.limparBeans();
            }
        }
    }

    public TurmaDataModel getDataModel() {
        if (dataModel == null) {
            try {
                BuscadorLdap buscadorLdap = loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap();
                List<Turma> turmasBuscadas;
                Integer codigoCurso;

                //Se perfil = Coordenador Curso: busca somente turmas do seu curso e do "ano/semestre corrente"
                //Se perfil = Coordenador Geral: busca todas as turmas do "ano/semestre corrente", inclusive relacionadas a disciplinas de serviço
                if (loginBean.getUsuario().getPerfilAtual().getPerfil().getId() == EnumPerfil.COORDENADOR_CURSO.getCodigo()) {
                    codigoCurso = loginBean.getUsuario().getPerfilAtual().getCurso().getId();
                    turmasBuscadas = Turma.buscaTurmas(loginBean.getConfiguracao().getAnoCorrente(), loginBean.getConfiguracao().getSemestreCorrente(), buscadorLdap, codigoCurso);

                } else if (loginBean.getUsuario().getPerfilAtual().getPerfil().getId() == EnumPerfil.COORDENADOR_GERAL.getCodigo()) {
                    turmasBuscadas = Turma.buscaTurmas(loginBean.getConfiguracao().getAnoCorrente(), loginBean.getConfiguracao().getSemestreCorrente(), buscadorLdap, 0);
                    //Se perfil = Administrador busca as turmas cadastradas
                } else {
                    turmasBuscadas = Turma.buscaTodasTurmas(buscadorLdap);
                }

                this.turmasTela = new ArrayList<TurmaTela>();
                for (Turma c : turmasBuscadas) {
                    this.turmasTela.add(new AdaptadorTurmaTela(c));
                }
                this.dataModel = new TurmaDataModel(this.turmasTela);
            } catch (Exception ie) {
                Paginas.redirecionePaginaErro();
                Logger.getLogger(TurmaBean.class.getName()).log(Level.SEVERE, null, ie);
            }
        }
        return dataModel;
    }

    public List<TurmaTela> getTurmasFiltradas() {
        return turmasFiltradas;
    }

    public void setTurmasFiltradas(List<TurmaTela> turmasFiltradas) {
        this.turmasFiltradas = turmasFiltradas;
    }

    private void prepararTurmaEditavel() {
        if (this.turmaSelecionada == null) {
            getTurmaEditavel();
        } else {
            this.turmaEditavel = new Turma(this.turmaSelecionada);
        }
    }

    public String editar() {
        prepararTurmaEditavel();
        return Paginas.getEditarTurma();
    }

    public void cancelar() {
        prepararTurmaEditavel();
    }

    public String voltarListaTurmas() {
        Sessoes.limparBeans();
        return Paginas.getManterTurma();
    }

    public void preProcess(Object document) {
        try {
            Document pdf = (Document) document;
            pdf.open();
            pdf.setPageSize(PageSize.A4);

            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String logo = servletContext.getRealPath("") + "/resources/img/cabecalho.png";
            pdf.add(Image.getInstance(logo));

        } catch (IOException ex) {
            Logger.getLogger(TurmaBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (BadElementException badEx) {
            Logger.getLogger(TurmaBean.class
                    .getName()).log(Level.SEVERE, null, badEx);
        } catch (DocumentException docEx) {
            Logger.getLogger(TurmaBean.class
                    .getName()).log(Level.SEVERE, null, docEx);
        }
    }

}
