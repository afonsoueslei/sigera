package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.email.GerenciadorEmail;
import br.ufg.inf.sigera.modelo.requerimento.Anexo;
import br.ufg.inf.sigera.modelo.requerimento.EnumTipoRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import br.ufg.inf.sigera.modelo.perfil.PerfilAlunoPosStrictoSensu;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoProrrogacaoDefesa;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@ViewScoped
public class ProrrogacaoDefesaBean implements Serializable {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private String justificativa;
    private Integer prazoEmMeses;
    private List<Anexo> anexos;
    private Anexo anexoExcluir;
    private final MensagensTela mensagemDeTela = new MensagensTela();

    public ProrrogacaoDefesaBean() {
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public Integer getPrazoEmMeses() {
        return prazoEmMeses;
    }

    public void setPrazoEmMeses(Integer prazoEmMeses) {
        this.prazoEmMeses = prazoEmMeses;
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

    public String salvar() {
        BuscadorLdap buscadorLdap = loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap();
        UsuarioSigera orientador = new PerfilAlunoPosStrictoSensu().obtenhaOrientador(loginBean.getUsuario(), buscadorLdap);
      
        Requerimento requerimento
                = new RequerimentoProrrogacaoDefesa(loginBean.getUsuario(),
                        this.getJustificativa(),
                        this.getPrazoEmMeses());

        requerimento.setAnexos(this.getAnexos());

        if (requerimento.salvar()) {
            limpar();
            if (loginBean.getConfiguracao().isEnviarEmail()) {
                GerenciadorEmail gerenciadorEmail = new GerenciadorEmail();
                gerenciadorEmail.adicionarEmailRequerimento(requerimento, orientador);
                gerenciadorEmail.enviarEmails();
            }

            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.002", EnumTipoRequerimento.PRORROGACAO_DEFESA.getNome()), Paginas.getDetalheRequerimento());

            return Paginas.getAbrirRequerimentoID() + requerimento.getId();
        }
        mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha("MT.002.Falha", EnumTipoRequerimento.obtenha(requerimento.getTipo()).getNome().toUpperCase()), Paginas.getPrincipal());
        return Paginas.getPrincipal();
    }

    public String voltar() {
        return Paginas.getPrincipal();
    }

    public String retornar() {
        return Paginas.getSegundaChamadaSelecaoTurma();
    }

    public String confirmar() {
        return "";
    }

    public String prosseguir() {
        return "";
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
            Logger.getLogger(ProrrogacaoDefesaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void limpar() {
        this.justificativa = null;
        this.anexos = null;
        this.anexoExcluir = null;
    }

    public StreamedContent download(Anexo anexo) {
        try {
            InputStream stream = new FileInputStream(anexo.getCaminho());
            return new DefaultStreamedContent(stream, "application/octet-stream", anexo.getNome());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProrrogacaoDefesaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
