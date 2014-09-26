/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.sigera.modelo.perfil;

import br.ufg.inf.sigera.modelo.Professor;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import static br.ufg.inf.sigera.modelo.perfil.Perfil.obtenhaEntityManager;
import br.ufg.inf.sigera.modelo.requerimento.Requerimento;
import br.ufg.inf.sigera.modelo.requerimento.RequerimentoPlano;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author auf
 */
@Entity
@DiscriminatorValue(value = "21")
public class PerfilAlunoPosStrictoSensu extends Perfil {

    public PerfilAlunoPosStrictoSensu() {
        setId(EnumPerfil.ALUNO_POS_STRICTO_SENSU.getCodigo());
        setNome(EnumPerfil.ALUNO_POS_STRICTO_SENSU.getNome());
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
        String consulta = " SELECT r FROM Requerimento as r WHERE r.usuario.id = :id ORDER BY r.status, r.id DESC";
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

    public UsuarioSigera obtenhaOrientador(UsuarioSigera usuario) {
        Professor orientador = null;
        EntityManager em = obtenhaEntityManager();
        StringBuilder consulta = new StringBuilder();
        consulta.append(" SELECT apc.orientador.id ");
        consulta.append(" FROM AssociacaoPerfilCurso as apc ");
        consulta.append(" WHERE (apc.usuario.id = :idUsuario AND apc.perfil.id = :idPerfil) ");

        Query query = em.createQuery(consulta.toString());
        query.setParameter("idUsuario", usuario.getId());
        query.setParameter("idPerfil", EnumPerfil.ALUNO_POS_STRICTO_SENSU.getCodigo());
        try {
            BuscadorLdap buscadorLdap = usuario.getUsuarioLdap().getBuscadorLdap();
            Integer id = (Integer) query.getSingleResult();
            orientador = em.find(Professor.class, id);
            orientador.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(orientador.getUsuario().getId()));            
            
            return orientador.getUsuario();
            
        } catch (NoResultException e) {
            return null;
        }                
    }
}
