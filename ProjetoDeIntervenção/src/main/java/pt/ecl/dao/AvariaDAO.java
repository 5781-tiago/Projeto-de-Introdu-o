package pt.ecl.dao;

import pt.ecl.model.Avaria;
import pt.ecl.util.Ligacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AvariaDAO {

    public void inserir(Avaria a) throws SQLException {
        String sql = "INSERT INTO avarias (sala, tipo, descricao, reportado_por) VALUES (?, ?, ?, ?)";

        try (Connection con = Ligacao.obter();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getSala());
            ps.setString(2, a.getTipo());
            ps.setString(3, a.getDescricao());
            ps.setString(4, a.getReportadoPor());
            ps.executeUpdate();
        }
    }

    public List<Avaria> listar() throws SQLException {
        List<Avaria> lista = new ArrayList<>();
        String sql = "SELECT * FROM avarias ORDER BY data_registo DESC";

        try (Connection con = Ligacao.obter();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Avaria a = new Avaria();
                a.setId(rs.getInt("id"));
                a.setSala(rs.getString("sala"));
                a.setTipo(rs.getString("tipo"));
                a.setDescricao(rs.getString("descricao"));
                a.setReportadoPor(rs.getString("reportado_por"));
                a.setEstado(rs.getString("estado"));
                a.setDataRegisto(rs.getTimestamp("data_registo").toLocalDateTime());
                lista.add(a);
            }
        }
        return lista;
    }

    public void alterarEstado(int id, String estado) throws SQLException {
        String sql = "UPDATE avarias SET estado = ? WHERE id = ?";

        try (Connection con = Ligacao.obter();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
}
