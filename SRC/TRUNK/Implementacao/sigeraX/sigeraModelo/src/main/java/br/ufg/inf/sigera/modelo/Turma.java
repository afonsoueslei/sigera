package br.ufg.inf.sigera.modelo;

import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.RollbackException;

@Entity
@Table(name = "turma")
@TableGenerator(name = "tab_id_turma", initialValue = 1000000, allocationSize = 1)
public class Turma implements Serializable, Comparable<Turma> {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tab_id_turma")
    private int id;

    @Column(name = "ano")
    private int ano;

    @Column(name = "nome")
    private String nome;

    @Column(name = "semestre")
    private int semestre;

    @ManyToOne
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    public Turma() {
    }

    public Turma(String nome, int semestre, Disciplina disciplina, Professor professor) {
        this.nome = nome;
        this.semestre = semestre;
        this.professor = professor;
    }

    public Turma(Turma turmaCopiar) {
        if (turmaCopiar != null) {
            this.id = turmaCopiar.getId();
            this.ano = turmaCopiar.getAno();
            this.semestre = turmaCopiar.getSemestre();
            this.disciplina = turmaCopiar.getDisciplina();
            this.nome = turmaCopiar.getNome();
            this.professor = turmaCopiar.getProfessor();
        }
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public static void salvar(Turma t) {
        EntityManager em = criarManager();
        em.getTransaction().begin();
        if (t.id == 0) {
            em.persist(t);
        } else {
            em.merge(t);
        }
        em.getTransaction().commit();
    }

    public static List<Turma> buscaTodasTurmas(BuscadorLdap buscadorLdap) {
        EntityManager em = criarManager();
        Query query = em.createQuery("select t from Turma t ORDER BY t.ano DESC, t.semestre DESC, t.disciplina.nome ASC");
        List<Turma> turmas = query.getResultList();

        for (Turma t : turmas) {
            Professor p = t.getProfessor();
            t.getProfessor().getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(p.getUsuario().getId()));
        }

        return turmas;
    }

    public static Boolean remover(Turma t) {
        EntityManager em = criarManager();
        em.getTransaction().begin();
        try {
            t = em.merge(t);
            em.remove(t);
            em.getTransaction().commit();
            return true;
        } catch (RollbackException e) {
            return false;
        }
    }

    private static EntityManager criarManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("br.ufg.inf.sigera");
        EntityManager em = emf.createEntityManager();
        return em;
    }

    public static Turma obtenhaTurma(int id) {
        EntityManager em = criarManager();
        return em.find(Turma.class, id);
    }

    public static Turma obtenhaTurmaDisciplina(int id, BuscadorLdap buscadorLdap) {

        EntityManager em = criarManager();
        Query query = em.createQuery(" SELECT t FROM Turma t WHERE t.disciplina.id = :idDisciplina");
        query.setParameter("idDisciplina", id);

        List<Turma> turmas = query.getResultList();

        for (Turma t : turmas) {
            UsuarioSigera professor = t.getProfessor().getUsuario();
            professor.setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(professor.getId()));
        }
        //returna a turma mais recente
        return turmas.get(turmas.size() - 1);
    }

    public static List<Turma> buscaTurmas(int anoCorrente, int semestreCorrente, BuscadorLdap buscadorLdap, int codCurso) {
        EntityManager em = criarManager();
        StringBuilder consulta = new StringBuilder();
        int codCursoMestrado = 0;
        int codCursoDoutorado = 0;

        consulta.append(" SELECT t ");
        consulta.append(" FROM Turma as t  ");        
        consulta.append(" WHERE ( ( t.ano = :anoCorrente AND t.semestre >= :semestreCorrente ) ");
        consulta.append(" OR t.ano > :anoCorrente )");        
        
        if (codCurso != 0) {
            consulta.append(" AND ( t.disciplina.curso.id = :codCurso ");

            if (Curso.obtenhaCurso(codCurso).getPrefixo().equalsIgnoreCase("POS")) {
                codCursoMestrado = Curso.obtenhaCursoPorPrefixo("msc").getId();
                codCursoDoutorado = Curso.obtenhaCursoPorPrefixo("dsc").getId();
                consulta.append(" OR t.disciplina.curso.id = :codCursoMestrado ");
                consulta.append(" OR t.disciplina.curso.id = :codCursoDoutorado ");
            }
            consulta.append(" )");
        }
        consulta.append(" ORDER BY t.disciplina.nome ASC");

        Query query = em.createQuery(consulta.toString());
        query.setParameter("anoCorrente", anoCorrente);
        query.setParameter("semestreCorrente", semestreCorrente);

        if (codCurso != 0) {
            query.setParameter("codCurso", codCurso);
            if (Curso.obtenhaCurso(codCurso).getPrefixo().equalsIgnoreCase("POS")) {
                query.setParameter("codCursoMestrado", codCursoMestrado);
                query.setParameter("codCursoDoutorado", codCursoDoutorado);
            }
        }

        List<Turma> turmas = query.getResultList();

        for (Turma t : turmas) {
            UsuarioSigera professor = t.getProfessor().getUsuario();
            professor.setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(professor.getId()));
        }

        return turmas;
    }

    public int compareTo(Turma t) {
        return this.disciplina.compareTo(t.disciplina);
    }

    @Override
    public String toString() {
        return String.format("%s - %s - %s - Turma: [ %s ]",
                this.getDisciplina().getNome(),
                this.getDisciplina().getCurso().getNome(),
                this.getProfessor().getNome(),
                this.getNome());
    }
}
