package pt.ecl.util;

import java.security.SecureRandom;

public class Codigos {

    private static final String LETRAS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final SecureRandom aleatorio = new SecureRandom();

    public static String novo(String prefixo) {
        StringBuilder sb = new StringBuilder(prefixo).append('-');
        for (int i = 0; i < 6; i++) {
            sb.append(LETRAS.charAt(aleatorio.nextInt(LETRAS.length())));
        }
        return sb.toString();
    }
}
