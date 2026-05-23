package com.riya;

import java.util.Scanner;

public class Loops {
    public static void main(String[] args) {

        //Q. Print numbers from 1 to 5

        System.out.println(1);
        System.out.println(2);
        System.out.println(3);
        System.out.println(4);
        System.out.println(5);

        // by for loop
        for (int i = 1; i<=5; i+=1){
            System.out.println(i);
        }

        //print numbers from 1 to n
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        for (int num = 1; num < n; num++) { // when you know how many times the loop is going to run
//            System.out.print(num + " "); // + " " for space
            System.out.println("Hello World");
        }


        // While loops // when you don't know how many times the loop is going to run
        int num = 1; // initialisation part
        while(num<n){ //condition part
            System.out.print(num + " ");
            num+=1; // increment/decrement part
        }
        System.out.println();

        //do while
        int m = 1;
        do {
            System.out.println("Hello Riya");
//            m++;
        }while(m !=1);
    }
}
