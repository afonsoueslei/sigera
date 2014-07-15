package br.ufg.inf.sigera.controle.adaptador;

import br.ufg.inf.sigera.controle.tela.PlanoTela;
import br.ufg.inf.sigera.modelo.Plano;
import br.ufg.inf.sigera.modelo.requerimento.EnumStatusRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoPlano;

public class AdaptadorPlanoTela implements PlanoTela {

    private Plano plano;    

    public AdaptadorPlanoTela(Plano plano) {
        this.plano = plano;
    }

    @Override
    public int getNumero() {
        return this.plano.getId();
    }

    @Override
    public String getTurma() {
        return this.plano.getTurma().getNome();
    }

    @Override
    public String getCriterioAvaliacao() {
        return this.plano.getCriterioAvaliacao();
    }

    @Override
    public String getDataRealizacaoProvas() {
        return this.plano.getDataRealizacaoProvas();
    }

    @Override
    public String getObjetivosEspecificos() {
        return this.plano.getObjetivosEspecificos();
    }

    @Override
    public String getRelacaoOutrasDisciplinas() {
        return this.plano.getRelacaoOutrasDisciplinas();
    }

    @Override
    public String getBibliografiaSugerida() {
        return this.plano.getBibliografiaSugerida();
    }

    @Override
    public String getPrograma() {
        return this.plano.getPrograma();
    }

    @Override
    public Plano getPlano() {
        return this.plano;
    }

    @Override
    public String getNomeDisciplina() {
        return this.plano.getTurma().getDisciplina().getNome();
    }

    @Override
    public String getNomeCurso() {
        return this.plano.getTurma().getDisciplina().getCurso().getNome();
    }

    @Override
    public String getPrefixoCurso() {
        return this.plano.getTurma().getDisciplina().getCurso().getPrefixo();
    }
        
    @Override
    public int getNumeroRequerimentoPlanoAssociado() {
        
        RequerimentoPlano  requerimento = Plano.buscarRequerimentoDessePlano(null, this.plano);
        if(requerimento!=null){
            return requerimento.getId();  
        }
        //Caso não tenha nenhum RequerimentoPlano Associado ao este Plano (NUNCA DEVE ACONTECER)
        return 0;  
    }

    @Override
    public String getAnoSemestre() {
        String ano = Integer.toString(this.plano.getTurma().getAno());
        String semestre = Integer.toString(this.plano.getTurma().getSemestre());
        
        StringBuilder anoSemestre = new StringBuilder();
        anoSemestre.append(ano);
        anoSemestre.append("/");
        anoSemestre.append(semestre);
        
        return anoSemestre.toString();
    }

    @Override
    public String getNomeProfessor() {     
        return this.getPlano().getTurma().getProfessor().getUsuario().getNome();
    }

    @Override
    public String getStatus() {
        RequerimentoPlano  requerimento = Plano.buscarRequerimentoDessePlano(null, this.plano);
        if(requerimento!=null){
            return EnumStatusRequerimento.obtenha(requerimento.getStatus()).getNome();
        }
        //Caso não tenha nenhum RequerimentoPlano Associado ao este Plano (NUNCA DEVE ACONTECER)
        return "NPA";  
    }
}
