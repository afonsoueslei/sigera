package br.ufg.inf.sigera.modelo.requerimento;

import br.ufg.inf.sigera.modelo.UsuarioSigera;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@DiscriminatorValue(value = "5")
@Table(name = "req_extratoacademico")
@PrimaryKeyJoinColumn(name = "Requerimento_id")
public class RequerimentoExtratoAcademico extends Requerimento {

    public RequerimentoExtratoAcademico() {
        this.setCodigoMensagemConfirmacao("MT.008");
    }

    public RequerimentoExtratoAcademico(UsuarioSigera usuario, String justificativa) {
        super(usuario);
        this.setTipo(EnumTipoRequerimento.EXTRATO_ACADEMICO.getCodigo());
        this.setJustificativa(justificativa);
        this.setCodigoMensagemConfirmacao("MT.008");
    }

    @Override
    public String getDescricaoTipo() {
        return EnumTipoRequerimento.EXTRATO_ACADEMICO.getNome();
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