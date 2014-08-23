/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.sigera.controle.servico;

/**
 *
 * @author auf
 */
public class Paginas {

    private static final String ABRIR_REQUERIMENTO_ID = "/usuario/requerimento.xhtml?faces-redirect=true&numero=";
    private static final String ACRESCIMO = "acrescimo";
    private static final String ASSINATURA = "assinatura";
    private static final String ASSINATURA_FINALIZACAO = "assinatura_finalizacao";
    private static final String ATRIBUIR_PERFIL = "atribuir_perfil";
    private static final String CANCELAMENTO = "cancelamento";
    private static final String CONFIG = "config";
    private static final String CONSULTAR = "consultar";
    private static final String DECLARACAO_MATRICULA = "declaracao";
    private static final String DECLARACAO_MATRICULA_FINALIZACAO = "declaracao_finalizacao";
    private static final String DETALHE_REQUERIMENTO = "detalhe_requerimento";
    private static final String EDITAR_CURSO = "editar_curso";
    private static final String EDITAR_DISCIPLINAS = "editar_disciplina";
    private static final String EDITAR_ITEM_CRONOGRAMA = "editar_item_cronograma";
    private static final String EDITAR_PERFIL = "editar_perfil";
    private static final String EDITAR_PLANO = "editar_plano";
    private static final String EDITAR_TURMA = "editar_turma";
    private static final String EDITAR_UNIDADE = "editar_unidade";
    private static final String EMENTAS = "ementas";
    private static final String EXTRATO_ACADEMICO = "extrato";
    private static final String EXTRATO_ACADEMICO_FINALIZACAO = "extrato_finalizacao";
    private static final String IMPORTACAO_CURSO = "importacao";
    private static final String IMPORTACAO_PROFESSOR = "importacaoProfessor";
    private static final String IMPORTACAO_DISCIPLINA = "importacaoDisciplina";
    private static final String IMPORTACAO_TURMA = "importacaoTurma";
    private static final String IMPRESSAO_EMENTA = "impressao_ementa";
    private static final String LOGIN = "login";
    private static final String MANTER_CURSO = "manter_curso";
    private static final String MANTER_DISCIPLINA = "manter_disciplina";
    private static final String MANTER_TURMA = "manter_turma";
    private static final String MANTER_UNIDADE = "manter_unidade";
    private static final String MSG_SEM_PLANO = "msg_sem_plano";
    private static final String PLANO_AULA = "plano_aula";
    private static final String PRINCIPAL = "principal";
    private static final String SEGUNDA_CHAMADA = "segunda_chamada";
    private static final String SEGUNDA_CHAMADA_SELECAO_TURMA = "segunda_chamada_selecao_turma";
    private static final String SEGUNDA_CHAMADA_FINALIZACAO = "segunda_chamada_finalizacao";
    private static final String SELECAO_PERFIL = "selecao_perfil";
    private static final String TURMAS_SEM_PLANO = "turmas_sem_plano";
    private static final String ALTERAR_SENHA = "alterar_senha";
    private static final String CONSULTAR_USUARIOS = "consultar_usuarios";
    private static final String RECUPERAR_SENHA = "recuperarSenha";
    private static final String TROCAR_SENHA = "trocarSenha";
    private static final String TROCAR_SENHA_FINAL = "trocarSenhaFinal";
    private static final String AJUDA = "ajuda";

    private Paginas() {
    }
    
    public static String getAbrirRequerimentoID() {
        return ABRIR_REQUERIMENTO_ID;
    }

    public static String getACrescimo() {
        return ACRESCIMO;
    }

    public static String getAssinatura() {
        return ASSINATURA;
    }

    public static String getAssinaturaFinalizacao() {
        return ASSINATURA_FINALIZACAO;
    }
    
    public static String getAtribuirPerfil() {
        return ATRIBUIR_PERFIL;
    }

    public static String getCancelamento() {
        return CANCELAMENTO;
    }

    public static String getConfig() {
        return CONFIG;
    }

    public static String getConsultar() {
        return CONSULTAR;
    }

    public static String getDeclaracaoMatricula() {
        return DECLARACAO_MATRICULA;
    }

    public static String getDeclaracaoMatriculaFinalizacao() {
        return DECLARACAO_MATRICULA_FINALIZACAO;
    }
    

    public static String getDetalheRequerimento() {
        return DETALHE_REQUERIMENTO;
    }

    public static String getEditarCurso() {
        return EDITAR_CURSO;
    }

    public static String getEditarDisciplinas() {
        return EDITAR_DISCIPLINAS;
    }

    public static String getEditarItemCronograma() {
        return EDITAR_ITEM_CRONOGRAMA;
    }

    public static String getEditarPerfil() {
        return EDITAR_PERFIL;
    }

    public static String getEditarPlano() {
        return EDITAR_PLANO;
    }

    public static String getEditarTurma() {
        return EDITAR_TURMA;
    }

    public static String getEditarUnidade() {
        return EDITAR_UNIDADE;
    }

    public static String getEmentas() {
        return EMENTAS;
    }

    public static String getExtratoAcademico() {
        return EXTRATO_ACADEMICO;
    }

    public static String getExtratoAcademicoFinalizacao() {
        return EXTRATO_ACADEMICO_FINALIZACAO;
    }

    public static String getImportacaoCurso() {
        return IMPORTACAO_CURSO;
    }

    public static String getImportacaoProfessor() {
        return IMPORTACAO_PROFESSOR;
    }

    public static String getImportacaoDisciplina() {
        return IMPORTACAO_DISCIPLINA;
    }

    public static String getImportacaoTurma() {
        return IMPORTACAO_TURMA;
    }

    public static String getImpressaoEmenta() {
        return IMPRESSAO_EMENTA;
    }

    public static String getLogin() {
        return LOGIN;
    }

    public static String getManterCurso() {
        return MANTER_CURSO;
    }

    public static String getManterDisciplina() {
        return MANTER_DISCIPLINA;
    }

    public static String getManterTurma() {
        return MANTER_TURMA;
    }

    public static String getManterUnidade() {
        return MANTER_UNIDADE;
    }

    public static String getMsgSemPlano() {
        return MSG_SEM_PLANO;
    }

    public static String getPlanoAula() {
        return PLANO_AULA;
    }

    public static String getPrincipal() {
        return PRINCIPAL;
    }

    public static String getSegundaChamada() {
        return SEGUNDA_CHAMADA;
    }

    public static String getSegundaChamadaSelecaoTurma() {
        return SEGUNDA_CHAMADA_SELECAO_TURMA;
    }

    public static String getSegundaChamadaFinalizacao() {
        return SEGUNDA_CHAMADA_FINALIZACAO;
    }

    public static String getSelecaoPerfil() {
        return SELECAO_PERFIL;
    }

    public static String getTurmasSemPlano() {
        return TURMAS_SEM_PLANO;
    }

    public static String getAlterarSenha() {
        return ALTERAR_SENHA;
    }

    public static String getConsultarUsuarios() {
        return CONSULTAR_USUARIOS;
    }    

    public static String getTrocarSenha() {
        return TROCAR_SENHA;
    }

    public static String getTrocarSenhaFinal() {
        return TROCAR_SENHA_FINAL;
    }
    
    public static String getRecuperarSenha() {
        return RECUPERAR_SENHA;
    }
    
    public static String getAjuda() {
        return AJUDA;
    }
    
}
