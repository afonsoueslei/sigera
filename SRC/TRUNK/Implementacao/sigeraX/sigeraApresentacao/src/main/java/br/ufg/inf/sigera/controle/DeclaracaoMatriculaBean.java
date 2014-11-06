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
import br.ufg.inf.sigera.modelo.Curso;
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
        Integer idCursoRequerente = requerimento.getCurso().getId();

        if (requerimento.salvar()) {
            //adição da regra para que professor&secretario(membros comissão) não recebam e-mails desse tipo de requerimento
            if(requerimento.getCurso().getPrefixo().equalsIgnoreCase("msc") || requerimento.getCurso().getPrefixo().equalsIgnoreCase("dsc")){
               idCursoRequerente = Curso.obtenhaCursoPorPrefixo("POS").getId();
            }
            
            List<UsuarioSigera> destinatarios
                    = AssociacaoPerfilCurso.obtenhaUsuariosDoPerfilCurso(EnumPerfil.SECRETARIA.getCodigo(),
                            idCursoRequerente,
                            loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap());

            if (loginBean.getConfiguracao().isEnviarEmail()) {
                GerenciadorEmail gerenciadorEmail = new GerenciadorEmail();
                gerenciadorEmail.adicionarEmailRequerimento(requerimento, destinatarios);
                gerenciadorEmail.enviarEmails();
            }
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.002", EnumTipoRequerimento.DECLARACAO_MATRICULA.getNome()), Paginas.getDetalheRequerimento());

            return Paginas.getAbrirRequerimentoID() + requerimento.getId();
        }
        mensagemDeTela.criar(FacesMessage.SEVERITY_ERROR, Mensagens.obtenha("MT.002.Falha", EnumTipoRequerimento.obtenha(requerimento.getTipo()).getNome().toUpperCase()), Paginas.getPrincipal());
        return Paginas.getPrincipal();
    }

    public String voltar() {
        return Paginas.getPrincipal();
    }

    public String prosseguir() {
        return Paginas.getDeclaracaoMatriculaFinalizacao();
    }
}
