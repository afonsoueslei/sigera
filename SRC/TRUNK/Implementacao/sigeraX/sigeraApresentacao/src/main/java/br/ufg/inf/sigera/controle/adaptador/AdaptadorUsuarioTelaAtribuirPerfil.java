package br.ufg.inf.sigera.controle.adaptador;

import br.ufg.inf.sigera.controle.tela.UsuarioTelaAtribuirPerfil;
import br.ufg.inf.sigera.modelo.AssociacaoPerfilCurso;
import br.ufg.inf.sigera.modelo.Curso;
import br.ufg.inf.sigera.modelo.Professor;
import br.ufg.inf.sigera.modelo.perfil.EnumPerfil;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.perfil.Perfil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AdaptadorUsuarioTelaAtribuirPerfil implements UsuarioTelaAtribuirPerfil {

    private final UsuarioSigera usuario;
    private final Map<Integer, Entry<AssociacaoPerfilCurso, Boolean>> perfis;
    private boolean teveAlteracoes;
    private String primeiroAcessoParaFiltro;
    private String ultimoAcessoParaFiltro;
    private static final String NCA = "Nenhum curso associado.";

    public AdaptadorUsuarioTelaAtribuirPerfil(UsuarioSigera usuario) {
        this.usuario = usuario;

        this.perfis = new HashMap<Integer, Entry<AssociacaoPerfilCurso, Boolean>>();

        modifiqueInformacaoPerfil(EnumPerfil.ADMINISTRADOR_SISTEMA.getCodigo(), null, false);
        modifiqueInformacaoPerfil(EnumPerfil.ALUNO.getCodigo(), null, false);
        modifiqueInformacaoPerfil(EnumPerfil.ALUNO_POS_STRICTO_SENSU.getCodigo(), null, false);
        modifiqueInformacaoPerfil(EnumPerfil.COORDENADOR_CURSO.getCodigo(), null, false);
        modifiqueInformacaoPerfil(EnumPerfil.COORDENADOR_ESTAGIO.getCodigo(), null, false);
        modifiqueInformacaoPerfil(EnumPerfil.COORDENADOR_GERAL.getCodigo(), null, false);
        modifiqueInformacaoPerfil(EnumPerfil.DIRETOR.getCodigo(), null, false);
        modifiqueInformacaoPerfil(EnumPerfil.PROFESSOR.getCodigo(), null, false);
        modifiqueInformacaoPerfil(EnumPerfil.SECRETARIA.getCodigo(), null, false);
        modifiqueInformacaoPerfil(EnumPerfil.SECRETARIA_GRADUACAO.getCodigo(), null, false);

        Collection<AssociacaoPerfilCurso> listaPerfis = this.usuario.getPerfis();

        if (listaPerfis != null) {
            for (AssociacaoPerfilCurso perfil : this.usuario.getPerfis()) {
                this.perfis.put(perfil.getPerfil().getId(),
                        new SimpleEntry<AssociacaoPerfilCurso, Boolean>(perfil, true));
            }
        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        if (this.usuario.getPrimeiroAcesso() != null) {
            primeiroAcessoParaFiltro = dateFormat.format(this.usuario.getPrimeiroAcesso());
        }
        if (this.usuario.getUltimoAcesso() != null) {
            ultimoAcessoParaFiltro = dateFormat.format(this.usuario.getUltimoAcesso());
        }
        this.teveAlteracoes = false;
    }

    @Override
    public String getNome() {
        return this.usuario.getUsuarioLdap().getCn();
    }

    @Override
    public String getEmail() {
        return this.usuario.getUsuarioLdap().getEmail();
    }

    @Override
    public String getEmailAlternativo() {
        return this.usuario.getUsuarioLdap().getEmailAternativo();
    }

    @Override
    public String getTelefoneCelular() {
        return this.usuario.getTelefoneCelular();
    }

    @Override
    public String getTelefoneResidencial() {
        return this.usuario.getTelefoneResidencial();
    }

    @Override
    public String getTelefoneComercial() {
        return this.usuario.getTelefoneComercial();
    }

    @Override
    public int getId() {
        return Integer.parseInt(this.usuario.getUsuarioLdap().getUidNumber());
    }

    @Override
    public Date getPrimeiroAcesso() {
        return this.usuario.getPrimeiroAcesso();
    }

    @Override
    public String getPrimeiroAcessoParaFiltro() {
        return this.primeiroAcessoParaFiltro;
    }

    @Override
    public Date getUltimoAcesso() {
        return this.usuario.getUltimoAcesso();
    }

    @Override
    public String getUltimoAcessoParaFiltro() {
        return this.ultimoAcessoParaFiltro;
    }

    @Override
    public boolean teveAlteracoes() {
        return this.teveAlteracoes;
    }

    private Boolean possuiPerfil(int codigoPerfil) {
        return perfis.get(codigoPerfil).getValue();
    }

    private AssociacaoPerfilCurso obtenhaAssociacaoPerfilCurso(int codigoPerfil) {
        return this.perfis.get(codigoPerfil).getKey();
    }

    private void modifiqueInformacaoPerfil(int codigoPerfil, AssociacaoPerfilCurso infoPerfil, boolean concederPerfil) {
        this.perfis.put(codigoPerfil,
                new SimpleEntry<AssociacaoPerfilCurso, Boolean>(infoPerfil, concederPerfil));
    }

    @Override
    public void modificarPerfil(Perfil perfilModificado, Curso cursoAssociado, Professor orientadorAssociado, boolean concederPerfil) {
        int codigoPerfil = perfilModificado.getId();
        AssociacaoPerfilCurso infoPerfil = obtenhaAssociacaoPerfilCurso(codigoPerfil);

        //se o perfil exigi curso associado e foi setado ou alterado esse curso
        if (possuiPerfil(codigoPerfil) != concederPerfil
                || (concederPerfil && (AssociacaoPerfilCurso.perfilExigeCursoAssociado(perfilModificado)
                && (infoPerfil.getCurso().getId() != cursoAssociado.getId())))) {
            this.teveAlteracoes = true;
        }
        //se o perfil exigi orientador associado e foi setado ou alterado esse orientador
        if (possuiPerfil(codigoPerfil) != concederPerfil
                || (concederPerfil && (AssociacaoPerfilCurso.perfilExigeOrientador(perfilModificado)
                && (infoPerfil.getOrientador().getId() != orientadorAssociado.getId())))) {
            this.teveAlteracoes = true;
        }

        if (infoPerfil != null) {
            infoPerfil.setCurso(cursoAssociado);
            infoPerfil.setOrientador(orientadorAssociado);
        } else {
            infoPerfil = new AssociacaoPerfilCurso(perfilModificado, cursoAssociado, orientadorAssociado, usuario);
        }

        modifiqueInformacaoPerfil(codigoPerfil, infoPerfil, concederPerfil);
    }

    @Override
    public UsuarioSigera processePerfisUsuario() {
        Collection<AssociacaoPerfilCurso> novosPerfis = new ArrayList<AssociacaoPerfilCurso>();

        for (Entry<Integer, Entry<AssociacaoPerfilCurso, Boolean>> entrada : this.perfis.entrySet()) {
            Boolean possuiPermissao = entrada.getValue().getValue();
            AssociacaoPerfilCurso infoPerfil = entrada.getValue().getKey();

            if (possuiPermissao) {
                novosPerfis.add(infoPerfil);
            }
        }

        this.usuario.setPerfis(novosPerfis);
        return this.usuario;
    }

    @Override
    public String getDescricaoAdministrador() {
        return getDescricaoPerfil(EnumPerfil.ADMINISTRADOR_SISTEMA.getCodigo());
    }

    @Override
    public String getDescricaoDiretor() {
        return getDescricaoPerfil(EnumPerfil.DIRETOR.getCodigo());
    }

    @Override
    public String getDescricaoProfessor() {
        return getDescricaoPerfil(EnumPerfil.PROFESSOR.getCodigo());
    }

    @Override
    public String getDescricaoAluno() {
        if (getDescricaoPerfil(EnumPerfil.ALUNO.getCodigo()).equalsIgnoreCase("Não")) {
            return getDescricaoPerfil(EnumPerfil.ALUNO_POS_STRICTO_SENSU.getCodigo());
        }
        return getDescricaoPerfil(EnumPerfil.ALUNO.getCodigo());
    }

    @Override
    public String getDescricaoCoordenadorCurso() {
        return getDescricaoPerfil(EnumPerfil.COORDENADOR_CURSO.getCodigo());
    }

    @Override
    public String getDescricaoCoordenadorEstagio() {
        return getDescricaoPerfil(EnumPerfil.COORDENADOR_ESTAGIO.getCodigo());
    }

    @Override
    public String getDescricaoCoordenadorGeral() {
        return getDescricaoPerfil(EnumPerfil.COORDENADOR_GERAL.getCodigo());
    }

    @Override
    public String getDescricaoSecretaria() {
        return getDescricaoPerfil(EnumPerfil.SECRETARIA.getCodigo());
    }

    @Override
    public String getDescricaoSecretariaGraduacao() {
        return getDescricaoPerfil(EnumPerfil.SECRETARIA_GRADUACAO.getCodigo());
    }

    private String getDescricaoPerfil(int codigoPerfil) {
        if (possuiPerfil(codigoPerfil)) {
            if (AssociacaoPerfilCurso.perfilExigeCursoAssociado(codigoPerfil)) {
                return obtenhaAssociacaoPerfilCurso(codigoPerfil).getCurso().getPrefixo();
            } else {
                return "Sim";
            }
        } else {
            return "Não";
        }
    }

    @Override
    public String getUsername() {
        return this.usuario.getUsuarioLdap().getUid();
    }

    private String getNomeCurso(int codigoPerfil) {
        if (possuiPerfil(codigoPerfil)) {
            return obtenhaAssociacaoPerfilCurso(codigoPerfil).getCurso().getNome();
        } else {
            return NCA;
        }
    }

    @Override
    public String getNomeCursoAluno() {

        if (getNomeCurso(EnumPerfil.ALUNO.getCodigo()).equalsIgnoreCase(NCA)) {
            return getNomeCurso(EnumPerfil.ALUNO_POS_STRICTO_SENSU.getCodigo());
        }
        return getNomeCurso(EnumPerfil.ALUNO.getCodigo());

    }

    @Override
    public String getNomeCursoCoordenadorCurso() {
        return getNomeCurso(EnumPerfil.COORDENADOR_CURSO.getCodigo());
    }

    @Override
    public String getNomeCursoCoordenadorEstagio() {
        return getNomeCurso(EnumPerfil.COORDENADOR_ESTAGIO.getCodigo());
    }

    @Override
    public Boolean getCoordenadorGeral() {
        return possuiPerfil(EnumPerfil.COORDENADOR_GERAL.getCodigo());
    }

    @Override
    public String getNomeCursoSecretaria() {
        return getNomeCurso(EnumPerfil.SECRETARIA.getCodigo());
    }

    @Override
    public Boolean getAdministrador() {
        return possuiPerfil(EnumPerfil.ADMINISTRADOR_SISTEMA.getCodigo());
    }

    @Override
    public Boolean getAluno() {
        return possuiPerfil(EnumPerfil.ALUNO.getCodigo());
    }

    @Override
    public Boolean getCoordenadorCurso() {
        return possuiPerfil(EnumPerfil.COORDENADOR_CURSO.getCodigo());
    }

    @Override
    public Boolean getCoordenadorEstagio() {
        return possuiPerfil(EnumPerfil.COORDENADOR_ESTAGIO.getCodigo());
    }

    @Override
    public Boolean getDiretor() {
        return possuiPerfil(EnumPerfil.DIRETOR.getCodigo());
    }

    @Override
    public Boolean getProfessor() {
        return possuiPerfil(EnumPerfil.PROFESSOR.getCodigo());
    }

    @Override
    public Boolean getSecretaria() {
        return possuiPerfil(EnumPerfil.SECRETARIA.getCodigo());
    }

    @Override
    public Boolean getSecretariaGraduacao() {
        return possuiPerfil(EnumPerfil.SECRETARIA_GRADUACAO.getCodigo());
    }

    @Override
    public Boolean getAlunoStrictoSensu() {
        return possuiPerfil(EnumPerfil.ALUNO_POS_STRICTO_SENSU.getCodigo());
    }

    @Override
    public Curso getCursoPerfilAlunoRemover(Integer codigoPerfil) {
        return obtenhaAssociacaoPerfilCurso(codigoPerfil).getCurso();
    }

}
