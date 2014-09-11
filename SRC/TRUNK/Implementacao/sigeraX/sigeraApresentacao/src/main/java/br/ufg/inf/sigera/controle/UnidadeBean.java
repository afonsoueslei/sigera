package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.controle.adaptador.AdaptadorUnidadeTela;
import br.ufg.inf.sigera.controle.datamodels.UnidadeDataModel;
import br.ufg.inf.sigera.controle.tela.UnidadeTela;
import br.ufg.inf.sigera.modelo.Unidade;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean
@SessionScoped
public class UnidadeBean {

    private UnidadeDataModel dataModel;
    private List<UnidadeTela> unidadesTela;
    private List<UnidadeTela> unidadesFiltradas;
    private Unidade unidadeSelecionada;
    private Unidade unidadeEditavel;
    private Unidade unidadeExcluir;
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private final MensagensTela mensagemDeTela = new MensagensTela();

    public UnidadeBean() {
    }

    public List<UnidadeTela> getUnidadesTela() {
        return unidadesTela;
    }

    public void setUnidadesTela(List<UnidadeTela> unidadesTela) {
        this.unidadesTela = unidadesTela;
    }

    public Unidade getUnidadeExcluir() {
        return unidadeExcluir;
    }

    public void setUnidadeExcluir(Unidade unidadeExcluir) {
        this.unidadeExcluir = unidadeExcluir;
    }

    public Unidade getUnidadeSelecionada() {
        return unidadeSelecionada;
    }

    public void setUnidadeSelecionada(Unidade unidadeSelecionada) {
        this.unidadeSelecionada = unidadeSelecionada;
    }

    public Unidade getUnidadeEditavel() {
        if (unidadeEditavel == null) {
            unidadeEditavel = new Unidade();
        }
        return unidadeEditavel;
    }

    public void setUnidadeEditavel(Unidade unidadeEditavel) {
        this.unidadeEditavel = unidadeEditavel;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String salvar() {
        if(Unidade.salvar(this.unidadeEditavel)){                    
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.515"), Paginas.getManterUnidade());
        }else{
            mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha("MT.709", "UNIDADE")  , Paginas.getManterUnidade());
        }
        return voltarListaUnidades();
    }

    public void remover() {
        if (this.unidadeExcluir != null) {
            Boolean removido = Unidade.remover(this.unidadeExcluir);
            if (removido) {
                mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.514"), Paginas.getManterUnidade());
                Sessoes.limparBeans();
            } else {
                mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.500"), Paginas.getManterUnidade());
                Sessoes.limparBeans();
            }
        }
    }

    public UnidadeDataModel getDataModel() {
        if (dataModel == null) {
            try {
                BuscadorLdap buscadorLdap = loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap();
                List<Unidade> unidadesBuscadas = Unidade.buscaTodasUnidades(buscadorLdap);
                this.unidadesTela = new ArrayList<UnidadeTela>();
                for (Unidade u : unidadesBuscadas) {
                    this.unidadesTela.add(new AdaptadorUnidadeTela(u));
                }
                this.dataModel = new UnidadeDataModel(this.unidadesTela);
            } catch (Exception ie) {
                Paginas.redirecionePaginaErro();
                 Logger.getLogger(UnidadeBean.class.getName()).log(Level.SEVERE, null, ie);
            }
        }
        return dataModel;
    }

    public List<UnidadeTela> getUnidadesFiltradas() {
        return unidadesFiltradas;
    }

    public void setUnidadesFiltradas(List<UnidadeTela> unidadesFiltradas) {
        this.unidadesFiltradas = unidadesFiltradas;
    }

    private void prepararUnidadeEditavel() {
        this.unidadeEditavel = new Unidade(this.unidadeSelecionada);
    }

    public String editar() {
        prepararUnidadeEditavel();
        return Paginas.getEditarUnidade();
    }

    public void cancelar() {
        prepararUnidadeEditavel();
    }

    public String voltarListaUnidades() {
        Sessoes.limparBeans();
        return Paginas.getManterUnidade();
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
            Logger.getLogger(UnidadeBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadElementException badEx) {
            Logger.getLogger(UnidadeBean.class.getName()).log(Level.SEVERE, null, badEx);
        } catch (DocumentException docEx) {
            Logger.getLogger(UnidadeBean.class.getName()).log(Level.SEVERE, null, docEx);
        }
    }
}
