package pt.ecl.dao;

import pt.ecl.model.Utilizador;
import pt.ecl.util.Ligacao;
import pt.ecl.util.Senhas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilizadorDAO {

    private static final String HASH_FALSO = "pbkdf2$210000$AAAAAAAAAAAAAAAAAAAAAA==$AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=";

    public Utilizador autenticar(String identificador, String password) throws SQLException {
        String sql = "SELECT * FROM utilizadores WHERE identificador = ? AND ativo = TRUE";
        try (Connection con = Ligacao.obter();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, identificador);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    Senhas.verificar(password, HASH_FALSO);
                    return null;
                }
                if (!Senhas.verificar(password, rs.getString("password_hash"))) {
                    return null;
                }
                Utilizador u = new Utilizador();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setIdentificador(rs.getString("identificador"));
                u.setPerfil(rs.getString("perfil"));
                return u;
            }
        }
    }
}
