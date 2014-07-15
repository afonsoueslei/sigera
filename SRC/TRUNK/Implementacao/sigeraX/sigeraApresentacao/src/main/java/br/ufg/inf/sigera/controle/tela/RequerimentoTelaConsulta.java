package br.ufg.inf.sigera.controle.tela;

import java.util.Date;

public interface RequerimentoTelaConsulta {
    
    int getNumero();
    String getNomeRequerente();
    String getMatricula();
    String getTipo();
    Date getDataAbertura();
    String getDataAberturaParaFiltro();
    String getStatus(); 
    String getCurso();
    String getPrefixoCurso();
}
