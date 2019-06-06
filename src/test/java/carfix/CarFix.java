package carfix;

import org.junit.Test;

/**
 * Что напечатает вызов A a = new B(); ?
 */
public class CarFix {

    static int z = 1;

    static {
        z = 2;
    }

    public class A {
        int x = 1;
        int y;

        {
            y = 1;
        }

        public A() {
            System.out.print(this);
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + getX() + getY();
        }
    }

    public class B extends A {
        int x = 2;
        int y;

        {
            y = 2;
        }

        public B() {
            System.out.print(this);
        }

        public B(int theX) {
            x = theX;
            System.out.print(this);
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }
    }

    @Test
    public void tst1() {
        A a3 = new A();
        System.out.println("");
        A a1 = new B();
        System.out.println("");
        A a2 = new B(3);
        System.out.println("");
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public class MyException extends Exception {
    }

    public class Person {
        public void go() throws Exception {
            throw new Exception();
        }
    }

    public class Me extends Person {
        public void go() throws Exception {
            throw new MyException();
            //throw new Exception();
        }
    }

    @Test
    public void tst2() {
        try {
            Person person = new Me();
            person.go();
        } catch (MyException e) {
            System.out.println("MyException");
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

}
