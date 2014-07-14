package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.controle.datamodels.DisciplinaImportadaDataModel;
import br.ufg.inf.sigera.modelo.DisciplinaImportada;
import br.ufg.inf.sigera.modelo.ImportacaoArquivoDisciplina;
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
public class ImportacaoDisciplinaBean {

    private DisciplinaImportadaDataModel dataModel;
    private List<DisciplinaImportada> disciplinasImportadas;
    private final MensagensTela mensagemDeTela = new MensagensTela();

    public void importarArquivoDisciplina(FileUploadEvent event) {

        UploadedFile arquivo = event.getFile();
        ImportacaoArquivoDisciplina importaDisciplina;
        importaDisciplina = new ImportacaoArquivoDisciplina();
        dataModel = null;
        disciplinasImportadas = null;

        if (importaDisciplina.validaArquivo(arquivo)) {
            // Arquivo Disciplina Validado
            setDisciplinasImportados(importaDisciplina.importaArquivo(arquivo));
            getDataModel();
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.604", arquivo.getFileName()), Paginas.getImportacaoCurso());

        } else {
            // Arquivo Turma nao Validado
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.605"), Paginas.getImportacaoCurso());
        }
    }

    public DisciplinaImportadaDataModel getDataModel() {
        this.dataModel = new DisciplinaImportadaDataModel(getDisciplinasImportados());
        return this.dataModel;
    }

    public void setDisciplinasImportados(List<DisciplinaImportada> cursosImportados) {
        this.disciplinasImportadas = cursosImportados;
    }

    public List<DisciplinaImportada> getDisciplinasImportados() {
        return disciplinasImportadas;
    }
}