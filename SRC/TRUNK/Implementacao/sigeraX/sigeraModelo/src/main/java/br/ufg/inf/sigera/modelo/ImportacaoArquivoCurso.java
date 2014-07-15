package br.ufg.inf.sigera.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.persistence.RollbackException;
import org.primefaces.model.UploadedFile;

public class ImportacaoArquivoCurso extends ImportacaoArquivo {

    private final int NUMERO_VARIAVEIS = 5;
    private final String ID = "id";
    private final String PREFIXO = "prefixo";
    private final String CODIGO_MATRIZ_CURRICULAR = "codigomatrizcurricular";
    private final String NOME = "nome";
    private final String UNIDADE = "unidadeid";

    public boolean validaArquivo(UploadedFile arquivo) {
        //As variaves deve ser enviadas na ordem em que estarao no arquivo.
        return super.validaArquivo(arquivo, NUMERO_VARIAVEIS, ID, PREFIXO, CODIGO_MATRIZ_CURRICULAR, NOME, UNIDADE);
    }

    public ArrayList<CursoImportado> importaArquivo(UploadedFile arquivo) {

        ArrayList<CursoImportado> cursosImportados = extraiConteudo(arquivo);
        for (CursoImportado cImportado : cursosImportados) {
            if (cImportado.getImportacaoOK()) {
                try {
                    Curso.salvar(cImportado.getCurso());
                    cImportado.setMotivo("Curso importado Com Sucesso");
                    cImportado.setImportacaoOK(true);

                } catch (RollbackException e) {
                    cImportado.setMotivo("Já existe um curso com esse ID");
                    cImportado.setImportacaoOK(false);
                }
            }
        }
        return cursosImportados;
    }

    private ArrayList<CursoImportado> extraiConteudo(UploadedFile arquivo) {
        try {
            InputStream stream = arquivo.getInputstream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String linha = reader.readLine();
            ArrayList<CursoImportado> cImportados = new ArrayList<CursoImportado>();
            while ((linha = reader.readLine()) != null) {
                CursoImportado cImportado = new CursoImportado();
                Curso curso = new Curso();
                String[] conteudo = super.extraiLinha(linha);
                try {
                    curso.setId(converteIDParaInteiro(conteudo[0], "ID"));
                    curso.setPrefixo(conteudo[1]);
                    curso.setCodMatriz(conteudo[2]);
                    curso.setNome(conteudo[3]);
//                    curso.setUnidade(Unidade.obtenhaUnidadePadrao());
                    curso.setUnidade(Unidade.obtenhaUnidade(Integer.parseInt(conteudo[4])));
                    cImportado.setImportacaoOK(true);

                } catch (Exception e) {
                    curso.setPrefixo(conteudo[1]);
                    curso.setCodMatriz(conteudo[2]);
                    curso.setNome(conteudo[3]);
                    //curso.setUnidade(Unidade.obtenhaUnidadePadrao());
                    curso.setUnidade(Unidade.obtenhaUnidade(Integer.parseInt(conteudo[4])));
                    cImportado.setImportacaoOK(false);
                    cImportado.setMotivo(e.getMessage());
                } finally {
                    cImportado.setCurso(curso);
                    cImportados.add(cImportado);
                }
            }
            return cImportados;

        } catch (IOException ex) {
            //Logger.getLogger(ImportacaoBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
