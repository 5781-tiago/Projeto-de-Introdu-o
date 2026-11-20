package pt.ecl.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pt.ecl.dao.CursoDAO;
import pt.ecl.dao.OcorrenciaDAO;
import pt.ecl.dao.PreInscricaoDAO;
import pt.ecl.dao.SalaDAO;
import pt.ecl.model.Ocorrencia;
import pt.ecl.model.Utilizador;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@WebServlet({"/gestao", "/gestao/cursos", "/gestao/inscricoes", "/gestao/ocorrencias", "/gestao/ocorrencia"})
public class GestaoServlet extends HttpServlet {

    private final CursoDAO cursoDAO = new CursoDAO();
    private final PreInscricaoDAO preInscricaoDAO = new PreInscricaoDAO();
    private final OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();
    private final SalaDAO salaDAO = new SalaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            switch (request.getServletPath()) {
                case "/gestao/cursos" -> {
                    request.setAttribute("cursos", cursoDAO.listar());
                    vista(request, response, "cursos");
                }
                case "/gestao/inscricoes" -> {
                    if (!isGestor(request)) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                    int curso = Parametros.inteiro(request, "curso");
                    String estado = valorPermitido(Parametros.texto(request, "estado"), PreInscricaoDAO.ESTADOS);
                    request.setAttribute("inscricoes", preInscricaoDAO.listar(curso, estado));
                    request.setAttribute("cursos", cursoDAO.listar());
                    request.setAttribute("estados", PreInscricaoDAO.ESTADOS);
                    request.setAttribute("filtroCurso", curso);
                    request.setAttribute("filtroEstado", estado);
                    vista(request, response, "inscricoes");
                }
                case "/gestao/ocorrencias" -> {
                    int sala = Parametros.inteiro(request, "sala");
                    String estado = valorPermitido(Parametros.texto(request, "estado"), OcorrenciaDAO.ESTADOS);
                    String prioridade = valorPermitido(Parametros.texto(request, "prioridade"), OcorrenciaDAO.PRIORIDADES);
                    request.setAttribute("ocorrencias", ocorrenciaDAO.listar(sala, estado, prioridade));
                    request.setAttribute("salas", salaDAO.listar());
                    request.setAttribute("estados", OcorrenciaDAO.ESTADOS);
                    request.setAttribute("prioridades", OcorrenciaDAO.PRIORIDADES);
                    request.setAttribute("filtroSala", sala);
                    request.setAttribute("filtroEstado", estado);
                    request.setAttribute("filtroPrioridade", prioridade);
                    vista(request, response, "ocorrencias");
                }
                case "/gestao/ocorrencia" -> {
                    Ocorrencia o = ocorrenciaDAO.procurarPorId(Parametros.inteiro(request, "id"));
                    if (o == null) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }
                    request.setAttribute("ocorrencia", o);
                    request.setAttribute("historico", ocorrenciaDAO.historico(o.getId()));
                    request.setAttribute("estados", OcorrenciaDAO.ESTADOS);
                    request.setAttribute("guardado", request.getParameter("guardado") != null);
                    vista(request, response, "ocorrencia");
                }
                default -> {
                    request.setAttribute("contagemInscricoes", preInscricaoDAO.contarPorEstado());
                    request.setAttribute("contagemOcorrencias", ocorrenciaDAO.contarPorEstado());
                    request.setAttribute("elevadasAbertas", ocorrenciaDAO.contarAbertasElevadas());
                    vista(request, response, "resumo");
                }
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int utilizadorId = utilizador(request).getId();
        String base = request.getContextPath();

        try {
            if (request.getServletPath().equals("/gestao/inscricoes")) {
                if (!isGestor(request)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                int id = Parametros.inteiro(request, "id");
                String estado = Parametros.texto(request, "novoEstado");
                if (id > 0 && PreInscricaoDAO.ESTADOS.contains(estado)) {
                    preInscricaoDAO.alterarEstado(id, estado, utilizadorId);
                }
                response.sendRedirect(base + "/gestao/inscricoes?curso=" + Parametros.inteiro(request, "filtroCurso")
                        + "&estado=" + url(Parametros.texto(request, "filtroEstado")));
                return;
            }

            if (request.getServletPath().equals("/gestao/ocorrencia")) {
                int id = Parametros.inteiro(request, "id");
                String estado = Parametros.texto(request, "estado");
                String resolucao = Parametros.texto(request, "resolucao");
                if (id <= 0 || !OcorrenciaDAO.ESTADOS.contains(estado) || resolucao.length() > 1000) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                ocorrenciaDAO.atualizar(id, estado, resolucao, utilizadorId);
                response.sendRedirect(base + "/gestao/ocorrencia?id=" + id + "&guardado=1");
                return;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    private void vista(HttpServletRequest request, HttpServletResponse response, String nome)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/gestao/" + nome + ".jsp").forward(request, response);
    }

    private Utilizador utilizador(HttpServletRequest request) {
        return (Utilizador) request.getSession().getAttribute("utilizador");
    }

    private boolean isGestor(HttpServletRequest request) {
        return "Gestor".equals(utilizador(request).getPerfil());
    }

    private String valorPermitido(String valor, java.util.List<String> permitidos) {
        return permitidos.contains(valor) ? valor : "";
    }

    private String url(String valor) {
        return URLEncoder.encode(valor, StandardCharsets.UTF_8);
    }
}
