package br.ufg.inf.sigera.modelo.perfil;

import br.ufg.inf.sigera.modelo.requerimento.EnumTipoRequerimento;
import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoPlano;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Entity
@DiscriminatorValue(value = "6")
public class PerfilSecretaria extends Perfil {

    public PerfilSecretaria() {
        setId(EnumPerfil.SECRETARIA.getCodigo());
        setNome(EnumPerfil.SECRETARIA.getNome());
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
    public boolean permiteImprimirEmenta() {
        return true;
    }

    @Override
    public List<Requerimento> obtenhaRequerimentos(UsuarioSigera usuarioAutenticado) {
        EntityManager em = obtenhaEntityManager();
        StringBuilder consulta = new StringBuilder();

        consulta.append(" SELECT r ");
        consulta.append(" FROM Requerimento as r  ");
        consulta.append(" WHERE r.tipo IN (:tipo1, :tipo2, :tipo3, :tipo4) ");
        consulta.append(" AND r.usuario.id IN (SELECT apc.usuario.id ");
        consulta.append("                      FROM AssociacaoPerfilCurso as apc ");
        consulta.append("                      WHERE apc.curso.id = :idCurso ");
        consulta.append("                      AND apc.perfil.id = :perfilAluno) ORDER BY r.id DESC");

        Query query = em.createQuery(consulta.toString());
        query.setParameter("tipo1", EnumTipoRequerimento.DECLARACAO_MATRICULA.getCodigo());
        query.setParameter("tipo2", EnumTipoRequerimento.EXTRATO_ACADEMICO.getCodigo());
        query.setParameter("tipo3", EnumTipoRequerimento.EMENTAS.getCodigo());
        query.setParameter("tipo4", EnumTipoRequerimento.ASSINATURA.getCodigo());
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
    public List<RequerimentoPlano> obtenhaRequerimentosPlanos(UsuarioSigera usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean permiteManterUsuarios() {
        return false;
    }
}
