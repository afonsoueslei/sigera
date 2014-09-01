package br.ufg.inf.sigera.modelo;

import br.ufg.inf.sigera.modelo.servico.Persistencia;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "configuracao")
public class Configuracao {

    @Id
    private int id;
    private static final int ID = 1;
    @Column(name = "data_inicial_ajustes_matricula")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataInicial;
    @Column(name = "data_final_ajustes_matricula")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataFinal;
    @Column(name = "data_inicial_semestre")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataInicialSemestre;
    @Column(name = "ano_corrente")
    private int anoCorrente;
    @Column(name = "semestre_corrente")
    private int semestreCorrente;
    @Column(name = "enviar_email")
    private boolean enviarEmail;

    public int getAnoCorrente() {
        return anoCorrente;
    }

    public void setAnoCorrente(int anoCorrente) {
        this.anoCorrente = anoCorrente;
    }

    public int getSemestreCorrente() {
        return semestreCorrente;
    }

    public void setSemestreCorrente(int semestreCorrente) {
        this.semestreCorrente = semestreCorrente;
    }

    public Date getDataInicialSemestre() {
        return dataInicialSemestre;
    }

    public void setDataInicialSemestre(Date dataInicialSemestre) {
        this.dataInicialSemestre = dataInicialSemestre;
    }

    public Date getDataFinalSemestre() {
        return dataFinalSemestre;
    }

    public void setDataFinalSemestre(Date dataFinalSemestre) {
        this.dataFinalSemestre = dataFinalSemestre;
    }

    public boolean isEnviarEmail() {
        return enviarEmail;
    }

    public void setEnviarEmail(boolean enviarEmail) {
        this.enviarEmail = enviarEmail;
    }
    @Column(name = "data_final_semestre")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataFinalSemestre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Configuracao() {
        this.id = ID;
    }

    public void atualizar(Date dataInicial, Date dataFinal,
            Date dataInicialSemestre, Date dataFinalSemestre,
            int anoCorrente, int semestreCorrente, boolean enviarEmail) {
        setDataInicial(dataInicial);
        setDataFinal(dataFinal);
        setDataInicialSemestre(dataInicialSemestre);
        setDataFinalSemestre(dataFinalSemestre);
        setAnoCorrente(anoCorrente);
        setSemestreCorrente(semestreCorrente);
        setEnviarEmail(enviarEmail);
        salvar();
    }

    public void salvar() {
        EntityManager em = Persistencia.obterManager();
        em.getTransaction().begin();
        em.merge(this);        
        em.getTransaction().commit();
    }

    public static Configuracao carregar() {
        EntityManager em = Persistencia.obterManager();
        return em.find(Configuracao.class, ID);
    }

    public boolean permiteRequererAcrescimoOuCancelamentoDisciplinas() {
        return conferirDatas(dataInicial, dataFinal);
    }

    public boolean periodoPermiteAbrirRequerimentos() {
        return conferirDatas(dataInicialSemestre, dataFinalSemestre);
    }

    private boolean conferirDatas(Date dataInicio, Date dataFim) {
        if (dataInicio == null || dataFim == null) {
            return false;
        }
        // Comparando datas de inicio e fim, considerando apenas dia, mês e ano.
        Date dataAtual = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        boolean dataAtualIgualDataInicial = sdf.format(dataAtual).equals(sdf.format(dataInicio));
        boolean dataAtualIgualDataFinal = sdf.format(dataAtual).equals(sdf.format(dataFim));
        if ((dataAtualIgualDataInicial || dataAtual.after(dataInicio))
                && (dataAtualIgualDataFinal || dataAtual.before(dataFim))) {
            return true;
        }
        return false;
    }

}
