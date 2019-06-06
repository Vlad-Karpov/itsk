package com.instanceofjava;

public class BBB {

    BBB b = new BBB();

    public int show(){
        return (true ? null : 0);
    }

    public static void main(String[] args)  {

        BBB b = new BBB();
        b.show();
    }
}