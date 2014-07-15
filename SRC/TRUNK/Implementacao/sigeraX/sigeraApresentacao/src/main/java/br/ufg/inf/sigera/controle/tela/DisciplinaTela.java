package br.ufg.inf.sigera.controle.tela;

import br.ufg.inf.sigera.modelo.Curso;
import br.ufg.inf.sigera.modelo.Disciplina;

public interface DisciplinaTela {

    int getNumero();
    String getNome();
    String getNomeCurso();

    int getChPratica();
    int getChTeorica();
    String getEmenta();
    Curso getCurso();
    String getBibBasica();
    String getBibComplementar();
    String getObjetivoGeral();
    Disciplina getDisciplina();
}