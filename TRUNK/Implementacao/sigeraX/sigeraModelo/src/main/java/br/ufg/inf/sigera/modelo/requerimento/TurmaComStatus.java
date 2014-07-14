package br.ufg.inf.sigera.modelo.requerimento;

import br.ufg.inf.sigera.modelo.Turma;

public interface TurmaComStatus {
    
    public Turma getTurma();
    public int getStatus();
    public void setStatus(int status);
    public String getDescricaoStatus();
    
}
