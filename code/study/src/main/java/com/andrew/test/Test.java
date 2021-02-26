package com.andrew.test;

import java.util.Objects;

public class Test {

    public static void main(String[] args) {
        int sshift = 0;
        int ssize = 1;
        while (ssize < 16) {
            ++sshift;
            ssize <<= 1;
        }
        System.out.println(sshift +" " + ssize);
    }

}
