package pt.ecl.dao;

import pt.ecl.model.Historico;
import pt.ecl.util.Ligacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoricoDAO {

    static void registar(Connection con, String tipo, int idRegisto, String anterior, String novo, int utilizadorId)
            throws SQLException {
        String sql = "INSERT INTO historico_estados (tipo, id_registo, estado_anterior, estado_novo, id_utilizador) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipo);
            ps.setInt(2, idRegisto);
            ps.setString(3, anterior);
            ps.setString(4, novo);
            ps.setInt(5, utilizadorId);
            ps.executeUpdate();
        }
    }

    static List<Historico> listar(String tipo, int idRegisto) throws SQLException {
        List<Historico> lista = new ArrayList<>();
        String sql = "SELECT h.*, u.nome FROM historico_estados h JOIN utilizadores u ON u.id = h.id_utilizador "
                + "WHERE h.tipo = ? AND h.id_registo = ? ORDER BY h.alterado_em DESC, h.id DESC";
        try (Connection con = Ligacao.obter();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipo);
            ps.setInt(2, idRegisto);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Historico h = new Historico();
                    h.setEstadoAnterior(rs.getString("estado_anterior"));
                    h.setEstadoNovo(rs.getString("estado_novo"));
                    h.setUtilizador(rs.getString("nome"));
                    h.setAlteradoEm(rs.getTimestamp("alterado_em").toLocalDateTime());
                    lista.add(h);
                }
            }
        }
        return lista;
    }
}
