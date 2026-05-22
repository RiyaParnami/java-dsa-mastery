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
        int d = (x*y) / z; // (x*y) this is performed using integer as it is a expression , and it is byte evaluation.so it is a integer now.
        System.out.println(d);

        int number = 'A'; // automatic type conversion give ascii value of this
        System.out.println("今日は"); // java follows unicode principle so we can put any language

        System.out.println(3 * 4.56874685f); // float > int..prints float

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
