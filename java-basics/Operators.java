package com.riya;

import java.util.ArrayList;

public class Operators {
    public static void main(String[] args) {
        System.out.println('a' + 'b'); // character prints 195
        System.out.println("a" + "b"); // string prints ab, operator minus cannot be applied on the string
        System.out.println((char)('a' + 3)); // type casting prints d

        System.out.println("a" + 1);
        // this is same as after a few steps: "a" + "1"
        // integer will be converted to Integer that will call toString()
        // we cannot modify or overload '+' operator in java

        System.out.println("Riya" + new ArrayList<>());
        System.out.println("Riya" + new Integer(56));
        String ans = new Integer(56) + "" + new ArrayList<>(); // at least one of these expressions should be string then only it will add,
        // it will not work on any random objects
        System.out.println(ans);

        System.out.println("a" + 'b');
    }
}
