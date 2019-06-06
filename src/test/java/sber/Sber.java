package sber;

import org.junit.Test;

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
