package com.riya;

import java.util.Scanner;

public class TypeCasting {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        float num1 = input.nextFloat();
        System.out.println(num1);

        //Type casting
        int num = (int)(67.56f);
        System.out.println(num);

        // automatic type promotion in expressions
        int a= 257;
        byte b = (byte)(a);
        System.out.println(b); // 257 % 256 = 1

        byte x = 40 ;
        byte y = 50 ;
        byte z = 100;
        int d = (x*y) / z;
        System.out.println(d);

        int number = 'A';
        System.out.println("今日は");

        System.out.println(3 * 4.56874685f);

        byte l = 42;
        char c = 'a';
        short s = 1024;
        int i = 50000;
        float f = 5.67f;
        double db = 0.1234;
        double result = (f*l) + (i/c) - (db * s);
        // float + int - double = double
        System.out.println((f*l) + " " + (i/c) + " "  + (db * s));
        System.out.println(result);

    }
}
