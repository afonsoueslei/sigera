package br.ufg.inf.sigera.controle.filtro;

import br.ufg.inf.sigera.controle.LoginBean;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;

public class AutorizacaoPlanoAula implements Filter {

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
            LoginBean loginBean = (LoginBean) session.getAttribute("loginBean");
            if (loginBean.getUsuario().getPerfilAtual().getPerfil().permitePlanoDeAula()) {
                chain.doFilter(req, resp);
            } else {
                RequestDispatcher paginaPrincipal = request.getRequestDispatcher("/faces/usuario/principal.xhtml");
                paginaPrincipal.forward(request, response);
            }
        } catch (IOException ex) {
            Logger.getLogger(AutorizacaoPlanoAula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException sEx) {
            Logger.getLogger(AutorizacaoPlanoAula.class.getName()).log(Level.SEVERE, null, sEx);
        }
    }

    @Override
    public void destroy() {
        //TODO implementação de filter exigir sobrescrever metodo destroy
    }
}