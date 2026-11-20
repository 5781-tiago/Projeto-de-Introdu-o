package pt.ecl.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pt.ecl.dao.CursoDAO;
import pt.ecl.dao.PreInscricaoDAO;
import pt.ecl.model.Curso;
import pt.ecl.model.PreInscricao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet({"/pre-inscricao", "/pre-inscricao/confirmacao"})
public class PreInscricaoServlet extends HttpServlet {

    private final CursoDAO cursoDAO = new CursoDAO();
    private final PreInscricaoDAO preInscricaoDAO = new PreInscricaoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getServletPath().equals("/pre-inscricao/confirmacao")) {
            HttpSession sessao = request.getSession();
            PreInscricao p = (PreInscricao) sessao.getAttribute("inscricaoFeita");
            if (p == null) {
                response.sendRedirect(request.getContextPath() + "/pre-inscricao");
                return;
            }
            sessao.removeAttribute("inscricaoFeita");
            request.setAttribute("inscricao", p);
            request.getRequestDispatcher("/WEB-INF/views/pre-inscricao-confirmacao.jsp").forward(request, response);
            return;
        }

        request.setAttribute("cursoId", Parametros.texto(request, "curso"));
        mostrarFormulario(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome = Parametros.texto(request, "nome");
        String email = Parametros.texto(request, "email");
        String telefone = Parametros.texto(request, "telefone").replace(" ", "");
        String cursoId = Parametros.texto(request, "curso");
        String observacoes = Parametros.texto(request, "observacoes");
        boolean privacidade = request.getParameter("privacidade") != null;

        Map<String, String> erros = new HashMap<>();

        if (nome.length() < 3) {
            erros.put("nome", "Indica o nome completo (mínimo 3 caracteres).");
        } else if (nome.length() > 120) {
            erros.put("nome", "O nome não pode ter mais de 120 caracteres.");
        }

        if (email.length() > 120 || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            erros.put("email", "Indica um email válido.");
        }

        if (!telefone.isEmpty() && !telefone.matches("^[29]\\d{8}$")) {
            erros.put("telefone", "O telefone tem de ter 9 dígitos e começar por 9 ou 2.");
        }

        Curso curso;
        try {
            curso = cursoDAO.procurarPorId(Parametros.inteiro(request, "curso"));
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        if (curso == null) {
            erros.put("curso", "Escolhe um curso da lista.");
        }

        if (observacoes.length() > 500) {
            erros.put("observacoes", "As observações não podem ter mais de 500 caracteres.");
        }

        if (!privacidade) {
            erros.put("privacidade", "Tens de confirmar que leste a informação de privacidade.");
        }

        if (!erros.isEmpty()) {
            request.setAttribute("erros", erros);
            request.setAttribute("nome", nome);
            request.setAttribute("email", email);
            request.setAttribute("telefone", telefone);
            request.setAttribute("cursoId", cursoId);
            request.setAttribute("observacoes", observacoes);
            request.setAttribute("privacidade", privacidade);
            mostrarFormulario(request, response);
            return;
        }

        PreInscricao p = new PreInscricao();
        p.setNome(nome);
        p.setEmail(email);
        p.setTelefone(telefone.isEmpty() ? null : telefone);
        p.setCursoId(curso.getId());
        p.setNomeCurso(curso.getNome());
        p.setObservacoes(observacoes.isEmpty() ? null : observacoes);
        p.setEstado("Recebida");

        try {
            p.setCodigo(preInscricaoDAO.inserir(p));
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        request.getSession().setAttribute("inscricaoFeita", p);
        response.sendRedirect(request.getContextPath() + "/pre-inscricao/confirmacao");
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("cursos", cursoDAO.listar());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        request.getRequestDispatcher("/WEB-INF/views/pre-inscricao.jsp").forward(request, response);
    }
}
