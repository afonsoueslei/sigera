package br.ufg.inf.sigera.modelo;

public class ArquivoImportado {
    
    private String motivo;
    private boolean importacaoOK;
    
     public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean getImportacaoOK() {
        return importacaoOK;
    }
    
    public void setImportacaoOK(boolean importacaoOK){
        this.importacaoOK = importacaoOK;
    }
}
