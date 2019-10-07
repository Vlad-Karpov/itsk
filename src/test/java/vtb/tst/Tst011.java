package vtb.tst;

import org.junit.Test;

/**
 * Какие из утверждений верны для следующего кода и будут скомпилированы без ошибок
 */
public class Tst011 {
    interface I {
        void test();
    }
    static class A implements I {
        public void test() {
            System.out.println("A#test");
        }
    }
    static class B {
        public void test() {
            System.out.println("B#test");
        }
    }
    static class C extends A {
        public void test() {
            System.out.println("C#test");
        }
    }
    @Test
    public void tst01() {
        A a = new A();
        //A aB = new B(); //compile time error
        I iA = new A();
        //I iB = new B(); //compile time error
        I iC = new C();
    }
}
