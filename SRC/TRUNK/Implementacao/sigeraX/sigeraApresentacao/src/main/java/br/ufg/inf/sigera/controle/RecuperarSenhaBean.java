package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import br.ufg.inf.sigera.modelo.Autenticacao;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.email.GerenciadorEmail;
import br.ufg.inf.sigera.modelo.ldap.AutenticacaoLdap;
import br.ufg.inf.sigera.modelo.ldap.UsuarioLdap;
import br.ufg.inf.sigera.modelo.servico.Conexoes;
import br.ufg.inf.sigera.modelo.servico.SenhaAleatoria;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class RecuperarSenhaBean {

    private String emailAlternativo;
    private String login;
    private String codigoVerificacao;
    private final MensagensTela mensagemDeTela = new MensagensTela();

    public String getEmailAlternativo() {
        return emailAlternativo;
    }

    public void setEmailAlternativo(String emailAlternativo) {
        this.emailAlternativo = emailAlternativo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCodigoVerificacao() {
        return codigoVerificacao;
    }

    public void setCodigoVerificacao(String codigoVerificacao) {
        this.codigoVerificacao = codigoVerificacao;
    }

    public String enviar() {
        //consultar e-mail alternativo existe no ldap e ?

        AutenticacaoLdap autenticador = new AutenticacaoLdap();

        Conexoes.lerParametros();
        UsuarioLdap userLdap = autenticador.efetuaLogin("administrator", Conexoes.getKEY_ADMIN());
        UsuarioLdap userEmail = userLdap.getBuscadorLdap().obtenhaUsuarioLdap(login);

        if (userEmail != null) {
            if (userEmail.getEmailAternativo().equalsIgnoreCase(emailAlternativo)) {
                gerarCodigoVerificacao(userLdap);

                mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.704"), Paginas.getLogin());

                GerenciadorEmail gerenciadorEmail = new GerenciadorEmail();
                gerenciadorEmail.adicionarEmailRecuperarSenha(userEmail, this.codigoVerificacao);
                gerenciadorEmail.enviarEmails();

                return Paginas.getLogin();

            } else {
                mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha("MT.705"), Paginas.getLogin());
                return Paginas.getRecuperarSenha();
            }
        }
        mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha("MT.706"), Paginas.getLogin());
        return Paginas.getRecuperarSenha();
    }

    public String limpar() {
        return Paginas.getRecuperarSenha();
    }

    private void gerarCodigoVerificacao(UsuarioLdap userLdap) {
        StringBuilder codigoGerado = new StringBuilder();
        Long longHoje = new Date().getTime();
        String strHoje = longHoje.toString();
        codigoGerado.append(strHoje).append("o");
        codigoGerado.append(userLdap.getBuscadorLdap().obtenhaUsuarioLdap(login).getUidNumber()).append("o");
        codigoGerado.append(SenhaAleatoria.GerarCodigoVerificacao());
        this.setCodigoVerificacao(codigoGerado.toString());
        //grava no banco
        Autenticacao autentificador = new Autenticacao();
        UsuarioSigera userBanco = autentificador.obtenhaUsuarioPorUidNumber(userLdap.getBuscadorLdap(), userLdap.getBuscadorLdap().obtenhaUsuarioLdap(login));
        if (userBanco != null) {
            userBanco.setChaveAtivacao(this.codigoVerificacao);
            userBanco.salvar();
        }        
    }
}
