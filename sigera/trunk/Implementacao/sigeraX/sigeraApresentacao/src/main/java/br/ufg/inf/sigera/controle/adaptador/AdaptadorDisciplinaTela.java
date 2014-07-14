package br.ufg.inf.sigera.controle.adaptador;

import br.ufg.inf.sigera.controle.tela.DisciplinaTela;
import br.ufg.inf.sigera.modelo.Curso;
import br.ufg.inf.sigera.modelo.Disciplina;

public class AdaptadorDisciplinaTela implements DisciplinaTela{

    private final Disciplina disciplina;

    public AdaptadorDisciplinaTela(Disciplina d) {
        this.disciplina = d;
    }
    
    @Override
    public int getNumero() {
        return this.disciplina.getId();
    }

    @Override
    public int getChPratica() {
        return this.disciplina.getHoraPratica();
    }

    @Override
    public int getChTeorica() {
        return this.disciplina.getHoraTeorica();
    }

    @Override
    public String getEmenta() {
        return this.disciplina.getEmenta();
    }

    @Override
    public Curso getCurso() {
        return this.disciplina.getCurso();
    }

    @Override
    public String getBibBasica() {
        return this.disciplina.getBibliografiaBasica();
    }

    @Override
    public String getBibComplementar() {
        return this.disciplina.getBibliografiaComplementar();
    }

    @Override
    public String getObjetivoGeral() {
        return this.disciplina.getObjetivoGeral();
    }

    @Override
    public Disciplina getDisciplina() {
        return disciplina;
    }

    @Override
    public String getNomeCurso() {
        return this.disciplina.getCurso().getNome();
    }
    
    @Override
    public String getNome() {
        return this.disciplina.getNome();
    }
}
