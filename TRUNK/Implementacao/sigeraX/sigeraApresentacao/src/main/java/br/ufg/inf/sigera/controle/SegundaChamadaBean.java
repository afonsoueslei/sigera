package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.controle.adaptador.AdaptadorTurmaTela;
import br.ufg.inf.sigera.controle.datamodels.TurmaDataModel;
import br.ufg.inf.sigera.controle.tela.TurmaTela;
import br.ufg.inf.sigera.modelo.Turma;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.email.GerenciadorEmail;
import br.ufg.inf.sigera.modelo.requerimento.Anexo;
import br.ufg.inf.sigera.modelo.requerimento.EnumTipoRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoSegundaChamada;
import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class SegundaChamadaBean implements Serializable {

    private TurmaDataModel dataModelTurmas;
    private List<TurmaTela> turmasTela;
    private List<TurmaTela> turmasFiltradas;
    private TurmaTela turmaSelecionada;
    private Date dataProva;
    private String justificativa;
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private List<Anexo> anexos;
    private Anexo anexoExcluir;
    private final MensagensTela mensagemDeTela = new MensagensTela();

    public SegundaChamadaBean() {
    }

    public Anexo getAnexoExcluir() {
        return anexoExcluir;
    }

    public void setAnexoExcluir(Anexo anexoExcluir) {
        this.anexoExcluir = anexoExcluir;
    }

    public List<Anexo> getAnexos() {
        if (anexos == null) {
            anexos = new ArrayList<Anexo>();
        }
        return anexos;
    }

    public void setAnexos(List<Anexo> anexos) {
        this.anexos = anexos;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public Date getDataProva() {
        return dataProva;
    }

    public void setDataProva(Date dataProva) {
        this.dataProva = dataProva;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public TurmaTela getTurmaSelecionada() {
        return turmaSelecionada;
    }

    public void setTurmaSelecionada(TurmaTela turmaSelecionada) {
        this.turmaSelecionada = turmaSelecionada;
    }

    public List<TurmaTela> getTurmasTela() {
        return turmasTela;
    }

    public void setTurmasTela(List<TurmaTela> turmasTela) {
        this.turmasTela = turmasTela;
    }

    public String salvar() {
        if (!validarRequerimento()) {
            return Paginas.getSegundaChamadaFinalizacao();
        }

        Turma turma = this.getTurmaSelecionada().getTurma();

        Requerimento requerimento =
                new RequerimentoSegundaChamada(loginBean.getUsuario(),
                turma,
                this.getDataProva(),
                this.getJustificativa(),
                this.getAnexos());
        requerimento.salvar();
        limpar();

        List<UsuarioSigera> destinatarios = new ArrayList<UsuarioSigera>();
        destinatarios.add(turma.getProfessor().getUsuario());

        if (loginBean.getConfiguracao().isEnviarEmail()) {
            GerenciadorEmail gerenciadorEmail = new GerenciadorEmail();
            gerenciadorEmail.adicionarEmailRequerimento(requerimento, destinatarios);
            gerenciadorEmail.enviarEmails();
        }

        mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.002", EnumTipoRequerimento.SEGUNDA_CHAMADA.getNome()), Paginas.getDetalheRequerimento());

        return Paginas.getAbrirRequerimentoID() + requerimento.getId();
    }

    public String voltar() {
        return Paginas.getPrincipal();
    }

    public String retornar() {
        return Paginas.getSegundaChamadaSelecaoTurma();
    }

    public TurmaDataModel getDataModelTurmas() {
        if (dataModelTurmas == null) {
            List<Turma> turmasbuscadas =
                    Turma.buscaTurmas(loginBean.getConfiguracao().getAnoCorrente(),
                    loginBean.getConfiguracao().getSemestreCorrente(),
                    loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap(),
                    loginBean.getUsuario().getPerfilAtual().getCurso().getId());
            Collections.sort(turmasbuscadas);
            this.turmasTela = new ArrayList<TurmaTela>();
            for (Turma d : turmasbuscadas) {
                this.turmasTela.add(new AdaptadorTurmaTela(d));
            }
            this.dataModelTurmas = new TurmaDataModel(this.turmasTela);
        }
        return dataModelTurmas;
    }

    public List<TurmaTela> getTurmasFiltradas() {
        return turmasFiltradas;
    }

    public void setTurmasFiltradas(List<TurmaTela> turmasFiltrados) {
        this.turmasFiltradas = turmasFiltrados;
    }

    private boolean validarRequerimento() {
        if (this.turmaSelecionada == null) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.403"), Paginas.getSegundaChamadaFinalizacao());
            return false;
        }
        return true;
    }

    public String confirmar() {
        if (this.turmaSelecionada == null) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.404"), Paginas.getSegundaChamadaSelecaoTurma());
            return Paginas.getSegundaChamadaSelecaoTurma();
        }
        return Paginas.getSegundaChamadaFinalizacao();
    }

    public String prosseguir() {
        return Paginas.getSegundaChamadaSelecaoTurma();
    }

    public void remover() {
        this.anexoExcluir.removerArquivo();
        this.getAnexos().remove(this.anexoExcluir);
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            String nomeArquivo = event.getFile().getFileName();
            InputStream is = event.getFile().getInputstream();

            String tamanho = Long.toString(event.getFile().getSize() / 1024);

            Anexo anexo = new Anexo(nomeArquivo, tamanho, is);
            getAnexos().add(anexo);

        } catch (IOException ex) {
            Logger.getLogger(SegundaChamadaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void limpar() {
        this.turmaSelecionada = null;
        this.dataProva = null;
        this.justificativa = null;
        this.anexos = null;
        this.anexoExcluir = null;
    }

    public StreamedContent download(Anexo anexo) {
        try {
            InputStream stream = new FileInputStream(anexo.getCaminho());
            return new DefaultStreamedContent(stream, "application/octet-stream", anexo.getNome());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SegundaChamadaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}