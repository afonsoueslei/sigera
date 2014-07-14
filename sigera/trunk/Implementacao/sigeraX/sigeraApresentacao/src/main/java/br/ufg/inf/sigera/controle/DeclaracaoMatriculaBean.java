package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.modelo.AssociacaoPerfilCurso;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.email.GerenciadorEmail;
import br.ufg.inf.sigera.modelo.perfil.EnumPerfil;
import br.ufg.inf.sigera.modelo.requerimento.EnumTipoRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoDeclaracaoMatricula;
import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class DeclaracaoMatriculaBean {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private final MensagensTela mensagemDeTela = new MensagensTela();
    private String justificativa;

    public DeclaracaoMatriculaBean() {
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
        Requerimento requerimento = new RequerimentoDeclaracaoMatricula(loginBean.getUsuario(), this.getJustificativa());
        requerimento.salvar();

        List<UsuarioSigera> destinatarios =
                AssociacaoPerfilCurso.obtenhaUsuarios(EnumPerfil.SECRETARIA.getCodigo(),
                requerimento.getCurso().getId(),
                loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap());

        if (loginBean.getConfiguracao().isEnviarEmail()) {
            GerenciadorEmail gerenciadorEmail = new GerenciadorEmail();
            gerenciadorEmail.adicionarEmailRequerimento(requerimento, destinatarios);
            gerenciadorEmail.enviarEmails();
        }
        mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.002", EnumTipoRequerimento.DECLARACAO_MATRICULA.getNome()), Paginas.getDetalheRequerimento());

        return Paginas.getAbrirRequerimentoID() + requerimento.getId();
    }

    public String voltar() {
        return Paginas.getPrincipal();
    }
    
    public String prosseguir(){
        return Paginas.getDeclaracaoMatriculaFinalizacao();
    }
}
