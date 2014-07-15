package br.ufg.inf.sigera.modelo.perfil;

import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoPlano;
import java.io.Serializable;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Persistence;
import javax.persistence.Table;

@Entity
@Table(name = "perfil")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "id", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Perfil implements Serializable {

    @Id
    private int id;
    private String nome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    protected static EntityManager obtenhaEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("br.ufg.inf.sigera");
        return emf.createEntityManager();
    }

    public abstract boolean permiteFazerRequerimento();

    public abstract boolean permiteConfigurarSistema();

    public abstract boolean permitePlanoDeAula();

    public abstract boolean permiteEditarPlanoDeAula();
    
    public abstract boolean permiteEditarTurma();     

    public abstract List<Requerimento> obtenhaRequerimentos(UsuarioSigera usuario);

    public abstract List<RequerimentoPlano> obtenhaRequerimentosPlanos(UsuarioSigera usuario);
    
    public abstract boolean permiteManterUsuarios();
    
    public abstract boolean permiteImprimirEmenta();
}
