package com.riya;

import java.util.ArrayList;
import java.util.Scanner;

public class ArrayListExample {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Syntax
        ArrayList<Integer> list = new ArrayList<>(5); // here we will write wrapper classes as datatype , cannot use primitives

//        list.add(67);
//        list.add(234);
//        list.add(654);
//        list.add(43);
//        list.add(654);
//        list.add(8765);
//
//        System.out.println(list.contains(654));
//
//        System.out.println(list);
//        list.set(0, 99);
//
//        list.remove(2);
//        System.out.println(list);

        // input
        for (int i = 0; i < 5; i++) {
            list.add(in.nextInt());
        }

        // get item at any index
        for (int i = 0; i < 5; i++) {
            System.out.print(list.get(i) + " "); // pass index here, list[index] syntax will not work here
        }

        System.out.println();
        System.out.println(list);
    }
}
