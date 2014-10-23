package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.modelo.AssociacaoPerfilCurso;
import br.ufg.inf.sigera.modelo.Autenticacao;
import br.ufg.inf.sigera.modelo.Configuracao;
import br.ufg.inf.sigera.modelo.Plano;
import br.ufg.inf.sigera.modelo.Turma;
import br.ufg.inf.sigera.modelo.perfil.GerenciadorPerfil;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.perfil.EnumPerfil;
import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import br.ufg.inf.sigera.controle.servico.Sessoes;
import br.ufg.inf.sigera.modelo.ldap.AutenticacaoLdap;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;

@ManagedBean
@SessionScoped
public class LoginBean {

    private static final String URL_ORIGINAL = "urlOriginal";
    private String username;
    private String password;
    private UsuarioSigera usuario;
    private Configuracao configuracao;
    private Plano plano;
    private final MensagensTela mensagemDeTela = new MensagensTela();
    private String novaSenha;
    private String novaSenhaConfirmar;
    private static final String MT005 = "MT.005";

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setCodigoPerfil(Integer codigoPerfil) {
        this.usuario.setPerfilAtual(GerenciadorPerfil.obtenhaAssociacaoPerfil(codigoPerfil, this.usuario.getId()));
    }

    public Integer getCodigoPerfil() {
        return null;
    }

    public LoginBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLogado() {
        return this.usuario != null && this.usuario.getPerfilAtual() != null;
    }

    public UsuarioSigera getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioSigera usuario) {
        this.usuario = usuario;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getNovaSenhaConfirmar() {
        return novaSenhaConfirmar;
    }

    public void setNovaSenhaConfirmar(String novaSenhaConfirmar) {
        this.novaSenhaConfirmar = novaSenhaConfirmar;
    }

    public boolean temTurmasSemPlano() {
        List<Turma> turmasbuscadas = Plano.buscaTurmasSemPlano(this.configuracao.getAnoCorrente(),
                this.configuracao.getSemestreCorrente(),
                this.usuario.getUsuarioLdap().getBuscadorLdap());

        return !(turmasbuscadas.isEmpty());
    }

    public String checkLogin() {
        Autenticacao autenticador = new Autenticacao(this.username, this.password);
        this.usuario = autenticador.getUsuarioSigera();

        if (this.usuario != null) {
            Collection<AssociacaoPerfilCurso> perfis = usuario.getPerfis();
            if (!temConexaoBanco()) {
                return Paginas.getLogin();
            }
            if(!temConexaoLdap()){
                return Paginas.getLogin();
            }
            
            return possuiPerfil(perfis);
        } else {
            if (autenticador.getUsuarioLdap() != null) {
                mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha("MT.213"), Paginas.getLogin());
            } else {
                mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha("MT.202"), Paginas.getLogin());
            }
            return Paginas.getLogin();
        }
    }

    private boolean temConexaoBanco() {
        //se houve falha na conexão com o banco de dados
        if (this.usuario.getTelefoneComercial() != null && "falha-banco".equalsIgnoreCase(this.usuario.getTelefoneComercial())) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha("MT.700"), Paginas.getLogin());
            return false;
        }
        return true;
    }

    private boolean temConexaoLdap() {
        //se houve falha na conexão com o banco de dados
        if (this.usuario.getTelefoneComercial() != null && "falha-conexao-ldap".equalsIgnoreCase(this.usuario.getTelefoneComercial())) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha("MT.700.ldap"), Paginas.getLogin());
            return false;
        }
        return true;
    }

    
    private String possuiPerfil(Collection<AssociacaoPerfilCurso> perfis) {
        if (perfis == null || perfis.isEmpty()) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha("MT.211"), Paginas.getLogin());
            return logout();
        } else if (perfis.size() == 1) {
            this.usuario.setPerfilAtual(perfis.iterator().next());
            return loginComSucesso();
        } else {
            return Paginas.getSelecaoPerfil();
        }
    }

    public String logout() {
        if (this.isLogado() && temConexaoBanco()) {
            //pegar o usuário atualizado no banco, caso este tenha sofrido alguma atualização de perfil por exemplo.
            this.usuario = UsuarioSigera.obtenhaUsuarioSigera(this.usuario.getId());
            this.usuario.setUltimoAcesso(new Date());
            this.usuario.salvar();
        }                
        Sessoes.invalidarSessoes();        
        return Paginas.getLogin();
    }

    public String manterUsuarios() {
        return Paginas.getConsultarUsuarios();
    }

    public String mudePerfil() {
        Sessoes.limparBeans();
        return Paginas.getSelecaoPerfil();
    }

    public String definaPerfil() {
        return loginComSucesso();
    }

    public String voltarPaginaPrincipal() {
        if (this.isLogado()) {
            return Paginas.getPrincipal();
        }
        return Paginas.getLogin();
    }

    public String loginComSucesso() {
        this.configuracao = Configuracao.carregar();
        
        //se for primeiro acesso ou não tem e-mail alternativo cadastrado
        if (this.getUsuario().getPrimeiroAcesso() == null || this.getUsuario().getUsuarioLdap().getEmailAternativo().isEmpty()) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_WARN, Mensagens.obtenha("MT.708"), Paginas.getEditarPerfil());
            redirecionePaginaEditarPerfil();
        }

        // Redireciona o usuário para a URL que ele estava tentando acessar originalmente (se for o caso).
        redirecionePaginaURLInformada();

        //Se o perfil do usuário = secretario(a) de curso ou de Graduação, Coordenador de Estagio 
        //ou de Diretor abri direto na página de consultar requerimentos.
        if (redirecionePaginaConsultarRequerimentos()) {
            return Paginas.getConsultarRequerimentos();
        }

        // Por padrão, o usuário é redirecionado para a página principal da aplicação.        
        return Paginas.getPrincipal();

    }

    private void redirecionePaginaURLInformada() {
        // Redireciona o usuário para a URL que ele estava tentando acessar originalmente (se for o caso).
        try {
            ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
            Object objUrlOriginal = contexto.getSessionMap().get(URL_ORIGINAL);
            if (objUrlOriginal != null && !(objUrlOriginal.toString().trim().isEmpty())) {
                contexto.getSessionMap().put(URL_ORIGINAL, null);
                contexto.redirect(objUrlOriginal.toString().trim());
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.WARNING, null, ex);
        }
    }

    private boolean redirecionePaginaConsultarRequerimentos() {
        return this.usuario.getPerfilAtual().getPerfil().getId() == EnumPerfil.SECRETARIA.getCodigo()
                || this.usuario.getPerfilAtual().getPerfil().getId() == EnumPerfil.SECRETARIA_GRADUACAO.getCodigo()
                || this.usuario.getPerfilAtual().getPerfil().getId() == EnumPerfil.COORDENADOR_ESTAGIO.getCodigo()
                || this.usuario.getPerfilAtual().getPerfil().getId() == EnumPerfil.DIRETOR.getCodigo();
    }

    public String cancelar() {
        return Paginas.getEditarPerfil();
    }

    public String alterarSenha() {
        AutenticacaoLdap autenticadorLdap = new AutenticacaoLdap();
        String login = "uid=" + username + ", ou=Users, dc=inf, dc=ufg, dc=br";
        if (autenticadorLdap.efetuaLogin(username, password) != null) {
            if (this.novaSenha.equals(this.novaSenhaConfirmar)) {
                //senha tem que ser diferente da atual
                if (!this.novaSenha.equals(this.password)) {
                    try {
                        DirContext dir = autenticadorLdap.getContext(login, password);
                        autenticadorLdap.alterarCampoUsuarioLdap(this.username, dir, "userPassword", this.novaSenha);

                    } catch (NamingException ex) {
                        Logger.getLogger(AutenticacaoLdap.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    //senha atual e nova são iguais
                    mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha(MT005), Paginas.getAlterarSenha());
                    mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.005.SenhaIgual"), Paginas.getAlterarSenha());
                    return Paginas.getAlterarSenha();
                }
            } else {
                //Senhas não correspondem
                mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha(MT005), Paginas.getAlterarSenha());
                mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.005.SenhaNaoCorresponde"), Paginas.getAlterarSenha());
                return Paginas.getAlterarSenha();
            }
        } else {
            //senha atual incorreta
            mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha(MT005), Paginas.getAlterarSenha());
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.005.SenhaIncorreta"), Paginas.getAlterarSenha());
            return Paginas.getAlterarSenha();
        }

        mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.005.Sucesso"), Paginas.getEditarPerfil());
        return Paginas.getEditarPerfil();
    }

    private void redirecionePaginaEditarPerfil() {
        try {
            ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
            contexto.redirect("usuario/atualizar_perfil.xhtml?uid=" + this.getUsuario().getUsuarioLdap().getUidNumber());

        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception iex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, iex);
        }
    }
    
    public boolean usuarioEhCoordenadorDeCurso(){
        return this.getUsuario().getPerfilAtual().getPerfil().getId() == EnumPerfil.COORDENADOR_CURSO.getCodigo() ||
               this.getUsuario().getPerfilAtual().getPerfil().getId() == EnumPerfil.COORDENADOR_GERAL.getCodigo();
    }

    public boolean usuarioEhSecretarioGraduação(){
        return this.getUsuario().getPerfilAtual().getPerfil().getId() == EnumPerfil.SECRETARIA_GRADUACAO.getCodigo();
    }
    
}
