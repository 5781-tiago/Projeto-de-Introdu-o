package pt.ecl.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Ligacao {

    private static String url;
    private static String utilizador;
    private static String password;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL não encontrado", e);
        }
        carregarConfiguracao();
    }

    private static void carregarConfiguracao() {
        Properties p = new Properties();

        String ficheiro = System.getenv("ECL_CONFIG");
        if (ficheiro == null && System.getProperty("catalina.base") != null) {
            ficheiro = System.getProperty("catalina.base") + "/conf/ecl-db.properties";
        }

        try {
            if (ficheiro != null && Files.exists(Paths.get(ficheiro))) {
                try (InputStream in = new FileInputStream(ficheiro)) {
                    p.load(in);
                }
            } else {
                try (InputStream in = Ligacao.class.getClassLoader().getResourceAsStream("db.properties")) {
                    if (in != null) {
                        p.load(in);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível ler a configuração da base de dados", e);
        }

        url = valor("ECL_DB_URL", p.getProperty("db.url"));
        utilizador = valor("ECL_DB_USER", p.getProperty("db.user"));
        password = valor("ECL_DB_PASSWORD", p.getProperty("db.password"));

        if (url == null || utilizador == null || password == null) {
            throw new RuntimeException("Configuração da base de dados em falta. Ver README (secção Configuração).");
        }
    }

    private static String valor(String variavel, String alternativa) {
        String v = System.getenv(variavel);
        return v != null && !v.isBlank() ? v : alternativa;
    }

    public static Connection obter() throws SQLException {
        return DriverManager.getConnection(url, utilizador, password);
    }
}
