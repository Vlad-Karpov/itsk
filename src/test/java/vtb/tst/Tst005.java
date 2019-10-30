package vtb.tst;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Какие из нижеперечисленных утверждений верны для параметра classpath
 *          1)   Такого параметра в Java не сужествует
 *      *   2)   Этот параметр для JVM или компилятора, кторый предоставляет пути к зависимостям (дополнительным классам или библиотекам)
 *          3)   Это параметр для JVM или компилятора, который предоставляет пути к системных библиотекам
 *      *   4)   Это необязательный параметр, который используется при запуске или компиляции java-программ
 */
public class Tst005 {
    static class QQQQ0001 {
        static class C1 {
            public C1() { } //Если закоментарить этот конструктор то будет ошибка компиляции, что нужен конструктор по умалчанию
            public C1(byte q) {}
        }
        static class C2 extends C1 {
            public C2(int q) { }
        }
    }

    static class QQQQ0002 {
        abstract interface IClazz{
            int doSmth(String s);
        }

        abstract class X implements IClazz{

        }

        private abstract class Y implements IClazz{ //в top-level классе private недопустимо

        }

        static class Z implements IClazz{
            public int doSmth(String s){    //без public - weaker access
                return 0;
            }
            public static void main(String...args) {
                System.out.println("that`s ok!");
            }
        }

    }

    /**
     * Пояснение: Класс С не будет загружаться, то есть статический блок в классе С не будет выполняться! Почему?
     * Загрузка класса выполнятся в таких условиях:
     * Обращение к СТАТИЧЕСКИМ ПЕРЕМЕННЫМ КЛАССА, статическому методу, создание объекта данного класса и тд..
     * В данном случае мы обращаемся ИМЕННО К СТАТИЧЕСКОЙ ПЕРЕМЕННОЙ КЛАССА B но не к статической переменной класса С - C.x - данная запись конечно критична, но жизнеспособна.
     * Исходя от этого мы делаем вывод:
     * 1) Статическая переменная x принадлежит классу B, но так как B extends A - то самым первым выполнится статический блок в классе A
     * 2) Выполнение статического блока в классе B
     * 3) Ну и вывод - AB5
     */
    static class QQQQ0003 {
        static public class Go {
            public static void main(String[] args) {
                System.out.print(C.x);
            }
        }

        static class A {
            static {
                System.out.print("A");
            }
        }
        static class B extends A{
            public static int x = 5;
            static {
                System.out.print("B");
            }
        }
        static class C extends B{
            static {
                System.out.print("C");
                System.exit(0);
            }
        }
    }

    static class QQQQ0004 {
        static class Main{

            public static void main(String...args){

                A a1 = new A();
                A a2 = new B();
                A a3 = new C();

                //B b1 = a2;       // 1
                B b2 = (B) a2;   // 2
                A a4 = a3;       // 3
                A a5 = (A) a3;  // 4
                //C c1 = a3;       // 5
                C c2 = (C) a3;  // 6
                A a6 = (B) a3; // 7

            }
        }
        static class A{
        }
        static class B extends A{
        }
        static class C extends B{
        }
    }

    static class QQQQ0005 {
        /**
         * Проверка методов, которые можно вызвать, происходит по типу ссылки, а не объекта, на который она ссылается.
         */
        static public class Test {

            static interface I {
                void a(Number n);
            }

            static class A implements I {

                public void a(Number n) {
                    System.out.println("number");
                }

                public void a(Double n) {
                    System.out.println("double");
                }
            }

            public static void main(String[] args) {

                I i = new A();
                Double d = new Double(12d);
                i.a(d);

                A a = new A();
                a.a(d);

                Integer integer = new Integer(12);
                a.a(integer);

                i.a(integer);

            }
        }
    }

    /**
     * Полиморфизм распространяется только на методы
     *
     * я сказал что ошибка компиляции не помню почему
     */
    static class QQQQ0006 {
        static class A {
            String name = "a ";
            String test() {
                return "test A ";
            }
        }

        static class B extends A {
            String name = "b ";
            String test() {
                return "test B ";
            }
        }

        static public class Main {
            public static void main(String[] args) {
                new Main().go();
            }

            void go() {
                A m = new B();
                System.out.println(m.name + m.test());
            }
        }
    }

    static class QQQQ0007 {

        static class Parent { }
        static class DerivedOne extends Parent { }
        static class DerivedTwo extends Parent { }

        public static void main(String[] args) {
            Parent p = new Parent();
            DerivedOne d1 = new DerivedOne();
            DerivedTwo d2 = new DerivedTwo();
            //d1 = (DerivedOne)d2;
        }
    }

    static class QQQQ0008 {
        static class Go extends A {
            public static  void main(String[] args)  {
                new Go().start();
            }

            private void start() {
                check(new A(), new Go());
                check((Go)new A(), new Go());
            }

            private void check(A a, A a1) {
                Go go = (Go) a;  // 1       //Exception in thread "main" java.lang.ClassCastException: vtb.tst.Tst005$QQQQ0008$A cannot be cast to vtb.tst.Tst005$QQQQ0008$Go
                A a2 = (A) a1;   //  2
            }
        }

        static class A{

        }
    }

    static class QQQQ0009 {
        private interface PInterface {
            String method();
        }
        static class A implements PInterface {
            @Override
            public String method() {
                return "A.method()";
            }
        }
        static class B extends A {
            @Override
            public String method() {
                return "B.method()";
            }
        }
        static class C extends B {
            @Override
            public String method() {
                return "C.method()";
            }
        }
        public static void main(String[] args) {
            List<Number> box = new ArrayList<Number>();
            box.add(10);   // OK
            box.add(10.1d);  // OK
            Number i = box.get(1);
            System.out.println(i);
            Integer intg = (Integer) box.get(0);
            System.out.println(intg);

            List<? super Number> box1 = new ArrayList<>();
            box1.add(10);   // OK
            box1.add(10.1d);  // OK
            i = (Double) box1.get(1);
            System.out.println(i);
            intg = (Integer) box1.get(0);
            System.out.println(intg);

            List<PInterface> pInt = new ArrayList<>();
            pInt.add(new A());
            System.out.println(pInt.get(0).method());
            pInt.add(new B());
            System.out.println(pInt.get(1).method());
            method1(pInt);
            //method2(pInt);
            //method3(pInt);
            method4(pInt);
            //method5(pInt);
            method6(pInt);
            method7(pInt);
            method8(pInt);

            List<A> pA = new ArrayList<>();
            pA.add(new A());
            System.out.println(pA.get(0).method());
            pA.add(new B());
            System.out.println(pA.get(1).method());
            //method1(pA);
            method2(pA);
            //method3(pA);
            method4(pA);
            method5(pA);
            method6(pA);
            method7(pA);
            //method8(pA);

            List<B> pB = new ArrayList<>();
            //pB.add(new A());
            //addAinB(pB, new A());
            addAinBasObject(pB, new A());
            System.out.println(((A)pB.get(0)).method());
            pB.add(new B());
            System.out.println(pB.get(1).method());
            //method1(pB);
            //method2(pB);
            method3(pB);
            method4(pB);
            method5(pB);
            method6(pB);
            //method7(pB);
            //method8(pB);

            new Integer("10");

            List<? super B> pSuperB = new ArrayList<>();
            //pSuperB.add(new A()); //незя...!
            addAinBasObject(pSuperB, new A());
            pSuperB.add(new B());
            pSuperB.add(new C());
            for (Object o : pSuperB) {
                PInterface next = (PInterface) o;
                System.out.println(next.method());
            }
            System.out.println(pSuperB.get(0).getClass().getName());
            //method1(pSuperB);
            //method2(pSuperB);
            //method3(pSuperB);
            //method4(pSuperB);
            //method5(pSuperB);
            method6(pSuperB);
            //method7(pSuperB);
            //method8(pSuperB);

            List<? extends B> pExtendsB = new ArrayList<>();
            //pExtendsB.add(new A());  //незя...!
            addAinBasObject(pExtendsB, new A());
            //pExtendsB.add(new B()); //незя...!
            //pExtendsB.add(new C()); //незя...!
            //Вааще ничего добавлять незя...! кроме как через Row
            //и это понятно, мы добавим new B(), а лист будет ArrayList<C>, незя....!
            printAll(pExtendsB);
            System.out.println(((PInterface)pExtendsB.get(0)).getClass().getName());    //без (PInterface) run time exception - ClassCastException
            //method1(pExtendsB);
            //method2(pExtendsB);
            //method3(pExtendsB);
            method4(pExtendsB);
            method5(pExtendsB);
            //method6(pExtendsB);
            //method7(pExtendsB);
            //method8(pExtendsB);

        }
        static <T> void addAinB(List<T> lst, T arg) {
            lst.add(arg);
        }
        static void addAinBasObject(List lst, Object arg) {
            lst.add(arg);
        }
        static void method1(List<PInterface> arg) {
            printAll(arg);
        }
        static void method2(List<A> arg) {
            printAll(arg);
        }
        static void method3(List<B> arg) {
            printAll(arg);
        }
        static void method4(List<? extends PInterface> arg) {
            printAll(arg);
        }
        static void method5(List<? extends A> arg) {
            printAll(arg);
        }
        static void method6(List<? super B> arg) {
            printAll(arg);
        }
        static void method7(List<? super A> arg) {
            printAll(arg);
        }
        static void method8(List<? super PInterface> arg) {
            printAll(arg);
        }
        static void printAll(List pList) {
            for (Object o : pList) {
                PInterface next = (PInterface) o;
                System.out.println(next.method());
            }

        }
    }

    static class QQQQ00010 {
        public static void main(String[] args) {
            int i = 0, j = 5;
            tp: for (;;) {
                i++;
                for (;;) {
                    if (i > --j) {
                        break tp;
                    }
                }
                //System.out.println("i =" + i + ", j = " + j);   //Error:(323, 17) java: unreachable statement
            }
        }
    }
    static class QQQQ00011 {
        public static void main(String[] args) {
            switch(10) {
                case 1:
                    System.out.println("1");
                    break;
                case 2:
                    System.out.println("2");
                    break;
                default:
                    break;
                //System.out.println("default");  //Error:(338, 17) java: unreachable statement
            }
        }
    }

    static class QQQQ00012 {
        public static void main(String[] args) {
            Integer i = new Integer("10");
            if (i.toString() == i.toString()) {
                System.out.println("Равный");
            } else {
                System.out.println("Неравный");
            }
        }
    }

    static class QQQQ00013 {
        public static void main(String[] args) {
            label1: for (int i = 0; i < 3; i++) {
                if (i == 1)
                    continue label1;
                label2:
                System.out.print("TEST ");
                label3:;
            }
        }
    }

    static class QQQQ00014 {
        public static void main(String[] argv){
            Integer i1 = new Integer("1");
            Integer i2 = new Integer("2");
            Integer i3 = Integer.valueOf("3");
            int i4 = i1 + i2 + i3;
            System.out.println(i4);
        }
    }

    static class QQQQ00015 {
        static class Tack {
            static short s = 17;
            public Tack(short ss) {
                new Tack();
                s *= ss;
            }
            public Tack() { ; }
        }
        public static class Bridle extends Tack {
            public Bridle(int s) { System.out.println(s + 1); }
            public static void main(String[] args) {
                Bridle b = new Bridle(3);
            }
        }
    }

}
