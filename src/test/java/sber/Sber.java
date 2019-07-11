package sber;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Sber {

    @Test
    public void test1() {
        int a = 123;
        int b = 765;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println(a);
        System.out.println(b);
    }

    @Test
    public void test2() {
        int a = 123;
        int b = 765;
        a = a + b;
        b = a - b;
        a = a - b;
        System.out.println(a);
        System.out.println(b);
    }

    @Test
    public void test3() {
        Node n = new Node();
        passage(n);
    }

    class A {

    }

    class B extends A {

    }

    class C extends B {

    }

    @Test
    public void test4() {
        List<? extends B> lst = new ArrayList<C>();
        //lst.add(new C()); //incompatible types: sber.Sber.C cannot be converted to capture#1 of ? extends sber.Sber.B
        for (B n: lst) {

        }
        System.out.println("!");
        List<C> lst1 = new ArrayList<>();
        lst1.add(new C());
        List<? extends B> lst2 = lst1;
        for (B n: lst2) {
            System.out.println(n.getClass().getSimpleName());
        }
    }

    @Test
    public void test5() {
        List<? super B> lst = new ArrayList<>();
        lst.add(new C());
        //for (B n: lst) { } //incompatible types: capture#1 of ? super sber.Sber.B cannot be converted to sber.Sber.B
        System.out.println("!");
    }

    @Test
    public void test6() {
        List<B> lst = new ArrayList<>();
        lst.add(new C());
        for (B n: lst) { }
        System.out.println("!");
    }

    @Test
    public void test7() {
        List list = new ArrayList();
        list.add("Hello");
        String text = list.get(0) + ", world!";
        //String text = list.get(0); //incompatible types: java.lang.Object cannot be converted to java.lang.String
        System.out.print(text);
    }

    private void passage(Node n) {
        if (n != null) {
            System.out.println(n.id);
            for (Node node : n.nodes) {
                passage(node);
            }
        }
    }

    static class Node {
        int id;
        Node parent;
        Node[] nodes;
    }

}
