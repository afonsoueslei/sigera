/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.sigera.modelo.servico;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author auf
 */
public class CodificarSenha {

    public static String hashMD5(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes("UTF8"));
            String md5Password = Base64.encode(digest.digest());            
            return "{MD5}" + md5Password;
        } catch (NoSuchAlgorithmException nsae) {
            System.err.println("Erro na codificação da senha! ");
        } catch (UnsupportedEncodingException uee) {
            System.err.println("Tipo de codificação não aceita! ");
        }

        return "";
    }
    
        
}
