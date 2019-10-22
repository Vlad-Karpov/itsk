package vtb.tst;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Необходим быстрый поиск объекта в коллекции. Какая из реализаций наиболее подходящая для следующего кода:
 * <p>
 * {@code
 * Collection<String> names = ???;
 * names.contains("Ivan");
 * }
 *
 * <ul>
 * <li>         names = new ArrayList<>()</li>
 * <li>         names = new LinkedList<>()</li>
 * <li>    *    names = new HashSet<>()</li>
 * <li>         names = new TreeSet<>()</li>
 * </ul>
 */
public class Tst022 {

    static public class Q0001 {
        static public class Parent<T extends Number> { // 1
            public Parent(T object) {
                System.out.print(object);
            }
        }

        static class Child extends Parent<Number> {    // 2
            public Child(Number object) {
                super(object);                  // 3
                System.out.print(object);
            }

            public static void main(String[] args) {
                new Child(0);                   // 4
            }
        }
    }

    static public class Q0002 {

        static public class Integers {
            static void append(List list) {
                list.add("0042");
            }

            public static void main(String[] args) {
                List<Integer> intList = new ArrayList<Integer>();
                append(intList);
                System.out.println(intList.get(0));
            }
        }
    }

    static public class Q0003 {

        static public class Test<T> {
            public static void main(String[] args) {
                Test<Exception> e = new Test<Exception>();
                Test<RuntimeException> re = new Test<RuntimeException>();
                //e = re;
                System.out.println(e);
            }
        }
    }

    static public class Q0004 {
        static class Starter extends Thread {
            private int x = 2;

            public static void main(String[] args) throws Exception {
                new Starter().makeItSo();
            }

            public Starter() {
                x = 5;
                start();
            }
            public void makeItSo() throws Exception {
                join();
                x = x - 1;
                System.out.println(x);
            }
            public void run() {
                x *= 2;
            }
        }
    }

    static public class Q0005 {
        static abstract class A {
            public void qq() {

            }
        }
        static class B  extends A {
        }
    }

    /**
     * Вызывается наиболее узкий метод!!!
     *
     * еще пример
     * m(Exception e)
     * m(IOException e)
     * m(FileNotFoundException e)
     *
     * что буюет вызвано m(null)
     *
     * вызовиться  m(FileNotFoundException e)
     *
     */
    static public class Q0006 {
        static class A { }
        static class B extends A { }
        static class C extends B { }

        static public class Test {
            public static void m(A a) {
                System.out.println("a");
            }
            public static void m(B b) {
                System.out.println("b");
            }

            static void m1(Exception e){ System.out.println("Exception"); };
            //void m1(RuntimeException e){ System.out.println("RuntimeException"); };
            static void m1(IOException e){ System.out.println("IOException"); };
            static void m1(FileNotFoundException e){ System.out.println("FileNotFoundException"); };


            public static void main(String[] args) {
                m(new C());
                m1(null);
            }
        }
    }

    static public class Q0007 {
        static public class Test {

            static class A extends B {
                static Integer q = 2;
                static {
                    System.out.print("A");
                    A.q = 4;
                }
            }

            static class B {
                static {
                    System.out.print("B");
                    A.q++;
                }
            }

            public static void main(String[] args) {
                System.out.println(A.q);
            }
        }
    }

    static public class Q0008 {
        static public class Test1 {
            int x = 0;
            void move(int dx) { x += dx; }

            int getX() {
                return x;
            }

            public static void main(String[] args) throws Exception {
                Child test1 = new Child();
                test1.move(1);
                System.out.println(test1.getX());
            }
        }

        /**
         * Условия overload - возвращаемые значения должны быть ковариантны (с java 1.5, а до нее вулючительно инвариантны (одинаковые))
         */
        static class Child extends Test1 {
            float x = 0.0f;
            void move(int dx) {
                move((float) dx);
            }
            void move(float dx) {
                x += dx;
            }
//            float getX() {        //compile error
//                return x;
//            }
        }
    }

    static public class Q0009 {
        private interface Print { // (0)
            public void print();
        }

        static class A implements Print {
            public void print() {
                System.out.println("Hello world !");
            }
        }

        static class B extends A implements Print {
            // (1)
        }

        static public class MyClass{
            public static void main(String args[]) {
                B classB  = new B();
                classB.print();
            }
        }
    }


}
