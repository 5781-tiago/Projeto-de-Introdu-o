package pt.ecl.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class Senhas {

    private static final int ITERACOES = 210000;
    private static final int TAMANHO = 256;
    private static final SecureRandom aleatorio = new SecureRandom();

    public static String gerarHash(String password) {
        byte[] sal = new byte[16];
        aleatorio.nextBytes(sal);
        byte[] hash = calcular(password.toCharArray(), sal, ITERACOES);
        return "pbkdf2$" + ITERACOES + "$"
                + Base64.getEncoder().encodeToString(sal) + "$"
                + Base64.getEncoder().encodeToString(hash);
    }

    public static boolean verificar(String password, String guardado) {
        if (password == null || guardado == null) {
            return false;
        }
        String[] partes = guardado.split("\\$");
        if (partes.length != 4 || !partes[0].equals("pbkdf2")) {
            return false;
        }
        int iteracoes = Integer.parseInt(partes[1]);
        byte[] sal = Base64.getDecoder().decode(partes[2]);
        byte[] esperado = Base64.getDecoder().decode(partes[3]);
        byte[] calculado = calcular(password.toCharArray(), sal, iteracoes);
        return MessageDigest.isEqual(esperado, calculado);
    }

    private static byte[] calcular(char[] password, byte[] sal, int iteracoes) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, sal, iteracoes, TAMANHO);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao calcular o hash da password", e);
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java pt.ecl.util.Senhas <password>");
            return;
        }
        System.out.println(gerarHash(args[0]));
    }
}
