package br.ufg.inf.sigera.modelo.requerimento;

import br.ufg.inf.sigera.modelo.Turma;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "req_acrescimodisciplina_turma")
public class AssociacaoReqAcrescimoTurma implements Serializable, TurmaComStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "Req_Acrescimo_Disciplina_Requerimento_id", nullable = false)
    private RequerimentoAcrescimoDisciplina reqAcrescimo;
    
    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;
    
    @Column(name = "status_disciplina")
    private int status;
    
    public AssociacaoReqAcrescimoTurma() {        
    }
    
    public AssociacaoReqAcrescimoTurma(Turma turma, RequerimentoAcrescimoDisciplina reqAcrescimo) {        
        this.reqAcrescimo = reqAcrescimo;
        setTurma(turma);
        setStatus(EnumStatusRequerimento.ABERTO.getCodigo());
    }
    
    public AssociacaoReqAcrescimoTurma(TurmaComStatus turmaCopiar) {        
        AssociacaoReqAcrescimoTurma infoTurma = (AssociacaoReqAcrescimoTurma) turmaCopiar;
        
        this.id = infoTurma.getId();
        this.reqAcrescimo = infoTurma.getReqAcrescimo();
        this.turma = infoTurma.getTurma();
        this.status = infoTurma.getStatus();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public RequerimentoAcrescimoDisciplina getReqAcrescimo() {
        return reqAcrescimo;
    }

    public void setReqAcrescimo(RequerimentoAcrescimoDisciplina reqAcrescimo) {
        this.reqAcrescimo = reqAcrescimo;
    }
    
    public String getDescricaoStatus() {
        return EnumStatusRequerimento.obtenha(this.getStatus()).getNome();
    }    
}
