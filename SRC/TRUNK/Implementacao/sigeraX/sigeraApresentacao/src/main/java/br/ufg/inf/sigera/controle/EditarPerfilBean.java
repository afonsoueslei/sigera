package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import br.ufg.inf.sigera.modelo.AssociacaoPerfilCurso;
import br.ufg.inf.sigera.modelo.Curso;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.email.GerenciadorEmail;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import br.ufg.inf.sigera.modelo.ldap.EnumGrupo;
import br.ufg.inf.sigera.modelo.ldap.UsuarioLdap;
import br.ufg.inf.sigera.modelo.perfil.Perfil;
import br.ufg.inf.sigera.modelo.perfil.PerfilAluno;
import br.ufg.inf.sigera.modelo.servico.SenhaAleatoria;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class EditarPerfilBean implements Serializable {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private String telefoneCelular;
    private String telefoneResidencial;
    private String telefoneComercial;
    private String emailAlternativo;
    private UsuarioSigera usuarioEdicao;
    private final MensagensTela mensagemDeTela = new MensagensTela();

    public EditarPerfilBean() {
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getTelefoneComercial() {
        return this.getUsuarioEdicao().getTelefoneComercial();
    }

    public void setTelefoneComercial(String telefoneComercial) {
        this.telefoneComercial = telefoneComercial;
    }

    public String getTelefoneCelular() {
        return this.getUsuarioEdicao().getTelefoneCelular();
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public String getTelefoneResidencial() {
        return this.getUsuarioEdicao().getTelefoneResidencial();
    }

    public void setTelefoneResidencial(String telefoneResidencial) {
        this.telefoneResidencial = telefoneResidencial;
    }

    public String getEmailAlternativo() {
        return this.getUsuarioEdicao().getUsuarioLdap().getEmailAternativo();
    }

    public void setEmailAlternativo(String emailAlternativo) {
        this.emailAlternativo = emailAlternativo;
    }

    public UsuarioSigera getUsuarioEdicao() {
        String uid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("uid");
        Integer uidInteiro;
        try {
            uidInteiro = Integer.parseInt(uid);
        } catch (NumberFormatException nfe) {
            uidInteiro = loginBean.getUsuario().getId();
            Logger.getLogger(EditarPerfilBean.class.getName()).log(Level.INFO, "Paramêtro passado não é um número válido.", nfe);
        }
        if ((uid != null) && (this.usuarioEdicao == null || this.usuarioEdicao.getId() != uidInteiro)) {
            construirUsuarioEdicao(uidInteiro);
        }
        return this.usuarioEdicao;
    }

    public void setUsuarioEdicao(UsuarioSigera usuarioEdicao) {
        this.usuarioEdicao = usuarioEdicao;
    }

    public void salvar() {
        //atualizar email-alternativo (registeredAddress) no LDAP            
        if (!this.emailAlternativo.equalsIgnoreCase(this.getUsuarioEdicao().getUsuarioLdap().getEmailAternativo())) {
            String userNameEditado = this.usuarioEdicao.getUsuarioLdap().getUid();
            this.loginBean.alterarCampoLdap(userNameEditado, "registeredAddress", this.emailAlternativo);
            this.getUsuarioEdicao().getUsuarioLdap().setEmailAternativo(emailAlternativo);
        }
        this.getUsuarioEdicao().atualizar(this.telefoneCelular, this.telefoneResidencial, this.telefoneComercial);
        mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.212"), Paginas.getEditarPerfil());
    }

    public String alterarSenha() {
        return Paginas.getAlterarSenha();
    }

    public void resetarSenha() {
        //1. altera o valor da senha do usuario no LDAP
        String userNameEditado = this.usuarioEdicao.getUsuarioLdap().getUid();
        String nomeCompletoUsuarioEditado = this.usuarioEdicao.getUsuarioLdap().getCn();

        String novaSenha = SenhaAleatoria.Gerar();
        //tratar erro quando não for possível a alteração
        this.loginBean.alterarCampoLdap(userNameEditado, "userPassword", novaSenha);

        //2.Envia uma MENSAGEM ao usuario no E-MAIL ALTERATIVO informando que sua senha foi resetada.
        if (loginBean.getConfiguracao().isEnviarEmail()) {
            GerenciadorEmail gerenciadorEmail = new GerenciadorEmail();
            gerenciadorEmail.adicionarEmailSenhaResetada(this.getUsuarioEdicao(), novaSenha);
            gerenciadorEmail.enviarEmails();
        }

        //3. mostra mensagem na tela que resetar senha foi efetivado com sucesso.
        mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.007", nomeCompletoUsuarioEditado), Paginas.getEditarPerfil());
    }

    public String cancelar() {
        return Paginas.getEditarPerfil();
    }

    public String editarPerfil(Integer uidNumber) {
        construirUsuarioEdicao(uidNumber);
        return "editar_perfil";
    }

    public void construirUsuarioEdicao(Integer uidNumber) {
        try {

            BuscadorLdap buscadorLdap = loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap();
            UsuarioLdap usuarioLdap = buscadorLdap.obtenhaUsuarioLdap(uidNumber);
            UsuarioSigera usuarioEditado = UsuarioSigera.obtenhaUsuarioSigera(uidNumber);

            if (usuarioLdap != null) {
                //se usuario (UsuarioSigera) não existe ainda no banco deve ser criado            
                if (usuarioEditado == null) {
                    usuarioEditado = new UsuarioSigera();
                    usuarioEditado.setId(uidNumber);
                    usuarioEditado.salvar();
                }

                usuarioEditado.setUsuarioLdap(usuarioLdap);

                if (usuarioEditado.getUsuarioLdap().getGrupo() == EnumGrupo.ALUNO) {
                    Curso c = Curso.obtenhaCursoPorPrefixo(usuarioEditado.getUsuarioLdap().getPrefixoCurso());
                    Perfil perfilAluno = new PerfilAluno();
                    AssociacaoPerfilCurso apc = new AssociacaoPerfilCurso(perfilAluno, c, usuarioEditado);
                    usuarioEditado.setPerfilAtual(apc);
                }

                this.setUsuarioEdicao(usuarioEditado);

            } else {
                this.setUsuarioEdicao(loginBean.getUsuario());
            }

        } catch (Exception e) {
            //caso não encontre nennhum usuario com o id fornecido, retorna os dados do usuario logado
            this.setUsuarioEdicao(loginBean.getUsuario());
            Logger.getLogger(EditarPerfilBean.class.getName()).log(Level.INFO, "Uid não existe, usuário inexistente.", e);
        }

    }

    public boolean isAluno() {
        if (this.usuarioEdicao.getUsuarioLdap().getGrupo() == EnumGrupo.ALUNO) {
            return true;
        }
        return false;
    }

    public boolean temEmailAlternativo() {
        return !(getEmailAlternativo().isEmpty());
    }
}
