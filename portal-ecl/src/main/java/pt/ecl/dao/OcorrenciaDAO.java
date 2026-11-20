package pt.ecl.dao;

import pt.ecl.model.Historico;
import pt.ecl.model.Ocorrencia;
import pt.ecl.util.Codigos;
import pt.ecl.util.Ligacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OcorrenciaDAO {

    public static final List<String> CATEGORIAS = List.of("PC", "Projetor", "Rede", "Wi-Fi", "Impressora", "Software", "Mobiliário", "Outro");
    public static final List<String> PRIORIDADES = List.of("Baixa", "Normal", "Elevada");
    public static final List<String> ESTADOS = List.of("Aberta", "Em análise", "Em resolução", "Resolvida");
    public static final List<String> PERFIS = List.of("Formando", "Formador");

    private static final String SELECT_BASE = "SELECT o.*, s.codigo AS sala, CONCAT(s.edificio, ', ', s.piso) AS localizacao, "
            + "u.nome AS tecnico FROM ocorrencias o JOIN salas s ON s.id = o.id_sala "
            + "LEFT JOIN utilizadores u ON u.id = o.id_utilizador";

    public String inserir(Ocorrencia o) throws SQLException {
        String sql = "INSERT INTO ocorrencias (codigo, nome_reportador, perfil_reportador, id_sala, categoria, descricao, prioridade) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        for (int tentativa = 0; tentativa < 5; tentativa++) {
            String codigo = Codigos.novo("AV");
            try (Connection con = Ligacao.obter();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, codigo);
                ps.setString(2, o.getNomeReportador());
                ps.setString(3, o.getPerfilReportador());
                ps.setInt(4, o.getSalaId());
                ps.setString(5, o.getCategoria());
                ps.setString(6, o.getDescricao());
                ps.setString(7, o.getPrioridade());
                ps.executeUpdate();
                return codigo;
            } catch (SQLIntegrityConstraintViolationException e) {
                if (tentativa == 4) {
                    throw e;
                }
            }
        }
        throw new SQLException("Não foi possível gerar um código único");
    }

    public Ocorrencia procurarPorCodigo(String codigo) throws SQLException {
        try (Connection con = Ligacao.obter();
             PreparedStatement ps = con.prepareStatement(SELECT_BASE + " WHERE o.codigo = ?")) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? ler(rs) : null;
            }
        }
    }

    public Ocorrencia procurarPorId(int id) throws SQLException {
        try (Connection con = Ligacao.obter();
             PreparedStatement ps = con.prepareStatement(SELECT_BASE + " WHERE o.id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? ler(rs) : null;
            }
        }
    }

    public List<Ocorrencia> listar(int salaId, String estado, String prioridade) throws SQLException {
        List<Ocorrencia> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder(SELECT_BASE + " WHERE 1 = 1");
        List<Object> parametros = new ArrayList<>();
        if (salaId > 0) {
            sql.append(" AND o.id_sala = ?");
            parametros.add(salaId);
        }
        if (!estado.isEmpty()) {
            sql.append(" AND o.estado = ?");
            parametros.add(estado);
        }
        if (!prioridade.isEmpty()) {
            sql.append(" AND o.prioridade = ?");
            parametros.add(prioridade);
        }
        sql.append(" ORDER BY FIELD(o.estado, 'Aberta', 'Em análise', 'Em resolução', 'Resolvida'), "
                + "FIELD(o.prioridade, 'Elevada', 'Normal', 'Baixa'), o.criado_em DESC");

        try (Connection con = Ligacao.obter();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(ler(rs));
                }
            }
        }
        return lista;
    }

    public void atualizar(int id, String novoEstado, String resolucao, int utilizadorId) throws SQLException {
        try (Connection con = Ligacao.obter()) {
            con.setAutoCommit(false);
            try {
                String atual;
                try (PreparedStatement ps = con.prepareStatement("SELECT estado FROM ocorrencias WHERE id = ? FOR UPDATE")) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            con.rollback();
                            return;
                        }
                        atual = rs.getString(1);
                    }
                }
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE ocorrencias SET estado = ?, resolucao = ?, id_utilizador = ?, atualizado_em = CURRENT_TIMESTAMP WHERE id = ?")) {
                    ps.setString(1, novoEstado);
                    ps.setString(2, resolucao.isEmpty() ? null : resolucao);
                    ps.setInt(3, utilizadorId);
                    ps.setInt(4, id);
                    ps.executeUpdate();
                }
                if (!atual.equals(novoEstado)) {
                    HistoricoDAO.registar(con, "Ocorrência", id, atual, novoEstado, utilizadorId);
                }
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    public List<Historico> historico(int id) throws SQLException {
        return HistoricoDAO.listar("Ocorrência", id);
    }

    public Map<String, Integer> contarPorEstado() throws SQLException {
        Map<String, Integer> contagem = new LinkedHashMap<>();
        for (String e : ESTADOS) {
            contagem.put(e, 0);
        }
        try (Connection con = Ligacao.obter();
             PreparedStatement ps = con.prepareStatement("SELECT estado, COUNT(*) FROM ocorrencias GROUP BY estado");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                contagem.put(rs.getString(1), rs.getInt(2));
            }
        }
        return contagem;
    }

    public int contarAbertasElevadas() throws SQLException {
        try (Connection con = Ligacao.obter();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT COUNT(*) FROM ocorrencias WHERE prioridade = 'Elevada' AND estado <> 'Resolvida'");
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    private Ocorrencia ler(ResultSet rs) throws SQLException {
        Ocorrencia o = new Ocorrencia();
        o.setId(rs.getInt("id"));
        o.setCodigo(rs.getString("codigo"));
        o.setNomeReportador(rs.getString("nome_reportador"));
        o.setPerfilReportador(rs.getString("perfil_reportador"));
        o.setSalaId(rs.getInt("id_sala"));
        o.setSala(rs.getString("sala"));
        o.setLocalizacao(rs.getString("localizacao"));
        o.setCategoria(rs.getString("categoria"));
        o.setDescricao(rs.getString("descricao"));
        o.setPrioridade(rs.getString("prioridade"));
        o.setEstado(rs.getString("estado"));
        o.setResolucao(rs.getString("resolucao"));
        o.setTecnico(rs.getString("tecnico"));
        o.setCriadoEm(rs.getTimestamp("criado_em").toLocalDateTime());
        Timestamp at = rs.getTimestamp("atualizado_em");
        o.setAtualizadoEm(at == null ? null : at.toLocalDateTime());
        return o;
    }
}
