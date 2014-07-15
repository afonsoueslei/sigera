package br.ufg.inf.sigera.modelo.email;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class RemetenteEmail implements Runnable {

    private final List<Email> emailsEnviar;

    public RemetenteEmail(List<Email> emailsEnviar) {
        this.emailsEnviar = emailsEnviar;
    }

    public void run() {
        for (Email email : emailsEnviar) {
            enviarEmail(email);
        }
    }

    private void enviarEmail(Email email) {

        String emailDestinatario = email.getEnderecoDestinatario();
        String assunto = email.getAssunto();
        String mensagem = email.getMensagem();

        if (emailDestinatario == null || emailDestinatario.trim().isEmpty()) {
            return;
        }

        final String nomeRemetente = "SIGERA";
        final String emailRemetente = "sigera@inf.ufg.br";
        final String senhaRementente = "aWah9eiv";

//ENVIO DE EMAIL VIA SSL       

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.inf.ufg.br");
        props.put("mail.smtp.localhost", "smtp.inf.ufg.br");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.auth.mechanisms", "LOGIN PLAIN DIGEST-MD5 NTLM");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.inf.ufg.br");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(props);

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(emailRemetente, nomeRemetente));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestinatario));
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(emailRemetente));
            message.setSubject(assunto, "utf-8");
            message.setText(mensagem, "utf-8", "html");

            Transport tr = session.getTransport("smtp");
            tr.connect("sigera", senhaRementente);// (emailRemetente, senhaRementente);
            message.saveChanges();
            tr.sendMessage(message, message.getAllRecipients());
            tr.close();

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException uex) {
            throw new RuntimeException(uex);
        } catch (Exception x) {
            throw new RuntimeException(x);
        }
    }
}
