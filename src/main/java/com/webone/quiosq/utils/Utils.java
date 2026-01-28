package com.webone.quiosq.utils;

import java.util.Random;

public class Utils {

    public static String gerarCodigoSms() {
        Random random = new Random();
        int codigo = 1000 + random.nextInt(9000); // Garante que o número tenha 4 dígitos
        return String.valueOf(codigo);
    }


}
