package br.ufg.inf.sigera.controle.filtro;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;

public class FiltroUrl implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        // TODO
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) {
        try {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) resp;
            String prefixoFacesServlet = "/faces";
            String prefixoDuplicado = prefixoFacesServlet + prefixoFacesServlet;
            String url = request.getRequestURI();

            if (!url.contains(prefixoDuplicado)) {
                chain.doFilter(req, resp);
                return;
            }

            do {
                url = url.replace(prefixoDuplicado, prefixoFacesServlet);
            } while (url.contains(prefixoDuplicado));

            response.sendRedirect(url);

        } catch (IOException ex) {
            Logger.getLogger(FiltroUrl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException sEx) {
            Logger.getLogger(FiltroUrl.class.getName()).log(Level.SEVERE, null, sEx);
        }
    }

    @Override
    public void destroy() {
        //TODO implementação de filter exigir sobrescrever metodo destroy
    }
}