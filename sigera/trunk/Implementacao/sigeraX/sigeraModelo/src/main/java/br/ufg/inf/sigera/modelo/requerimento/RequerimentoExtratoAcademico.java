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
    public boolean perfilPermiteDarParecer(UsuarioSigera usuario) {
        // Só os usuários autenticados com perfil de secretaria do mesmo curso do estudante 
        // requerente podem dar parecer sobre requerimentos de extrato acadêmico.
        // Adição de autorização para o perfil Secretária de Graduação

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