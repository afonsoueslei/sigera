package br.ufg.inf.sigera.modelo;

import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.persistence.RollbackException;
import org.primefaces.model.UploadedFile;

public class ImportacaoArquivoTurma extends ImportacaoArquivo {
//id	ano	nome	semestre	disciplina_id	professor_id

    private final int NUMERO_VARIAVEIS_TURMA = 6;
    private final String ID = "id";
    private final String ANO = "ano";
    private final String NOME = "nome";
    private final String SEMESTRE = "semestre";
    private final String DISCIPLINA = "disciplina_id";
    private final String PROFESSOR = "professor_id";
    private BuscadorLdap buscadorLdap;

    public ImportacaoArquivoTurma(BuscadorLdap buscadorLdap) {
        super();
        this.buscadorLdap = buscadorLdap;
    }

    public boolean validaArquivo(UploadedFile arquivo) {
        return super.validaArquivo(arquivo, NUMERO_VARIAVEIS_TURMA, ID, ANO, NOME, SEMESTRE, DISCIPLINA, PROFESSOR);
    }

    public ArrayList<TurmaImportada> importaArquivo(UploadedFile arquivo) {

        ArrayList<TurmaImportada> turmasImportadas = extraiConteudo(arquivo);
        for (TurmaImportada dImportada : turmasImportadas) {
            if (dImportada.getImportacaoOK()) {
                try {
                    Turma.salvar(dImportada.getTurma());
                    dImportada.setMotivo("Turma importada com Sucesso");
                    dImportada.setImportacaoOK(true);

                } catch (RollbackException e) {
                    dImportada.setMotivo("Já existe uma turma com esse ID");
                    dImportada.setImportacaoOK(false);
                }
            }
        }
        return turmasImportadas;
    }

    private ArrayList<TurmaImportada> extraiConteudo(UploadedFile arquivo) {
        try {
            InputStream stream = arquivo.getInputstream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String linha = reader.readLine();
            ArrayList<TurmaImportada> tImportadas = new ArrayList<TurmaImportada>();
            while ((linha = reader.readLine()) != null) {
                TurmaImportada dImportada = new TurmaImportada();
                Turma turma = new Turma();
                String[] conteudo = super.extraiLinha(linha);
                try {
                    //id	ano	nome	semestre	disciplina_id	professor_id                      turma.setId(converteIDParaInteiro(conteudo[0], "ID"));
                    turma.setId(converteIDParaInteiro(conteudo[0], "ID"));
                    turma.setAno(converteIDParaInteiro(conteudo[1], "Ano"));
                    turma.setNome(conteudo[2]);
                    turma.setSemestre(converteIDParaInteiro(conteudo[3], "Semestre"));
                    turma.setDisciplina(buscaDisciplina(conteudo[4]));
                    turma.setProfessor(buscaProfessor(conteudo[5]));
                    dImportada.setImportacaoOK(true);

                } catch (NumberFormatException e) {
                    dImportada.setImportacaoOK(false);
                    dImportada.setMotivo(e.getMessage());
                } catch (Exception e) {
                    turma.setNome(conteudo[2]);
                    dImportada.setImportacaoOK(false);
                    dImportada.setMotivo(e.getMessage());

                } finally {
                    dImportada.setTurma(turma);
                    tImportadas.add(dImportada);
                }
            }
            return tImportadas;

        } catch (IOException ex) {
            //Logger.getLogger(ImportacaoBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private Disciplina buscaDisciplina(String id) throws Exception, NumberFormatException {
        Disciplina d = Disciplina.obtenhaDisciplina(converteIDParaInteiro(id, "Disciplina"));
        if (d == null) {
            throw new Exception("Não exite Disciplina com o ID especificado.");
        } else {
            return d;
        }
    }

    private Professor buscaProfessor(String id) throws Exception, NumberFormatException {
        Professor p = Professor.obtenhaProfessor(buscadorLdap, converteIDParaInteiro(id, "Professor"));
        if (p == null) {
            throw new Exception("Não exite Professor com o ID especificado.");
        } else {
            return p;
        }
    }
}
