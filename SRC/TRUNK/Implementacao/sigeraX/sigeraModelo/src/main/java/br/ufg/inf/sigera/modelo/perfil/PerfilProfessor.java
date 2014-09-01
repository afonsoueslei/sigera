package br.ufg.inf.sigera.modelo.perfil;

import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import static br.ufg.inf.sigera.modelo.perfil.Perfil.obtenhaEntityManager;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoPlano;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Entity
@DiscriminatorValue(value = "5")
public class PerfilProfessor extends Perfil {

    public PerfilProfessor() {
        setId(EnumPerfil.PROFESSOR.getCodigo());
        setNome(EnumPerfil.PROFESSOR.getNome());
    }

    @Override
    public boolean permiteFazerRequerimento() {
        return false;
    }

    @Override
    public boolean permiteConfigurarSistema() {
        return false;
    }

    @Override
    public boolean permitePlanoDeAula() {
        return true;
    }

    @Override
    public boolean permiteEditarPlanoDeAula() {
        return true;
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
        return true;
    }
    
    @Override
    public List<Requerimento> obtenhaRequerimentos(UsuarioSigera usuarioAutenticado) {
        EntityManager em = obtenhaEntityManager();
        StringBuilder consulta = new StringBuilder();

        consulta.append(" SELECT r ");
        consulta.append(" FROM RequerimentoSegundaChamada as r  ");
        consulta.append(" WHERE r.turma.professor.usuario.id = :id ORDER BY r.status, r.id DESC");

        Query query = em.createQuery(consulta.toString());
        query.setParameter("id", usuarioAutenticado.getId());
        List<Requerimento> requerimentos = query.getResultList();
        BuscadorLdap buscadorLdap = usuarioAutenticado.getUsuarioLdap().getBuscadorLdap();
        
        StringBuilder consulta2 = new StringBuilder();
        consulta2.append("SELECT r");
        consulta2.append(" FROM RequerimentoPlano as r  ");
        consulta2.append(" WHERE r.plano.turma.professor.usuario.id = :idUsuario ORDER BY r.status, r.id DESC");
        
        Query query2 = em.createQuery(consulta2.toString());
        query2.setParameter("idUsuario", usuarioAutenticado.getId());
                
        List<Requerimento> requerimentosPlanos = query2.getResultList();
        
        for(Requerimento rp: requerimentosPlanos){
            requerimentos.add(rp);
        }
        
        for (Requerimento r : requerimentos) {
            r.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(r.getUsuario().getId()));
        }

        return requerimentos;
    }

    @Override
    public List<RequerimentoPlano> obtenhaRequerimentosPlanos(UsuarioSigera usuarioAutenticado) {
        EntityManager em = obtenhaEntityManager();
        StringBuilder consulta = new StringBuilder();

        consulta.append("SELECT r");
        consulta.append(" FROM RequerimentoPlano as r  ");
        consulta.append(" WHERE r.plano.turma.professor.usuario.id = :idUsuario ORDER BY r.status, r.id DESC");

        Query query = em.createQuery(consulta.toString());
        query.setParameter("idUsuario", usuarioAutenticado.getId());

        BuscadorLdap buscadorLdap = usuarioAutenticado.getUsuarioLdap().getBuscadorLdap();
        List<RequerimentoPlano> planos = query.getResultList();

        for (RequerimentoPlano p : planos) {
            p.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(p.getUsuario().getId()));
            //setar usuário Ldap do professor do plano
            p.getPlano().getTurma().getProfessor().getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(p.getPlano().getTurma().getProfessor().getUsuario().getId()));
        }
        return planos;
    }
}
