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
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@WebServlet({"/pre-inscricao", "/pre-inscricao/sucesso"})
public class PreInscricaoServlet extends HttpServlet {

    private final CursoDAO cursoDAO = new CursoDAO();
    private final PreInscricaoDAO preInscricaoDAO = new PreInscricaoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getServletPath().equals("/pre-inscricao/sucesso")) {
            HttpSession sessao = request.getSession();
            PreInscricao p = (PreInscricao) sessao.getAttribute("inscricaoFeita");
            if (p == null) {
                response.sendRedirect(request.getContextPath() + "/pre-inscricao");
                return;
            }
            sessao.removeAttribute("inscricaoFeita");
            request.setAttribute("inscricao", p);
            request.getRequestDispatcher("/WEB-INF/views/pre-inscricao-sucesso.jsp").forward(request, response);
            return;
        }

        String curso = request.getParameter("curso");
        request.setAttribute("cursoId", curso == null ? "" : curso);
        mostrarFormulario(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome = limpar(request.getParameter("nome"));
        String email = limpar(request.getParameter("email"));
        String telefone = limpar(request.getParameter("telefone")).replace(" ", "");
        String dataNascimento = limpar(request.getParameter("dataNascimento"));
        String cursoId = limpar(request.getParameter("curso"));
        String observacoes = limpar(request.getParameter("observacoes"));
        String rgpd = request.getParameter("rgpd");

        Map<String, String> erros = new HashMap<>();

        if (nome.length() < 3) {
            erros.put("nome", "Indica o nome completo.");
        }

        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            erros.put("email", "Indica um email válido.");
        }

        if (!telefone.matches("^[29]\\d{8}$")) {
            erros.put("telefone", "O telefone tem de ter 9 dígitos e começar por 9 ou 2.");
        }

        LocalDate data = null;
        try {
            data = LocalDate.parse(dataNascimento);
            int idade = Period.between(data, LocalDate.now()).getYears();
            if (data.isAfter(LocalDate.now()) || idade < 14 || idade > 100) {
                erros.put("dataNascimento", "A idade tem de estar entre 14 e 100 anos.");
            }
        } catch (DateTimeParseException e) {
            erros.put("dataNascimento", "Indica a data de nascimento.");
        }

        Curso curso = null;
        try {
            curso = cursoDAO.procurarPorId(Integer.parseInt(cursoId));
        } catch (NumberFormatException e) {
            curso = null;
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        if (curso == null) {
            erros.put("curso", "Escolhe um curso da lista.");
        }

        if (observacoes.length() > 500) {
            erros.put("observacoes", "As observações não podem ter mais de 500 caracteres.");
        }

        if (rgpd == null) {
            erros.put("rgpd", "Tens de autorizar o tratamento dos dados para continuar.");
        }

        if (!erros.isEmpty()) {
            request.setAttribute("erros", erros);
            request.setAttribute("nome", nome);
            request.setAttribute("email", email);
            request.setAttribute("telefone", telefone);
            request.setAttribute("dataNascimento", dataNascimento);
            request.setAttribute("cursoId", cursoId);
            request.setAttribute("observacoes", observacoes);
            request.setAttribute("rgpd", rgpd != null);
            mostrarFormulario(request, response);
            return;
        }

        PreInscricao p = new PreInscricao();
        p.setNome(nome);
        p.setEmail(email);
        p.setTelefone(telefone);
        p.setDataNascimento(data);
        p.setCursoId(curso.getId());
        p.setNomeCurso(curso.getNome());
        p.setObservacoes(observacoes);

        try {
            preInscricaoDAO.inserir(p);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        request.getSession().setAttribute("inscricaoFeita", p);
        response.sendRedirect(request.getContextPath() + "/pre-inscricao/sucesso");
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

    private String limpar(String valor) {
        return valor == null ? "" : valor.trim();
    }
}
