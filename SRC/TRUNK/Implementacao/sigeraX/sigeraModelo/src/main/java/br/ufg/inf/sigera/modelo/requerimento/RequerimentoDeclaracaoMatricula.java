package br.ufg.inf.sigera.modelo.requerimento;

import br.ufg.inf.sigera.modelo.UsuarioSigera;
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
    public boolean perfilPermiteDarParecer(UsuarioSigera usuarioLogado) {
        return usuarioEhDaSecretariaDoCurso(usuarioLogado);
    }

    @Override
    public boolean perfilPermiteEditarPlano(UsuarioSigera usuario) {
        return false;
    }
}
