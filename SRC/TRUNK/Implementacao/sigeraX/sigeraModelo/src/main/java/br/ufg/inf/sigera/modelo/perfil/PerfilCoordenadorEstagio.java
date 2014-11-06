package br.ufg.inf.sigera.modelo.perfil;

import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import static br.ufg.inf.sigera.modelo.perfil.Perfil.obtenhaEntityManager;
import br.ufg.inf.sigera.modelo.requerimento.EnumStatusRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoPlano;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Entity
@DiscriminatorValue(value = "4")
public class PerfilCoordenadorEstagio extends Perfil {

    public PerfilCoordenadorEstagio() {
        setId(EnumPerfil.COORDENADOR_ESTAGIO.getCodigo());
        setNome(EnumPerfil.COORDENADOR_ESTAGIO.getNome());
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
        return true;
    }

    @Override
    public List<Requerimento> obtenhaRequerimentos(UsuarioSigera usuarioAutenticado) {
        EntityManager em = obtenhaEntityManager();
        StringBuilder consulta = new StringBuilder();

        consulta.append(" SELECT r ");
        consulta.append(" FROM RequerimentoAssinatura as r ");
        consulta.append(" WHERE r.status != :statusAberto AND r.usuario.id IN (SELECT apc.usuario.id ");
        consulta.append("                        FROM AssociacaoPerfilCurso as apc ");
        consulta.append("                        WHERE apc.curso.id = :idCurso ");
        consulta.append("                        AND apc.perfil.id = :perfilAluno) ORDER BY r.status, r.id DESC");

        Query query = em.createQuery(consulta.toString());
        query.setParameter("statusAberto", EnumStatusRequerimento.ABERTO.getCodigo());
        query.setParameter("idCurso", usuarioAutenticado.getPerfilAtual().getCurso().getId());        
        query.setParameter("perfilAluno", EnumPerfil.ALUNO.getCodigo());

        List<Requerimento> requerimentos = query.getResultList();
        BuscadorLdap buscadorLdap = usuarioAutenticado.getUsuarioLdap().getBuscadorLdap();

        for (Requerimento r : requerimentos) {
            r.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(r.getUsuario().getId()));
        }

        return requerimentos;
    }

    @Override
    public List<Requerimento> obtenhaRequerimentosDoCurso(UsuarioSigera usuario) {
        return null;
    }

    @Override
    public List<RequerimentoPlano> obtenhaRequerimentosPlanos(UsuarioSigera usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean permiteCancelarRequerimento() {
        return false;
    }

}
