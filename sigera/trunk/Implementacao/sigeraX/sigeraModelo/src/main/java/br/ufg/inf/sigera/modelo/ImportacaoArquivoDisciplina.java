package br.ufg.inf.sigera.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.persistence.RollbackException;
import org.primefaces.model.UploadedFile;

public class ImportacaoArquivoDisciplina extends ImportacaoArquivo {

    private final int NUMERO_VARIAVEIS = 9;
    private final String ID = "id";
    private final String CH_PRATICA = "cargahorariapratica";
    private final String CH_TEORICA = "cargahorariateorica";
    private final String EMENTA = "ementa";
    private final String NOME = "nome";
    private final String CURSO_ID = "curso_id";
    private final String BL_BASICA = "bibliografiabasica";
    private final String BL_COMPLEMENTAR = "bibliografiacomplementar";
    private final String OBJETIVO_GERAL = "objetivogeral";

    public boolean validaArquivo(UploadedFile arquivo) {
        //As variaves deve ser enviadas na ordem em que estarao no arquivo.
        return super.validaArquivo(arquivo, NUMERO_VARIAVEIS, ID, CH_PRATICA, CH_TEORICA, EMENTA, NOME, CURSO_ID, BL_BASICA, BL_COMPLEMENTAR, OBJETIVO_GERAL);
    }

    public ArrayList<DisciplinaImportada> importaArquivo(UploadedFile arquivo) {

        ArrayList<DisciplinaImportada> disciplinasImportadas = extraiConteudo(arquivo);
        for (DisciplinaImportada dImportada : disciplinasImportadas) {
            if (dImportada.getImportacaoOK()) {
                try {
                    Disciplina.salvar(dImportada.getDisciplina());
                    dImportada.setMotivo("Disciplina importada com Sucesso");
                    dImportada.setImportacaoOK(true);

                } catch (RollbackException e) {
                    dImportada.setMotivo("Já existe uma disciplina com esse ID");
                    dImportada.setImportacaoOK(false);
                }
            }
        }
        return disciplinasImportadas;
    }

    private ArrayList<DisciplinaImportada> extraiConteudo(UploadedFile arquivo) {
        try {
            InputStream stream = arquivo.getInputstream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String linha = reader.readLine();
            ArrayList<DisciplinaImportada> dImportadas = new ArrayList<DisciplinaImportada>();
            while ((linha = reader.readLine()) != null) {
                DisciplinaImportada dImportada = new DisciplinaImportada();
                Disciplina disciplina = new Disciplina();
                String[] conteudo = super.extraiLinha(linha);
                try {
                    //ID, CH_PRATICA, CH_TEORICA, EMENTA, NOME, CURSO_ID, BL_BASICA, BL_COMPLEMENTAR, OBJETIVO_GERAL
                    disciplina.setId(converteIDParaInteiro(conteudo[0], "ID"));
                    disciplina.setHoraPratica(converteIDParaInteiro(conteudo[1], "Carga Horária Prática"));
                    disciplina.setHoraTeorica(converteIDParaInteiro(conteudo[2], "Carga Horária Teórica"));
                    disciplina.setEmenta(conteudo[3]);
                    disciplina.setNome(conteudo[4]);
                    disciplina.setCurso(buscaCurso(conteudo[5]));
                    disciplina.setBibliografiaBasica(conteudo[6]);
                    disciplina.setBibliografiaComplementar(conteudo[7]);
                    disciplina.setObjetivoGeral(conteudo[8]);
                    dImportada.setImportacaoOK(true);

                } catch (NumberFormatException e) {
                    disciplina.setNome(conteudo[4]);
                    dImportada.setImportacaoOK(false);
                    dImportada.setMotivo(e.getMessage());

                } catch (Exception e) {
                    disciplina.setNome(conteudo[4]);
                    dImportada.setImportacaoOK(false);
                    dImportada.setMotivo(e.getMessage());
                } finally {
                    dImportada.setDisciplina(disciplina);
                    dImportadas.add(dImportada);
                }
            }
            return dImportadas;

        } catch (IOException ex) {
            //Logger.getLogger(ImportacaoBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private Curso buscaCurso(String curso) throws Exception, NumberFormatException {
        Curso c = Curso.obtenhaCurso(converteIDParaInteiro(curso, "Curso"));
        if (c == null) {
            throw new Exception("Não exite curso com o ID especificado.");
        } else {
            return c;
        }
    }
}
