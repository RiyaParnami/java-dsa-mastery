package com.riya;

public class Comparison {
    public static void main(String[] args) {
        String a = "Riya";
        String b = "Riya"; // this is in string pool coz value is same
        String c = a;
        System.out.println(c == a);

        // can check by ==
        System.out.println(a == b);

        String name1 = new String("Riya");
        String name2 = new String("Riya");

        System.out.println(name1 == name2); // == is comparator and .equals is method

        System.out.println(name1.equals(name2)); // to check only values
    }
}
