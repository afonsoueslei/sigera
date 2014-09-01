package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.controle.datamodels.AtribuirPerfilDataModel;
import br.ufg.inf.sigera.controle.tela.UsuarioTelaAtribuirPerfil;
import br.ufg.inf.sigera.controle.adaptador.AdaptadorUsuarioTelaAtribuirPerfil;
import br.ufg.inf.sigera.modelo.AssociacaoPerfilCurso;
import br.ufg.inf.sigera.modelo.Autenticacao;
import br.ufg.inf.sigera.modelo.Curso;
import br.ufg.inf.sigera.modelo.perfil.GerenciadorPerfil;
import br.ufg.inf.sigera.modelo.perfil.Perfil;
import br.ufg.inf.sigera.modelo.email.GerenciadorEmail;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

@ManagedBean
@ViewScoped
public class AtribuirPerfilBean {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private List<UsuarioTelaAtribuirPerfil> usuariosTela;
    private List<UsuarioTelaAtribuirPerfil> usuariosFiltrados;
    private List<UsuarioTelaAtribuirPerfil> usuariosSelecionados;
    private UsuarioSigera usuarioEditado;
    private AtribuirPerfilDataModel dataModel;
    private final SelectItem[] opcoesPerfil;
    private Perfil perfilSelecionado;
    private Curso cursoSelecionado;
    private boolean selecaoCursosHabilitada;
    private final MensagensTela mensagemDeTela = new MensagensTela();

    public AtribuirPerfilBean() {
        this.opcoesPerfil = crieOpcoesPerfil();
    }

    public List<UsuarioTelaAtribuirPerfil> getUsuariosTela() {
        return usuariosTela;
    }

    public void setUsuariosTela(List<UsuarioTelaAtribuirPerfil> usuariosTela) {
        this.usuariosTela = usuariosTela;
    }

    public boolean isSelecaoCursosHabilitada() {
        return selecaoCursosHabilitada;
    }

    public SelectItem[] getOpcoesPerfil() {
        return opcoesPerfil;
    }

    public Integer getCodigoPerfil() {
        if (perfilSelecionado != null) {
            return perfilSelecionado.getId();
        }
        return null;
    }

    public void setCodigoPerfil(Integer codigoPerfil) {
        if (codigoPerfil != null) {
            this.perfilSelecionado = GerenciadorPerfil.obtenhaPerfil(codigoPerfil);
        } else {
            this.perfilSelecionado = null;
        }
        this.selecaoCursosHabilitada = AssociacaoPerfilCurso.perfilExigeCursoAssociado(this.perfilSelecionado);
    }

    public Integer getCodigoCurso() {
        if (cursoSelecionado != null) {
            return cursoSelecionado.getId();
        }
        return null;
    }

    public void setCodigoCurso(Integer codigoCurso) {
        if (codigoCurso != null) {
            this.cursoSelecionado = Curso.obtenhaCurso(codigoCurso);
        } else {
            this.cursoSelecionado = null;
        }
    }

    public List<Perfil> getListaPerfis() {
        return GerenciadorPerfil.obtenhaTodosPerfis();
    }

    public List<Curso> getListaCursos() {
        return Curso.buscaTodosCursos();
    }

    public List<UsuarioTelaAtribuirPerfil> getUsuariosSelecionados() {
        return usuariosSelecionados;
    }

    public void setUsuariosSelecionados(List<UsuarioTelaAtribuirPerfil> usuariosSelecionados) {
        this.usuariosSelecionados = usuariosSelecionados;
    }

    public UsuarioSigera getUsuarioEditado() {
        return usuarioEditado;
    }

    public void setUsuarioEditado(UsuarioSigera usuarioEditado) {
        this.usuarioEditado = usuarioEditado;
    }

    public AtribuirPerfilDataModel getDataModel() {
        if (dataModel == null) {
            try {
                List<UsuarioSigera> usuarios = Autenticacao.obtenhaUsuarios(loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap());
                this.usuariosTela = new ArrayList<UsuarioTelaAtribuirPerfil>();
                for (UsuarioSigera usuario : usuarios) {
                    this.usuariosTela.add(new AdaptadorUsuarioTelaAtribuirPerfil(usuario));
                }

                this.dataModel = new AtribuirPerfilDataModel(this.usuariosTela);
            } catch (Exception ie) {
                Paginas.redirecionePaginaErro();
            }
        }
        return dataModel;
    }

    public void setDataModel(AtribuirPerfilDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public List<UsuarioTelaAtribuirPerfil> getUsuariosFiltrados() {
        return usuariosFiltrados;
    }

    public void setUsuariosFiltrados(List<UsuarioTelaAtribuirPerfil> usuariosFiltrados) {
        this.usuariosFiltrados = usuariosFiltrados;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void salvar() {
        List<UsuarioTelaAtribuirPerfil> usuariosComAlteracoes = new ArrayList<UsuarioTelaAtribuirPerfil>();
        List<UsuarioSigera> usuariosComPerfilModificado = new ArrayList<UsuarioSigera>();

        for (UsuarioTelaAtribuirPerfil usuario : this.usuariosTela) {
            if (usuario.teveAlteracoes()) {
                usuariosComAlteracoes.add(usuario);
            }
        }
        for (UsuarioTelaAtribuirPerfil usuarioTela : usuariosComAlteracoes) {
            //verifico mudanças de perfil
            UsuarioSigera userComPerfilModificado = usuarioTela.processePerfisUsuario();
            //atualizo no banco mudanças realizadas
            userComPerfilModificado.salvar();
            //crio lista com usuarios que tiveram mudança de perfil para enviar email
            usuariosComPerfilModificado.add(userComPerfilModificado);
        }

        if (loginBean.getConfiguracao().isEnviarEmail()) {
            GerenciadorEmail gerenciadorEmail = new GerenciadorEmail();
            for (UsuarioSigera usuarioEnvioEmail : usuariosComPerfilModificado) {
                gerenciadorEmail.adicionarEmailAtribuicaoPerfil(usuarioEnvioEmail);
            }
            gerenciadorEmail.enviarEmails();
        }

        if (!(usuariosComAlteracoes.isEmpty())) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.101"), Paginas.getAtribuirPerfil());
        }
        limparSelecao();
        limpar();

    }

    public String cancelar() {
        limpar();
        return Paginas.getAtribuirPerfil();
    }

    public void definirPerfilSelecionados(boolean concederPerfil) {

        if (validarConcederPerfil(concederPerfil)) {
            return;
        }

        if (usuariosSelecionados.isEmpty()) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.016"), Paginas.getAtribuirPerfil());
            return;
        }

        for (UsuarioTelaAtribuirPerfil usuario : usuariosSelecionados) {
            usuario.modificarPerfil(this.perfilSelecionado, this.cursoSelecionado, concederPerfil);
        }
        limparSelecao();
    }

    private SelectItem[] crieOpcoesPerfil() {
        SelectItem[] options = new SelectItem[3];

        options[0] = new SelectItem("", "Todos");
        options[1] = new SelectItem("Sim", "Sim");
        options[2] = new SelectItem("Não", "Não");

        return options;
    }

    public void preProcess(Object document) {
        try {
            Document pdf = (Document) document;
            pdf.open();
            pdf.setPageSize(PageSize.A4);

            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String logo = servletContext.getRealPath("") + "/resources/img/cabecalho.png";
            pdf.add(Image.getInstance(logo));

        } catch (IOException ex) {
            Logger.getLogger(AtribuirPerfilBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (BadElementException badEx) {
            Logger.getLogger(AtribuirPerfilBean.class
                    .getName()).log(Level.SEVERE, null, badEx);
        } catch (DocumentException docEx) {
            Logger.getLogger(AtribuirPerfilBean.class
                    .getName()).log(Level.SEVERE, null, docEx);
        }
    }

    private void limparSelecao() {
        this.usuariosSelecionados = null;
    }

    private void limpar() {
        dataModel = null;
        getDataModel();
    }

    private boolean validarConcederPerfil(boolean concederPerfil) {
        if (this.perfilSelecionado == null) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.014"), Paginas.getAtribuirPerfil());
            return true;
        } else {
            if (concederPerfil && AssociacaoPerfilCurso.perfilExigeCursoAssociado(perfilSelecionado) && this.cursoSelecionado == null) {
                mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.015"), Paginas.getAtribuirPerfil());
                return true;
            }
        }
        return false;
    }

}
