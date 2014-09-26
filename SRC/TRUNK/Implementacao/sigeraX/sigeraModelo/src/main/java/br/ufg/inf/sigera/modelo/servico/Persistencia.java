/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.sigera.modelo.servico;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author auf
 */
public class Persistencia {

    private static EntityManager em;

    public static EntityManager obterManager() {
        EntityManagerFactory emf;
//        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("br.ufg.inf.sigera");
            if ( em == null ||  !em.isOpen()) {
                em = emf.createEntityManager();
            }
        } catch (Exception e) {
            System.out.println("Não foi possível criar Entidade de Persistência");
        }
        return em;
    }
    
    public static Boolean versaoValida(Object entidade) {
        EntityManager emCopia = obterManager();
        try {
            //Versão da entidade que está sendo editada
            Object versaoEdicao = entidade.getClass().getMethod("getVersao", null).invoke(entidade, null);
            //Consulta entidade que esta no banco
            Object idObj = entidade.getClass().getMethod("getId", null).invoke(entidade, null);
            try{
                entidade = emCopia.find(entidade.getClass(), idObj);
            }catch(Exception eie){                
                Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, "Falha conexão com banco, impossível obter entidade!", eie);
                return false;
            }
            //Caso entidade não exista no banco, caso de estar salvando pela 1a vez
            if(entidade==null) 
                return true;
            //Versão da entidade que está no banco
            Object versaoBanco = entidade.getClass().getMethod("getVersao", null).invoke(entidade, null);

            if (versaoEdicao.toString().equals(versaoBanco.toString())) {
                return true;
            }

        } catch (IllegalAccessException e) {
            System.out.println("Versão não pode ser validada! : Illegal Access ");
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Versão não pode ser validado! : Illegal Argument");
            return false;
        } catch (NoSuchMethodException e) {
            System.out.println("Versão não pode ser validado! : No Such Method");
            return false;
        } catch (SecurityException e) {
            System.out.println("Versão não pode ser validado! : Security Exception");
            return false;
        } catch (InvocationTargetException e) {
            System.out.println("Versão não pode ser validado! : Invocation Target");
            return false;
        }
        return false;
    }
}
