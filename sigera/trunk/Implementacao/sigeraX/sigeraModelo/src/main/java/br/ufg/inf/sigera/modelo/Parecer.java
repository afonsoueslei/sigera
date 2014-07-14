package br.ufg.inf.sigera.modelo;

import br.ufg.inf.sigera.modelo.requerimento.EnumStatusRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name="parecer")
public class Parecer implements Serializable{
    
    @Id
    @GeneratedValue
    private int id;
    
    @ManyToOne
    @JoinColumn(name="usuario_id", nullable=false)
    private UsuarioSigera usuario;
    
    @Column(name="data_parecer")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataParecer;
    
    @Column(name="conteudo")    
    private String conteudo;
    
    @Column(name="status")    
    private int status;
    
    @ManyToOne
    @JoinColumn(name = "requerimento_id")    
    private Requerimento requerimento;
    
    public Parecer() {        
    }

    public Parecer(Requerimento requerimento, UsuarioSigera usuario, String justificativaDeferimento, int status) {
        this.dataParecer = new Date();
        this.requerimento = requerimento;
        this.usuario = usuario;
        this.conteudo = justificativaDeferimento;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public Date getDataParecer() {
        return dataParecer;
    }

    public void setDataParecer(Date data_parecer) {
        this.dataParecer = data_parecer;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Requerimento getRequerimento() {
        return requerimento;
    }

    public void setRequerimento(Requerimento requerimento) {
        this.requerimento = requerimento;
    }
    
    public String getDescricaoStatus() {
        return EnumStatusRequerimento.obtenha(this.getStatus()).getNome();
    }

}
