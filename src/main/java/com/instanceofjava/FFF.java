package com.instanceofjava;

public class FFF {
    static class A1
    {
        void method(int i)
        {

        }
    }

    static class B1 extends A1
    {
        //@Override
        void method(Integer i)
        {

        }
    }
    public static void main(String[] args)  {
        B1 b = new B1();
        b.method(10);
    }

}

