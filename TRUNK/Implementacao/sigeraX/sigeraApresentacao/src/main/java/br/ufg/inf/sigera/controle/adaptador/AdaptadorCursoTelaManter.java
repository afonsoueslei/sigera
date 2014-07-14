package br.ufg.inf.sigera.controle.adaptador;

import br.ufg.inf.sigera.controle.tela.CursoTelaManter;
import br.ufg.inf.sigera.modelo.Curso;
import br.ufg.inf.sigera.modelo.Unidade;

public class AdaptadorCursoTelaManter implements CursoTelaManter {

    private Curso curso;

    public AdaptadorCursoTelaManter(Curso curso) {
        this.curso = curso;
    }

    @Override
    public int getNumero() {
        return this.curso.getId();
    }

    @Override
    public String getPrefixo() {
        return this.curso.getPrefixo();
    }

    @Override
    public String getCodMatriz() {
        return this.curso.getCodMatriz();
    }

    @Override
    public String getNome() {
        return this.curso.getNome();
    }
    @Override
    public void setNumero(int id) {
        curso.setId(id);
    }

    @Override
    public void setPrefixo(String prefixo) {
        curso.setPrefixo(prefixo);
    }

    @Override
    public void setCodMatriz(String codMatriz) {
        curso.setCodMatriz(codMatriz);
    }

    @Override
    public void setNome(String nome) {
        curso.setNome(nome);
        
    }

    @Override
    public Curso getCurso() {
        return curso;
    }
    
    @Override
    public Unidade getUnidade() {
        return this.curso.getUnidade();
    }
            
}
