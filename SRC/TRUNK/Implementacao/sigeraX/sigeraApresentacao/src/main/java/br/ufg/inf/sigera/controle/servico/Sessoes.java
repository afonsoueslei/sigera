/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.sigera.controle.servico;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author auf
 */
public class Sessoes {

    private Sessoes() {
    }

    public static void limparBeans() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("turmaBean");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("disciplinaBean");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("segundaChamadaBean");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("unidadeBean");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("planoBean");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("cursoBean");
        limparBeanConsultarRequerimento();
    }

    public static void limparBeanConsultarRequerimento() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("consultarRequerimentoBean");
    }

    public static void invalidarSessoes() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

}
