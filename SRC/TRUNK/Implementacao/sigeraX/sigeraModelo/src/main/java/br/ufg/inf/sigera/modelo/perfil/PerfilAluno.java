package br.ufg.inf.sigera.modelo.perfil;

import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoPlano;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Entity
@DiscriminatorValue(value="2")
public class PerfilAluno extends Perfil {

    public PerfilAluno() {
        setId(EnumPerfil.ALUNO.getCodigo());
        setNome(EnumPerfil.ALUNO.getNome());
    }

    @Override
    public boolean permiteFazerRequerimento() {
        return true;
    }

    @Override
    public boolean permiteConfigurarSistema() {
        return false;
    }
    @Override
    public boolean permitePlanoDeAula() {
        return false;
    }
    
    @Override
    public boolean permiteEditarPlanoDeAula() {
        return false;
    }
    
    @Override
    public boolean permiteEditarTurma() {
        return false;
    }
    
    @Override
    public boolean permiteManterUsuarios() {
        return false;
    }
   
    @Override
    public boolean permiteImprimirEmenta() {
        return false;
    }
    
    @Override
    public List<Requerimento> obtenhaRequerimentos(UsuarioSigera usuario) {
        EntityManager em = obtenhaEntityManager();
        String consulta = " SELECT r FROM Requerimento as r WHERE r.usuario.id = :id ORDER BY r.id DESC";
        Query query = em.createQuery(consulta);
        query.setParameter("id", usuario.getId());
        List<Requerimento> requerimentos = query.getResultList();

        for (Requerimento r : requerimentos) {
            r.setUsuario(usuario);
        }

        return requerimentos;
    }    

    @Override
    public List<RequerimentoPlano> obtenhaRequerimentosPlanos(UsuarioSigera usuario) {
       return null;
    }        
}
