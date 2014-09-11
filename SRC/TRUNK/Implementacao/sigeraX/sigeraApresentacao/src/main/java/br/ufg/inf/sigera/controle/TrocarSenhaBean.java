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
    private static final long LIMITE = 86400000;
    private String stringErro;
    private String novaSenha;
    private String novaSenhaConfirmada;
    private String userUidNumber;
    private UsuarioSigera userBanco;
    private final String contextoImagem;

    public TrocarSenhaBean() {
        this.contextoImagem = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
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
            mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha("MT.701"), Paginas.getTrocarSenha());
            return "";
        }

        if (this.getNovaSenha().length() < 8) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.702"), Paginas.getTrocarSenha());
            return "";
        }
        AutenticacaoLdap autenticador = new AutenticacaoLdap();
        UsuarioLdap userLdap = autenticador.efetuaLogin(Conexoes.getUSER_ADMIN(), Conexoes.getKEY_ADMIN()).getBuscadorLdap().obtenhaUsuarioLdap(Integer.parseInt(userUidNumber));
        //gravo no senha no ldap
        AcessoLdap.alterarCampoLdap(userLdap.getUid(), "userPassword", this.getNovaSenha());
        //imprimo mensagem de senha alterada com sucesso     
        mensagemDeTela.criar(FacesMessage.SEVERITY_WARN, Mensagens.obtenha("MT.703"), Paginas.getTrocarSenha());

        //apago chave no banco
        this.userBanco.setChaveAtivacao(null);
        this.userBanco.salvar();

        return Paginas.getTrocarSenhaFinal();
    }

    public String cancelar() {
        return Paginas.getLogin();
    }

    private Boolean validaCodigoVerificacao() {

        Conexoes.lerParametros();

        try {
            String param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("token");

            if (param == null) {
                return false;
            }

            //extrair codigoVerificação
            String[] codigoVerificacaoExtraido = param.split("o");
            Long longDataRecuperada = Long.parseLong(codigoVerificacaoExtraido[0]);
            Long longHoje = new Date().getTime();

            userUidNumber = codigoVerificacaoExtraido[1];
            AutenticacaoLdap autenticador = new AutenticacaoLdap();
            UsuarioLdap userLdap = autenticador.efetuaLogin(Conexoes.getUSER_ADMIN(), Conexoes.getKEY_ADMIN()).getBuscadorLdap().obtenhaUsuarioLdap(Integer.parseInt(userUidNumber));

            //Se usuário não é valido, não existe no ldap
            if (userLdap == null) {
                this.setStringErro(obtenhaMensagemErro("Este token não é válido!"));
                return true;
            }

            //se codigo expirou (data VENCIDA)
            if ((longHoje - longDataRecuperada) > LIMITE) {
                this.setStringErro(obtenhaMensagemErro("A validade do token expirou! "));
                return true;
            }

            //Token diferente do que esta no banco
            this.userBanco = Autenticacao.obtenhaUsuarioPorUidNumber(userLdap.getBuscadorLdap(), userLdap);
            if (!param.equalsIgnoreCase(userBanco.getChaveAtivacao())) {
                this.setStringErro(obtenhaMensagemErro("Token é inválido!"));
                return true;
            }

            this.setLogin(userLdap.getUid());
            this.getLogin();

        } catch (NumberFormatException ex) {
            Logger.getLogger(UsuarioSigera.class.getName()).log(Level.WARNING, "Paramêtro não pode ser recuperado", ex);
        }

        return true;
    }

    private void redirecioneParaPaginaLogin() {
        try {
            ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
            contexto.redirect("login.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(DetalheRequerimentoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String obtenhaMensagemErro(String textoPersonalizado) {
        return "<img src=\"" + contextoImagem + "/resources/img/importacaoErro.png\" alt=\"Aviso:\" /> "
                + textoPersonalizado + " <br> <a href=\"recuperarSenha.xhtml\"> "
                + "&nbsp; &nbsp; &nbsp; Clique refazer processo </a>";
    }

}
