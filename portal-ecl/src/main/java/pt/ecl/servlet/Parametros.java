package pt.ecl.servlet;

import jakarta.servlet.http.HttpServletRequest;

public class Parametros {

    public static String texto(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);
        return valor == null ? "" : valor.trim();
    }

    public static int inteiro(HttpServletRequest request, String nome) {
        try {
            return Integer.parseInt(texto(request, nome));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
