package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.controle.servico.AcessoLdap;
import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import br.ufg.inf.sigera.modelo.Autenticacao;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.ldap.AutenticacaoLdap;
import br.ufg.inf.sigera.modelo.ldap.UsuarioLdap;
import br.ufg.inf.sigera.modelo.servico.Conexoes;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class TrocarSenhaBean {

    private String login;
    private final MensagensTela mensagemDeTela = new MensagensTela();
    private final static long LIMITE = 86400000;
    private String stringErro;    
    private String novaSenha;
    private String novaSenhaConfirmada;
    private String userUidNumber;
    private UsuarioSigera userBanco;
    private String contextoImagem = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();

    public TrocarSenhaBean() {
    }

    @PostConstruct
    public void init() {
        if (!validaCodigoVerificacao()) {
            redirecioneParaPaginaLogin();
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getStringErro() {
        return stringErro;
    }

    public void setStringErro(String stringErro) {
        this.stringErro = stringErro;
    }
    
    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getNovaSenhaConfirmada() {
        return novaSenhaConfirmada;
    }

    public void setNovaSenhaConfirmada(String novaSenhaConfirmada) {
        this.novaSenhaConfirmada = novaSenhaConfirmada;
    }

    public String enviar() {
        //Nova senha e confirmar nova senha são diferentes
        if (!this.getNovaSenha().equals(this.getNovaSenhaConfirmada())) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha("MT.701"), "trocarSenha.xhtml");
            return "";
        }

        if (this.getNovaSenha().length() < 8) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.702"), "trocarSenha.xhtml");
            return "";
        }
        AutenticacaoLdap autenticador = new AutenticacaoLdap();
        UsuarioLdap userLdap = autenticador.efetuaLogin("administrator", Conexoes.getKEY_ADMIN()).getBuscadorLdap().obtenhaUsuarioLdap(Integer.parseInt(userUidNumber));
        //gravo no senha no ldap
        AcessoLdap.alterarCampoLdap(userLdap.getUid(), "userPassword", this.getNovaSenha());
        //imprimo mensagem de senha alterada com sucesso     
        mensagemDeTela.criar(FacesMessage.SEVERITY_WARN, Mensagens.obtenha("MT.703"), "trocarSenha.xhtml");

        //apago chave no banco
        this.userBanco.setChaveAtivacao(null);
        this.userBanco.salvar();

        return Paginas.getTrocarSenhaFinal();
    }

    public String cancelar() {
        return Paginas.getLogin();
    }

    private Boolean validaCodigoVerificacao() {

        String[] codigoVerificacaoExtraido = null;
        Long longDataRecuperada = null;
        Long longHoje = new Date().getTime();

        Conexoes.lerParametros();

        try {
            String param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("token");

            if (param != null) {
                //extrair codigoVerificação
                codigoVerificacaoExtraido = param.split("o");
                longDataRecuperada = Long.parseLong(codigoVerificacaoExtraido[0]);

                userUidNumber = codigoVerificacaoExtraido[1];
                AutenticacaoLdap autenticador = new AutenticacaoLdap();
                UsuarioLdap userLdap = autenticador.efetuaLogin("administrator", Conexoes.getKEY_ADMIN()).getBuscadorLdap().obtenhaUsuarioLdap(Integer.parseInt(userUidNumber));

                //Se usuário não é valido, não existe no ldap
                if (userLdap == null) {
                    this.setStringErro("<img src=\"" + contextoImagem + "/resources/img/importacaoErro.png\" alt=\"Aviso:\" style=\"width: 20px; height: 20px; \" /> "
                            + "Este token não é válido! <br> <a href=\"recuperarSenha.xhtml\"> "
                            + "&nbsp; &nbsp; &nbsp; Clique refazer processo </a>");
                    return true;
                }

                this.setLogin(userLdap.getUid());
                this.getLogin();

                //se codigo expirou (data VENCIDA)
                if ((longHoje - longDataRecuperada) > LIMITE) {
                    this.setStringErro("<img src=\"" + contextoImagem + "/resources/img/importacaoErro.png\" alt=\"Aviso:\" style=\"width: 20px; height: 20px; \" /> "
                            + "A válidade do token expirou você deve refazer o processo! <br> <a href=\"recuperarSenha.xhtml\"> "
                            + "&nbsp; &nbsp; &nbsp; Clique refazer processo </a> ");
                    return true;
                }

                //Token diferente do que esta no banco
                this.userBanco = Autenticacao.obtenhaUsuarioPorUidNumber(userLdap.getBuscadorLdap(), userLdap);
                if (!param.equalsIgnoreCase(userBanco.getChaveAtivacao())) {
                    this.setStringErro("<img src=\"" + contextoImagem + "/resources/img/importacaoErro.png\" alt=\"Aviso:\" style=\"width: 20px; height: 20px; \" /> "
                            + "Este token não é válido! <br> <a href=\"recuperarSenha.xhtml\"> "
                            + "&nbsp; &nbsp; &nbsp; Clique refazer processo</a > ");
                    return true;
                }
            } else {
                return false;
            }

        } catch (NumberFormatException ex) {
            Logger.getLogger(UsuarioSigera.class.getName()).log(Level.WARNING, "Paramêtro não pode ser recuperado", ex);
        }

        return true;
    }

    private void redirecioneParaPaginaLogin() {
        try {
            ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
            contexto.redirect("login.xhtml");
            //contexto.dispatch(Paginas.getConsultar());

        } catch (IOException ex) {
            Logger.getLogger(DetalheRequerimentoBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (Exception iex) {
            Logger.getLogger(DetalheRequerimentoBean.class
                    .getName()).log(Level.SEVERE, null, iex);
        }
    }
}
