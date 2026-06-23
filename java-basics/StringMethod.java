package com.riya;

import java.util.Arrays;

public class StringMethod {
    public static void main(String[] args) {
        String name = "Riya Parnami Hello World";
        System.out.println(Arrays.toString(name.toCharArray())); // creates character array
        System.out.println(name.toLowerCase());
        System.out.println(name);
        System.out.println(name.indexOf('a'));
        System.out.println("   Riya   ".strip()); // extra spaces will be removed
        System.out.println(Arrays.toString(name.split(" "))); // array will have two elements
    }
}
