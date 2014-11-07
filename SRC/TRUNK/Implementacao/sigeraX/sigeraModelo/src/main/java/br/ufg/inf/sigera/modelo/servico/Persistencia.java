/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.sigera.modelo.servico;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
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
    private static EntityManagerFactory emf;

    public static EntityManager obterManager() {

        try {
            emf = criaEntityManagerFactory();
            if (em == null || !em.isOpen()) {
                em = emf.createEntityManager();
            }
        } catch (Exception e) {
            System.out.println("Não foi possível criar Entidade de Persistência");
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, "Não foi possível criar Entidade de Persistência!", e);
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
            try {
                entidade = emCopia.find(entidade.getClass(), idObj);
            } catch (Exception eie) {
                Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, "Falha conexão com banco, impossível obter entidade!", eie);
                return false;
            }
            //Caso entidade não exista no banco, caso de estar salvando pela 1a vez
            if (entidade == null) {
                return true;
            }
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

    public static EntityManagerFactory criaEntityManagerFactory() {
        Conexoes.lerParametros();
        if (emf == null) {
            Logger.getLogger("Aplicando propriedades para o persistence.xml ....");
            Properties configOverrides = new Properties();
            configOverrides.put("javax.persistence.jdbc.driver", Conexoes.getDRIVE_BANCO());
            configOverrides.put("javax.persistence.jdbc.url", Conexoes.getSERVIDOR_BANCO() + Conexoes.getPORTA_BANCO() + Conexoes.getNOME_BANCO());
            configOverrides.put("javax.persistence.jdbc.user", Conexoes.getUSUARIO_BANCO());
            configOverrides.put("javax.persistence.jdbc.password", Conexoes.getCHAVE_BANCO());
            configOverrides.put("eclipselink.query-results-cache", Conexoes.getRESULT_CACHE());
            configOverrides.put("eclipselink.logging.level.sql", Conexoes.getLEVEL_SQL());
            configOverrides.put("eclipselink.logging.parameters", Conexoes.getLOGGING_PARAMETERS());

            return emf = Persistence.createEntityManagerFactory(Conexoes.getPERSISTENCE_UNIT(), configOverrides);
        } else {
            return emf;
        }
    }
}
