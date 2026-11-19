package pt.ecl.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet({"/login", "/logout"})
public class LoginServlet extends HttpServlet {

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
        String utilizador = request.getParameter("utilizador");
        String password = request.getParameter("password");

        String utilizadorCerto = getServletContext().getInitParameter("admin.utilizador");
        String passwordCerta = getServletContext().getInitParameter("admin.password");

        if (utilizadorCerto.equals(utilizador) && passwordCerta.equals(password)) {
            request.getSession().invalidate();
            request.getSession(true).setAttribute("admin", utilizador);
            response.sendRedirect(request.getContextPath() + "/gestao/inscricoes");
        } else {
            request.setAttribute("erro", "Utilizador ou password errados.");
            request.setAttribute("utilizador", utilizador);
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}
