package br.ufg.inf.sigera.modelo.email;

public class Email {
    
    private String enderecoDestinatario;
    private String assunto;
    private String mensagem;
    
    public Email(String emailDestinatario, String assunto, String mensagem) {
        this.enderecoDestinatario = emailDestinatario;
        this.assunto = assunto;
        this.mensagem = mensagem;
    }

    public String getEnderecoDestinatario() {
        return enderecoDestinatario;
    }

    public void setEnderecoDestinatario(String enderecoDestinatario) {
        this.enderecoDestinatario = enderecoDestinatario;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    
}
