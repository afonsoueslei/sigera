package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.controle.datamodels.CursoDataModel;
import br.ufg.inf.sigera.controle.tela.CursoTelaManter;
import br.ufg.inf.sigera.controle.adaptador.AdaptadorCursoTelaManter;
import br.ufg.inf.sigera.modelo.Curso;
import br.ufg.inf.sigera.modelo.Unidade;
import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import br.ufg.inf.sigera.controle.servico.Sessoes;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import org.primefaces.event.RowEditEvent;

@ManagedBean
@SessionScoped
public class CursoBean {

    protected static final String MANTER_CURSO = "manter_curso";

    private CursoDataModel dataModel;
    private List<CursoTelaManter> cursosTela;
    private List<CursoTelaManter> cursosFiltrados;
    private Curso curso;
    private List<Curso> cursos;
    private Curso cursoSelecionado;
    private Curso cursoEditavel;
    private final MensagensTela mensagemDeTela = new MensagensTela();

    public CursoBean() {
    }

    public Curso getCursoEditavel() {
        return cursoEditavel;
    }

    public void setCursoEditavel(Curso cursoEditavel) {
        this.cursoEditavel = cursoEditavel;
    }

    public Curso getCursoSelecionado() {
        return cursoSelecionado;
    }

    public void setCursoSelecionado(Curso cursoSelecionado) {
        this.cursoSelecionado = cursoSelecionado;
    }

    public List<CursoTelaManter> getCursosTela() {
        return cursosTela;
    }

    public void setCursosTela(List<CursoTelaManter> cursosTela) {
        this.cursosTela = cursosTela;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public Integer getCodigoUnidade() {
        if (getCursoEditavel().getUnidade() != null) {
            return this.cursoEditavel.getUnidade().getId();
        }
        return null;
    }

    public void setCodigoUnidade(Integer codigoUnidade) {
        getCursoEditavel().setUnidade(Unidade.obtenhaUnidade(codigoUnidade));
    }

    public List<Unidade> getListaUnidades() {
        return Unidade.buscaTodasUnidades();
    }

    public String salvar() {
        Curso.salvar(this.cursoEditavel);
        mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.501"), Paginas.getManterCurso());        
        return voltarListaCursos();
    }

    private void prepararCursoEditavel() {
        this.cursoEditavel = new Curso(this.cursoSelecionado);
    }

    public String editar() {
        prepararCursoEditavel();
        return Paginas.getEditarCurso();
    }

    private void editar(Curso c) {
        Curso.salvar(c);
    }

    public void remover() {
        if (this.curso != null) {
            Boolean removido = Curso.remover(this.curso);
            if (removido) {
                mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.502"), Paginas.getManterCurso());
                Sessoes.limparBeans();
            } else {
                mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.500"), Paginas.getManterCurso());
                Sessoes.limparBeans();
            }
        }
    }

    public CursoDataModel getDataModel() {
        if (dataModel == null) {
            List<Curso> cursosbuscados = Curso.buscaTodosCursos();
            this.cursosTela = new ArrayList<CursoTelaManter>();
            for (Curso c : cursosbuscados) {
                this.cursosTela.add(new AdaptadorCursoTelaManter(c));
            }
            this.dataModel = new CursoDataModel(this.cursosTela);
        }
        return dataModel;
    }

    public List<CursoTelaManter> getCursosFiltrados() {
        return cursosFiltrados;
    }

    public void setCursosFiltrados(List<CursoTelaManter> cursosFiltrados) {
        this.cursosFiltrados = cursosFiltrados;
    }

    public void onEdit(RowEditEvent event) {
        editar(((CursoTelaManter) event.getObject()).getCurso());
    }

    public void preparaInsercao(ActionEvent ev) {
        setCurso(new Curso());
    }

    public void cancelar() {
        prepararCursoEditavel();
    }
    
     public String voltarListaCursos() {
        Sessoes.limparBeans();
        return Paginas.getManterCurso();
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
            Logger.getLogger(CursoBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadElementException badEx) {
            Logger.getLogger(CursoBean.class.getName()).log(Level.SEVERE, null, badEx);
        } catch (DocumentException docEx) {
            Logger.getLogger(CursoBean.class.getName()).log(Level.SEVERE, null, docEx);
        }
    }
}
