package pt.ecl.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pt.ecl.dao.AvariaDAO;
import pt.ecl.dao.PreInscricaoDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet({"/gestao/inscricoes", "/gestao/avarias"})
public class GestaoServlet extends HttpServlet {

    private static final List<String> ESTADOS = List.of("Pendente", "Em resolução", "Resolvida");

    private final PreInscricaoDAO preInscricaoDAO = new PreInscricaoDAO();
    private final AvariaDAO avariaDAO = new AvariaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (request.getServletPath().equals("/gestao/avarias")) {
                request.setAttribute("avarias", avariaDAO.listar());
                request.setAttribute("estados", ESTADOS);
                request.getRequestDispatcher("/WEB-INF/views/gestao-avarias.jsp").forward(request, response);
            } else {
                request.setAttribute("inscricoes", preInscricaoDAO.listar());
                request.getRequestDispatcher("/WEB-INF/views/gestao-inscricoes.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getServletPath().equals("/gestao/avarias")) {
            String estado = request.getParameter("estado");
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                if (ESTADOS.contains(estado)) {
                    avariaDAO.alterarEstado(id, estado);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }
        response.sendRedirect(request.getContextPath() + request.getServletPath());
    }
}
