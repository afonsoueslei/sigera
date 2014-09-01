package br.ufg.inf.sigera.modelo;

import br.ufg.inf.sigera.modelo.servico.Persistencia;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "curso")
@TableGenerator(name = "tab_id_curso", initialValue = 1000000, allocationSize = 1)
public class Curso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tab_id_curso")
    private int id;

    @Column(name = "prefixo")
    private String prefixo;
    @Column(name = "cod_matriz_curricular")
    private String codMatriz;
    @Column(name = "nome")
    private String nome;
    @ManyToOne
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;
    @Column(name = "versao")
    private int versao;
       

    public Curso() {
    }

    public Curso(Curso cursoCopiar) {
        if (cursoCopiar != null) {
            this.id = cursoCopiar.id;
            this.prefixo = cursoCopiar.prefixo;
            this.codMatriz = cursoCopiar.codMatriz;
            this.nome = cursoCopiar.nome;
            this.unidade = cursoCopiar.unidade;
            this.versao = cursoCopiar.versao;
        } else {
            this.unidade = Unidade.obtenhaUnidadePadrao();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }

    public String getCodMatriz() {
        return codMatriz;
    }

    public void setCodMatriz(String cod_matriz) {
        this.codMatriz = cod_matriz;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public int getVersao() {
        return versao;
    }

    public void setVersao(int versao) {
        this.versao = versao;
    }

    public static List<Curso> buscaTodosCursos() {
        EntityManager em = Persistencia.obterManager();
        Query query = em.createQuery("select c from Curso c");
        List<Curso> cursos = query.getResultList();
        return cursos;
    }

    public static Boolean salvar(Curso c) {
        EntityManager em = Persistencia.obterManager();
        if (Persistencia.versaoValida(c)) {            
            em.getTransaction().begin();
            c.setVersao(c.getVersao()+1);
            if (c.id == 0) {
                em.persist(c);
            } else {
                em.merge(c);
            }            
            em.getTransaction().commit();
            return true;
        }
        return false;
    }

    public static Boolean remover(Curso c) {
        EntityManager em = Persistencia.obterManager();
        em.getTransaction().begin();
        try {
            c = em.merge(c);
            em.remove(c);
            em.getTransaction().commit();
            return true;
        } catch (RollbackException e) {
            return false;
        }
    }

    public static Curso obtenhaCursoPorPrefixo(String prefixo) {
        if (prefixo != null) {
            EntityManager em = Persistencia.obterManager();
            Query query = em.createQuery("SELECT c FROM Curso c WHERE c.prefixo = :prefixo");
            query.setParameter("prefixo", prefixo);
            return (Curso) query.getSingleResult();
        }
        return null;
    }

    public static Curso obtenhaCurso(int id) {
        EntityManager em = Persistencia.obterManager();
        return em.find(Curso.class, id);
    }
}
