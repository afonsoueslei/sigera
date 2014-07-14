package br.ufg.inf.sigera.controle.tela;

import br.ufg.inf.sigera.modelo.Plano;

public interface PlanoTela {    

    int getNumero();    
    String getTurma();
    String getCriterioAvaliacao();        
    String getDataRealizacaoProvas();
    String getObjetivosEspecificos();
    String getRelacaoOutrasDisciplinas();
    String getBibliografiaSugerida();            
    String getPrograma();                
    Plano getPlano();
    String getNomeDisciplina();
    
    int getNumeroRequerimentoPlanoAssociado();
    String getAnoSemestre();
    String getNomeProfessor();
    String getStatus();
    String getNomeCurso();
    String getPrefixoCurso();
}