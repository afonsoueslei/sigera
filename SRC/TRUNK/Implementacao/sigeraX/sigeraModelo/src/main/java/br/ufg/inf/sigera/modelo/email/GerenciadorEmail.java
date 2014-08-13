package br.ufg.inf.sigera.modelo.email;

import br.ufg.inf.sigera.modelo.AssociacaoPerfilCurso;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import br.ufg.inf.sigera.modelo.ldap.UsuarioLdap;
import br.ufg.inf.sigera.modelo.requerimento.EnumTipoRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoPlano;
import br.ufg.inf.sigera.modelo.servico.Conexoes;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GerenciadorEmail {

    private final List<Email> emailsEnviar;
    
    public GerenciadorEmail() {
        Conexoes.lerParametros();
        this.emailsEnviar = new ArrayList<Email>();
    }

    /*
     Mensagem de e-mail ME.001 - Para um ou mais destinatários
     */
    public void adicionarEmailRequerimento(Requerimento requerimento, List<UsuarioSigera> destinatarios) {

        if (requerimento == null || requerimento.getId() == 0 || destinatarios.size() < 1) {
            return;
        }

        try {
            Properties prop = new Properties();
            prop.load(GerenciadorEmail.class.getClassLoader().getResourceAsStream("mensagensEmail.properties"));

            String assunto = MessageFormat.format(prop.getProperty("ME.001"), requerimento.getDescricaoTipo());
            String nomeRequerente = requerimento.getUsuario().getUsuarioLdap().getCn();
            String matriculaRequerente = requerimento.getUsuario().getUsuarioLdap().getMatricula();
            String nomeCurso = requerimento.getCurso().getNome();
            String linkRequerimento = Conexoes.getURL_SIGERA() + "faces/usuario/requerimento.xhtml?numero=" + requerimento.getId();

            for (UsuarioSigera usuario : destinatarios) {
                String emailDestinatario = usuario.getUsuarioLdap().getEmail();
                String nomeUsuario = usuario.getUsuarioLdap().getCn();

                String mensagem;

                if (requerimento.getTipo() == EnumTipoRequerimento.PLANO.getCodigo()) {
                    String professor = requerimento.getPlano().getTurma().getProfessor().getNome();
                    String nomeDisciplina = requerimento.getPlano().getTurma().getDisciplina().getNome();
                    String descricaoTurma = nomeDisciplina.concat(" - [ " + requerimento.getPlano().getTurma().getNome() + " ]");

                    mensagem = MessageFormat.format(prop.getProperty("ME.001.Plano.Concluir"),
                            nomeUsuario,
                            professor,
                            descricaoTurma,
                            linkRequerimento);
                } else {
                    mensagem = MessageFormat.format(prop.getProperty("ME.001.Corpo"),
                            nomeUsuario,
                            requerimento.getDescricaoTipo(),
                            nomeRequerente,
                            matriculaRequerente,
                            nomeCurso,
                            linkRequerimento);
                }
                adicionarEmail(emailDestinatario, assunto, mensagem);
            }

        } catch (IOException ex) {
            Logger.getLogger(GerenciadorEmail.class.getName()).log(Level.WARNING, null, ex);
        }
    }

    /*
     Mensagem de e-mail ME.001 - Só para um destinatário
     */
    public void adicionarEmailRequerimento(Requerimento requerimento, UsuarioSigera destinatario) {

        if (requerimento == null || requerimento.getId() == 0 || destinatario == null) {
            return;
        }

        try {
            Properties prop = new Properties();
            prop.load(GerenciadorEmail.class.getClassLoader().getResourceAsStream("mensagensEmail.properties"));

            String assunto = MessageFormat.format(prop.getProperty("ME.001"), requerimento.getDescricaoTipo());
            String nomeRequerente = requerimento.getUsuario().getUsuarioLdap().getCn();
            String matriculaRequerente = requerimento.getUsuario().getUsuarioLdap().getMatricula();
            String nomeCurso = requerimento.getCurso().getNome();
            String linkRequerimento = Conexoes.getURL_SIGERA() + "faces/usuario/requerimento.xhtml?numero=" + requerimento.getId();

            String emailDestinatario = destinatario.getUsuarioLdap().getEmail();
            String nomeUsuario = destinatario.getUsuarioLdap().getCn();

            String mensagem;

            if (requerimento.getTipo() == EnumTipoRequerimento.PLANO.getCodigo()) {
                String professor = requerimento.getPlano().getTurma().getProfessor().getNome();
                String nomeDisciplina = requerimento.getPlano().getTurma().getDisciplina().getNome();
                String descricaoTurma = nomeDisciplina.concat(" - [" + requerimento.getPlano().getTurma().getNome() + " ]");

                mensagem = MessageFormat.format(prop.getProperty("ME.001.Plano.Reabrir"),
                        professor,
                        descricaoTurma,
                        linkRequerimento);
            } else {
                mensagem = MessageFormat.format(prop.getProperty("ME.001.Corpo"),
                        nomeUsuario,
                        requerimento.getDescricaoTipo(),
                        nomeRequerente,
                        matriculaRequerente,
                        nomeCurso,
                        linkRequerimento);
            }
            adicionarEmail(emailDestinatario, assunto, mensagem);

        } catch (IOException ex) {
            Logger.getLogger(GerenciadorEmail.class.getName()).log(Level.WARNING, null, ex);
        }
    }

    /*
     Mensagem de e-mail ME.002
     */
    public void adicionarEmailParecer(BuscadorLdap buscadorLdap, Requerimento requerimento) {

        if (requerimento == null || requerimento.getId() == 0 || requerimento.getUsuario() == null) {
            return;
        }

        try {
            Properties prop = new Properties();
            prop.load(GerenciadorEmail.class.getClassLoader().getResourceAsStream("mensagensEmail.properties"));

            String assunto = MessageFormat.format(prop.getProperty("ME.002"), requerimento.getDescricaoTipo());
            String statusRequerimento = requerimento.getDescricaoStatus().toLowerCase();
            String linkRequerimento = Conexoes.getURL_SIGERA() + "faces/usuario/requerimento.xhtml?numero=" + requerimento.getId();
            String emailDestinatario;
            String nomeProfessor;
            String mensagem;            

            if (requerimento.getTipo() == EnumTipoRequerimento.PLANO.getCodigo()) {
                
                String nomeDisciplina = requerimento.getPlano().getTurma().getDisciplina().getNome();
                String descricaoTurma = nomeDisciplina.concat(" - [" + requerimento.getPlano().getTurma().getNome() + " ]");

                //recuperar usuarioLdap
                UsuarioLdap user = buscadorLdap.obtenhaUsuarioLdap(requerimento.getPlano().getTurma().getProfessor().getUsuario().getId());
                emailDestinatario = user.getEmail();
                nomeProfessor = user.getCn();

                mensagem = MessageFormat.format(prop.getProperty("ME.002.Corpo.Plano"),
                        nomeProfessor,
                        requerimento.getDescricaoTipo(),
                        descricaoTurma,
                        statusRequerimento,
                        linkRequerimento);
            } else {
                emailDestinatario = requerimento.getUsuario().getUsuarioLdap().getEmail();
                String nomeUsuario = requerimento.getUsuario().getUsuarioLdap().getCn();
                mensagem = MessageFormat.format(prop.getProperty("ME.002.Corpo"),
                        nomeUsuario,
                        requerimento.getDescricaoTipo(),
                        statusRequerimento,
                        linkRequerimento);
            }
            adicionarEmail(emailDestinatario, assunto, mensagem);

        } catch (IOException ex) {
            Logger.getLogger(GerenciadorEmail.class.getName()).log(Level.WARNING, null, ex);
        }
    }

    /*
     Mensagem de e-mail ME.003 - PLANO DE ENSINO
     */
    public void adicionarEmailRequerimento(RequerimentoPlano requerimento, UsuarioSigera destinatario, String ehPlano) {

        if (requerimento == null || requerimento.getId() == 0 || destinatario == null) {
            return;
        }

        try {
            Properties prop = new Properties();
            prop.load(GerenciadorEmail.class.getClassLoader().getResourceAsStream("mensagensEmail.properties"));

            String assunto = MessageFormat.format(prop.getProperty("ME.003"), requerimento.getDescricaoTipo());

            String disciplina = requerimento.getPlano().getTurma().getDisciplina().getNome();
            String nomeCurso = requerimento.getPlano().getTurma().getDisciplina().getCurso().getNome();
            String nomeRequerente = requerimento.getUsuario().getUsuarioLdap().getCn();

            String linkRequerimento = Conexoes.getURL_SIGERA() + "faces/usuario/requerimento.xhtml?numero=" + requerimento.getId();

            String emailDestinatario = destinatario.getUsuarioLdap().getEmail();
            String nomeProfessor = destinatario.getUsuarioLdap().getCn();

            String mensagem = MessageFormat.format(prop.getProperty("ME.003.Corpo"),
                    nomeProfessor,
                    disciplina,
                    nomeCurso,
                    nomeRequerente,
                    linkRequerimento);
            adicionarEmail(emailDestinatario, assunto, mensagem);

        } catch (IOException ex) {
            Logger.getLogger(GerenciadorEmail.class.getName()).log(Level.WARNING, null, ex);
        }
    }

    /*
     Mensagem de e-mail ME.101
     */
    public void adicionarEmailAtribuicaoPerfil(UsuarioSigera usuario) {

        if (!usuario.temAtribuicaoPerfil()) {
            return;
        }

        try {
            Properties prop = new Properties();
            prop.load(GerenciadorEmail.class.getClassLoader().getResourceAsStream("mensagensEmail.properties"));

            String emailDestinatario = usuario.getUsuarioLdap().getEmail();

            String assunto = prop.getProperty("ME.101");

            String nomeUsuario = usuario.getUsuarioLdap().getCn();
            String nomesPerfis = formateNomesPerfis(usuario.getPerfis());            
            String mensagem = MessageFormat.format(prop.getProperty("ME.101.Corpo"), nomeUsuario, nomesPerfis, Conexoes.getURL_SIGERA());

            adicionarEmail(emailDestinatario, assunto, mensagem);

        } catch (IOException ex) {
            Logger.getLogger(GerenciadorEmail.class.getName()).log(Level.WARNING, null, ex);
        }
    }

    /*
     Mensagem de e-mail ME.500
     */
    public void adicionarEmailSenhaResetada(UsuarioSigera usuario, String novaSenha) {

        try {
            Properties prop = new Properties();
            prop.load(GerenciadorEmail.class.getClassLoader().getResourceAsStream("mensagensEmail.properties"));

            String emailDestinatario = usuario.getUsuarioLdap().getEmailAternativo();

            String assunto = prop.getProperty("ME.004");

            String nomeUsuario = usuario.getUsuarioLdap().getCn();
            
            String login = usuario.getUsuarioLdap().getUid();

            String mensagem = MessageFormat.format(prop.getProperty("ME.004.Corpo"), nomeUsuario, login, novaSenha, Conexoes.getURL_SIGERA());

            adicionarEmail(emailDestinatario, assunto, mensagem);

        } catch (IOException ex) {
            Logger.getLogger(GerenciadorEmail.class.getName()).log(Level.WARNING, null, ex);
        }
    }

    private String formateNomesPerfis(Collection<AssociacaoPerfilCurso> colecaoPerfis) {
        Iterator perfis = colecaoPerfis.iterator();
        String nomesPerfis = ((AssociacaoPerfilCurso) perfis.next()).getPerfil().getNome();
        while (perfis.hasNext()) {
            String nomePerfil = ((AssociacaoPerfilCurso) perfis.next()).getPerfil().getNome();
            nomesPerfis += perfis.hasNext() ? ", " : " e ";
            nomesPerfis += nomePerfil;
        }

        return nomesPerfis;
    }

    private void adicionarEmail(String emailDestinatario, String assunto, String mensagem) {
        this.emailsEnviar.add(new Email(emailDestinatario, assunto, mensagem));
    }

    public void enviarEmails() {
        RemetenteEmail remetenteEmail = new RemetenteEmail(this.emailsEnviar);
        Thread threadEmail = new Thread(remetenteEmail);
        threadEmail.start();
    }
}