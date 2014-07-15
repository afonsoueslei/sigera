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
@DiscriminatorValue(value = "1")
public class PerfilAdministrador extends Perfil {

    public PerfilAdministrador() {
        setId(EnumPerfil.ADMINISTRADOR_SISTEMA.getCodigo());
        setNome(EnumPerfil.ADMINISTRADOR_SISTEMA.getNome());
    }

    @Override
    public boolean permiteFazerRequerimento() {
        return false;
    }

    @Override
    public boolean permiteConfigurarSistema() {
        return true;
    }

    @Override
    public boolean permitePlanoDeAula() {
        return true;
    }

    @Override
    public boolean permiteEditarPlanoDeAula() {
        return false;
    }

    @Override
    public boolean permiteEditarTurma() {
        return true;
    }

    @Override
    public boolean permiteManterUsuarios() {
        return true;
    }

    @Override
    public boolean permiteImprimirEmenta() {
        return true;
    }

    @Override
    public List<Requerimento> obtenhaRequerimentos(UsuarioSigera usuarioAutenticado) {
        BuscadorLdap buscadorLdap = usuarioAutenticado.getUsuarioLdap().getBuscadorLdap();
        EntityManager em = obtenhaEntityManager();
        String consulta = " SELECT r FROM Requerimento as r ORDER BY r.id DESC";
        Query query = em.createQuery(consulta);
        List<Requerimento> requerimentos = query.getResultList();

        for (Requerimento r : requerimentos) {
            r.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(r.getUsuario().getId()));
        }

        return requerimentos;
    }

    @Override
    public List<RequerimentoPlano> obtenhaRequerimentosPlanos(UsuarioSigera usuarioAutenticado) {
        BuscadorLdap buscadorLdap = usuarioAutenticado.getUsuarioLdap().getBuscadorLdap();
        EntityManager em = obtenhaEntityManager();
        String consulta = " SELECT r FROM RequerimentoPlano as r ORDER BY r.id DESC";
        Query query = em.createQuery(consulta);
        List<RequerimentoPlano> planos = query.getResultList();
        for (RequerimentoPlano p : planos) {
            //setar usuário ldap do Requerimento
            p.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(p.getUsuario().getId()));
            //setar usuário Ldap do professor do plano
            p.getPlano().getTurma().getProfessor().getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(p.getPlano().getTurma().getProfessor().getUsuario().getId()));
        }
        return planos;
    }
}
