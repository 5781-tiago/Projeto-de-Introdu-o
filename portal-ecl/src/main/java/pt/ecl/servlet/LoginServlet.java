package pt.ecl.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pt.ecl.dao.UtilizadorDAO;
import pt.ecl.model.Utilizador;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;

@WebServlet({"/login", "/logout"})
public class LoginServlet extends HttpServlet {

    private final UtilizadorDAO utilizadorDAO = new UtilizadorDAO();
    private final SecureRandom aleatorio = new SecureRandom();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getServletPath().equals("/logout")) {
            HttpSession sessao = request.getSession(false);
            if (sessao != null) {
                sessao.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/inicio");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getServletPath().equals("/logout")) {
            doGet(request, response);
            return;
        }

        String identificador = Parametros.texto(request, "utilizador");
        String password = request.getParameter("password");

        Utilizador u;
        try {
            u = identificador.isEmpty() || password == null ? null : utilizadorDAO.autenticar(identificador, password);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        if (u == null) {
            request.setAttribute("erro", "Utilizador ou password incorretos.");
            request.setAttribute("utilizador", identificador);
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        HttpSession antiga = request.getSession(false);
        if (antiga != null) {
            antiga.invalidate();
        }
        HttpSession sessao = request.getSession(true);
        byte[] token = new byte[24];
        aleatorio.nextBytes(token);
        sessao.setAttribute("utilizador", u);
        sessao.setAttribute("csrf", Base64.getUrlEncoder().withoutPadding().encodeToString(token));
        response.sendRedirect(request.getContextPath() + "/gestao");
    }
}
