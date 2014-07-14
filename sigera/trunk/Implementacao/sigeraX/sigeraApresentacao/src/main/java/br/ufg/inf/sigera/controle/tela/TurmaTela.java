package br.ufg.inf.sigera.controle.tela;

import br.ufg.inf.sigera.modelo.Turma;

public interface TurmaTela {    

    int getNumero();
    
    int getAno();
    int getSemestre();
    
    String getNome();        
    String getNomeDisciplina();
    String getNomeCurso();
    String getPrefixoCurso();
    String getNomeProfessor();            

    Turma getTurma();
}