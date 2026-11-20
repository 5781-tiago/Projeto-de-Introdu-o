package pt.ecl.dao;

import pt.ecl.model.Sala;
import pt.ecl.util.Ligacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaDAO {

    public List<Sala> listar() throws SQLException {
        List<Sala> salas = new ArrayList<>();
        String sql = "SELECT * FROM salas ORDER BY edificio, piso, codigo";
        try (Connection con = Ligacao.obter();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Sala s = new Sala();
                s.setId(rs.getInt("id"));
                s.setCodigo(rs.getString("codigo"));
                s.setEdificio(rs.getString("edificio"));
                s.setPiso(rs.getString("piso"));
                salas.add(s);
            }
        }
        return salas;
    }

    public Sala procurarPorId(int id) throws SQLException {
        for (Sala s : listar()) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }
}
