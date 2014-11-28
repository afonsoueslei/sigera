package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.controle.servico.AcessoLdap;
import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import br.ufg.inf.sigera.modelo.AssociacaoPerfilCurso;
import br.ufg.inf.sigera.modelo.Professor;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.email.GerenciadorEmail;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import br.ufg.inf.sigera.modelo.ldap.EnumGrupo;
import br.ufg.inf.sigera.modelo.ldap.UsuarioLdap;
import br.ufg.inf.sigera.modelo.perfil.EnumPerfil;
import br.ufg.inf.sigera.modelo.perfil.GerenciadorPerfil;
import br.ufg.inf.sigera.modelo.perfil.Perfil;
import br.ufg.inf.sigera.modelo.perfil.PerfilAlunoPosStrictoSensu;
import br.ufg.inf.sigera.modelo.servico.SenhaAleatoria;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    private Professor orientadorSelecionado;
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
        String uid = null;
        Integer uidInteiro;
        try {
            uid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("uid");
            uidInteiro = Integer.parseInt(uid);
        } catch (NumberFormatException nfe) {
            uidInteiro = loginBean.getUsuario().getId();
            Logger.getLogger(EditarPerfilBean.class.getName()).log(Level.INFO, "Paramêtro passado não é um número válido.", nfe);
        }

        //verifica se o usuário tem permissão para editar o usuario passado como parametro (uid)
        if (loginBean.getUsuario().getPerfilAtual().getPerfil().permiteManterUsuarios() || loginBean.getUsuario().getId() == uidInteiro) {
            if ((uid != null) && (this.usuarioEdicao == null || this.usuarioEdicao.getId() != uidInteiro)) {
                construirUsuarioEdicao(uidInteiro);
            }
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
            AcessoLdap.alterarCampoLdap(userNameEditado, "registeredAddress", this.emailAlternativo);
            this.getUsuarioEdicao().getUsuarioLdap().setEmailAternativo(emailAlternativo);
        }
        verifiqueInformacaoOrientador();

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
        AcessoLdap.alterarCampoLdap(userNameEditado, "userPassword", novaSenha);

        //2.Envia uma MENSAGEM ao usuario no E-MAIL ALTERATIVO informando que sua senha foi resetada.
        if (loginBean.getConfiguracao().isEnviarEmail()) {
            GerenciadorEmail gerenciadorEmail = new GerenciadorEmail();
            gerenciadorEmail.adicionarEmailSenhaResetada(this.getUsuarioEdicao(), novaSenha);
            gerenciadorEmail.enviarEmails();
        }
        //atualiza o usuario para caso ele seja aluno e ainda não tenha este perfil associado lhe seja atribuido
        this.usuarioEdicao.atualizar(telefoneCelular, telefoneResidencial, telefoneComercial);

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
        this.orientadorSelecionado = null;
        try {

            BuscadorLdap buscadorLdap = loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap();
            UsuarioLdap usuarioLdap = buscadorLdap.obtenhaUsuarioLdap(uidNumber);
            UsuarioSigera usuarioEditado = UsuarioSigera.obtenhaUsuarioSigera(uidNumber);

            if (usuarioLdap != null) {
                //se usuario (UsuarioSigera) não existe ainda no banco deve ser criado            
                if (usuarioEditado == null) {
                    usuarioEditado = new UsuarioSigera();
                    usuarioEditado.setId(uidNumber);
                }

                usuarioEditado.setUsuarioLdap(usuarioLdap);
                usuarioEditado.getUsuarioLdap().setBuscadorLdap(buscadorLdap);

                if (usuarioLdap.getGrupo().equals(EnumGrupo.ALUNO) && (usuarioEditado.getPerfis() == null || usuarioEditado.getPerfis().isEmpty())) {
                    Collection<AssociacaoPerfilCurso> perfis = new ArrayList<AssociacaoPerfilCurso>();

                    //se for aluno regular de Pos Stricto Sensu, cria perfil proprio e associa ao orientador (Administrador = uidNumber 1786)
                    if (usuarioLdap.isAlunoRegularPosStrictoSensu()) {
                        AssociacaoPerfilCurso perfilEstudantePosStrictoSensu = GerenciadorPerfil.criePerfilAlunoPosStrictoSensu(usuarioEditado, Professor.obtenhaProfessorPorIdUsuario(1786));
                        perfis.add(perfilEstudantePosStrictoSensu);
                    } else {
                        AssociacaoPerfilCurso perfilEstudante = GerenciadorPerfil.criePerfilAluno(usuarioEditado);
                        perfis.add(perfilEstudante);
                    }
                    usuarioEditado.setPerfis(perfis);
                }

                usuarioEditado.salvar();

                this.setUsuarioEdicao(usuarioEditado);

            } else {
                this.setUsuarioEdicao(loginBean.getUsuario());
            }
            this.getCodigoOrientador();
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

    public String usuarioEdicaoTemOrientador() {
        BuscadorLdap buscadorLdap = loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap();
        UsuarioSigera orientador = new PerfilAlunoPosStrictoSensu().obtenhaOrientador(usuarioEdicao, buscadorLdap);
        if (orientador != null) {
            return orientador.getUsuarioLdap().getCn();
        }
        return null;
    }

    public boolean isAlunoPosStrictoSensu() {
        return ((this.usuarioEdicao.getUsuarioLdap().getGrupo() == EnumGrupo.ALUNO
                && (this.usuarioEdicao.getUsuarioLdap().getPrefixoCurso().endsWith("sc") || this.usuarioEdicao.getUsuarioLdap().getPrefixoCurso().endsWith("SC")))
                || Perfil.usuarioTemPerfil(this.usuarioEdicao, EnumPerfil.ALUNO_POS_STRICTO_SENSU.getCodigo()));
    }

    public List<Professor> getListaOrientadores() {
        return Professor.buscaTodosProfessores(loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap());
    }

    public Integer getCodigoOrientador() {
        if (orientadorSelecionado != null || usuarioEdicaoTemOrientador() != null) {
            if (orientadorSelecionado == null) {
                BuscadorLdap buscadorLdap = loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap();
                UsuarioSigera orientador = new PerfilAlunoPosStrictoSensu().obtenhaOrientador(usuarioEdicao, buscadorLdap);
                orientadorSelecionado = Professor.obtenhaProfessorPorIdUsuario(orientador.getId());
            }
            return orientadorSelecionado.getId();
        }
        return null;
    }

    public void setCodigoOrientador(Integer codigoOrientador) {
        if (codigoOrientador != null) {
            this.orientadorSelecionado = Professor.obtenhaProfessor(codigoOrientador);
        } else {
            this.orientadorSelecionado = null;
        }
    }

    //Verifica UsuarioEdição tem orientador e este foi alterado 
    private void verifiqueInformacaoOrientador() {
        if (usuarioEdicaoTemOrientador() != null && !this.orientadorSelecionado.getNome().equalsIgnoreCase(usuarioEdicaoTemOrientador())) {
            for (AssociacaoPerfilCurso p : usuarioEdicao.getPerfis()) {
                if (p.getPerfil().getId() == EnumPerfil.ALUNO_POS_STRICTO_SENSU.getCodigo()) {
                    p.setOrientador(orientadorSelecionado);
                }
            }
        }
    }
}
