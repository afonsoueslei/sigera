package br.ufg.inf.sigera.modelo.ldap;

public class UsuarioLdap {

    private String uid; // login de usuário no LDAP
    private final String email;
    private final String cn; // nome completo do usuário
    private String uidNumber; // número único de identificação de cada usuário
    private EnumGrupo grupo;
    private String prefixoCurso;
    private String matricula;
    private String emailAternativo;
    private BuscadorLdap buscadorLdap; // O usuário só possui uma instância deste objeto se tiver efetuado login no LDAP.

    public UsuarioLdap(String uid, String email, String cn, String uidNumber, EnumGrupo grupo, String emailAlternativo) {
        this.uid = uid;
        this.email = email;
        this.cn = cn;
        this.uidNumber = uidNumber;
        this.grupo = grupo;
        this.emailAternativo = emailAlternativo;
        obterPrefixoCursoMatricula();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public String getCn() {
        return cn;
    }

    public String getUidNumber() {
        return uidNumber;
    }

    public void setUidNumber(String uidNumber) {
        this.uidNumber = uidNumber;
    }

    public EnumGrupo getGrupo() {
        return grupo;
    }

    public void setGrupo(EnumGrupo grupo) {
        this.grupo = grupo;
    }
    
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula == null ? "" : matricula;
    }

    public String getPrefixoCurso() {
        return prefixoCurso;
    }   

    public String getEmailAternativo() {
        if(possuiEmailAlternativo()){
            return emailAternativo;
        }
        return ""; 
    }

    public void setEmailAternativo(String emailAternativo) {
        this.emailAternativo = emailAternativo;
    }
    
    public BuscadorLdap getBuscadorLdap() {
        return buscadorLdap;
    }

    public void setBuscadorLdap(BuscadorLdap pesquisaLdap) {
        this.buscadorLdap = pesquisaLdap;
    }    
    
    private void obterPrefixoCursoMatricula() {
        String cursotmp = null;
        String matriculatmp = null;

        for (int i = 0; i < uid.length(); i++) {
            if (uid.charAt(i) < 65) {
                cursotmp = uid.substring(0, i);
                matriculatmp = uid.substring(i);
                break;
            }
        }
        
        prefixoCurso = cursotmp;
        setMatricula(matriculatmp);
    }
    
    public static String obtenhaPrefixoCurso(String uid) {
        String cursotmp = null;

        for (int i = 0; i < uid.length(); i++) {
            if (uid.charAt(i) < 65) {
                cursotmp = uid.substring(0, i);
                break;
            }
        }
        
        return cursotmp;
    }
    public boolean possuiEmailAlternativo(){
        if(emailAternativo == null || emailAternativo.isEmpty() ){
            return false;
        }
        return true;
    }
    
    
    public boolean isAlunoRegularPosStrictoSensu(){
        return this.getPrefixoCurso()!= null && (this.getPrefixoCurso().endsWith("SC") || this.getPrefixoCurso().endsWith("sc")) && this.getUid().charAt(7) == '0';       
    }
    
}