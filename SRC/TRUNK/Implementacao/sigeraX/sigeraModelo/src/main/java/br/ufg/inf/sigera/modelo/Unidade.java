package br.ufg.inf.sigera.modelo;

import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Persistence;
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
    @Transient
    private static final int CODIGO_UNIDADE_PADRAO = 7;

    public Unidade() {
    }

    public Unidade(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Unidade(Unidade unidadeCopiar) {
        if (unidadeCopiar != null) {
            this.id = unidadeCopiar.getId();
            this.nome = unidadeCopiar.getNome();
        }
    }

    public static List<Unidade> buscaTodasUnidades(BuscadorLdap buscadorLdap) {
        EntityManager em = criarManager();
        Query query = em.createQuery("select u from Unidade u");
        List<Unidade> unidades = query.getResultList();
        return unidades;
    }

    public static List<Unidade> buscaTodasUnidades() {
        EntityManager em = criarManager();
        Query query = em.createQuery("select u from Unidade u");
        List<Unidade> unidades = query.getResultList();
        return unidades;
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

    public static void salvar(Unidade u) {
        EntityManager em = criarManager();
        em.getTransaction().begin();
        if (u.id == 0) {
            em.persist(u);
        } else {
            em.merge(u);
        }
        em.getTransaction().commit();
    }

    public static Boolean remover(Unidade u) {
        EntityManager em = criarManager();
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
        EntityManager em = criarManager();

        return em.find(Unidade.class, id);
    }

    private static EntityManager criarManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("br.ufg.inf.sigera");
        EntityManager em = emf.createEntityManager();
        return em;
    }
}
