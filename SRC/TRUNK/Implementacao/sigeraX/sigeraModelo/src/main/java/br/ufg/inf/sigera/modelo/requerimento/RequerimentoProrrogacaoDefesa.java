package br.ufg.inf.sigera.modelo.requerimento;

import br.ufg.inf.sigera.modelo.Curso;
import br.ufg.inf.sigera.modelo.UsuarioSigera;
import br.ufg.inf.sigera.modelo.ldap.BuscadorLdap;
import br.ufg.inf.sigera.modelo.perfil.EnumPerfil;
import br.ufg.inf.sigera.modelo.perfil.Perfil;
import br.ufg.inf.sigera.modelo.perfil.PerfilAlunoPosStrictoSensu;
import br.ufg.inf.sigera.modelo.servico.Persistencia;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "req_prorrogacao_defesa")
@DiscriminatorValue(value = "9")
@PrimaryKeyJoinColumn(name = "Requerimento_id")
public class RequerimentoProrrogacaoDefesa extends Requerimento {

    @Column(name = "prazo_em_meses")
    private Integer prazoEmMeses;

    @Column(name = "membro_avaliador")
    private Integer membroAvaliador;

    public RequerimentoProrrogacaoDefesa() {
    }

    public RequerimentoProrrogacaoDefesa(UsuarioSigera usuario, String justificativa, Integer prazo) {
        super(usuario);
        this.setJustificativa(justificativa);
        this.setTipo(EnumTipoRequerimento.PRORROGACAO_DEFESA.getCodigo());
        this.setPrazoEmMeses(prazo);
    }

    public Integer getPrazoEmMeses() {
        return prazoEmMeses;
    }

    public void setPrazoEmMeses(Integer prazoEmMeses) {
        this.prazoEmMeses = prazoEmMeses;
    }

    public Integer getMembroAvaliador() {
        return membroAvaliador;
    }

    public void setMembroAvaliador(Integer membroAvaliador) {
        this.membroAvaliador = membroAvaliador;
    }

    @Override
    public String getDescricaoTipo() {
        return EnumTipoRequerimento.PRORROGACAO_DEFESA.getNome();
    }

    @Override
    public int codigoStatusQuePermiteParecer() {
        return EnumStatusRequerimento.CONFERIDO.getCodigo();
    }

    @Override
    public boolean perfilPermiteDarParecer(UsuarioSigera usuario) {
        // Podem dar parecer sobre requerimentos de prorrogação de defesa:
        // Presidente da Comissão da Pos (usuário com perfil Coordenador da pos) e
        // professores membros dessa comissão ( usuários professores com perfil secretario da pos)                      
        int idCurso = this.getCurso().getId();

        Boolean ehPresidente = usuario.getPerfilAtual().getPerfil().getId() == EnumPerfil.COORDENADOR_CURSO.getCodigo();

        Boolean ehProfessor = Perfil.usuarioTemPerfil(usuario, EnumPerfil.PROFESSOR.getCodigo());
        Boolean ehMembroComissao = usuarioEhDaSecretariaDoCurso(usuario) && ehProfessor
                && usuario.getPerfilAtual().getPerfil().getId() == EnumPerfil.SECRETARIA.getCodigo();

        return ehPresidente || ehMembroComissao;
    }

    @Override
    public boolean autorizaDarParecer(UsuarioSigera usuario) {
        //Coordenador de curso pode dar parecer tanto para os requerimetos com status autorizado quanto para os que conferiu 
        if (usuario.getPerfilAtual().getPerfil().getId() == EnumPerfil.COORDENADOR_CURSO.getCodigo()
                && (this.getStatus() == codigoStatusQuePermiteParecer() || this.getStatus() == EnumStatusRequerimento.AUTORIZADO.getCodigo())) {
            return true;
        }
        //Membro de comissao somente para requerimentos conferidos
        return perfilPermiteDarParecer(usuario) && this.getStatus() == codigoStatusQuePermiteParecer();

    }

    @Override
    public boolean autorizaVisualizarParecer(UsuarioSigera usuario) {
        //Quem pode visualizar: o Requerente, os Destinatários do requerimento e o Administrador
        boolean usuarioEhAdministrador = (usuario.getPerfilAtual().getPerfil().getId() == EnumPerfil.ADMINISTRADOR_SISTEMA.getCodigo());

        if (this.getPareceres() != null && this.getPareceres().size() > 0) {
            if (this.getUsuario().getId() == usuario.getId()
                    || perfilPermiteDarParecer(usuario)
                    || usuarioEhAdministrador
                    || usuarioEhOrientadorDoRequerente(usuario)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean autorizaVisualizarAnexos(UsuarioSigera usuario) {
        if (this.getAnexos() != null && this.getAnexos().size() > 0) {
            if (this.getUsuario().getId() == usuario.getId()
                    || autorizaDarParecer(usuario)
                    || usuarioEhOrientadorDoRequerente(usuario)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean perfilPermiteEditarPlano(UsuarioSigera usuario) {
        return false;
    }

    public static RequerimentoProrrogacaoDefesa obtenhaRequerimentoProrrogacao(BuscadorLdap buscadorLdap, Integer idReq) {
        EntityManager em = Persistencia.obterManager();
        RequerimentoProrrogacaoDefesa reqProrrogacao = em.find(RequerimentoProrrogacaoDefesa.class, idReq);
        if (reqProrrogacao != null) {
            reqProrrogacao.getUsuario().setUsuarioLdap(buscadorLdap.obtenhaUsuarioLdap(reqProrrogacao.getUsuario().getId()));
        }
        return reqProrrogacao;
    }
}
