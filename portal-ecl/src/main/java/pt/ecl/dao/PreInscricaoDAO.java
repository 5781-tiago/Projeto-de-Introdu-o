package pt.ecl.dao;

import pt.ecl.model.Historico;
import pt.ecl.model.PreInscricao;
import pt.ecl.util.Codigos;
import pt.ecl.util.Ligacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PreInscricaoDAO {

    public static final List<String> ESTADOS = List.of("Recebida", "Em análise", "Contactada");

    public String inserir(PreInscricao p) throws SQLException {
        String sql = "INSERT INTO pre_inscricoes (codigo, id_curso, nome, email, telefone, observacoes) VALUES (?, ?, ?, ?, ?, ?)";

        for (int tentativa = 0; tentativa < 5; tentativa++) {
            String codigo = Codigos.novo("PI");
            try (Connection con = Ligacao.obter();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, codigo);
                ps.setInt(2, p.getCursoId());
                ps.setString(3, p.getNome());
                ps.setString(4, p.getEmail());
                if (p.getTelefone() == null) {
                    ps.setNull(5, Types.VARCHAR);
                } else {
                    ps.setString(5, p.getTelefone());
                }
                ps.setString(6, p.getObservacoes());
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

    public List<PreInscricao> listar(int cursoId, String estado) throws SQLException {
        List<PreInscricao> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT p.*, c.nome AS nome_curso FROM pre_inscricoes p "
                + "JOIN cursos c ON c.id_curso = p.id_curso WHERE 1 = 1");
        if (cursoId > 0) {
            sql.append(" AND p.id_curso = ?");
        }
        if (!estado.isEmpty()) {
            sql.append(" AND p.estado = ?");
        }
        sql.append(" ORDER BY p.criado_em DESC");

        try (Connection con = Ligacao.obter();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            int i = 1;
            if (cursoId > 0) {
                ps.setInt(i++, cursoId);
            }
            if (!estado.isEmpty()) {
                ps.setString(i, estado);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(ler(rs));
                }
            }
        }
        return lista;
    }

    public void alterarEstado(int id, String novoEstado, int utilizadorId) throws SQLException {
        try (Connection con = Ligacao.obter()) {
            con.setAutoCommit(false);
            try {
                String atual;
                try (PreparedStatement ps = con.prepareStatement("SELECT estado FROM pre_inscricoes WHERE id = ? FOR UPDATE")) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            con.rollback();
                            return;
                        }
                        atual = rs.getString(1);
                    }
                }
                if (!atual.equals(novoEstado)) {
                    try (PreparedStatement ps = con.prepareStatement(
                            "UPDATE pre_inscricoes SET estado = ?, atualizado_em = CURRENT_TIMESTAMP WHERE id = ?")) {
                        ps.setString(1, novoEstado);
                        ps.setInt(2, id);
                        ps.executeUpdate();
                    }
                    HistoricoDAO.registar(con, "Pré-inscrição", id, atual, novoEstado, utilizadorId);
                }
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    public List<Historico> historico(int id) throws SQLException {
        return HistoricoDAO.listar("Pré-inscrição", id);
    }

    public Map<String, Integer> contarPorEstado() throws SQLException {
        Map<String, Integer> contagem = new LinkedHashMap<>();
        for (String e : ESTADOS) {
            contagem.put(e, 0);
        }
        try (Connection con = Ligacao.obter();
             PreparedStatement ps = con.prepareStatement("SELECT estado, COUNT(*) FROM pre_inscricoes GROUP BY estado");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                contagem.put(rs.getString(1), rs.getInt(2));
            }
        }
        return contagem;
    }

    private PreInscricao ler(ResultSet rs) throws SQLException {
        PreInscricao p = new PreInscricao();
        p.setId(rs.getInt("id"));
        p.setCodigo(rs.getString("codigo"));
        p.setCursoId(rs.getInt("id_curso"));
        p.setNomeCurso(rs.getString("nome_curso"));
        p.setNome(rs.getString("nome"));
        p.setEmail(rs.getString("email"));
        p.setTelefone(rs.getString("telefone"));
        p.setObservacoes(rs.getString("observacoes"));
        p.setEstado(rs.getString("estado"));
        p.setCriadoEm(rs.getTimestamp("criado_em").toLocalDateTime());
        Timestamp at = rs.getTimestamp("atualizado_em");
        p.setAtualizadoEm(at == null ? null : at.toLocalDateTime());
        return p;
    }
}
