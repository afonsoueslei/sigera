package br.ufg.inf.sigera.modelo;

/*
 * Representa o curso apos ser importado do arquivo de importacao;
 * Alem do objeto curso, contem um boolean que diz se a importacao
 * foi bem sucedida e um Motivo que diz por qual motivo o curso nao 
 * pode ser incluido do banco de dados ou se a importacao foi bem
 * sucedida contem uma mensagem de operacao concluida com sucesso.
 */
public class CursoImportado extends ArquivoImportado{

    private Curso curso;
    
    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

}
