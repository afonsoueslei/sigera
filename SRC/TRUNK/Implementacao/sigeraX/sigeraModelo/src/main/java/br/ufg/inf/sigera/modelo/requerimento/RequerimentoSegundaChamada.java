package br.ufg.inf.sigera.modelo.requerimento;

import br.ufg.inf.sigera.modelo.Turma;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.perfil.EnumPerfil;
import br.ufg.inf.sigera.modelo.perfil.Perfil;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "req_segunda_chamada")
@DiscriminatorValue(value = "3")
@PrimaryKeyJoinColumn(name = "Requerimento_id")
public class RequerimentoSegundaChamada extends Requerimento {

    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;
    
    @Column(name = "data_prova")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataProva;
    
    @Override
    public String getDescricaoTipo() {
        return EnumTipoRequerimento.SEGUNDA_CHAMADA.getNome();
    }
    
    public RequerimentoSegundaChamada() {        
    }
    
    public RequerimentoSegundaChamada(UsuarioSigera usuario, Turma turma, Date dataProva, String justificativa){
        super(usuario);
        this.setTipo(EnumTipoRequerimento.SEGUNDA_CHAMADA.getCodigo());      
        this.setTurma(turma);
        this.setDataProva(dataProva);
        this.setJustificativa(justificativa);
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Date getDataProva() {
        return dataProva;
    }

    public void setDataProva(Date dataProva) {
        this.dataProva = dataProva;
    }    
    
    @Override
    public boolean perfilPermiteDarParecer(UsuarioSigera usuarioLogado) {
        
        // Só o próprio professor da turma é que pode dar parecer
        // sobre requerimentos de segunda chamada de prova.        
        
        Perfil perfilUsuario = usuarioLogado.getPerfilAtual().getPerfil();
        UsuarioSigera usuarioProfessorTurma = this.turma.getProfessor().getUsuario();
        
        if (perfilUsuario.getId() == EnumPerfil.PROFESSOR.getCodigo()
            && usuarioProfessorTurma.getId() == usuarioLogado.getId()) {
            return true;
        }
        else {
            return false;
        }
    }   

    @Override
    public boolean perfilPermiteEditarPlano(UsuarioSigera usuario) {
        return false;
    }
    
    
}