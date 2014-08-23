package br.ufg.inf.sigera.modelo.servico;

import java.util.Random;

public class SenhaAleatoria {

    public static String Gerar() {
        Integer comprimento = 7;
        Random ran = new Random();

        String[] letras = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
            "X", "Y", "Z", "6", "7", "8", "9",};

        String senhaGerada = "";

        for (int i = 0; i < comprimento; i++) {
            int a = ran.nextInt(letras.length);
            senhaGerada += letras[a];
        }

        return senhaGerada;
    }
    public static String GerarCodigoVerificacao() {
        Integer comprimento = 30;
        Random ran = new Random();

        String[] letras = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
            "X", "Y", "Z", "6", "7", "8", "9"};

        String codigoVerificacao = "";

        for (int i = 0; i < comprimento; i++) {
            int a = ran.nextInt(letras.length);
            codigoVerificacao += letras[a];
        }

        return codigoVerificacao;
    }
}
