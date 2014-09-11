package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.modelo.AssociacaoPerfilCurso;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.email.GerenciadorEmail;
import br.ufg.inf.sigera.modelo.perfil.EnumPerfil;
import br.ufg.inf.sigera.modelo.requerimento.EnumTipoRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoExtratoAcademico;
import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class ExtratoAcademicoBean {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private String justificativa;
    private final MensagensTela mensagemDeTela = new MensagensTela();

    public ExtratoAcademicoBean() {
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

    public String salvar() {
        Requerimento requerimento = new RequerimentoExtratoAcademico(loginBean.getUsuario(), this.getJustificativa());
        if (requerimento.salvar()) {

            List<UsuarioSigera> destinatarios
                    = AssociacaoPerfilCurso.obtenhaUsuariosDoPerfilCurso(EnumPerfil.SECRETARIA.getCodigo(),
                            requerimento.getCurso().getId(),
                            loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap());

            if (loginBean.getConfiguracao().isEnviarEmail()) {
                GerenciadorEmail gerenciadorEmail = new GerenciadorEmail();
                gerenciadorEmail.adicionarEmailRequerimento(requerimento, destinatarios);
                gerenciadorEmail.enviarEmails();
            }
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.002", EnumTipoRequerimento.EXTRATO_ACADEMICO.getNome()), Paginas.getDetalheRequerimento());
            return Paginas.getAbrirRequerimentoID() + requerimento.getId();
        }
        mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha("MT.002.Falha", EnumTipoRequerimento.obtenha(requerimento.getTipo()).getNome().toUpperCase()), Paginas.getPrincipal());
        return Paginas.getPrincipal();
    }

    public String voltar() {
        return Paginas.getPrincipal();
    }

    public String prosseguir() {
        return Paginas.getExtratoAcademicoFinalizacao();
    }
}
