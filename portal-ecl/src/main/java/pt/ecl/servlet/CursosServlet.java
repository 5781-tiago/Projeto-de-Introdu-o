package pt.ecl.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pt.ecl.dao.CursoDAO;
import pt.ecl.model.Curso;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet({"/cursos", "/cursos/detalhe"})
public class CursosServlet extends HttpServlet {

    private final CursoDAO cursoDAO = new CursoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (request.getServletPath().equals("/cursos/detalhe")) {
                mostrarDetalhe(request, response);
            } else {
                mostrarLista(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void mostrarLista(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String texto = Parametros.texto(request, "q");
        String modalidade = Parametros.texto(request, "modalidade");
        String area = Parametros.texto(request, "area");

        request.setAttribute("cursos", cursoDAO.pesquisar(texto, modalidade, area));
        request.setAttribute("modalidades", cursoDAO.listarModalidades());
        request.setAttribute("areas", cursoDAO.listarAreas());
        request.setAttribute("q", texto);
        request.setAttribute("modalidadeEscolhida", modalidade);
        request.setAttribute("areaEscolhida", area);
        request.getRequestDispatcher("/WEB-INF/views/cursos.jsp").forward(request, response);
    }

    private void mostrarDetalhe(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Curso curso = null;
        int id = Parametros.inteiro(request, "id");
        if (id > 0) {
            curso = cursoDAO.procurarPorId(id);
        }
        if (curso == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        request.setAttribute("curso", curso);
        request.getRequestDispatcher("/WEB-INF/views/curso-detalhe.jsp").forward(request, response);
    }
}
