package br.ufg.inf.sigera.controle.adaptador;

import br.ufg.inf.sigera.controle.tela.UnidadeTela;
import br.ufg.inf.sigera.modelo.Unidade;

public class AdaptadorUnidadeTela implements UnidadeTela {

    private Unidade unidade;

    public AdaptadorUnidadeTela(Unidade unidade) {
        this.unidade = unidade;
    }

    @Override
    public int getId() {
        return this.unidade.getId();
    }
    
    @Override
    public String getNome() {
        return this.unidade.getNome();
    }

    @Override
    public Unidade getUnidade() {
        return this.unidade;
    }

    
}
