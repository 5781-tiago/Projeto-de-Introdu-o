package pt.ecl.dao;

import pt.ecl.model.Curso;
import pt.ecl.util.Ligacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    public List<Curso> listar() throws SQLException {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM cursos ORDER BY tipo, nome";

        try (Connection con = Ligacao.obter();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                cursos.add(lerCurso(rs));
            }
        }
        return cursos;
    }

    public Curso procurarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM cursos WHERE id = ?";

        try (Connection con = Ligacao.obter();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return lerCurso(rs);
                }
            }
        }
        return null;
    }

    private Curso lerCurso(ResultSet rs) throws SQLException {
        Curso c = new Curso();
        c.setId(rs.getInt("id"));
        c.setNome(rs.getString("nome"));
        c.setTipo(rs.getString("tipo"));
        c.setNivel(rs.getInt("nivel"));
        c.setDuracao(rs.getString("duracao"));
        c.setDescricao(rs.getString("descricao"));
        return c;
    }
}
