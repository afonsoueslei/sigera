package br.ufg.inf.sigera.modelo.requerimento;

import br.ufg.inf.sigera.modelo.Curso;
import br.ufg.inf.sigera.modelo.Disciplina;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.perfil.EnumPerfil;
import br.ufg.inf.sigera.modelo.perfil.Perfil;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "req_ementa")
@DiscriminatorValue(value = "6")
@PrimaryKeyJoinColumn(name = "Requerimento_id")
public class RequerimentoEmenta extends Requerimento {

    @ManyToMany
    @JoinTable(
            name = "reqementa_disciplina",
            joinColumns =
            @JoinColumn(name = "req_ementa_requerimento_id"),
            inverseJoinColumns =
            @JoinColumn(name = "Disciplina_id"))
    private Collection<Disciplina> disciplinas;

    public RequerimentoEmenta() {
        this.setCodigoMensagemConfirmacao("MT.011");
    }

    public RequerimentoEmenta(UsuarioSigera usuario, Collection<Disciplina> disciplinas, String justificativa) {
        super(usuario);
        this.setTipo(EnumTipoRequerimento.EMENTAS.getCodigo());
        this.setDisciplinas(disciplinas);
        this.setCodigoMensagemConfirmacao("MT.011");
        this.setJustificativa(justificativa);
    }

    @Override
    public String getDescricaoTipo() {
        return EnumTipoRequerimento.EMENTAS.getNome();
    }

    @Override
    public Collection<Disciplina> getDisciplinas() {
        if (this.disciplinas != null) {
            Collections.sort((List<Disciplina>) this.disciplinas);
        }
        return this.disciplinas;
    }

    public void setDisciplinas(Collection<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    @Override
    public boolean perfilPermiteDarParecer(UsuarioSigera usuario) {

        // Só os usuários autenticados com perfil de secretaria do mesmo curso do estudante 
        // requerente podem dar parecer sobre requerimentos de ementas.
        // Adição de autorização para o perfil secretaria de graduação

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