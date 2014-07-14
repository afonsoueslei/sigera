package br.ufg.inf.sigera.modelo;

import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import br.ufg.inf.sigera.modelo.ldap.UsuarioLdap;
import br.ufg.inf.sigera.modelo.perfil.EnumPerfil;
import br.ufg.inf.sigera.modelo.perfil.PerfilProfessor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import org.primefaces.model.UploadedFile;

public class ImportacaoArquivoProfessor extends ImportacaoArquivo {

    private final int NUMERO_VARIAVEIS_TURMA = 2;
    private final String ID = "id";
    private final String USUARIO_LDAP = "usuario_ldap";
    private BuscadorLdap buscadorLdap;

    public ImportacaoArquivoProfessor(BuscadorLdap buscadorLdap) {
        super();
        this.buscadorLdap = buscadorLdap;
    }

    public boolean validaArquivo(UploadedFile arquivo) {
        return super.validaArquivo(arquivo, NUMERO_VARIAVEIS_TURMA, ID, USUARIO_LDAP);
    }

    public ArrayList<ProfessorImportado> importaArquivo(UploadedFile arquivo) {

        ArrayList<ProfessorImportado> professoresImportados = extraiConteudo(arquivo);
        for (ProfessorImportado pImportado : professoresImportados) {
            if (pImportado.getImportacaoOK()) {
                try {

                    Professor.salvar(pImportado.getProfessor());
                    pImportado.setMotivo("Professor importado com Sucesso");
                    pImportado.setImportacaoOK(true);

                } catch (Exception e) {
                    pImportado.setMotivo(e.getMessage());
                    pImportado.setImportacaoOK(false);
                }
            }
        }
        return professoresImportados;
    }

    private ArrayList<ProfessorImportado> extraiConteudo(UploadedFile arquivo) {
        try {
            InputStream stream = arquivo.getInputstream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String linha = reader.readLine();
            ArrayList<ProfessorImportado> pImportados = new ArrayList<ProfessorImportado>();
            while ((linha = reader.readLine()) != null) {

                ProfessorImportado pImportado = new ProfessorImportado();
                Professor professor = new Professor();
                String[] conteudo = super.extraiLinha(linha);

                try {
                    professor.setId(converteIDParaInteiro(conteudo[0], "ID"));
                    professor.setUsuario(buscaUsuarioSigera(converteIDParaInteiro(conteudo[0], "ID"), conteudo[1]));
                    pImportado.setImportacaoOK(true);

                } catch (Exception e) {
                    professor.setUsuario(null);
                    pImportado.setImportacaoOK(false);
                    pImportado.setMotivo(e.getMessage());

                } finally {
                    pImportado.setProfessor(professor);
                    pImportados.add(pImportado);
                }
            }
            return pImportados;

        } catch (IOException ex) {
            //Logger.getLogger(ImportacaoBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private UsuarioSigera buscaUsuarioSigera(int idProfessor, String loginUnico) throws Exception {

        UsuarioLdap uLdap = buscadorLdap.obtenhaUsuarioLdap(loginUnico);

        if (uLdap == null) {
            throw new Exception("Não existe este usuário cadastrado no LDAP.");
        }

        UsuarioSigera uSigera = UsuarioSigera.obtenhaUsuarioSigera(Integer.valueOf(uLdap.getUidNumber()));
        if (uSigera == null) {
            uSigera = new UsuarioSigera();
            uSigera.setId(Integer.valueOf(uLdap.getUidNumber()));
        }

        Professor professor = Professor.obtenhaProfessor(idProfessor);
        if (professor != null && professor.getUsuario().getId() != uSigera.getId()) {
            throw new Exception("Já existe um professor com esse ID");
        }

        if (professor == null) {
            professor = Professor.obtenhaProfessorPorIdUsuario(uSigera.getId());
            if (professor != null) {
                throw new Exception("Usuário " + loginUnico + " está cadastrado como professor pelo ID " + professor.getId());
            }

        }


        Collection<AssociacaoPerfilCurso> perfis = uSigera.getPerfis();
        if (perfis != null) {
            if (!possuiPerfilProfessor(perfis)) {
                AssociacaoPerfilCurso apCurso = new AssociacaoPerfilCurso();
                apCurso.setPerfil(new PerfilProfessor());
                apCurso.setUsuario(uSigera);
                perfis.add(apCurso);
                uSigera.setPerfis(perfis);
            }
        } else {
            perfis = new ArrayList<AssociacaoPerfilCurso>();
            AssociacaoPerfilCurso apCurso = new AssociacaoPerfilCurso();
            apCurso.setPerfil(new PerfilProfessor());
            apCurso.setUsuario(uSigera);
            perfis.add(apCurso);
            uSigera.setPerfis(perfis);
        }

        uSigera.setUsuarioLdap(uLdap);


        return uSigera;
    }

    private boolean possuiPerfilProfessor(Collection<AssociacaoPerfilCurso> perfis) {
        for (AssociacaoPerfilCurso apCurso : perfis) {
            if (apCurso.getPerfil().getId() == EnumPerfil.PROFESSOR.getCodigo()) {
                return true;
            }
        }
        return false;
    }
}
