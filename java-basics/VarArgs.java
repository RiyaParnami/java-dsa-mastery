package com.riya;

import java.util.Arrays;

public class VarArgs {
    public static void main(String[] args) {
        fun(2, 3, 4, 5, 56, 87 , 34, 23 ,96);
        multiple(2,3,"Riya" , "Akriti" , "Tara" , "Kriti" );
        demo(5,34,34,223,234,654,43,65);
        demo("Priti","Kinjal", "Prerna","Amrisha");
//        demo(); // it cannot be empty , Ambiguous method call. Both demo
    }

    static void demo(int ...v) {
        System.out.println(Arrays.toString(v));
    }

    // overloading
    static void demo(String ...v) {
        System.out.println(Arrays.toString(v));
    }

    static void multiple(int a, int b, String ...v){ // variable argument parameters should be in the last of this list
        System.out.print(a + " ");
        System.out.print(b + " ");
        System.out.println(Arrays.toString(v));

    }

    static void fun(int ...v){
        System.out.println(Arrays.toString(v));
    }
}
