package pt.ecl.dao;

import pt.ecl.model.Curso;
import pt.ecl.util.Ligacao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    public List<Curso> listar() throws SQLException {
        return pesquisar("", "", "");
    }

    public List<Curso> pesquisar(String texto, String modalidade, String area) throws SQLException {
        List<Curso> cursos = new ArrayList<>();
        List<String> parametros = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM cursos WHERE ativo = TRUE");

        if (!texto.isEmpty()) {
            sql.append(" AND (nome LIKE ? OR area LIKE ? OR modalidade LIKE ?)");
            String padrao = "%" + texto + "%";
            parametros.add(padrao);
            parametros.add(padrao);
            parametros.add(padrao);
        }
        if (!modalidade.isEmpty()) {
            sql.append(" AND modalidade = ?");
            parametros.add(modalidade);
        }
        if (!area.isEmpty()) {
            sql.append(" AND area = ?");
            parametros.add(area);
        }
        sql.append(" ORDER BY area, nome");

        try (Connection con = Ligacao.obter();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametros.size(); i++) {
                ps.setString(i + 1, parametros.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cursos.add(ler(rs));
                }
            }
        }
        return cursos;
    }

    public Curso procurarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM cursos WHERE id_curso = ? AND ativo = TRUE";
        try (Connection con = Ligacao.obter();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? ler(rs) : null;
            }
        }
    }

    public List<String> listarModalidades() throws SQLException {
        return listarValores("SELECT DISTINCT modalidade FROM cursos WHERE ativo = TRUE ORDER BY modalidade");
    }

    public List<String> listarAreas() throws SQLException {
        return listarValores("SELECT DISTINCT area FROM cursos WHERE ativo = TRUE ORDER BY area");
    }

    private List<String> listarValores(String sql) throws SQLException {
        List<String> valores = new ArrayList<>();
        try (Connection con = Ligacao.obter();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                valores.add(rs.getString(1));
            }
        }
        return valores;
    }

    private Curso ler(ResultSet rs) throws SQLException {
        Curso c = new Curso();
        c.setId(rs.getInt("id_curso"));
        c.setNome(rs.getString("nome"));
        c.setModalidade(rs.getString("modalidade"));
        c.setArea(rs.getString("area"));
        c.setNivel(rs.getString("nivel"));
        int horas = rs.getInt("horas_total");
        c.setHorasTotal(rs.wasNull() ? null : horas);
        c.setComponentes(rs.getString("componentes"));
        c.setPerfil(rs.getString("perfil"));
        c.setSaidasProfissionais(rs.getString("saidas_profissionais"));
        c.setFonte(rs.getString("fonte"));
        Date data = rs.getDate("data_verificacao");
        c.setDataVerificacao(data == null ? null : data.toLocalDate());
        return c;
    }
}
