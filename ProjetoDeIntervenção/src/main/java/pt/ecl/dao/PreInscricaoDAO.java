package pt.ecl.dao;

import pt.ecl.model.PreInscricao;
import pt.ecl.util.Ligacao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PreInscricaoDAO {

    public void inserir(PreInscricao p) throws SQLException {
        String sql = "INSERT INTO pre_inscricoes (nome, email, telefone, data_nascimento, curso_id, observacoes) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = Ligacao.obter();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getTelefone());
            ps.setDate(4, Date.valueOf(p.getDataNascimento()));
            ps.setInt(5, p.getCursoId());
            ps.setString(6, p.getObservacoes());
            ps.executeUpdate();
        }
    }

    public List<PreInscricao> listar() throws SQLException {
        List<PreInscricao> lista = new ArrayList<>();
        String sql = "SELECT p.*, c.nome AS nome_curso FROM pre_inscricoes p "
                + "JOIN cursos c ON c.id = p.curso_id ORDER BY p.data_registo DESC";

        try (Connection con = Ligacao.obter();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PreInscricao p = new PreInscricao();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setEmail(rs.getString("email"));
                p.setTelefone(rs.getString("telefone"));
                p.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                p.setCursoId(rs.getInt("curso_id"));
                p.setNomeCurso(rs.getString("nome_curso"));
                p.setObservacoes(rs.getString("observacoes"));
                p.setDataRegisto(rs.getTimestamp("data_registo").toLocalDateTime());
                lista.add(p);
            }
        }
        return lista;
    }
}
