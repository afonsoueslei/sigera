package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.controle.datamodels.RequerimentoTelaConsultaDataModel;
import br.ufg.inf.sigera.controle.tela.RequerimentoTelaConsulta;
import br.ufg.inf.sigera.controle.adaptador.AdaptadorRequerimentoTelaConsulta;
import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import br.ufg.inf.sigera.modelo.requerimento.EnumStatusRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.EnumTipoRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
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
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

@ManagedBean
@SessionScoped
public class ConsultarRequerimentoBean {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private RequerimentoTelaConsultaDataModel dataModel;
    private List<RequerimentoTelaConsulta> requerimentosTela;
    private List<RequerimentoTelaConsulta> requerimentosFiltrados;
    private static final String[] TIPOS_REQUERIMENTO;
    private SelectItem[] opcoesTipoRequerimento;
    private static final String[] TIPOS_STATUS;
    private SelectItem[] opcoesTipoStatus;

    static {
        TIPOS_REQUERIMENTO = new String[8];
        TIPOS_REQUERIMENTO[0] = EnumTipoRequerimento.ACRESCIMO_DISCIPLINAS.getNome();
        TIPOS_REQUERIMENTO[1] = EnumTipoRequerimento.CANCELAMENTO_DISCIPLINAS.getNome();
        TIPOS_REQUERIMENTO[2] = EnumTipoRequerimento.SEGUNDA_CHAMADA.getNome();
        TIPOS_REQUERIMENTO[3] = EnumTipoRequerimento.EMENTAS.getNome();
        TIPOS_REQUERIMENTO[4] = EnumTipoRequerimento.ASSINATURA.getNome();
        TIPOS_REQUERIMENTO[5] = EnumTipoRequerimento.EXTRATO_ACADEMICO.getNome();
        TIPOS_REQUERIMENTO[6] = EnumTipoRequerimento.DECLARACAO_MATRICULA.getNome();
        TIPOS_REQUERIMENTO[7] = EnumTipoRequerimento.PLANO.getNome();

        TIPOS_STATUS = new String[6];
        TIPOS_STATUS[0] = EnumStatusRequerimento.ABERTO.getNome();
        TIPOS_STATUS[1] = EnumStatusRequerimento.DEFERIDO.getNome();
        TIPOS_STATUS[2] = EnumStatusRequerimento.INDEFERIDO.getNome();
        TIPOS_STATUS[3] = EnumStatusRequerimento.CANCELADO.getNome();
        TIPOS_STATUS[4] = EnumStatusRequerimento.CONFERIDO.getNome();
        TIPOS_STATUS[5] = EnumStatusRequerimento.CONCLUIDO.getNome();
    }

    public List<RequerimentoTelaConsulta> getRequerimentosTela() {
        return requerimentosTela;
    }

    public void setRequerimentosTela(List<RequerimentoTelaConsulta> requerimentosTela) {
        this.requerimentosTela = requerimentosTela;
    }

    public List<RequerimentoTelaConsulta> getRequerimentosFiltrados() {
        return requerimentosFiltrados;
    }

    public void setRequerimentosFiltrados(List<RequerimentoTelaConsulta> requerimentosFiltrados) {
        this.requerimentosFiltrados = requerimentosFiltrados;
    }

    public ConsultarRequerimentoBean() {
        this.opcoesTipoRequerimento = crieOpcoesFiltro(TIPOS_REQUERIMENTO);
        this.opcoesTipoStatus = crieOpcoesFiltro(TIPOS_STATUS);
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public RequerimentoTelaConsultaDataModel getDataModel() {
        if (dataModel == null) {
            try {
                List<Requerimento> requerimentos = this.loginBean.getUsuario().obtenhaRequerimentos();
                this.requerimentosTela = new ArrayList<RequerimentoTelaConsulta>();
                for (Requerimento requerimento : requerimentos) {
                    this.requerimentosTela.add(new AdaptadorRequerimentoTelaConsulta(requerimento));
                }                
                this.dataModel = new RequerimentoTelaConsultaDataModel(this.requerimentosTela);
            } catch (Exception ie) {
                Paginas.redirecionePaginaErro();
            }
        }
        return dataModel;
    }

    private SelectItem[] crieOpcoesFiltro(String[] data) {
        SelectItem[] options = new SelectItem[data.length + 1];

        options[0] = new SelectItem("", "Todos");
        for (int i = 0; i < data.length; i++) {
            options[i + 1] = new SelectItem(data[i], data[i]);

        }

        return options;
    }

    public SelectItem[] getListaTipos() {
        return this.opcoesTipoRequerimento;
    }

    public SelectItem[] getListaStatus() {
        return this.opcoesTipoStatus;
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
            Logger.getLogger(ConsultarRequerimentoBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadElementException badEx) {
            Logger.getLogger(ConsultarRequerimentoBean.class.getName()).log(Level.SEVERE, null, badEx);
        } catch (DocumentException docEx) {
            Logger.getLogger(ConsultarRequerimentoBean.class.getName()).log(Level.SEVERE, null, docEx);
        }
    }

    public String visualizarDetalhes() {
        return "detalhe_requerimento";
    }
    
    public String atualizarLista(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("consultarRequerimentoBean");        
        return Paginas.getConsultarRequerimentos();
    }
    

}
