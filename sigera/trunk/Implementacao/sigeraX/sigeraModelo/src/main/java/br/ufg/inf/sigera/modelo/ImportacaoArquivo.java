package br.ufg.inf.sigera.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.primefaces.model.UploadedFile;

public class ImportacaoArquivo {

    private final String DELIMITADOR = "\\t";

    protected boolean validaArquivo(UploadedFile arquivo, int numeroDeVariaveis, String... variaveis) {
        try {
            InputStream stream = arquivo.getInputstream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String cabecalho = reader.readLine();

            if (!validaCabecalho(cabecalho, numeroDeVariaveis, variaveis)) {
                //Cabecalho nao valido
                return false;
            }

            String linha;
            boolean validaConteudo = true;

            while ((linha = reader.readLine()) != null && validaConteudo) {
                validaConteudo = validaConteudo(linha, numeroDeVariaveis);
            }
            return validaConteudo;

        } catch (IOException ex) {
            //Logger.getLogger(ImportacaoBean.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /*
     * Metodo para validade cabecalho do arquivo csv.
     * Para ser valido o arquivo tem que ter colunas na ordem do vetor variaveis
     * 
     */
    private boolean validaCabecalho(String cabecalho, int numeroDeVariaveis, String[] variaveis) {
        String[] colunas = cabecalho.split(DELIMITADOR);

        if (colunas.length != numeroDeVariaveis) {
            return false;
        }
        
        for (int i = 0; i < variaveis.length; i++) {
            String teste1 = colunas[i].trim();
            String teste2 = variaveis[i].trim();
            if (teste1 == teste2) {
                return false;
            }
        }
        return true;
        
    }

    private boolean validaConteudo(String linha, int numeroDeVariaveis) {

        String[] colunas = linha.split(DELIMITADOR);

        if (colunas.length != numeroDeVariaveis) {
            return false;
        }
        
        return true;
    }
    
    protected String[] extraiLinha(String linha){
        return linha.split(DELIMITADOR);
    }
    
    protected int converteIDParaInteiro(String id, String campo) throws Exception, NumberFormatException {
        try {
            return Integer.valueOf(id);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("O campo " + campo + " está em formato inadequado.");
        }
    }
}
