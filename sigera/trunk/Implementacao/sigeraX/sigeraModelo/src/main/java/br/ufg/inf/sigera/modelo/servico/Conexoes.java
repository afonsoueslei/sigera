/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.sigera.modelo.servico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author auf
 */
public class Conexoes {

    private static String SERVIDOR_BANCO;
    private static String PORTA_BANCO;
    private static String NOME_BANCO;
    private static String CONEXAO_BANCO;
    private static String USUARIO_BANCO;
    private static String CHAVE_BANCO;
    private static String PATH_ADMIN;
    private static String KEY_ADMIN;
    private static String INITIAL_CTX;
    private static String SERVIDOR_LDAP;
    private static String SEARCHBASE;
    private static String BASE_DN;
    private static String PASTA_PLANOS_DE_AULA;
    private static String PASTA_ANEXOS;

    public static void lerParametros() {
        String pathSistema = System.getenv("SIGERA_CONF_PROPERTIES");

        if (pathSistema == null) {        //tratar
            //tratar exceção             
            throw new NotImplementedException();
        }

        File file = new File(pathSistema);
        if (!file.exists()) {
            throw new NotImplementedException();
        }

        InputStream input = null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Conexoes.class.getName()).log(Level.SEVERE, null, ex);
        }
        Properties prop = new Properties();
        try {
            prop.load(input);
        } catch (IOException ex) {
            Logger.getLogger(Conexoes.class.getName()).log(Level.SEVERE, null, ex);
        }

        Conexoes.setSERVIDOR_BANCO(prop.getProperty("SERVIDOR_BANCO"));
        Conexoes.setPORTA_BANCO(prop.getProperty("PORTA_BANCO"));
        Conexoes.setNOME_BANCO(prop.getProperty("NOME_BANCO"));
        Conexoes.setUSUARIO_BANCO(prop.getProperty("USUARIO_BANCO"));
        Conexoes.setCHAVE_BANCO(prop.getProperty("CHAVE_BANCO"));
        Conexoes.setPATH_ADMIN(prop.getProperty("PATH_ADMIN"));
        Conexoes.setKEY_ADMIN(prop.getProperty("KEY_ADMIN"));
        Conexoes.setCONEXAO_BANCO(SERVIDOR_BANCO + PORTA_BANCO + NOME_BANCO);
        Conexoes.setINITIAL_CTX(prop.getProperty("INITIAL_CTX"));
        Conexoes.setSERVIDOR_LDAP(prop.getProperty("SERVIDOR_LDAP"));
        Conexoes.setSEARCHBASE(prop.getProperty("SEARCHBASE"));
        Conexoes.setBASE_DN(prop.getProperty("BASE_DN"));
        Conexoes.setPASTA_PLANOS_DE_AULA(prop.getProperty("PASTA_PLANOS_DE_AULA"));
        Conexoes.setPASTA_ANEXOS(prop.getProperty("PASTA_ANEXOS"));

    }

    public static void setSERVIDOR_BANCO(String SERVIDOR_BANCO) {
        Conexoes.SERVIDOR_BANCO = SERVIDOR_BANCO;
    }

    public static void setPORTA_BANCO(String PORTA_BANCO) {
        Conexoes.PORTA_BANCO = PORTA_BANCO;
    }

    public static void setNOME_BANCO(String NOME_BANCO) {
        Conexoes.NOME_BANCO = NOME_BANCO;
    }

    public static void setCONEXAO_BANCO(String CONEXAO_BANCO) {
        Conexoes.CONEXAO_BANCO = CONEXAO_BANCO;
    }

    public static void setUSUARIO_BANCO(String USUARIO_BANCO) {
        Conexoes.USUARIO_BANCO = USUARIO_BANCO;
    }

    public static void setCHAVE_BANCO(String CHAVE_BANCO) {
        Conexoes.CHAVE_BANCO = CHAVE_BANCO;
    }

    public static void setPATH_ADMIN(String PATH_ADMIN) {
        Conexoes.PATH_ADMIN = PATH_ADMIN;
    }

    public static void setKEY_ADMIN(String KEY_ADMIN) {
        Conexoes.KEY_ADMIN = KEY_ADMIN;
    }

    public static void setINITIAL_CTX(String INITIAL_CTX) {
        Conexoes.INITIAL_CTX = INITIAL_CTX;
    }

    public static void setSERVIDOR_LDAP(String SERVIDOR_LDAP) {
        Conexoes.SERVIDOR_LDAP = SERVIDOR_LDAP;
    }

    public static void setSEARCHBASE(String SEARCHBASE) {
        Conexoes.SEARCHBASE = SEARCHBASE;
    }

    public static void setBASE_DN(String BASE_DN) {
        Conexoes.BASE_DN = BASE_DN;
    }

    public static void setPASTA_PLANOS_DE_AULA(String PASTA_PLANOS_DE_AULA) {
        Conexoes.PASTA_PLANOS_DE_AULA = PASTA_PLANOS_DE_AULA;
    }

    public static void setPASTA_ANEXOS(String PASTA_ANEXOS) {
        Conexoes.PASTA_ANEXOS = PASTA_ANEXOS;
    }

    public static String getSERVIDOR_BANCO() {
        return SERVIDOR_BANCO;
    }

    public static String getPORTA_BANCO() {
        return PORTA_BANCO;
    }

    public static String getNOME_BANCO() {
        return NOME_BANCO;
    }

    public static String getCONEXAO_BANCO() {
        return CONEXAO_BANCO;
    }

    public static String getUSUARIO_BANCO() {
        return USUARIO_BANCO;
    }

    public static String getCHAVE_BANCO() {
        return CHAVE_BANCO;
    }

    public static String getPATH_ADMIN() {
        return PATH_ADMIN;
    }

    public static String getKEY_ADMIN() {
        return KEY_ADMIN;
    }

    public static String getINITIAL_CTX() {
        return INITIAL_CTX;
    }

    public static String getSERVIDOR_LDAP() {
        return SERVIDOR_LDAP;
    }

    public static String getSEARCHBASE() {
        return SEARCHBASE;
    }

    public static String getBASE_DN() {
        return BASE_DN;
    }

    public static String getPASTA_PLANOS_DE_AULA() {
        return PASTA_PLANOS_DE_AULA;
    }

    public static String getPASTA_ANEXOS() {
        return PASTA_ANEXOS;
    }

}