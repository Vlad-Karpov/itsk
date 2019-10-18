package vtb.tst;

import org.junit.Test;

/**
 * Какие из условий должны выполняться для правильной работы метода <pre>{@link  java.util.Collections#sort(java.util.List) Collections.sort(...)}</pre>
 * <ul>
 *  <li>Входящий список должен содержать объекты в который переписаны методы <pre>{@link Object#equals(Object) equals(Object)} и  {@link Object#hashCode() hashCode()}</pre></li>
 *  <li>* Входящий список должен содержать объекты, которые имплементируют интерфейс <pre>{@link Comparable Comparable}</pre></li>
 *  <li>* Входящий список может содержать любые объекты, при условии, что вторым объектом передается объект типа <pre>{@link java.util.Comparator Comparator}</pre></li>
 *  <li>Входящий список может содержать любые объекты, которые имплементируют интерфейс <pre>{@link java.io.Serializable Serializable}</pre></li>
 * </ul>
 */
public class Tst026 {

    @Test
    public void tst01() {
        int x = 11 & 9;
        int y = x ^ 3;
        System.out.println(y | 12);
    }

    @Test
    public void tst02() {
        boolean b1 = true;
        boolean b2 = false;
        boolean b3 = true;
        if (b1 & b2 | b2 & b3 | b2) /* Line 8 */
            System.out.print("ok ");
        if (b1 & b2 | b2 & b3 | b2 | b1) /*Line 10*/
            System.out.println("dokey");
    }

    @Test
    public void tst03() {
        try {
            Float f1 = new Float("3.0");
            int x = f1.intValue();
            byte b = f1.byteValue();
            double d = f1.doubleValue();
            System.out.println(x + b + d);
        } catch (NumberFormatException e) /* Line 9 */ {
            System.out.println("bad number"); /* Line 11 */
        }
    }


//    static public class ExamQuestion6
//    {
//        static int x;
//        boolean catch()
//        {
//            x++;
//            return true;
//        }
//        public static void main(String[] args)
//        {
//            x=0;
//            if ((catch() | catch()) || catch())
//            x++;
//            System.out.println(x);
//        }
//    }

    static class Parent{
        static int x = 1;
        public Parent(){
            x += 2;
        }
    }
    static class Child extends Parent{
        public Child(){
            x += 1;
        }

        public static void main(String[] args) {
            Child c = new Child();
            System.out.println(x);
        }
    }

//    class A{
//        public Single method(){
//            return new Single();
//        }
//    }

//    class Single extends A{
//        public A method() {
//            return new Single();
//        }
//    }

    static public class TestQQQ {

        static class A extends B {
            //static Integer q = 2;     //npe
            static int q = 2;
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



//    class A {
//        public A(String s) {
//            System.out.print("A");
//        }
//    }
//    class B extends A {
//        public B() {
//            System.out.print("Empty");
//        }
//        public B(String s) {
//            System.out.print(s);
//        }
//
//        public static void main(String[] args) {
//            new B("AB");
//        }
//    }


    static class QQQQQ1 {

        interface I1 {
            final int p = 10;

            public int get();

        }

        static class B implements I1 {
            private int p = 20;

            public int get() {
                return p;
            }
        }

        static class C extends B implements I1 {
            public int get() {
                return p;
            }
        }

        static public class A {

            public static void main(String... args) {
                B obj = new C();
                System.out.print(obj.get());

            }
        }

    }

    static class QQQQQ2 {
        static class A { }
        static class B extends A { }
        static abstract class C {
            abstract void doAction(A a);
            void start(A a){
                doAction(a);
            }
        }
        static class D extends C {
            void doAction(A a) {
                System.out.println("A action");
            }
            void doAction(B b) {
                System.out.println("B action");
            }
        }
        static public class Test {
            public static void main(String[] args) {
                new D().start(new B());
            }
        }
    }

    //Error:(209, 23) java: cannot reference Str before supertype constructor has been called
//    static class QQQQQ3 {
//        static class Base {
//            static {
//                System.out.println("Static");
//            }
//            public Base(String s) {
//                System.out.println("Base " + s);
//            }
//        }
//
//        static class Sub extends Base {
//            private final String Str = "Constructor";
//            public Sub() {
//                super(Str);
//                System.out.println("Sub " + Str);
//            }
//
//            public static void main(String...args) {
//                Base B = new Sub();
//            }
//        }
//
//    }


}
