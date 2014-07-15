package br.ufg.inf.sigera.modelo.requerimento;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum EnumTipoRequerimento {
    ACRESCIMO_DISCIPLINAS(1, "Acréscimo de disciplinas"),
    CANCELAMENTO_DISCIPLINAS(2, "Cancelamento de disciplinas"),
    SEGUNDA_CHAMADA(3, "Segunda chamada de prova"),
    DECLARACAO_MATRICULA(4, "Declaração de matrícula"),
    EXTRATO_ACADEMICO(5, "Extrato acadêmico"),
    EMENTAS(6, "Ementas de disciplinas"),
    ASSINATURA(7, "Assinatura de contratos/relatórios de estágio"),
    PLANO(8,"Plano de Ensino");
    
    private int codigo;
    private String nome;
    
    private static final Map<Integer, EnumTipoRequerimento> tiposRequerimento
                = new HashMap<Integer, EnumTipoRequerimento>();                

    static {
        for(EnumTipoRequerimento p : EnumSet.allOf(EnumTipoRequerimento.class)) {
            tiposRequerimento.put(p.getCodigo(), p);
        }
    }
    
    EnumTipoRequerimento(int codigo, String nome) {
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
    
    public static EnumTipoRequerimento obtenha(int codigo) {
        return tiposRequerimento.get(codigo);
    }
}