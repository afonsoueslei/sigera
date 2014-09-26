package br.ufg.inf.sigera.modelo;

import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import br.ufg.inf.sigera.modelo.ldap.UsuarioLdap;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoPlano;
import br.ufg.inf.sigera.modelo.servico.Conexoes;
import br.ufg.inf.sigera.modelo.servico.Persistencia;
import com.mysql.jdbc.Connection;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NoResultException;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;
import org.eclipse.persistence.annotations.PrivateOwned;

@Entity
@Table(name = "plano")
@TableGenerator(name = "tab_id_plano", initialValue = 1000000, allocationSize = 1)
public class Plano implements Serializable, Comparable<Plano> {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tab_id_plano")
    private int id;
    @OneToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;
    @Column(name = "criterioavaliacao")
    private String criterioAvaliacao;
    @Column(name = "datarealizacaoprovas")
    private String dataRealizacaoProvas;
    @Column(name = "objetivosespecificos")
    private String objetivosEspecificos;
    @Column(name = "relacaooutrasdisciplinas")
    private String relacaoOutrasDisciplinas;
    @Column(name = "bibliografiasugerida")
    private String bibliografiaSugerida;
    @Column(name = "programa")
    private String programa;
    @OneToMany(mappedBy = "plano", cascade = CascadeType.ALL)
    @PrivateOwned
    private Collection<ItemCronograma> itensCronograma;

    public Plano() {
    }

    public Plano(Turma t) {
        this.turma = t;
    }

    public Plano(Plano planoCopiar) {
        if (planoCopiar != null) {
            this.turma = planoCopiar.getTurma();
            this.criterioAvaliacao = planoCopiar.getCriterioAvaliacao();
            this.dataRealizacaoProvas = planoCopiar.getDataRealizacaoProvas();
            this.objetivosEspecificos = planoCopiar.getObjetivosEspecificos();
            this.relacaoOutrasDisciplinas = planoCopiar.getRelacaoOutrasDisciplinas();
            this.bibliografiaSugerida = planoCopiar.getBibliografiaSugerida();
            this.setItensCronograma(planoCopiar.getItensCronograma());
            this.programa = planoCopiar.getPrograma();
        }
    }

    public Plano(String criterioAvaliacao, String dataRealizacaoProvas, String objetivosEspecificos, String relacaoOutrasDisciplinas, Turma turma, String bibliografiaSugerida, Collection<ItemCronograma> itensCronograma, String programa) {
        this.criterioAvaliacao = criterioAvaliacao;
        this.dataRealizacaoProvas = dataRealizacaoProvas;
        this.objetivosEspecificos = objetivosEspecificos;
        this.relacaoOutrasDisciplinas = relacaoOutrasDisciplinas;
        this.turma = turma;
        this.bibliografiaSugerida = bibliografiaSugerida;
        this.itensCronograma = itensCronograma;
        this.programa = programa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCriterioAvaliacao() {
        return criterioAvaliacao;
    }

    public void setCriterioAvaliacao(String criterioAvaliacao) {
        this.criterioAvaliacao = criterioAvaliacao;
    }

    public String getDataRealizacaoProvas() {
        return dataRealizacaoProvas;
    }

    public void setDataRealizacaoProvas(String dataRealizacaoProvas) {
        this.dataRealizacaoProvas = dataRealizacaoProvas;
    }

    public String getObjetivosEspecificos() {
        return objetivosEspecificos;
    }

    public void setObjetivosEspecificos(String objetivosEspecificos) {
        this.objetivosEspecificos = objetivosEspecificos;
    }

    public String getRelacaoOutrasDisciplinas() {
        return relacaoOutrasDisciplinas;
    }

    public void setRelacaoOutrasDisciplinas(String relacaoOutrasDisciplinas) {
        this.relacaoOutrasDisciplinas = relacaoOutrasDisciplinas;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public String getBibliografiaSugerida() {
        return bibliografiaSugerida;
    }

    public void setBibliografiaSugerida(String bibliografiaSugerida) {
        this.bibliografiaSugerida = bibliografiaSugerida;
    }

    public Collection<ItemCronograma> getItensCronograma() {
        return itensCronograma;
    }

    public void setItensCronograma(Collection<ItemCronograma> itensCronograma) {
        for (ItemCronograma i : itensCronograma) {
            i.setPlano(this);
        }
        this.itensCronograma = itensCronograma;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public static List<Turma> buscaTurmasSemPlano(int ano, int semestre, BuscadorLdap buscadorLdap) {
        EntityManager em = Persistencia.obterManager();
        StringBuilder consulta = new StringBuilder();

        consulta.append(" SELECT t ");
        consulta.append(" FROM Turma as t  ");        
        consulta.append(" WHERE ( ( t.ano = :ano AND t.semestre >= :semestre ) ");
        consulta.append(" OR t.ano > :ano )");                
        consulta.append(" AND t.id not in");
        consulta.append(" ( SELECT p.turma.id ");
        consulta.append(" FROM Plano as p ) ORDER BY t.disciplina.nome ASC");

        Query query = em.createQuery(consulta.toString());
        query.setParameter("ano", ano);
        query.setParameter("semestre", semestre);

        List<Turma> turmas = query.getResultList();

        for (Turma t : turmas) {
            UsuarioSigera professor = t.getProfessor().getUsuario();
            professor.setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(professor.getId()));
        }

        return turmas;
    }

    public static Plano buscarPlanoExistenteEmSemestresAnteriores(Turma t) {
        EntityManager em = Persistencia.obterManager();
        StringBuilder consulta = new StringBuilder();
        consulta.append(" SELECT p ");
        consulta.append(" FROM Plano as p  ");
        consulta.append(" WHERE ( p.turma.ano < :ano ");
        consulta.append(" OR ( p.turma.ano = :ano ");
        consulta.append(" AND p.turma.semestre < :semestre)) ");
        consulta.append(" AND p.turma.disciplina.nome = :disciplina ");
        consulta.append(" AND p.turma.disciplina.curso.id = :curso ");

        Query query = em.createQuery(consulta.toString());
        query.setParameter("ano", t.getAno());
        query.setParameter("semestre", t.getSemestre());
        query.setParameter("disciplina", t.getDisciplina().getNome());
        query.setParameter("curso", t.getDisciplina().getCurso().getId());

        List<Plano> planos = query.getResultList();

        if (planos.size() > 0) {
            for (Plano p : planos) {
                if (p.turma.getNome().equalsIgnoreCase(t.getNome())) {
                    p.setTurma(t);
                    //apagar os indices antigos de plano e de ItemCronograma copiados dos planos que já existiam 
                    for (ItemCronograma item : p.itensCronograma) {
                        item.getPlano().setId(0);
                        item.setId(0);
                    }

                    return new Plano(p);
                }
            }
            //apagar os indices antigos de plano e de ItemCronograma copiados dos planos que já existiam
            for (ItemCronograma item : planos.get(0).itensCronograma) {
                item.getPlano().setId(0);
                item.setId(0);
            }
            planos.get(0).setTurma(t);
            return new Plano(planos.get(0));
        } else {
            return new Plano(t);
        }
    }

    public static List<Plano> buscaTodosPlanos() {
        EntityManager em = Persistencia.obterManager();
        Query query = em.createQuery(" SELECT p FROM Plano p");
        return query.getResultList();
    }

    public static RequerimentoPlano buscarRequerimentoDessePlano(BuscadorLdap buscadorLdap, Plano p) {
        EntityManager em = Persistencia.obterManager();
        Query query = em.createQuery(" SELECT r FROM RequerimentoPlano r WHERE r.plano.id = :planoId");
        query.setParameter("planoId", p.getId());

        RequerimentoPlano requerimentoDoPlano = (RequerimentoPlano) query.getSingleResult();

        if (buscadorLdap != null) {
            UsuarioLdap usuarioLdap = buscadorLdap.obtenhaUsuarioLdap(requerimentoDoPlano.getUsuario().getId());
            UsuarioLdap professorTurma = buscadorLdap.obtenhaUsuarioLdap(requerimentoDoPlano.getPlano().getTurma().getProfessor().getUsuario().getId());

            requerimentoDoPlano.getUsuario().setUsuarioLdap(usuarioLdap);
            requerimentoDoPlano.getPlano().getTurma().getProfessor().getUsuario().setUsuarioLdap(professorTurma);
        }

        return requerimentoDoPlano;
    }

    public static void salvar(Plano p) {
        EntityManager em = Persistencia.obterManager();
        em.getTransaction().begin();
        if (p.id == 0) {
            em.persist(p);
        } else {
            em.merge(p);
        }
        em.getTransaction().commit();
    }

    public static Plano obtenhaPlano(int id) {
        EntityManager em = Persistencia.obterManager();
        return em.find(Plano.class, id);
    }

    public static Plano obtenhaPlanoTurma(Turma turma) {
        EntityManager em = Persistencia.obterManager();
        Query query = em.createQuery(" SELECT p FROM Plano p WHERE p.turma.id = :idTurma");
        query.setParameter("idTurma", turma.getId());
        Plano p;
        try {
            p = (Plano) query.getSingleResult();
            p.setTurma(turma);
            return p;
        } catch (NoResultException nre) {
            return null;
        }
    }

    public int compareTo(Plano p) {
        return this.getTurma().compareTo(p.getTurma());
    }

    public boolean temCampoVazio() {
        if (this.getObjetivosEspecificos() != null && !"".equalsIgnoreCase(this.getObjetivosEspecificos())
                && this.getPrograma() != null && !"".equalsIgnoreCase(this.getPrograma())
                && this.getRelacaoOutrasDisciplinas() != null && !"".equalsIgnoreCase(this.getRelacaoOutrasDisciplinas())
                && this.getCriterioAvaliacao() != null && !"".equalsIgnoreCase(this.getCriterioAvaliacao())
                && this.getDataRealizacaoProvas() != null && !"".equalsIgnoreCase(this.getDataRealizacaoProvas())
                && this.getBibliografiaSugerida() != null && !"".equalsIgnoreCase(this.getBibliografiaSugerida())
                && this.getItensCronograma().size() > 0) {         
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        //verifica se algum campo esta vazio        
        if (!((Plano) obj).temCampoVazio()) {
            //verifica se houve alguma modificação
            if ((obj instanceof Plano)
                    && ((Plano) obj).getObjetivosEspecificos().equalsIgnoreCase(this.getObjetivosEspecificos())
                    && ((Plano) obj).getPrograma().equalsIgnoreCase(this.getPrograma())
                    && ((Plano) obj).getRelacaoOutrasDisciplinas().equalsIgnoreCase(this.getRelacaoOutrasDisciplinas())
                    && ((Plano) obj).getCriterioAvaliacao().equalsIgnoreCase(this.getCriterioAvaliacao())
                    && ((Plano) obj).getDataRealizacaoProvas().equalsIgnoreCase(this.getDataRealizacaoProvas())
                    && ((Plano) obj).getBibliografiaSugerida().equalsIgnoreCase(this.getBibliografiaSugerida())
                    && ((Plano) obj).getItensCronograma().size() == this.getItensCronograma().size()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.id;
        hash = 59 * hash + (this.turma != null ? this.turma.hashCode() : 0);
        hash = 59 * hash + (this.criterioAvaliacao != null ? this.criterioAvaliacao.hashCode() : 0);
        hash = 59 * hash + (this.dataRealizacaoProvas != null ? this.dataRealizacaoProvas.hashCode() : 0);
        hash = 59 * hash + (this.objetivosEspecificos != null ? this.objetivosEspecificos.hashCode() : 0);
        hash = 59 * hash + (this.relacaoOutrasDisciplinas != null ? this.relacaoOutrasDisciplinas.hashCode() : 0);
        hash = 59 * hash + (this.bibliografiaSugerida != null ? this.bibliografiaSugerida.hashCode() : 0);
        hash = 59 * hash + (this.programa != null ? this.programa.hashCode() : 0);
        hash = 59 * hash + (this.itensCronograma != null ? this.itensCronograma.hashCode() : 0);
        return hash;
    }

    public void imprimir() throws IOException {
        Integer planoId = this.id;
        String nomeProfessor = this.getTurma().getProfessor().getNome();

        final Integer DEFAULT_BUFFER_SIZE = 1000;
        FacesContext contexto = FacesContext.getCurrentInstance();
        ExternalContext contextoExterno = contexto.getExternalContext();
        HttpServletResponse resp = (HttpServletResponse) contextoExterno.getResponse();

        ServletContext servletContext = (ServletContext) contextoExterno.getContext();
        String realPath = servletContext.getRealPath("") + "/resources/relatorios";
        String dataHora = Long.toString(new Date().getTime());

        String caminhoArquivoPDF = Conexoes.getPASTA_PLANOS_DE_AULA() + dataHora+"-PLA-"+planoId + "-" + nomeProfessor.trim() + ".pdf";

        File arquivoPdf = new File(caminhoArquivoPDF);
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Conexoes.lerParametros();
            Connection conn = (Connection) DriverManager.getConnection(Conexoes.getCONEXAO_BANCO(), Conexoes.getUSUARIO_BANCO(), Conexoes.getCHAVE_BANCO());

            File arquivo = new File(realPath + "/planoAula.jasper");
            String rep0 = realPath + "/PlanoAula_subreport0.jasper";
            String rep1 = realPath + "/PlanoAula_subreport1.jasper";
            String rep2 = realPath + "/PlanoAula_subreport2.jasper";
            String rep3 = realPath + "/PlanoAula_subreport3.jasper";
            String caminhoImagem = realPath + "/brasao republica.jpg";

            JasperReport relatorioJasper = (JasperReport) JRLoader.loadObject(arquivo);
            Map parametros = new HashMap();
            parametros.put("planoId", planoId);
            parametros.put("nomeProfessor", nomeProfessor);
            parametros.put("rep0", rep0);
            parametros.put("rep1", rep1);
            parametros.put("rep2", rep2);
            parametros.put("rep3", rep3);
            parametros.put("caminhoImagem", caminhoImagem);

            JasperPrint jasperPrint = JasperFillManager.fillReport(relatorioJasper, parametros, conn);

            JasperExportManager.exportReportToPdfFile(jasperPrint, caminhoArquivoPDF);

            byte[] bytes = JasperRunManager.runReportToPdf(relatorioJasper, parametros);
            resp.reset();
            resp.setContentType("application/pdf");
            resp.setContentLength(bytes.length);
            resp.setHeader("Content-Disposition", "inline; filename=\"" + arquivoPdf.getName() + "\"");
            ServletOutputStream ouputStream = resp.getOutputStream();
            ouputStream.write(bytes, 0, bytes.length);
            ouputStream.flush();
            ouputStream.close();

            // Informar ao  JSF que não precisa lidar com a resposta, caso contrário, terá a seguinte exceção nos logs:
            // java.lang.IllegalStateException: Cannot forward after response has been committed.
            contexto.responseComplete();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Plano.class.getName()).log(Level.WARNING, "Impossível conectar ao driver JDBC do Mysql!", ex);
        }catch(SQLException sqlex){
            Logger.getLogger(Plano.class.getName()).log(Level.WARNING, "Impossível obter coneção como banco!", sqlex);            
        }catch(JRException jrex){
            Logger.getLogger(Plano.class.getName()).log(Level.WARNING, "Erro ao criar objeto JasperReport", jrex);
        }
        
    }
       
}
