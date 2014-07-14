package br.ufg.inf.sigera.controle.filtro;

import br.ufg.inf.sigera.controle.SegundaChamadaBean;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;

public class FiltroSegundaChamada implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        // TODO
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) {
        try {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) resp;
            HttpSession session = request.getSession();

            Object bean = session.getAttribute("segundaChamadaBean");
            if (bean == null) {
                enviarParaPaginaDeSelecaoDeTurma(request, response);
                return;
            }

            SegundaChamadaBean segundaChamadaBean = (SegundaChamadaBean) bean;
            if (segundaChamadaBean.getTurmaSelecionada() == null) {
                enviarParaPaginaDeSelecaoDeTurma(request, response);
                return;
            }

            chain.doFilter(req, resp);

        } catch (IOException ex) {
            Logger.getLogger(FiltroSegundaChamada.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException sEx) {
            Logger.getLogger(FiltroSegundaChamada.class.getName()).log(Level.SEVERE, null, sEx);
        }
    }

    private void enviarParaPaginaDeSelecaoDeTurma(HttpServletRequest request, HttpServletResponse response) {
        try {
            RequestDispatcher paginaPrincipal = request.getRequestDispatcher("/faces/usuario/requisicoes/segunda_chamada_selecao_turma.xhtml");
            paginaPrincipal.forward(request, response);
        } catch (IOException ex) {
            Logger.getLogger(FiltroSegundaChamada.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException sEx) {
            Logger.getLogger(FiltroSegundaChamada.class.getName()).log(Level.SEVERE, null, sEx);
        }
    }

    @Override
    public void destroy() {
        //TODO implementação de filter exigir sobrescrever metodo destroy
    }
}