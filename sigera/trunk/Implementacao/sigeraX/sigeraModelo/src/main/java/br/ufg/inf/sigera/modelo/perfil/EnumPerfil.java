package br.ufg.inf.sigera.modelo.perfil;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum EnumPerfil {
    ADMINISTRADOR_SISTEMA(1, "Administrador do Sistema"),
    ALUNO(2, "Aluno"),
    COORDENADOR_CURSO(3, "Coordenador de Curso"),
    COORDENADOR_ESTAGIO(4, "Coordenador de Estágio"),
    PROFESSOR(5, "Professor"),
    SECRETARIA(6, "Secretário(a) de Curso"),
    DIRETOR(7, "Diretor"),
    SECRETARIA_GRADUACAO(8, "Secretário(a) de Graduação"),
    COORDENADOR_GERAL(9, "Coordenador Geral");
    
    private int codigo;
    private String nome;
    
    private static final Map<Integer, EnumPerfil> perfis
                = new HashMap<Integer, EnumPerfil>();                

    static {
        for(EnumPerfil p : EnumSet.allOf(EnumPerfil.class)) {
            perfis.put(p.getCodigo(), p);
        }
    }
    
    EnumPerfil(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public static EnumPerfil obtenha(int codigo) {
        return perfis.get(codigo);
    }
}
