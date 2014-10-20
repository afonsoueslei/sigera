package br.ufg.inf.sigera.modelo.requerimento;

import br.ufg.inf.sigera.modelo.Disciplina;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import java.util.Collection;
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
            joinColumns
            = @JoinColumn(name = "req_ementa_requerimento_id"),
            inverseJoinColumns
            = @JoinColumn(name = "Disciplina_id"))
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
        return this.disciplinas;
    }

    public void setDisciplinas(Collection<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
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
