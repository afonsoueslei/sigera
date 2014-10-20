package br.ufg.inf.sigera.modelo.requerimento;

import br.ufg.inf.sigera.modelo.servico.Conexoes;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "anexo")
public class Anexo {

    @Id
    @GeneratedValue
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "requerimento_id")
    private Requerimento requerimento;
    
    @Column(name = "caminho")
    private String caminho;
    
    @Column(name = "nome")
    private String nome;

    @Transient
    private String tamanho;
    
    @Transient
    private final String CAMINHO_ARQUIVOS = Conexoes.getPASTA_ANEXOS();
    
    public Anexo() {
    }

    public Anexo(String nomeArquivo, String tamanho, InputStream is) {
        String caminhoCompleto = graveArquivo(CAMINHO_ARQUIVOS, nomeArquivo, is);
        setCaminho(caminhoCompleto);
        setNome(nomeArquivo);    
        setTamanho(tamanho);
    }
    
    private String graveArquivo(String caminhoDestino, String nomeArquivo, InputStream is) {        
        try {            
            SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
            String name = fmt.format(new Date())
                    + "_" + nomeArquivo;
            
            File diretorioAnexos = new File(caminhoDestino + "anexos/");
            if (!diretorioAnexos.exists()) {                
                diretorioAnexos.mkdir();
            }
            
            String caminhoCompleto = caminhoDestino + "anexos/" + name;
            File file = new File(caminhoCompleto);

            OutputStream out = new FileOutputStream(file);
            byte buf[] = new byte[1024];
            int len;
            while ((len = is.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            is.close();
            out.close();
            
            return caminhoCompleto;            
        } catch (IOException e) {
            Logger.getLogger(Anexo.class.getName()).log(Level.WARNING, null, e);  
            return null;
        }        
    }    

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Requerimento getRequerimento() {
        return requerimento;
    }

    public void setRequerimento(Requerimento requerimento) {
        this.requerimento = requerimento;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public void removerArquivo() {
        try {
            File file = new File(this.caminho);
            file.delete();
        }
        catch(Exception ex) {
            Logger.getLogger(Anexo.class.getName()).log(Level.WARNING, null, ex);  
        }        
    }
}