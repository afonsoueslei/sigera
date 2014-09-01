package br.ufg.inf.sigera.modelo;

import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import br.ufg.inf.sigera.modelo.servico.Persistencia;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "unidade")
public class Unidade implements Serializable {

    @Id
    private int id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "versao")
    private int versao;
    @Transient
    private static final int CODIGO_UNIDADE_PADRAO = 7;        

    public Unidade() {
    }

    public Unidade(int id, String nome, int versao) {
        this.id = id;
        this.nome = nome;
        this.versao = versao;
    }

    public Unidade(Unidade unidadeCopiar) {
        if (unidadeCopiar != null) {
            this.id = unidadeCopiar.getId();
            this.nome = unidadeCopiar.getNome();
            this.versao = unidadeCopiar.getVersao();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getVersao() {
        return versao;
    }

    public void setVersao(int versao) {
        this.versao = versao;
    }

    public static List<Unidade> buscaTodasUnidades(BuscadorLdap buscadorLdap) {
        EntityManager em = Persistencia.obterManager();
        Query query = em.createQuery("select u from Unidade u");        
        return query.getResultList();
    }

    public static List<Unidade> buscaTodasUnidades() {
        EntityManager em = Persistencia.obterManager();
        Query query = em.createQuery("select u from Unidade u");        
        return query.getResultList();
    }

    public static Boolean salvar(Unidade u) {
        EntityManager em = Persistencia.obterManager();
        if (Persistencia.versaoValida(u)) {
            em.getTransaction().begin();
            u.setVersao(u.getVersao() + 1);
            if (u.id == 0) {
                em.persist(u);
            } else {
                em.merge(u);
            }
            em.getTransaction().commit();
            return true;
        }        
        return false;
    }

    public static Boolean remover(Unidade u) {
        EntityManager em = Persistencia.obterManager();
        em.getTransaction().begin();
        try {
            u = em.merge(u);
            em.remove(u);
            em.getTransaction().commit();
            return true;
        } catch (RollbackException e) {
            return false;
        }
    }

    public static Unidade obtenhaUnidadePadrao() {
        return obtenhaUnidade(CODIGO_UNIDADE_PADRAO);
    }

    public static Unidade obtenhaUnidade(int id) {  
        EntityManager em = Persistencia.obterManager();
        return em.find(Unidade.class, id);
    }

}
