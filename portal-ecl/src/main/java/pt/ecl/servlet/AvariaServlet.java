package pt.ecl.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pt.ecl.dao.OcorrenciaDAO;
import pt.ecl.dao.SalaDAO;
import pt.ecl.model.Ocorrencia;
import pt.ecl.model.Sala;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet({"/avarias", "/avarias/confirmacao", "/avarias/consultar"})
public class AvariaServlet extends HttpServlet {

    private final OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();
    private final SalaDAO salaDAO = new SalaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            switch (request.getServletPath()) {
                case "/avarias/confirmacao" -> mostrarConfirmacao(request, response);
                case "/avarias/consultar" -> consultar(request, response);
                default -> mostrarFormulario(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome = Parametros.texto(request, "nome");
        String perfil = Parametros.texto(request, "perfil");
        String salaId = Parametros.texto(request, "sala");
        String categoria = Parametros.texto(request, "categoria");
        String descricao = Parametros.texto(request, "descricao");
        String prioridade = Parametros.texto(request, "prioridade");

        Map<String, String> erros = new HashMap<>();

        if (nome.length() < 3 || nome.length() > 120) {
            erros.put("nome", "Indica o teu nome (entre 3 e 120 caracteres).");
        }
        if (!OcorrenciaDAO.PERFIS.contains(perfil)) {
            erros.put("perfil", "Indica se és formando ou formador.");
        }

        Sala sala;
        try {
            sala = salaDAO.procurarPorId(Parametros.inteiro(request, "sala"));
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        if (sala == null) {
            erros.put("sala", "Escolhe a sala da lista.");
        }

        if (!OcorrenciaDAO.CATEGORIAS.contains(categoria)) {
            erros.put("categoria", "Escolhe o equipamento ou serviço afetado.");
        }
        if (descricao.length() < 10) {
            erros.put("descricao", "Descreve o problema com pelo menos 10 caracteres.");
        } else if (descricao.length() > 1000) {
            erros.put("descricao", "A descrição não pode ter mais de 1000 caracteres.");
        }
        if (!OcorrenciaDAO.PRIORIDADES.contains(prioridade)) {
            erros.put("prioridade", "Escolhe a prioridade.");
        }

        if (!erros.isEmpty()) {
            request.setAttribute("erros", erros);
            request.setAttribute("nome", nome);
            request.setAttribute("perfil", perfil);
            request.setAttribute("salaId", salaId);
            request.setAttribute("categoria", categoria);
            request.setAttribute("descricao", descricao);
            request.setAttribute("prioridade", prioridade);
            try {
                mostrarFormulario(request, response);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
            return;
        }

        Ocorrencia o = new Ocorrencia();
        o.setNomeReportador(nome);
        o.setPerfilReportador(perfil);
        o.setSalaId(sala.getId());
        o.setSala(sala.getCodigo());
        o.setLocalizacao(sala.getEdificio() + ", " + sala.getPiso());
        o.setCategoria(categoria);
        o.setDescricao(descricao);
        o.setPrioridade(prioridade);
        o.setEstado("Aberta");

        try {
            o.setCodigo(ocorrenciaDAO.inserir(o));
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        request.getSession().setAttribute("ocorrenciaRegistada", o);
        response.sendRedirect(request.getContextPath() + "/avarias/confirmacao");
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        request.setAttribute("salas", salaDAO.listar());
        request.setAttribute("categorias", OcorrenciaDAO.CATEGORIAS);
        request.setAttribute("prioridades", OcorrenciaDAO.PRIORIDADES);
        request.setAttribute("perfis", OcorrenciaDAO.PERFIS);
        request.getRequestDispatcher("/WEB-INF/views/avaria.jsp").forward(request, response);
    }

    private void mostrarConfirmacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        Ocorrencia o = (Ocorrencia) sessao.getAttribute("ocorrenciaRegistada");
        if (o == null) {
            response.sendRedirect(request.getContextPath() + "/avarias");
            return;
        }
        sessao.removeAttribute("ocorrenciaRegistada");
        request.setAttribute("ocorrencia", o);
        request.getRequestDispatcher("/WEB-INF/views/avaria-confirmacao.jsp").forward(request, response);
    }

    private void consultar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String codigo = Parametros.texto(request, "codigo").toUpperCase();
        if (!codigo.isEmpty()) {
            request.setAttribute("codigo", codigo);
            Ocorrencia o = codigo.matches("^AV-[A-Z0-9]{6}$") ? ocorrenciaDAO.procurarPorCodigo(codigo) : null;
            if (o == null) {
                request.setAttribute("naoEncontrada", true);
            } else {
                request.setAttribute("ocorrencia", o);
            }
        }
        request.getRequestDispatcher("/WEB-INF/views/avaria-consulta.jsp").forward(request, response);
    }
}
