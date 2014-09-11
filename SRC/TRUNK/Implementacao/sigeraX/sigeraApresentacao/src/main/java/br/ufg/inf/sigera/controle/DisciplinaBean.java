package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.controle.datamodels.DisciplinaDataModel;
import br.ufg.inf.sigera.controle.tela.DisciplinaTela;
import br.ufg.inf.sigera.controle.adaptador.AdaptadorDisciplinaTela;
import br.ufg.inf.sigera.modelo.Curso;
import br.ufg.inf.sigera.modelo.Disciplina;
import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import br.ufg.inf.sigera.controle.servico.Sessoes;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean
@SessionScoped
public class DisciplinaBean implements Serializable {

    private DisciplinaDataModel dataModel;
    private List<DisciplinaTela> disciplinasTela;
    private List<DisciplinaTela> disciplinasFiltradas;
    private Disciplina disciplinaSelecionada;
    private Disciplina disciplinaEditavel;
    private Disciplina disciplinaExcluir;
    private final MensagensTela mensagemDeTela = new MensagensTela();

    public DisciplinaBean() {
    }

    public List<DisciplinaTela> getDisciplinasTela() {
        return disciplinasTela;
    }

    public void setDisciplinasTela(List<DisciplinaTela> disciplinasTela) {
        this.disciplinasTela = disciplinasTela;
    }

    public Disciplina getDisciplinaExcluir() {
        return disciplinaExcluir;
    }

    public void setDisciplinaExcluir(Disciplina disciplinaExcluir) {
        this.disciplinaExcluir = disciplinaExcluir;
    }

    public Integer getCodigoCurso() {
        if (getDisciplinaEditavel().getCurso() != null) {
            return this.disciplinaEditavel.getCurso().getId();
        }
        return null;
    }

    public void setCodigoCurso(Integer codigoCurso) {
        getDisciplinaEditavel().setCurso(Curso.obtenhaCurso(codigoCurso));
    }

    public List<Curso> getListaCursos() {
        return Curso.buscaTodosCursos();
    }

    public void setDisciplinaSelecionada(Disciplina disciplinaSelecionada) {
        this.disciplinaSelecionada = disciplinaSelecionada;
    }

    public Disciplina getDisciplinaEditavel() {
        if (disciplinaEditavel == null) {
            disciplinaEditavel = new Disciplina();
        }
        return disciplinaEditavel;
    }

    public void setDisciplinaEditavel(Disciplina disciplinaEditavel) {
        this.disciplinaEditavel = disciplinaEditavel;
    }

    public String salvar() {
        if (Disciplina.salvar(this.disciplinaEditavel)) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.506"), Paginas.getManterDisciplina());
        }else{
            mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha("MT.709", "DISCIPLINA"), Paginas.getManterDisciplina());
        }
        return voltarListaDisciplinas();
    }

    public void remover() {
        if (this.disciplinaExcluir != null) {
            Boolean removido = Disciplina.remover(this.disciplinaExcluir);
            if (removido) {
                mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.505"), Paginas.getManterDisciplina());
                Sessoes.limparBeans();
            } else {
                mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.500"), Paginas.getManterDisciplina());
                Sessoes.limparBeans();
            }
        }
    }

    public DisciplinaDataModel getDataModel() {
        if (dataModel == null) {
            try {
                List<Disciplina> disciplinasbuscados = Disciplina.buscaTodosDisciplinas();
                this.disciplinasTela = new ArrayList<DisciplinaTela>();
                for (Disciplina d : disciplinasbuscados) {
                    this.disciplinasTela.add(new AdaptadorDisciplinaTela(d));
                }
                this.dataModel = new DisciplinaDataModel(this.disciplinasTela);
            } catch (Exception ie) {
                Paginas.redirecionePaginaErro();
                Logger.getLogger(DisciplinaBean.class.getName()).log(Level.SEVERE, null, ie);
            }
        }
        return dataModel;
    }

    public List<DisciplinaTela> getDisciplinasFiltradas() {
        return disciplinasFiltradas;
    }

    public void setDisciplinasFiltradas(List<DisciplinaTela> disciplinasFiltrados) {
        this.disciplinasFiltradas = disciplinasFiltrados;
    }

    private void prepararDisciplinaEditavel() {
        this.disciplinaEditavel = new Disciplina(this.disciplinaSelecionada);
    }

    public String editar() {
        prepararDisciplinaEditavel();
        return Paginas.getEditarDisciplinas();
    }

    public void cancelar() {
        prepararDisciplinaEditavel();
    }

    public String voltarListaDisciplinas() {
        Sessoes.limparBeans();
        return Paginas.getManterDisciplina();
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
            Logger.getLogger(DisciplinaBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadElementException badEx) {
            Logger.getLogger(DisciplinaBean.class.getName()).log(Level.SEVERE, null, badEx);
        } catch (DocumentException docEx) {
            Logger.getLogger(DisciplinaBean.class.getName()).log(Level.SEVERE, null, docEx);
        }
    }
}
