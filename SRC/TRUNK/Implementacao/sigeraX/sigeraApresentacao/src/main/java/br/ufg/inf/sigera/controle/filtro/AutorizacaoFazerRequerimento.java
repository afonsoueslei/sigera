package br.ufg.inf.sigera.controle.filtro;

import br.ufg.inf.sigera.controle.LoginBean;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;

public class AutorizacaoFazerRequerimento extends FiltroUrlBase {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) {
        try {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) resp;
            HttpSession session = request.getSession();
            LoginBean loginBean = (LoginBean) session.getAttribute("loginBean");
            if (loginBean.getUsuario().getPerfilAtual().getPerfil().permiteFazerRequerimento()) {
                chain.doFilter(req, resp);
            } else {
                RequestDispatcher paginaPrincipal = request.getRequestDispatcher("/faces/usuario/principal.xhtml");
                paginaPrincipal.forward(request, response);
            }
        } catch (IOException ex) {
            Logger.getLogger(AutorizacaoFazerRequerimento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException sEx) {
            Logger.getLogger(AutorizacaoFazerRequerimento.class.getName()).log(Level.SEVERE, null, sEx);
        }
    }

}