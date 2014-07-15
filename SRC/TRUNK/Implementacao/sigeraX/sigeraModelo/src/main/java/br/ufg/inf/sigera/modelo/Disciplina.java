package br.ufg.inf.sigera.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "disciplina")
@TableGenerator(name = "tab_id_disciplina", initialValue = 1000000, allocationSize = 1)
public class Disciplina implements Serializable, Comparable<Disciplina> {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tab_id_disciplina")
    private int id;
    @Column(name = "carga_horaria_pratica")
    private int horaPratica;
    @Column(name = "carga_horaria_teorica")
    private int horaTeorica;
    @Column(name = "ementa")
    private String ementa;
    @Column(name = "nome")
    private String nome;
    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
    @Column(name = "bibliografia_basica")
    private String bibliografiaBasica;
    @Column(name = "bibliografia_complementar")
    private String bibliografiaComplementar;
    @Column(name = "objetivo_geral")
    private String objetivoGeral;

    public Disciplina(Disciplina disciplinaCopiar) {
        if (disciplinaCopiar != null) {
            this.id = disciplinaCopiar.getId();
            this.horaPratica = disciplinaCopiar.getHoraPratica();
            this.horaTeorica = disciplinaCopiar.getHoraTeorica();
            this.ementa = disciplinaCopiar.getEmenta();
            this.nome = disciplinaCopiar.getNome();
            this.curso = disciplinaCopiar.getCurso();
            this.bibliografiaBasica = disciplinaCopiar.getBibliografiaBasica();
            this.bibliografiaComplementar = disciplinaCopiar.getBibliografiaComplementar();
            this.objetivoGeral = disciplinaCopiar.getObjetivoGeral();
        }
    }

    public Disciplina(int horaPratica, int horaTeorica, String ementa, String nome, Curso curso, String bibliografiaBasica, String bibliografiaComplementar, String objetivoGeral) {
        this.horaPratica = horaPratica;
        this.horaTeorica = horaTeorica;
        this.ementa = ementa;
        this.nome = nome;
        this.curso = curso;
        this.bibliografiaBasica = bibliografiaBasica;
        this.bibliografiaComplementar = bibliografiaComplementar;
        this.objetivoGeral = objetivoGeral;
    }

    public Disciplina() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHoraPratica() {
        return horaPratica;
    }

    public void setHoraPratica(int horaPratica) {
        this.horaPratica = horaPratica;
    }

    public int getHoraTeorica() {
        return horaTeorica;
    }

    public void setHoraTeorica(int horaTeorica) {
        this.horaTeorica = horaTeorica;
    }

    public String getEmenta() {
        return ementa;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getBibliografiaBasica() {
        return bibliografiaBasica;
    }

    public void setBibliografiaBasica(String bibliografiaBasica) {
        this.bibliografiaBasica = bibliografiaBasica;
    }

    public String getBibliografiaComplementar() {
        return bibliografiaComplementar;
    }

    public void setBibliografiaComplementar(String bibliografiaComplementar) {
        this.bibliografiaComplementar = bibliografiaComplementar;
    }

    public String getObjetivoGeral() {
        return objetivoGeral;
    }

    public void setObjetivoGeral(String objetivoGeral) {
        this.objetivoGeral = objetivoGeral;
    }

    public static List<Disciplina> buscaTodosDisciplinas() {
        EntityManager em = criarManager();
        Query query = em.createQuery(" SELECT d FROM Disciplina d ORDER BY d.nome ");
        return query.getResultList();
    }

    public static List<Disciplina> buscaDisciplinasDoCurso(Integer codCurso) {
        EntityManager em = criarManager();
        StringBuilder consulta = new StringBuilder();
        int codCursoMestrado = 0;
        int codCursoDoutorado = 0;

        consulta.append(" SELECT d ");
        consulta.append(" FROM Disciplina as d  ");
        consulta.append(" WHERE ( d.curso.id = :codCurso ");
        if (Curso.obtenhaCurso(codCurso).getPrefixo().equalsIgnoreCase("POS")) {
            codCursoMestrado = Curso.obtenhaCursoPorPrefixo("msc").getId();
            codCursoDoutorado = Curso.obtenhaCursoPorPrefixo("dsc").getId();
            consulta.append(" OR d.curso.id = :codCursoMestrado ");
            consulta.append(" OR d.curso.id = :codCursoDoutorado ");
        }
        consulta.append(" ) ORDER BY d.nome");

        Query query = em.createQuery(consulta.toString());
        query.setParameter("codCurso", codCurso);
        if (Curso.obtenhaCurso(codCurso).getPrefixo().equalsIgnoreCase("POS")) {
            query.setParameter("codCursoMestrado", codCursoMestrado);
            query.setParameter("codCursoDoutorado", codCursoDoutorado);
        }
        return query.getResultList();
    }

    public static void salvar(Disciplina d) {
        EntityManager em = criarManager();
        em.getTransaction().begin();
        if (d.id == 0) {
            em.persist(d);
        } else {
            em.merge(d);
        }
        em.getTransaction().commit();
    }

    public static Boolean remover(Disciplina d) {
        EntityManager em = criarManager();
        em.getTransaction().begin();
        try {
            d = em.merge(d);
            em.remove(d);
            em.getTransaction().commit();
            return true;
        } catch (RollbackException e) {
            return false;
        }
    }

    public static Disciplina obtenhaDisciplina(int id) {
        EntityManager em = criarManager();
        return em.find(Disciplina.class, id);
    }

    private static EntityManager criarManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("br.ufg.inf.sigera");
        EntityManager em = emf.createEntityManager();
        return em;
    }

    public int compareTo(Disciplina d) {
        return this.getNome().compareTo(d.getNome());
    }
}
