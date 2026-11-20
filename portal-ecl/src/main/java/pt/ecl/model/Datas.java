package pt.ecl.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Datas {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static String formatar(LocalDateTime data) {
        return data == null ? "" : data.format(FORMATO);
    }
}
