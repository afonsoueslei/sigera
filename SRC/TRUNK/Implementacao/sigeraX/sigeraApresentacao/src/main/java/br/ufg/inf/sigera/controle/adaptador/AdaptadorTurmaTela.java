package br.ufg.inf.sigera.controle.adaptador;

import br.ufg.inf.sigera.controle.tela.TurmaTela;
import br.ufg.inf.sigera.modelo.Turma;

public class AdaptadorTurmaTela implements TurmaTela {

    private Turma turma;

    public AdaptadorTurmaTela(Turma turma) {
        this.turma = turma;
    }

    @Override
    public int getNumero() {
        return this.turma.getId();
    }

    @Override
    public int getAno() {
        return this.turma.getAno();
    }

    @Override
    public String getNome() {
        return this.turma.getNome();
    }

    @Override
    public int getSemestre() {
        return this.turma.getSemestre();
    }   

    @Override
    public Turma getTurma() {
        return this.turma;
    }

    @Override
    public String getNomeDisciplina() {
        return this.turma.getDisciplina().getNome();
    }

    @Override
    public String getNomeProfessor() {
        return this.turma.getProfessor().getUsuario().getUsuarioLdap().getCn();
    }

    @Override
    public String getNomeCurso() {
        return this.turma.getDisciplina().getCurso().getNome();
    }

    @Override
    public String getPrefixoCurso() {
        return this.turma.getDisciplina().getCurso().getPrefixo();
    }
}
