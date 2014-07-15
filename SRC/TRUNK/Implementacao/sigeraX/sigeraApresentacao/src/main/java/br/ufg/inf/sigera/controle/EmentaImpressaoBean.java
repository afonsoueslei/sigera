package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.modelo.Disciplina;
import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class EmentaImpressaoBean {

    private Disciplina disciplina;

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }    
    
   @PostConstruct
    public void init() {
        obtenhaDisciplina();        
        if (this.disciplina == null) {
            redirecioneParaPaginaConsulta();
        }        
    }

    private void redirecioneParaPaginaConsulta() {
        try {
            ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
            contexto.dispatch("consultar_requerimento.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(DetalheRequerimentoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void obtenhaDisciplina() {
        this.disciplina = null;
        String param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

        if (param != null) {
            try {
                Integer id = Integer.valueOf(param);
                disciplina = Disciplina.obtenhaDisciplina(id);
            } catch (NumberFormatException ex) {
                Logger.getLogger(Requerimento.class.getName()).log(Level.WARNING, null, ex);
            }
        }
    }
    
    public String getObjetivoGeral() {
        if (disciplina.getObjetivoGeral() != null) {
            return disciplina.getObjetivoGeral().trim();
        }
        return disciplina.getObjetivoGeral();
    }
    
    public String getBibliografiaBasica() {
        if (disciplina.getBibliografiaBasica() != null) {
            return disciplina.getBibliografiaBasica().trim();
        }
        return disciplina.getBibliografiaBasica();
    }
    
    public String getBibliografiaComplementar() {
        if (disciplina.getBibliografiaComplementar() != null) {
            return disciplina.getBibliografiaComplementar().trim();
        }
        return disciplina.getBibliografiaComplementar();
    }
}
