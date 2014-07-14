package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.controle.datamodels.CursoImportadoDataModel;
import br.ufg.inf.sigera.modelo.CursoImportado;
import br.ufg.inf.sigera.modelo.ImportacaoArquivoCurso;
import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@ViewScoped
public class ImportacaoCursoBean {

    private CursoImportadoDataModel dataModel;
    private List<CursoImportado> cursosImportados;
    private final MensagensTela mensagemDeTela = new MensagensTela();

    public void importarArquivoCurso(FileUploadEvent event) {

        UploadedFile arquivo = event.getFile();
        ImportacaoArquivoCurso importaCurso;
        importaCurso = new ImportacaoArquivoCurso();
        dataModel = null;
        cursosImportados = null;

        if (importaCurso.validaArquivo(arquivo)) {
            // Arquivo Curso Validado
            setCursosImportados(importaCurso.importaArquivo(arquivo));
            getDataModel();

            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.604", arquivo.getFileName()), Paginas.getImportacaoCurso());

        } else {
            // Arquivo Turma nao Validado
            mensagemDeTela.criar(FacesMessage.SEVERITY_FATAL, Mensagens.obtenha("MT.605"), Paginas.getImportacaoCurso());            
        }
    }

    public CursoImportadoDataModel getDataModel() {
        this.dataModel = new CursoImportadoDataModel(getCursosImportados());
        return this.dataModel;
    }

    public void setCursosImportados(List<CursoImportado> cursosImportados) {
        this.cursosImportados = cursosImportados;
    }

    public List<CursoImportado> getCursosImportados() {
        return cursosImportados;
    }
}
