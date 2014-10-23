package br.ufg.inf.sigera.modelo.ldap;

import br.ufg.inf.sigera.modelo.servico.CodificarSenha;
import br.ufg.inf.sigera.modelo.servico.Conexoes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class AutenticacaoLdap {

    public UsuarioLdap efetuaLogin(String login, String senha) {
        String pathUser = "uid=" + login + ", ou=Users, dc=inf, dc=ufg, dc=br";
        UsuarioLdap usuario = null;
        try {
            DirContext dir = this.getContext(pathUser, senha);
            Attributes matchAttrs = new BasicAttributes(false);
            usuario = buscaDados(login, dir, matchAttrs);
        } catch (CommunicationException cex) {
            Logger.getLogger(AutenticacaoLdap.class.getName()).log(Level.SEVERE, null, cex);
            return new UsuarioLdap("FalhaConexaoLDAP", "FalhaConexaoLDAP", "FalhaConexaoLDAP", "FalhaConexaoLDAP", EnumGrupo.OUTROS, "FalhaConexaoLDAP");
        } catch (NamingException nex) {
            Logger.getLogger(AutenticacaoLdap.class.getName()).log(Level.SEVERE, null, nex);
        }
        return usuario;
    }

    public DirContext getContext(String login, String senha) throws NamingException {
        Hashtable env = new Hashtable(11);
        Conexoes.lerParametros();
        env.put(Context.INITIAL_CONTEXT_FACTORY, Conexoes.getINITIAL_CTX());
        env.put(Context.PROVIDER_URL, Conexoes.getSERVIDOR_LDAP());
        env.put(Context.SECURITY_AUTHENTICATION, "simple");

        env.put(Context.SECURITY_PRINCIPAL, login);
        env.put(Context.SECURITY_CREDENTIALS, senha);

        return new InitialDirContext(env);
    }

    public DirContext getContextAdmin() throws NamingException {
        Hashtable env = new Hashtable(11);
        Conexoes.lerParametros();
        env.put(Context.INITIAL_CONTEXT_FACTORY, Conexoes.getINITIAL_CTX());
        env.put(Context.PROVIDER_URL, Conexoes.getSERVIDOR_LDAP());
        env.put(Context.SECURITY_AUTHENTICATION, "simple");

        env.put(Context.SECURITY_PRINCIPAL, Conexoes.getPATH_ADMIN());
        env.put(Context.SECURITY_CREDENTIALS, Conexoes.getKEY_ADMIN());

        return new InitialDirContext(env);
    }

    private UsuarioLdap buscaDados(String login, DirContext ctx, Attributes matchAttrs) {
        UsuarioLdap usuario = null;
        Conexoes.lerParametros();
        String[] atributosRetorno = new String[]{"uid", "mail", "cn", "uidNumber", "gidNumber", "registeredAddress"};
        SearchControls searchCtls = new SearchControls();
        searchCtls.setReturningAttributes(atributosRetorno);

        String searchFilter = "(&(objectClass=*)(uid=" + login + "))";

        try {
            NamingEnumeration resultado = ctx.search(
                    Conexoes.getSEARCHBASE(), searchFilter, searchCtls);

            if (resultado.hasMore()) {
                SearchResult sr = (SearchResult) resultado.next();
                Attributes atributos = sr.getAttributes();
                usuario = constroiUsuario(atributos);
                if (usuario != null) {
                    // Quando o usuário efetua o login ele obtém uma instância do objeto que permite
                    // que ele pesquise informações do LDAP.
                    usuario.setBuscadorLdap(new CacheLdap(login, ctx, matchAttrs));
                    return usuario;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public void alterarCampoUsuarioLdap(String login, DirContext ctx, String campoQueSeraAlterado, String novoValor) {

        String pathUser = "uid=" + login + ", ou=Users, dc=inf, dc=ufg, dc=br";

        Attributes matchAttrs = new BasicAttributes(false);
        Conexoes.lerParametros();

        try {
            NamingEnumeration resultado = ctx.search(
                    Conexoes.getSEARCHBASE(), matchAttrs);

            if (resultado.hasMore()) {
                SearchResult sr = (SearchResult) resultado.next();
                Attributes atributos = sr.getAttributes();
                for (NamingEnumeration todosAtributos = atributos.getAll(); todosAtributos.hasMore();) {
                    Attribute attrib = (Attribute) todosAtributos.next();
                    String nomeAtributo = attrib.getID();

                    if (campoQueSeraAlterado.equalsIgnoreCase("userPassword")) {
                        novoValor = CodificarSenha.hashMD5(novoValor);
                    }
                    ModificationItem[] moit = {new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(campoQueSeraAlterado, novoValor))};
                    ctx.modifyAttributes(pathUser, moit);
                    ctx.close();
                    break;
                }

            }
        } catch (AuthenticationException authEx) {
            System.out.println("Erro na autenticação! ");
        } catch (NamingException namEx) {
            System.out.println("Problemas na conexão! ");
        }
    }

    private String getValorAtributo(Attributes atributos, String nomeAtributo) throws NamingException {
        if (nomeAtributo.equalsIgnoreCase("registeredAddress")) {
            return atributos.get(nomeAtributo) == null ? "" : atributos.get(nomeAtributo).get().toString();
        }
        return atributos.get(nomeAtributo) == null ? null : atributos.get(nomeAtributo).get().toString();
    }

    private UsuarioLdap constroiUsuario(Attributes atributos) throws NamingException {
        return new UsuarioLdap(getValorAtributo(atributos, "uid"),
                getValorAtributo(atributos, "mail"),
                getValorAtributo(atributos, "cn"),
                getValorAtributo(atributos, "uidNumber"),
                EnumGrupo.obtenha(getValorAtributo(atributos, "gidNumber")),
                getValorAtributo(atributos, "registeredAddress")
        );
    }

    // Esta classe é interna para que seu construtor seja acessível somente 
    // a partir da classe AutenticacaoLdap, já que apenas um usuário que efetuou login no LDAP
    // é que pode obter uma instância do objeto de pesquisa de informações no LDAP.
    private class CacheLdap implements BuscadorLdap {

        List<UsuarioLdap> usuariosLdap;
        Map<String, UsuarioLdap> usuariosPorUid;
        Map<Integer, UsuarioLdap> usuariosPorUidNumber;

        private CacheLdap(String login, DirContext ctx, Attributes matchAttrs) {
            this.usuariosLdap = buscaDadosTodosUsuarios(login, ctx, matchAttrs);
            this.usuariosPorUid = construaMapaUsuariosPorUid(this.usuariosLdap);
            this.usuariosPorUidNumber = construaMapaUsuariosPorUidNumber(this.usuariosLdap);
        }

        private List<UsuarioLdap> buscaDadosTodosUsuarios(String login, DirContext ctx, Attributes matchAttrs) {
            List<UsuarioLdap> usuarios = new ArrayList<UsuarioLdap>();
            String[] atributosRetorno = new String[]{"uid", "mail", "cn", "uidNumber", "gidNumber", "registeredAddress"};
            Conexoes.lerParametros();
            try {

                NamingEnumeration resultado = ctx.search(
                        Conexoes.getSEARCHBASE(), matchAttrs, atributosRetorno);

                while (resultado.hasMore()) {
                    SearchResult sr = (SearchResult) resultado.next();
                    Attributes atributos = sr.getAttributes();
                    if (atributos.size() > 0) {
                        usuarios.add(constroiUsuario(atributos));
                    } else {
                        System.out.println("Fallha! Registro não possui nenhum atributo.");
                    }

                }
            } catch (NamingException e) {
                return null;
            }
            return usuarios;
        }

        private Map<String, UsuarioLdap> construaMapaUsuariosPorUid(List<UsuarioLdap> usuarios) {
            Map<String, UsuarioLdap> mapa = new HashMap<String, UsuarioLdap>();

            for (UsuarioLdap usuario : usuarios) {
                mapa.put(usuario.getUid(), usuario);
            }

            return mapa;
        }

        private Map<Integer, UsuarioLdap> construaMapaUsuariosPorUidNumber(List<UsuarioLdap> usuarios) {
            Map<Integer, UsuarioLdap> mapa = new HashMap<Integer, UsuarioLdap>();

            for (UsuarioLdap usuario : usuarios) {
                if (usuario.getUidNumber() != null) {
                    mapa.put(Integer.valueOf(usuario.getUidNumber()), usuario);
                } else {
                    System.out.println("Falha: Usuario: " + usuario.getUid() + " não possui UidNumber");
                }
            }

            return mapa;
        }

        public List<UsuarioLdap> obtenhaTodosUsuariosLdap() {
            return this.usuariosLdap;
        }

        public UsuarioLdap obtenhaUsuarioLdap(int id) {
            return this.usuariosPorUidNumber.get(id);
        }

        public UsuarioLdap obtenhaUsuarioLdap(String nomeUsuario) {
            return this.usuariosPorUid.get(nomeUsuario);
        }
    }

}
