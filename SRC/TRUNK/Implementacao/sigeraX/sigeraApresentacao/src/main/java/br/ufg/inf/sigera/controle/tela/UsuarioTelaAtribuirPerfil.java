package br.ufg.inf.sigera.controle.tela;

import br.ufg.inf.sigera.modelo.Curso;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.perfil.Perfil;
import java.util.Date;

public interface UsuarioTelaAtribuirPerfil {

    int getId();    
    String getNome();
    String getUsername();
    String getEmail();
    String getEmailAlternativo();  
    String getTelefoneCelular();
    String getTelefoneResidencial();
    String getTelefoneComercial();
    boolean teveAlteracoes();
    Date getPrimeiroAcesso();
    String getPrimeiroAcessoParaFiltro();
    Date getUltimoAcesso();
    String getUltimoAcessoParaFiltro();
    
    String getNomeCursoAluno();
    String getNomeCursoCoordenadorCurso();
    String getNomeCursoCoordenadorEstagio();
    String getNomeCursoSecretaria();

    String getDescricaoAdministrador();
    String getDescricaoAluno();
    String getDescricaoCoordenadorCurso();
    String getDescricaoCoordenadorEstagio();
    String getDescricaoCoordenadorGeral();
    String getDescricaoDiretor();
    String getDescricaoProfessor();
    String getDescricaoSecretaria();
    String getDescricaoSecretariaGraduacao();
    
        
    Boolean getAdministrador();
    Boolean getAluno();
    Boolean getCoordenadorCurso();
    Boolean getCoordenadorEstagio();
    Boolean getCoordenadorGeral();
    Boolean getDiretor();
    Boolean getProfessor();
    Boolean getSecretaria();
    Boolean getSecretariaGraduacao();
   
    UsuarioSigera processePerfisUsuario();

    void modificarPerfil(Perfil perfilModificado, Curso cursoAssociado, boolean concederPerfil);
}