package br.ufg.inf.sigera.sigeramodel;

import br.ufg.inf.sigera.modelo.ldap.AutenticacaoLdap;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author auf
 */

public class LoginTest {

    @Test
    public void efetuaLogin(){
        String login = "afonso";
        String senha = "invalido";
        
        AutenticacaoLdap loginObj = new AutenticacaoLdap();
        assertNull("Login inválido", loginObj.efetuaLogin(login, senha));
        System.out.println("passou 1");
        
        
//        senha = "Aed5ahm4";
//        assertNotNull("Login válido", loginObj.efetuaLogin(login, senha));
//        System.out.println("passou 2");
    }
    
}
