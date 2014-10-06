package br.ufg.inf.sigera.controle.adaptador;

import br.ufg.inf.sigera.controle.tela.RequerimentoTelaConsulta;
import br.ufg.inf.sigera.modelo.requerimento.EnumStatusRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdaptadorRequerimentoTelaConsulta implements RequerimentoTelaConsulta {

    private Requerimento requerimento;
    private String dataAberturaParaFiltro;
    
    public AdaptadorRequerimentoTelaConsulta(Requerimento requerimento) {
        this.requerimento = requerimento;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");                
        this.dataAberturaParaFiltro = dateFormat.format(this.requerimento.getDataAbertura());        
    }
    
    @Override
    public int getNumero() {
        return this.requerimento.getId();
    }

    @Override
    public String getNomeRequerente() {
        return this.requerimento.getUsuario().getNome();
    }

    @Override
    public String getMatricula() {
        return this.requerimento.getUsuario().getMatricula();
    }
    

    @Override
    public String getTipo() {
        return this.requerimento.getDescricaoTipo();
    }

    @Override
    public Date getDataAbertura() {
        return this.requerimento.getDataAbertura();
    }

    @Override
    public String getStatus() {
        return EnumStatusRequerimento.obtenha(this.requerimento.getStatus()).getNome();
    }

    @Override
    public String getDataAberturaParaFiltro() {
        return this.dataAberturaParaFiltro;
    }

    @Override
    public String getCurso() {        
        return this.requerimento.getCurso().getNome();
    }
    
    @Override
    public String getPrefixoCurso() {        
        return this.requerimento.getCurso().getPrefixo();
    }
    
}
