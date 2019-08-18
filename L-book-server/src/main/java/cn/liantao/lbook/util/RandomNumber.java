package cn.liantao.lbook.util;

import java.util.Random;

public class RandomNumber {
    public static String generate(int digit) {
        Random random = new Random();
        String result="";
        for (int i=0;i<digit;i++)
        {
            result+=random.nextInt(10);
        }

        return result;
    }
}
