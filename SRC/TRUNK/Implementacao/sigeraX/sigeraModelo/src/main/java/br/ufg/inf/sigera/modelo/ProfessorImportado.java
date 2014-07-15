package br.ufg.inf.sigera.modelo;

/*
 * Representa o professor apos ser importado do arquivo de importacao;
 * Alem do objeto professor, contem um boolean que diz se a importacao
 * foi bem sucedida e um Motivo que diz por qual motivo o curso nao 
 * pode ser incluido do banco de dados ou se a importacao foi bem
 * sucedida, contem uma mensagem de "operacao concluida com sucesso".
 */

public class ProfessorImportado extends ArquivoImportado{
    
    private Professor professor;

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
