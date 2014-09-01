package br.ufg.inf.sigera.modelo;

import br.ufg.inf.sigera.modelo.ldap.EnumGrupo;
import br.ufg.inf.sigera.modelo.perfil.EnumPerfil;
import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.ldap.UsuarioLdap;
import br.ufg.inf.sigera.modelo.perfil.GerenciadorPerfil;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoPlano;
import br.ufg.inf.sigera.modelo.servico.Persistencia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import org.eclipse.persistence.annotations.PrivateOwned;

@Entity(name = "UsuarioSigera")
@Table(name = "usuario")
public class UsuarioSigera implements Serializable, Comparable<UsuarioSigera> {

    @Id
    private int id;
    @Column(name = "telefone_celular")
    private String telefoneCelular;
    @Column(name = "telefone_residencial")
    private String telefoneResidencial;
    @Column(name = "telefone_comercial")
    private String telefoneComercial;
    @Column(name = "primeiro_acesso")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date primeiroAcesso;
    @Column(name = "ultimo_acesso")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date ultimoAcesso;
    @Column(name = "chave_ativacao")
    private String chaveAtivacao;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    @PrivateOwned
    private Collection<AssociacaoPerfilCurso> perfis;
    @Transient
    private AssociacaoPerfilCurso perfilAtual;
    @Transient
    private UsuarioLdap usuarioLdap;

    public UsuarioSigera() {
    }   

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public String getTelefoneResidencial() {
        return telefoneResidencial;
    }

    public void setTelefoneResidencial(String telefoneResidencial) {
        this.telefoneResidencial = telefoneResidencial;
    }

    public AssociacaoPerfilCurso getPerfilAtual() {
        return perfilAtual;
    }

    public void setPerfilAtual(AssociacaoPerfilCurso perfilAtual) {
        this.perfilAtual = perfilAtual;
    }

    public Collection<AssociacaoPerfilCurso> getPerfis() {
        if (perfis != null) {
            Collections.sort((List<AssociacaoPerfilCurso>) perfis);
        }
        return perfis;
    }

    public void setPerfis(Collection<AssociacaoPerfilCurso> perfis) {
        this.perfis = perfis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelefoneComercial() {
        return telefoneComercial;
    }

    public void setTelefoneComercial(String telefone) {
        this.telefoneComercial = telefone;
    }

    public Date getPrimeiroAcesso() {
        return primeiroAcesso;
    }

    public void setPrimeiroAcesso(Date primeiroAcesso) {
        this.primeiroAcesso = primeiroAcesso;
    }

    public Date getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(Date ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    public UsuarioLdap getUsuarioLdap() {
        return usuarioLdap;
    }

    public String getNome() {
        return this.usuarioLdap.getCn();
    }

    public String getMatricula() {
        return this.usuarioLdap.getMatricula();
    }

    public void setUsuarioLdap(UsuarioLdap usuarioLdap) {
        this.usuarioLdap = usuarioLdap;
    }

    public String getChaveAtivacao() {
        return chaveAtivacao;
    }

    public void setChaveAtivacao(String chaveAtivacao) {
        this.chaveAtivacao = chaveAtivacao;
    }

    public void atualizar(String telefoneCelular, String telefoneResidencial, String telefoneComercial) {
        setTelefoneCelular(telefoneCelular);
        setTelefoneResidencial(telefoneResidencial);
        setTelefoneComercial(telefoneComercial);
        setChaveAtivacao(chaveAtivacao);

        //Ao atualizar um usuário que é aluno e que ainda não foi setado esse perfil (estudante) para ele
        if (this.getUsuarioLdap().getGrupo().equals(EnumGrupo.ALUNO) && this.getPerfis().isEmpty()) {
            Collection<AssociacaoPerfilCurso> meusPerfis = new ArrayList<AssociacaoPerfilCurso>();
            AssociacaoPerfilCurso perfilEstudante = GerenciadorPerfil.criePerfilAluno(this);
            meusPerfis.add(perfilEstudante);
            this.setPerfis(meusPerfis);
        }
        salvar();
    }

    public void salvar() {
        EntityManager em = Persistencia.obterManager();
        em.getTransaction().begin();

        tratePerfilProfessor(em);

        em.merge(this);

        em.getTransaction().commit();
        
        em.close();
        
    }

    private void tratePerfilProfessor(EntityManager em) {
        boolean possuiPerfilProfessor = false;

        if (this.perfis != null && this.perfis.size() > 0) {

            for (AssociacaoPerfilCurso infoPerfil : this.perfis) {
                if (infoPerfil.getPerfil().getId() == EnumPerfil.PROFESSOR.getCodigo()) {
                    possuiPerfilProfessor = true;
                    break;
                }
            }

        }

        if (possuiPerfilProfessor) {
            Professor prof = Professor.obtenhaProfessorPorIdUsuario(this.id);

            if (prof == null) {
                prof = new Professor(this);
                em.merge(prof);
            }
        }
    }

    public boolean temAtribuicaoPerfil() {
        return (this.perfis != null && this.perfis.size() > 0);
    }

    public List<Requerimento> obtenhaRequerimentos() {
        return this.perfilAtual.getPerfil().obtenhaRequerimentos(this);
    }

    public List<RequerimentoPlano> obtenhaRequerimentosPlanos() {
        return this.perfilAtual.getPerfil().obtenhaRequerimentosPlanos(this);
    }

    public static UsuarioSigera obtenhaUsuarioSigera(int uidNumber) {
        EntityManager em = Persistencia.obterManager();
        return em.find(UsuarioSigera.class, uidNumber);
    }

    public int compareTo(UsuarioSigera u) {
        return this.getNome().compareTo(u.getNome());
    }
}
