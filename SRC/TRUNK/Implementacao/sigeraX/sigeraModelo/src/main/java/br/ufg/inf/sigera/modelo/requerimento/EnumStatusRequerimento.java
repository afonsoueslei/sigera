package br.ufg.inf.sigera.modelo.requerimento;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum EnumStatusRequerimento {
    ABERTO(1, "Aberto"),
    DEFERIDO(2, "Deferido"),
    INDEFERIDO(3, "Indeferido"),
    CANCELADO(4,"Cancelado"),
    CONFERIDO(5,"Conferido"),
    CONCLUIDO(6,"Concluído"); //Quando um plano foi concluído pelo professor.
    
    private int codigo;
    private String nome;
    
    private static final Map<Integer, EnumStatusRequerimento> tiposStatusRequerimento
                = new HashMap<Integer, EnumStatusRequerimento>();                

    static {
        for(EnumStatusRequerimento p : EnumSet.allOf(EnumStatusRequerimento.class)) {
            tiposStatusRequerimento.put(p.getCodigo(), p);
        }
    }
    
    EnumStatusRequerimento(int codigo, String nome) {
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
    
    public static EnumStatusRequerimento obtenha(int codigo) {
        return tiposStatusRequerimento.get(codigo);
    }
}