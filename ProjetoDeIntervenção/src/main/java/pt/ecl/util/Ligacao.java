package pt.ecl.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Ligacao {

    private static final Properties config = new Properties();

    static {
        try (InputStream in = Ligacao.class.getClassLoader().getResourceAsStream("db.properties")) {
            config.load(in);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Não foi possível configurar a ligação à base de dados", e);
        }
    }

    public static Connection obter() throws SQLException {
        return DriverManager.getConnection(
                config.getProperty("db.url"),
                config.getProperty("db.user"),
                config.getProperty("db.password"));
    }
}
