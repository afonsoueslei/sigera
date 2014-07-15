package br.ufg.inf.sigera.modelo;

import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NoResultException;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "professor")
@TableGenerator(name = "tab_id_professor", initialValue = 1000000, allocationSize = 1)
public class Professor implements Serializable, Comparable<Professor> {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tab_id_professor")
    private int id;
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "usuario_id", nullable = true)
    private UsuarioSigera usuario;

    public Professor() {
    }

    public Professor(UsuarioSigera usuario) {
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UsuarioSigera getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioSigera usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return this.usuario.getUsuarioLdap().getCn();
    }

    public static void salvar(Professor p) {
        EntityManager em = criarManager();
        em.getTransaction().begin();
        if (p.id == 0) {
            em.persist(p);
        } else {
            em.merge(p);
        }
        em.getTransaction().commit();
    }

    public static Professor obtenhaProfessor(int id) {
        EntityManager em = criarManager();
        return em.find(Professor.class, id);
    }

    public static Professor obtenhaProfessor(BuscadorLdap buscadorLdap, int id) {
        EntityManager em = criarManager();
        Professor prof = em.find(Professor.class, id);
        if (prof != null) {
            prof.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(prof.getUsuario().getId()));
        }

        return prof;
    }

    public static Professor obtenhaProfessorPorIdUsuario(int idUsuario) {
        try {
            EntityManager em = criarManager();
            String consulta = " SELECT p FROM Professor as p WHERE p.usuario.id = :idUsuario ";
            Query query = em.createQuery(consulta);
            query.setParameter("idUsuario", idUsuario);

            return (Professor) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static List<Professor> buscaTodosProfessores(BuscadorLdap buscadorLdap) {
        EntityManager em = criarManager();
        Query query = em.createQuery("SELECT p FROM Professor p");
        List<Professor> profs = query.getResultList();

        for (Professor p : profs) {
            p.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(p.getUsuario().getId()));
        }

        Collections.sort(profs);

        return profs;
    }

    private static EntityManager criarManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("br.ufg.inf.sigera");
        EntityManager em = emf.createEntityManager();
        return em;
    }

    public int compareTo(Professor p) {
        return this.getNome().compareTo(p.getNome());
    }
}
