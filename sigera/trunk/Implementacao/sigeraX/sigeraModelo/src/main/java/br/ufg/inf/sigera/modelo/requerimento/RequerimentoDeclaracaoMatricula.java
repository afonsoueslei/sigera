package br.ufg.inf.sigera.modelo.requerimento;

import br.ufg.inf.sigera.modelo.Curso;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.perfil.EnumPerfil;
import br.ufg.inf.sigera.modelo.perfil.Perfil;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@DiscriminatorValue(value = "4")
@Table(name = "req_declaracao_matricula")
@PrimaryKeyJoinColumn(name = "Requerimento_id")
public class RequerimentoDeclaracaoMatricula extends Requerimento {

    public RequerimentoDeclaracaoMatricula() {
        this.setCodigoMensagemConfirmacao("MT.003");
    }

    public RequerimentoDeclaracaoMatricula(UsuarioSigera usuario, String justificativa) {
        super(usuario);
        this.setTipo(EnumTipoRequerimento.DECLARACAO_MATRICULA.getCodigo());
        this.setJustificativa(justificativa);
        this.setCodigoMensagemConfirmacao("MT.003");
    }

    @Override
    public String getDescricaoTipo() {
        return EnumTipoRequerimento.DECLARACAO_MATRICULA.getNome();
    }

    @Override
    public boolean perfilPermiteDarParecer(UsuarioSigera usuario) {
        // Só os usuários autenticados com perfil de secretaria do mesmo curso do estudante 
        // requerente podem dar parecer sobre requerimentos de declaração de matrícula.
        // Adição de autorização para o perfil secretaria de Graduação

        Perfil perfilUsuario = usuario.getPerfilAtual().getPerfil();
        Curso cursoUsuario = usuario.getPerfilAtual().getCurso();

        if ((perfilUsuario.getId() == EnumPerfil.SECRETARIA.getCodigo()  
            && cursoUsuario.getId() == getCurso().getId()) 
            || perfilUsuario.getId() == EnumPerfil.SECRETARIA_GRADUACAO.getCodigo()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean perfilPermiteEditarPlano(UsuarioSigera usuario) {
        return false;
    }
}
