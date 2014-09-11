package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.controle.datamodels.TurmaImportadaDataModel;
import br.ufg.inf.sigera.modelo.TurmaImportada;
import br.ufg.inf.sigera.modelo.ImportacaoArquivoTurma;
import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
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
public class ImportacaoTurmaBean {

    private TurmaImportadaDataModel dataModel;
    private List<TurmaImportada> turmasImportadas;
    private final MensagensTela mensagemDeTela = new MensagensTela();
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;

    public void importarArquivoTurma(FileUploadEvent event) {

        UploadedFile arquivo = event.getFile();
        ImportacaoArquivoTurma importaTurma;
        importaTurma = new ImportacaoArquivoTurma(getLoginBean().getUsuario().getUsuarioLdap().getBuscadorLdap());
        dataModel = null;
        turmasImportadas = null;

        if (importaTurma.validaArquivo(arquivo)) {
            // Arquivo Turma Validado
            setTurmasImportados(importaTurma.importaArquivo(arquivo));

            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.604", arquivo.getFileName()), Paginas.getImportacaoCurso());

        } else {
            // Arquivo Turma nao Validado
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.605"), Paginas.getImportacaoCurso());
        }
    }

    public TurmaImportadaDataModel getDataModel() {
        try {
            this.dataModel = new TurmaImportadaDataModel(getTurmasImportados());
        } catch (Exception ie) {
            Paginas.redirecionePaginaErro();
            Logger.getLogger(ImportacaoTurmaBean.class.getName()).log(Level.SEVERE, null, ie);
        }
        return this.dataModel;
    }

    public void setTurmasImportados(List<TurmaImportada> cursosImportados) {
        this.turmasImportadas = cursosImportados;
    }

    public List<TurmaImportada> getTurmasImportados() {
        return turmasImportadas;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
}
