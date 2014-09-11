package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.controle.datamodels.ProfessorImportadoDataModel;
import br.ufg.inf.sigera.modelo.ProfessorImportado;
import br.ufg.inf.sigera.modelo.ImportacaoArquivoProfessor;
import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import br.ufg.inf.sigera.controle.servico.Sessoes;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@ViewScoped
public class ImportacaoProfessorBean {

    private ProfessorImportadoDataModel dataModel;
    private List<ProfessorImportado> professoresImportados;
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    @ManagedProperty(value = "#{atribuirPerfilBean}")
    private AtribuirPerfilBean atribuirPerfilBean;
    private final MensagensTela mensagemDeTela = new MensagensTela();

    public void importarArquivoProfessor(FileUploadEvent event) {

        UploadedFile arquivo = event.getFile();
        ImportacaoArquivoProfessor importaProfessor = new ImportacaoArquivoProfessor(this.getLoginBean().getUsuario().getUsuarioLdap().getBuscadorLdap());

        dataModel = null;
        professoresImportados = null;

        if (importaProfessor.validaArquivo(arquivo)) {
            // Arquivo Professor Validado
            setProfessorsImportados(importaProfessor.importaArquivo(arquivo));
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.604", arquivo.getFileName()), Paginas.getImportacaoCurso());
            Sessoes.limparBeans();
        } else {
            // Arquivo Turma nao Validado
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.605"), Paginas.getImportacaoCurso());
        }
    }

    public ProfessorImportadoDataModel getDataModel() {
        try {
            this.dataModel = new ProfessorImportadoDataModel(getProfessorsImportados());
        } catch (Exception ie) {
            Paginas.redirecionePaginaErro();
            Logger.getLogger(ImportacaoProfessorBean.class.getName()).log(Level.SEVERE, null, ie);
        }
        return this.dataModel;
    }

    public void setProfessorsImportados(List<ProfessorImportado> professorsImportados) {
        this.professoresImportados = professorsImportados;
    }

    public List<ProfessorImportado> getProfessorsImportados() {
        return professoresImportados;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public AtribuirPerfilBean getAtribuirPerfilBean() {
        return atribuirPerfilBean;
    }

    public void setAtribuirPerfilBean(AtribuirPerfilBean atribuirPerfilBean) {
        this.atribuirPerfilBean = atribuirPerfilBean;
    }
}
