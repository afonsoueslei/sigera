package br.ufg.inf.sigera.controle.filtro;

import javax.servlet.*;

public abstract class FiltroUrlBase implements Filter {
    @Override
    public void init(FilterConfig config) throws ServletException {
        System.out.println("Método init da classe FiltroUrlBase ");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) {
      System.out.println("Método doFilter da classe FiltroUrlBase será SOBREESCRITO em cada filtro");
    }

    @Override
    public void destroy() {        
        System.out.println("Método destroy da classe FiltroUrlBase ");
    }
}
