package br.ufg.inf.sigera.controle.tela;

import br.ufg.inf.sigera.modelo.Curso;
import br.ufg.inf.sigera.modelo.Unidade;

public interface CursoTelaManter {
    
    int getNumero();
    String getPrefixo();
    String getCodMatriz();
    String getNome();    
    Curso getCurso();
    Unidade getUnidade();    
    void setNumero(int id);
    void setPrefixo(String prefixo);
    void setCodMatriz(String codMatriz);
    void setNome(String nome); 

        
}