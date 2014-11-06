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
    @Column(name = "versao")
    private int versao;

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
            this.versao = disciplinaCopiar.getVersao();
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

    public int getVersao() {
        return versao;
    }

    public void setVersao(int versao) {
        this.versao = versao;
    }

    public static List<Disciplina> buscaTodosDisciplinas() {
        EntityManager em = Persistencia.obterManager();
        Query query = em.createQuery(" SELECT d FROM Disciplina d ORDER BY d.nome ");
        return query.getResultList();
    }

    public static List<Disciplina> buscaDisciplinasDoCurso(Integer codCurso) {
        EntityManager em = Persistencia.obterManager();
        StringBuilder consulta = new StringBuilder();
        int codCursoMestrado = 0;
        int codCursoDoutorado = 0;
        int codCursoPos = 0;

        consulta.append(" SELECT d ");
        consulta.append(" FROM Disciplina as d  ");
        consulta.append(" WHERE ( d.curso.id = :codCurso ");
        if (Curso.obtenhaCurso(codCurso).getPrefixo().equalsIgnoreCase("msc") || Curso.obtenhaCurso(codCurso).getPrefixo().equalsIgnoreCase("dsc")) {
            consulta.append(" OR d.curso.id = :codPos ");
            codCursoPos = Curso.obtenhaCursoPorPrefixo("pos").getId();
        }
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
        if (Curso.obtenhaCurso(codCurso).getPrefixo().equalsIgnoreCase("msc") || Curso.obtenhaCurso(codCurso).getPrefixo().equalsIgnoreCase("dsc")) {
            query.setParameter("codPos", codCursoPos);
        }
        return query.getResultList();
    }

    public static boolean salvar(Disciplina d) {
        EntityManager em = Persistencia.obterManager();
        if (Persistencia.versaoValida(d)) {
            em.getTransaction().begin();
            d.setVersao(d.getVersao() + 1);
            if (d.id == 0) {
                em.persist(d);
            } else {
                em.merge(d);
            }
            em.getTransaction().commit();
            return true;
        }
        return false;
    }

    public static Boolean remover(Disciplina d) {
        EntityManager em = Persistencia.obterManager();
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
        EntityManager em = Persistencia.obterManager();
        return em.find(Disciplina.class, id);
    }

    public int compareTo(Disciplina d) {
        return this.getNome().compareTo(d.getNome());
    }
}
