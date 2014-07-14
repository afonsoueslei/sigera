package br.ufg.inf.sigera.modelo.ldap;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum EnumGrupo {
    PROFESSOR("101"),
    FUNCIONARIO("201"),
    ALUNO("500"),
    OUTROS("");            
    
    private static final Map<String, EnumGrupo> grupos
                = new HashMap<String, EnumGrupo>();                

    static {
        for(EnumGrupo g : EnumSet.allOf(EnumGrupo.class)) {
            grupos.put(g.getCodigo(), g);
        }
    }
    
    private String codigo;
    
    EnumGrupo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
    
    public static EnumGrupo obtenha(String codigo) {
        EnumGrupo grupo = grupos.get(codigo);        
        if (grupo != null) return grupo;        
        return OUTROS;        
    }
}