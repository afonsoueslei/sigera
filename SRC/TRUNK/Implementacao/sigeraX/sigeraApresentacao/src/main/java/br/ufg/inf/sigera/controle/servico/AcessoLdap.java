/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufg.inf.sigera.controle.servico;

import br.ufg.inf.sigera.modelo.ldap.AutenticacaoLdap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;

/**
 *
 * @author auf
 */
public class AcessoLdap {
    
    public static void alterarCampoLdap(String userNameEditado, String campoQueSeraAlterado, String novoValor) {
        AutenticacaoLdap autenticador = new AutenticacaoLdap();
        try {
            DirContext dir = autenticador.getContextAdmin();
            autenticador.alterarCampoUsuarioLdap(userNameEditado, dir, campoQueSeraAlterado, novoValor);
        } catch (NamingException ex) {
            Logger.getLogger(AutenticacaoLdap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
