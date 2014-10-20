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
@Table(name="req_assinatura")
@DiscriminatorValue(value="7")
@PrimaryKeyJoinColumn(name = "Requerimento_id")
public class RequerimentoAssinatura extends Requerimento {
    
    public RequerimentoAssinatura() {
    }
    
    public RequerimentoAssinatura(UsuarioSigera usuario, String justificativa) {
        super(usuario);
        this.setJustificativa(justificativa);
        this.setTipo(EnumTipoRequerimento.ASSINATURA.getCodigo());        
    }       

    @Override
    public String getDescricaoTipo() {        
        return EnumTipoRequerimento.ASSINATURA.getNome();
    }    
    
    @Override
    public boolean usuarioPodeConferirDocumentos(UsuarioSigera usuarioLogado) {        
        return usuarioEhDaSecretariaDoCurso(usuarioLogado);
    }
    
    @Override
    public int codigoStatusQuePermiteParecer() {
        return EnumStatusRequerimento.CONFERIDO.getCodigo();
    }
    
    public boolean perfilPermiteDarParecer(UsuarioSigera usuario) {
        // Só os usuários autenticados com perfil de coordenador de estágio do mesmo curso do estudante 
        // requerente ou diretor podem dar parecer sobre requerimentos de assinatura de estágio.

        Perfil perfilUsuario = usuario.getPerfilAtual().getPerfil();
        Curso cursoUsuario = usuario.getPerfilAtual().getCurso();

        if ((perfilUsuario.getId() == EnumPerfil.COORDENADOR_ESTAGIO.getCodigo()
                && cursoUsuario.getId() == getCurso().getId())
                || (perfilUsuario.getId() == EnumPerfil.DIRETOR.getCodigo())) {
            return true;
        } else {
            return false;
        }
    }    
    
    @Override
    public boolean podeVisualizarTelefone(UsuarioSigera usuario) {
        if (getUsuario().getId() == usuario.getId()) {
            return true;
        }

        if (perfilPermiteDarParecer(usuario)) {
            return true;
        }
        
        if (usuarioEhDaSecretariaDoCurso(usuario)) {
            return true;
        }

        return false;
    }
    
    @Override
    public boolean perfilPermiteEditarPlano(UsuarioSigera usuario) {
        return false;
    }
}