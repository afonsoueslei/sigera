package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.modelo.Configuracao;
import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import br.ufg.inf.sigera.controle.servico.Sessoes;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class CalendarioBean {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private Date dataInicial;
    private Date dataFinal;
    private Date dataInicialSemestre;
    private Date dataFinalSemestre;
    private int anoCorrente;
    private int semestreCorrente;
    private boolean enviarEmail;
    private final MensagensTela mensagemDeTela = new MensagensTela();

    public int getAnoCorrente() {
        int ano = 0;
        if (getConfiguracao() != null) {
            ano = loginBean.getConfiguracao().getAnoCorrente();
        }
        return ano;
    }

    public void setAnoCorrente(int anoCorrente) {
        this.anoCorrente = anoCorrente;
    }

    public int getSemestreCorrente() {
        int semestre = 0;
        if (getConfiguracao() != null) {
            semestre = loginBean.getConfiguracao().getSemestreCorrente();
        }
        return semestre;
    }

    public void setSemestreCorrente(int semestreCorrente) {
        this.semestreCorrente = semestreCorrente;
    }

    public Date getDataInicialSemestre() {
        Date data = null;
        if (getConfiguracao() != null) {
            data = loginBean.getConfiguracao().getDataInicialSemestre();
        }

        return data;
    }

    public void setDataInicialSemestre(Date dataInicialSemestre) {
        this.dataInicialSemestre = dataInicialSemestre;
    }

    public Date getDataFinalSemestre() {
        Date data = null;
        if (getConfiguracao() != null) {
            data = loginBean.getConfiguracao().getDataFinalSemestre();
        }

        return data;
    }

    public void setDataFinalSemestre(Date dataFinalSemestre) {
        this.dataFinalSemestre = dataFinalSemestre;
    }

    public boolean isEnviarEmail() {
        boolean envioEmail = false;
        if (getConfiguracao() != null) {
            envioEmail = loginBean.getConfiguracao().isEnviarEmail();
        }
        return envioEmail;
    }

    public void setEnviarEmail(boolean enviarEmail) {
        this.enviarEmail = enviarEmail;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public Date getDataInicial() {
        Date data = null;
        if (getConfiguracao() != null) {
            data = loginBean.getConfiguracao().getDataInicial();
        }

        return data;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        Date data = null;
        if (getConfiguracao() != null) {
            data = loginBean.getConfiguracao().getDataFinal();
        }

        return data;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public CalendarioBean() {
    }

    public void salvar() {
        Sessoes.limparBeans();
        Configuracao configuracao = new Configuracao();
        configuracao.atualizar(dataInicial, dataFinal, dataInicialSemestre, dataFinalSemestre, anoCorrente, semestreCorrente, enviarEmail);
        loginBean.setConfiguracao(configuracao);
        loginBean.getConfiguracao();
        mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.212"), Paginas.getConfig());
    }

    public String cancelar() {
        return Paginas.getConfig();
    }

    private Configuracao getConfiguracao() {
        return this.loginBean.getConfiguracao();
    }
       
}
