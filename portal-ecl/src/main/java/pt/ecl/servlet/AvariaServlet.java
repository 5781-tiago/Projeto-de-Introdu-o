package pt.ecl.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pt.ecl.dao.AvariaDAO;
import pt.ecl.model.Avaria;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet({"/avarias", "/avarias/sucesso"})
public class AvariaServlet extends HttpServlet {

    public static final List<String> TIPOS = List.of("Equipamento", "Software", "Rede");

    private final AvariaDAO avariaDAO = new AvariaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getServletPath().equals("/avarias/sucesso")) {
            HttpSession sessao = request.getSession();
            Avaria a = (Avaria) sessao.getAttribute("avariaRegistada");
            if (a == null) {
                response.sendRedirect(request.getContextPath() + "/avarias");
                return;
            }
            sessao.removeAttribute("avariaRegistada");
            request.setAttribute("avaria", a);
            request.getRequestDispatcher("/WEB-INF/views/avaria-sucesso.jsp").forward(request, response);
            return;
        }

        request.setAttribute("tipos", TIPOS);
        request.getRequestDispatcher("/WEB-INF/views/avaria.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sala = limpar(request.getParameter("sala"));
        String tipo = limpar(request.getParameter("tipo"));
        String descricao = limpar(request.getParameter("descricao"));
        String reportadoPor = limpar(request.getParameter("reportadoPor"));

        Map<String, String> erros = new HashMap<>();

        if (sala.isEmpty()) {
            erros.put("sala", "Indica a sala.");
        } else if (sala.length() > 20) {
            erros.put("sala", "O nome da sala é demasiado longo.");
        }

        if (!TIPOS.contains(tipo)) {
            erros.put("tipo", "Escolhe o tipo de avaria.");
        }

        if (descricao.length() < 10) {
            erros.put("descricao", "A descrição tem de ter pelo menos 10 caracteres.");
        }

        if (reportadoPor.length() < 3) {
            erros.put("reportadoPor", "Indica o teu nome.");
        }

        if (!erros.isEmpty()) {
            request.setAttribute("erros", erros);
            request.setAttribute("sala", sala);
            request.setAttribute("tipo", tipo);
            request.setAttribute("descricao", descricao);
            request.setAttribute("reportadoPor", reportadoPor);
            request.setAttribute("tipos", TIPOS);
            request.getRequestDispatcher("/WEB-INF/views/avaria.jsp").forward(request, response);
            return;
        }

        Avaria a = new Avaria();
        a.setSala(sala);
        a.setTipo(tipo);
        a.setDescricao(descricao);
        a.setReportadoPor(reportadoPor);

        try {
            avariaDAO.inserir(a);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        request.getSession().setAttribute("avariaRegistada", a);
        response.sendRedirect(request.getContextPath() + "/avarias/sucesso");
    }

    private String limpar(String valor) {
        return valor == null ? "" : valor.trim();
    }
}
