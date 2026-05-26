package com.riya;

public class Swap {
    public static void main(String[] args) {
        int a = 10;
        int b = 20;

        // swap numbers code
//        int temp = a;
//        a = b;
//        b = temp;


        swap(a,b);

        System.out.println(a + " " + b);

        String name = "Riya Parnami";
        changeName(name);
        System.out.println(name);

    }

    static void changeName(String name) {
        name = "Akriti negi"; // // creating a new object , it won't change the name
    }

    static void swap(int a, int b){
        int temp = a;
        a = b;
        b = temp;
        // this change will only be valid in this function scope only.
    }
}
