package pt.ecl.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

@WebFilter({"/gestao", "/gestao/*"})
public class AutenticacaoFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpSession sessao = request.getSession(false);
        if (sessao == null || sessao.getAttribute("utilizador") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if ("POST".equalsIgnoreCase(request.getMethod())) {
            String esperado = (String) sessao.getAttribute("csrf");
            String recebido = request.getParameter("csrf");
            if (esperado == null || recebido == null
                    || !MessageDigest.isEqual(esperado.getBytes(StandardCharsets.UTF_8), recebido.getBytes(StandardCharsets.UTF_8))) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        response.setHeader("Cache-Control", "no-store");
        chain.doFilter(request, response);
    }
}
