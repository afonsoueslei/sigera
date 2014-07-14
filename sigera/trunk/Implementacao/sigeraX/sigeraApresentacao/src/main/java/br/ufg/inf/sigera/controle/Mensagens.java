package br.ufg.inf.sigera.controle;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mensagens {

    private Mensagens() {
    }

    public static String obtenha(String codigoMensagem) {
        try {
            Properties prop = new Properties();
            prop.load(Mensagens.class.getClassLoader().getResourceAsStream("mensagens.properties"));
            return prop.getProperty(codigoMensagem);
            
        } catch (IOException ex) {
            Logger.getLogger(Mensagens.class.getName()).log(Level.WARNING, null, ex);
        }
        return "";
    }

    public static String obtenha(String codigoMensagem, Object... parametros) {
        String msg = obtenha(codigoMensagem);

        if (msg != null && !msg.trim().isEmpty()) {
            msg = MessageFormat.format(msg, parametros);
        }

        return msg;
    }
}
