package br.ufg.inf.sigera.modelo.perfil;

import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import static br.ufg.inf.sigera.modelo.perfil.Perfil.obtenhaEntityManager;
import br.ufg.inf.sigera.modelo.requerimento.EnumTipoRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoPlano;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Entity
@DiscriminatorValue(value = "9")
public class PerfilCoordenadorGeral extends Perfil {

    public PerfilCoordenadorGeral() {
        setId(EnumPerfil.COORDENADOR_GERAL.getCodigo());
        setNome(EnumPerfil.COORDENADOR_GERAL.getNome());
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
        return true;
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

        BuscadorLdap buscadorLdap = usuarioAutenticado.getUsuarioLdap().getBuscadorLdap();
        EntityManager em = obtenhaEntityManager();
        StringBuilder consulta = new StringBuilder();
                       
        consulta.append(" SELECT r ");
        consulta.append(" FROM Requerimento as r ");
        consulta.append(" WHERE r.tipo IN (:tipo1, :tipo2, :tipo3 ) ORDER BY r.status, r.id DESC");
        
        Query query = em.createQuery(consulta.toString());
        query.setParameter("tipo1", EnumTipoRequerimento.ACRESCIMO_DISCIPLINAS.getCodigo());
        query.setParameter("tipo2", EnumTipoRequerimento.CANCELAMENTO_DISCIPLINAS.getCodigo());
        query.setParameter("tipo3", EnumTipoRequerimento.PLANO.getCodigo());
        
        List<Requerimento> requerimentos = query.getResultList();

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
        consulta.append(" FROM RequerimentoPlano as r ORDER BY r.status, r.id DESC");

        Query query = em.createQuery(consulta.toString());

        BuscadorLdap buscadorLdap = usuarioAutenticado.getUsuarioLdap().getBuscadorLdap();
        List<RequerimentoPlano> planos = query.getResultList();

        for (RequerimentoPlano p : planos) {
            p.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(p.getUsuario().getId()));
            UsuarioSigera professor = p.getPlano().getTurma().getProfessor().getUsuario();
            professor.setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(professor.getId()));
        }
        return planos;
    }
}
