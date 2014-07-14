package br.ufg.inf.sigera.controle.filtro;

import br.ufg.inf.sigera.controle.LoginBean;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;

public class VerificadorAutenticacao implements Filter {

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

            Object bean = session.getAttribute("loginBean");
            if (bean == null) {
                enviarParaPaginaDeLogin(request, response);
                return;
            }

            LoginBean loginBean = (LoginBean) bean;
            if (!loginBean.isLogado()) {
                enviarParaPaginaDeLogin(request, response);
                return;
            }

            chain.doFilter(req, resp);
        } catch (IOException ex) {
            Logger.getLogger(VerificadorAutenticacao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException sEx) {
            Logger.getLogger(VerificadorAutenticacao.class.getName()).log(Level.SEVERE, null, sEx);
        }
    }

    private void enviarParaPaginaDeLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            String url = obtenhaUrlCompleta(request);

            HttpSession session = request.getSession();
            session.setAttribute("urlOriginal", url);

            RequestDispatcher paginaLogin = request.getRequestDispatcher("/faces/login.xhtml");
            paginaLogin.forward(request, response);
        } catch (IOException ex) {
            Logger.getLogger(VerificadorAutenticacao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException sEx) {
            Logger.getLogger(VerificadorAutenticacao.class.getName()).log(Level.SEVERE, null, sEx);
        }

    }

    private String obtenhaUrlCompleta(HttpServletRequest request) {
        StringBuilder urlCompleta = new StringBuilder();
        String queryString = request.getQueryString();

        urlCompleta.append(request.getRequestURL().toString());

        if (queryString == null) {
            return urlCompleta.toString();
        } else {
            return urlCompleta.append('?').append(queryString).toString();
        }
    }

    @Override
    public void destroy() {
        //TODO implementação de filter exigir sobrescrever metodo destroy
    }
}