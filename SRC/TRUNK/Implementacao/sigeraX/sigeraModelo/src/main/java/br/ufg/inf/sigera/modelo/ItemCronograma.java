package br.ufg.inf.sigera.modelo;

import br.ufg.inf.sigera.modelo.servico.Persistencia;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.RollbackException;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

@Entity
@Table(name = "itemcronograma")
public class ItemCronograma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JoinColumn(name = "plano_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
    private Plano plano;
    @Column(name = "inicio")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date inicio;
    @Column(name = "numeroaulas")
    private Integer numeroAulas;
    @Column(name = "procedimentodidatico")
    private String procedimentoDidatico;
    @Column(name = "topico")
    private String topico;
    @Transient
    private String inicioFormatado;

    public ItemCronograma() {
    }

    public ItemCronograma(ItemCronograma ic) {
        if (ic != null) {
            this.id = ic.getId();
            this.inicio = ic.getInicio();
            this.inicioFormatado = ic.getInicioFormatado();
            this.numeroAulas = ic.getNumeroAulas();
            this.plano = ic.getPlano();
            this.procedimentoDidatico = ic.getProcedimentoDidatico();
            this.topico = ic.getTopico();
        }
    }

    public ItemCronograma(Plano plano, Date inicio, Integer numeroAulas, String procedimentoDidatico, String topico) {
        this.plano = plano;
        this.inicio = inicio;
        this.numeroAulas = numeroAulas;
        this.procedimentoDidatico = procedimentoDidatico;
        this.topico = topico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public Date getInicio() {
        return inicio;
    }

    public String getInicioFormatado() {
        SimpleDateFormat formatBra = new SimpleDateFormat("dd/MM/yy");
        return formatBra.format(inicio);
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Integer getNumeroAulas() {
        return numeroAulas;
    }

    public void setNumeroAulas(Integer numeroAulas) {
        this.numeroAulas = numeroAulas;
    }

    public String getProcedimentoDidatico() {
        return procedimentoDidatico;
    }

    public void setProcedimentoDidatico(String procedimentoDidatico) {
        this.procedimentoDidatico = procedimentoDidatico;
    }

    public String getTopico() {
        return topico;
    }

    public void setTopico(String topico) {
        this.topico = topico;
    }

    public void salvar(ItemCronograma i) {
        EntityManager em = Persistencia.obterManager();
        em.getTransaction().begin();
        em.merge(i);        
        em.getTransaction().commit();
    }

    public Boolean remover() {
        EntityManager em = Persistencia.obterManager();
        em.getTransaction().begin();
        try {
            ItemCronograma ic;
            ic = em.merge(this);
            em.remove(ic);
            em.getTransaction().commit();
            return true;
        } catch (RollbackException e) {
            return false;
        }
    }

}
