package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.controle.adaptador.AdaptadorPlanoTela;
import br.ufg.inf.sigera.controle.adaptador.AdaptadorTurmaTela;
import br.ufg.inf.sigera.controle.datamodels.PlanoDataModel;
import br.ufg.inf.sigera.controle.datamodels.TurmaDataModel;
import br.ufg.inf.sigera.controle.tela.PlanoTela;
import br.ufg.inf.sigera.controle.tela.TurmaTela;
import br.ufg.inf.sigera.modelo.AssociacaoPerfilCurso;
import br.ufg.inf.sigera.modelo.ItemCronograma;
import br.ufg.inf.sigera.modelo.Plano;
import br.ufg.inf.sigera.modelo.Turma;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.email.GerenciadorEmail;
import br.ufg.inf.sigera.modelo.ldap.UsuarioLdap;
import br.ufg.inf.sigera.modelo.perfil.EnumPerfil;
import br.ufg.inf.sigera.modelo.requerimento.EnumStatusRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.EnumTipoRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoPlano;
import br.ufg.inf.sigera.controle.servico.MensagensTela;
import br.ufg.inf.sigera.controle.servico.Paginas;
import br.ufg.inf.sigera.controle.servico.Sessoes;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class PlanoBean implements Serializable {

    private PlanoDataModel dataModelPlanos;
    private TurmaDataModel dataModelTurmasSemPlano;
    private List<TurmaTela> turmasTela;
    private List<TurmaTela> turmasFiltradas;
    private List<TurmaTela> turmasSelecionadas;
    private List<PlanoTela> planosTela;
    private List<PlanoTela> planosFiltrados;
    private Plano planoSelecionado;
    private Plano planoEditavel;
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private List<ItemCronograma> itens;
    private ItemCronograma itemSelecionado;
    private ItemCronograma itemEditavel;
    private int activeIndex;
    private final MensagensTela mensagemDeTela = new MensagensTela();
    private final GerenciadorEmail gerenciadorEmail = new GerenciadorEmail();
    private static final String MT002 = "MT.002";
    private static final String MT522 = "MT.522";

    public PlanoBean() {
        this.activeIndex = 0;
    }

    //indice da tab que está ativa   
    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public ItemCronograma getItemSelecionado() {
        return itemSelecionado;
    }

    public void setItemSelecionado(ItemCronograma itemSelecionado) {
        this.itemSelecionado = itemSelecionado;
    }

    public ItemCronograma getItemEditavel() {
        if (itemEditavel == null) {
            itemEditavel = new ItemCronograma();
        }
        return itemEditavel;
    }

    public void setItemEditavel(ItemCronograma itemEditavel) {
        this.itemEditavel = itemEditavel;
    }

    public List<ItemCronograma> getItens() {
        if (itens == null) {
            itens = new ArrayList<ItemCronograma>();
        }
        return itens;
    }

    public void setItens(List<ItemCronograma> itens) {
        this.itens = itens;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public List<TurmaTela> getTurmasTela() {
        return turmasTela;
    }

    public void setTurmasTela(List<TurmaTela> turmasTela) {
        this.turmasTela = turmasTela;
    }

    public Plano getPlanoEditavel() {
        if (planoEditavel == null) {
            planoEditavel = new Plano();
        }
        return planoEditavel;
    }

    public void setPlanoEditavel(Plano planoEditavel) {
        this.planoEditavel = planoEditavel;
    }

    public List<PlanoTela> getPlanosTela() {
        return planosTela;
    }

    public void setPlanosTela(List<PlanoTela> planosTela) {
        this.planosTela = planosTela;
    }

    public List<PlanoTela> getPlanosFiltrados() {
        return planosFiltrados;
    }

    public List<TurmaTela> getTurmasFiltradas() {
        return turmasFiltradas;
    }

    public void setTurmasFiltradas(List<TurmaTela> turmasFiltradas) {
        this.turmasFiltradas = turmasFiltradas;
    }

    public List<TurmaTela> getTurmasSelecionadas() {
        return turmasSelecionadas;
    }

    public void setTurmasSelecionadas(List<TurmaTela> turmasSelecionadas) {
        this.turmasSelecionadas = turmasSelecionadas;
    }

    public void setPlanosFiltrados(List<PlanoTela> planosFiltrados) {
        this.planosFiltrados = planosFiltrados;
    }

    public Plano getPlanoSelecionado() {
        return planoSelecionado;
    }

    public void setPlanoSelecionado(Plano planoSelecionado) {
        this.planoSelecionado = planoSelecionado;
    }

    public PlanoDataModel getDataModelPlanos() {
        try {
            List<RequerimentoPlano> requerimentosPlanosBuscados = this.loginBean.getUsuario().obtenhaRequerimentosPlanos();
            this.planosTela = new ArrayList<PlanoTela>();
            for (RequerimentoPlano p : requerimentosPlanosBuscados) {
                this.planosTela.add(new AdaptadorPlanoTela(p.getPlano()));
            }
            this.dataModelPlanos = new PlanoDataModel(this.planosTela);
        } catch (Exception ie) {
            Paginas.redirecionePaginaErro();
            Logger.getLogger(PlanoBean.class.getName()).log(Level.SEVERE, null, ie);
        }
        return dataModelPlanos;
    }

    public void setDataModelPlanos(PlanoDataModel dataModelPlanos) {
        this.dataModelPlanos = dataModelPlanos;
    }

    public TurmaDataModel getDataModelTurmasSemPlano() {
        if (dataModelTurmasSemPlano == null) {
            try {
                List<Turma> turmasbuscadas
                        = Plano.buscaTurmasSemPlano(loginBean.getConfiguracao().getAnoCorrente(),
                                loginBean.getConfiguracao().getSemestreCorrente(),
                                loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap());

                this.turmasTela = new ArrayList<TurmaTela>();
                for (Turma d : turmasbuscadas) {
                    this.turmasTela.add(new AdaptadorTurmaTela(d));
                }
                dataModelTurmasSemPlano = new TurmaDataModel(turmasTela);
            } catch (Exception ie) {
                Paginas.redirecionePaginaErro();
                Logger.getLogger(PlanoBean.class.getName()).log(Level.SEVERE, null, ie);
            }
        }
        return dataModelTurmasSemPlano;
    }

    public String editar() {
        prepararPlanoEditavel();
        return Paginas.getEditarPlano();
    }

    public String editar(Plano planoAtual) {
        this.planoSelecionado = planoAtual;
        prepararPlanoEditavel();
        return Paginas.getEditarPlano();
    }

    private void prepararPlanoEditavel() {
        UsuarioLdap usuarioLdap = loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap().obtenhaUsuarioLdap(this.planoSelecionado.getTurma().getProfessor().getUsuario().getId());
        this.planoEditavel = new Plano(this.planoSelecionado);
        this.planoEditavel.setId(this.planoSelecionado.getId());
        this.planoEditavel.getTurma().getProfessor().getUsuario().setUsuarioLdap(usuarioLdap);

        if (this.planoSelecionado.getItensCronograma().size() > 0) {
            for (ItemCronograma i : this.planoEditavel.getItensCronograma()) {
                i.setPlano(this.planoSelecionado);
            }
        } else {
            this.planoEditavel.setItensCronograma(this.planoSelecionado.getItensCronograma());
        }
        setActiveIndex(0);
    }

    private void prepararItemCronogramaEditavel() {
        if (this.itemSelecionado == null) {
            this.itemEditavel = new ItemCronograma();
            this.itemEditavel.setPlano(planoSelecionado);
        } else {
            this.itemEditavel = new ItemCronograma(this.itemSelecionado);
        }
    }

    public String editarItemCronograma() {
        prepararItemCronogramaEditavel();
        return Paginas.getEditarItemCronograma();
    }

    public String voltarListaPlanos() {
        limpar();
        return Paginas.getPlanoAula();
    }

    public String voltarListaItens() {
        limparItens();
        return Paginas.getEditarPlano();
    }

    private void limparItens() {
        itemSelecionado = null;
        itemEditavel = null;
        setActiveIndex(3);
    }

    private void limpar() {
        planoSelecionado = null;
        planoEditavel = null;
        itemEditavel = null;
        itemSelecionado = null;
        this.dataModelPlanos = null;
        getDataModelPlanos();
    }

    //Salva requerimento Plano para turmas que não tinham plano e foram selecionadas
    public String salvar() {
        if (!validarRequerimento()) {
            return Paginas.getTurmasSemPlano();
        }
        for (TurmaTela t : turmasSelecionadas) {
            Plano p = Plano.buscarPlanoExistenteEmSemestresAnteriores(t.getTurma());
            Plano.salvar(p);

            RequerimentoPlano reqPlano = new RequerimentoPlano(loginBean.getUsuario(), p);
            reqPlano.salvar();
            UsuarioSigera professorDestinatario = t.getTurma().getProfessor().getUsuario();

            gerenciadorEmail.adicionarEmailRequerimento(reqPlano, professorDestinatario, "EhPlano");
        }

        if (loginBean.getConfiguracao().isEnviarEmail()) {
            gerenciadorEmail.enviarEmails();
            Sessoes.limparBeans();
        }

        mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha(MT002, EnumTipoRequerimento.PLANO.getNome()), Paginas.getPlanoAula());
        return Paginas.getPlanoAula();
    }

    public String salvarEdicaoPlano() {
        //caso nada tenha sido alterado ou algum campo esteja vazio então   
        if (this.planoEditavel.equals(this.planoSelecionado)) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.521"), Paginas.getEditarPlano());
        } else {
            if (temItensCronograma()) {
                //persiste plano no banco
                Plano.salvar(this.planoEditavel);
                mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.520"), Paginas.getPlanoAula());
            }
        }
        return Paginas.getEditarPlano();
    }

    public String salvarItemSelecionado() {
        Integer totalCargaHoraria = this.planoEditavel.getTurma().getDisciplina().getHoraPratica()
                + this.planoEditavel.getTurma().getDisciplina().getHoraTeorica();

        Integer horasAcrescentadas = itemEditavel.getNumeroAulas();

        if (this.itemSelecionado != null) {
            horasAcrescentadas = itemEditavel.getNumeroAulas() - itemSelecionado.getNumeroAulas();
        }
        //O totalAulas atual + numero de aulas acrescentadas deve ser <= Carga Horaria Total        
        if (this.totalAulas() + horasAcrescentadas > totalCargaHoraria) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.524"), Paginas.getEditarItemCronograma());
            return Paginas.getEditarItemCronograma();
        }
        if (this.itemSelecionado != null) {
            this.planoEditavel.getItensCronograma().remove(this.itemSelecionado);
        }
        this.planoEditavel.getItensCronograma().add(this.itemEditavel);

        //persistir o item no banco
        this.itemEditavel.salvar(this.itemEditavel);
        mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.516"), Paginas.getEditarItemCronograma());
        return voltarListaItens();
    }

    public String concluir() {

        if (essePlanoPodeSerConcluido()) {
            RequerimentoPlano requerimento = Plano.buscarRequerimentoDessePlano(loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap(), this.planoSelecionado);

            if (requerimento.getPlano() != null) {
                requerimento.setStatus(EnumStatusRequerimento.CONCLUIDO.getCodigo());
                requerimento.salvar();
            } else {
                mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha(MT522), Paginas.getEditarPlano());
                return Paginas.getPlanoAula();
            }
            List<UsuarioSigera> destinatarios = criarDestinatarios();

            if (!destinatarios.isEmpty() && loginBean.getConfiguracao().isEnviarEmail()) {
                gerenciadorEmail.adicionarEmailRequerimento(requerimento, destinatarios);
                gerenciadorEmail.enviarEmails();
                Sessoes.limparBeans();
            }
            //emitir messagem de plano concluído
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.519"), Paginas.getEditarPlano());
            return Paginas.getAbrirRequerimentoID() + requerimento.getId();
        }
        return Paginas.getEditarPlano();
    }

    public String reabrirPlano() {

        RequerimentoPlano requerimento = Plano.buscarRequerimentoDessePlano(loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap(), this.planoSelecionado);
        //mudar estatus do Requerimento do Plano - para ABERTO se ele existir
        if (requerimento.getPlano() != null) {
            requerimento.setStatus(EnumStatusRequerimento.ABERTO.getCodigo());
            //persisti o requerimento no BD
            requerimento.salvar();
        } else {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha(MT522), Paginas.getEditarPlano());

            return Paginas.getPlanoAula();
        }
        //enviar e-mail para professor relacionado ao plano
        UsuarioSigera professor = this.planoSelecionado.getTurma().getProfessor().getUsuario();

        if (loginBean.getConfiguracao().isEnviarEmail()) {
            gerenciadorEmail.adicionarEmailRequerimento(requerimento, professor);
            gerenciadorEmail.enviarEmails();
            Sessoes.limparBeans();
        }
        //emitir messagem de plano reaberto
        mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.523"), Paginas.getEditarPlano());
        return Paginas.getAbrirRequerimentoID() + requerimento.getId();
    }

    public void excluirItemCronograma() {
        if (this.itemSelecionado != null) {
            Boolean removido = this.itemSelecionado.remover();
            if (removido) {
                this.planoEditavel.getItensCronograma().remove(this.itemSelecionado);
                mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.517"), Paginas.getEditarItemCronograma());
                voltarListaItens();
            } else {
                mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.500"), Paginas.getEditarItemCronograma());
                voltarListaItens();
            }
        }
    }

    public void cancelarItemCronograma() {
        prepararItemCronogramaEditavel();
    }

    public String voltarItemCronograma() {
        this.setActiveIndex(3);
        return Paginas.getEditarPlano();
    }

    public String voltar() {
        return Paginas.getPlanoAula();
    }

    public void imprimir() throws IOException {
        this.planoEditavel.imprimir();
    }

    public String solitarPreenchimento() {
        Sessoes.limparBeans();
        return Paginas.getTurmasSemPlano();
    }

    public String listarPlanos() {
        return Paginas.getPlanoAula();
    }

    public String visualizarRequerimentoPlano() {
        return Paginas.getDetalheRequerimento();
    }

    public Boolean usuarioPodeEditar() {
        return loginBean.getUsuario().getPerfilAtual().getPerfil().permiteEditarPlanoDeAula();
    }

    public Boolean essePlanoPodeSerEditado() {
        RequerimentoPlano req = Plano.buscarRequerimentoDessePlano(loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap(), planoSelecionado);
        return req.getStatus() == EnumStatusRequerimento.ABERTO.getCodigo() && this.usuarioPodeEditar();
    }

    public boolean essePlanoPodeSerConcluido() {
        return !temCargaHorariaIncompleta() && !this.planoEditavel.temCampoVazio() && temItensCronograma();
    }

    public Boolean essePlanoPodeSerReaberto() {
        RequerimentoPlano req = Plano.buscarRequerimentoDessePlano(loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap(), planoSelecionado);
        return req.getStatus() != EnumStatusRequerimento.ABERTO.getCodigo() && req.perfilPermiteDarParecer(loginBean.getUsuario());
    }

    public String verRequerimento() {
        RequerimentoPlano requerimento = Plano.buscarRequerimentoDessePlano(loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap(), this.planoSelecionado);
        return Paginas.getAbrirRequerimentoID() + requerimento.getId();
    }

    private boolean validarRequerimento() {
        if (this.turmasSelecionadas == null
                || this.turmasSelecionadas.size() < 1) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.403"), Paginas.getEditarPlano());
            return false;
        }
        return true;
    }

    public boolean temTurmasSemPlano() {
        return this.turmasTela.size() > 0;
    }

    private boolean temItensCronograma() {
        if (this.planoEditavel.getItensCronograma() == null || this.planoEditavel.getItensCronograma().size() < 1) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.518"), Paginas.getEditarPlano());
            return false;
        }
        return true;
    }

    private boolean temCargaHorariaIncompleta() {
        Integer totalCargaHoraria = this.planoEditavel.getTurma().getDisciplina().getHoraPratica() + this.planoEditavel.getTurma().getDisciplina().getHoraTeorica();

        if (this.totalAulas() < totalCargaHoraria) {
            mensagemDeTela.criar(FacesMessage.SEVERITY_INFO, Mensagens.obtenha("MT.526"), Paginas.getEditarPlano());
            return true;
        }
        return false;
    }

    public String strTotalAulas() {
        Integer total = 0;
        for (ItemCronograma i : this.planoEditavel.getItensCronograma()) {
            total += i.getNumeroAulas();
        }
        return Integer.toString(total);
    }

    public Integer totalAulas() {
        Integer total = 0;
        for (ItemCronograma i : this.planoEditavel.getItensCronograma()) {
            total += i.getNumeroAulas();
        }
        return total;
    }

    private List<UsuarioSigera> criarDestinatarios() {
        Integer idCurso = this.planoSelecionado.getTurma().getDisciplina().getCurso().getId();
        //enviar e-mail para destinatarios = coordenador de curso e coordenador Geral
        List<UsuarioSigera> destinatarios
                = AssociacaoPerfilCurso.obtenhaUsuariosDoPerfilCurso(EnumPerfil.COORDENADOR_CURSO.getCodigo(), idCurso, loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap());

        //coordenadores Geral só recebe email dos planos que não tem coordenador de curso 
        if (destinatarios.isEmpty()) {
            List<UsuarioSigera> coordenadoresGeral
                    = AssociacaoPerfilCurso.obtenhaUsuariosDoPerfilCurso(EnumPerfil.COORDENADOR_GERAL.getCodigo(), idCurso, loginBean.getUsuario().getUsuarioLdap().getBuscadorLdap());

            if (coordenadoresGeral != null) {
                for (UsuarioSigera user : coordenadoresGeral) {
                    if (!destinatarios.contains(user)) {
                        destinatarios.add(user);
                    }
                }
            }
        }
        return destinatarios;
    }
}
