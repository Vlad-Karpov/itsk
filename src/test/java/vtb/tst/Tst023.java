package vtb.tst;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Какова эффективность поиска объекта в HashMap по ключю, при равномерном распределении хэш функции
 * <ul>
 * <li>          O(n^2)</li>
 * <li>          O(n)</li>
 * <li>          O(log(n))</li>
 * <li>     *    O(1)</li>
 * </ul>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 *
 *  Какие вызовы метода будут скомпилированны без ошибок
 *
 * <code>
 *     private void copy(List<? extends Animal> source, List<? super Animal> target);
 *     public class Animal {};
 *     public class Dog extends Animal {};
 *     public class Cat extends Animal {};
 * </code>
 *
 * <ul>
 * <li>        copy(new ArrayList<Dog>(), new ArrayList<Dog>());  </li>
 * <li>        copy(new ArrayList<Dog>(), new ArrayList<Cat>());  </li>
 * <li>    *   copy(new ArrayList<Dog>(), new ArrayList<Animal>());  </li>
 * <li>        copy(new ArrayList<Animal>(), new ArrayList<Dog>());  </li>
 * <li>    *   copy(new ArrayList<Animal>(), new ArrayList<Animal>());  </li>
 * </ul>
 */
public class Tst023 {

    public static void main(String args[]) {
        int i;
        float f = 2.3f;
        double d = 2.7;
        i = ((int) Math.ceil(f)) * ((int) Math.round(d));

        System.out.println(i);
    }

    public void qq() {
        int i;
        float f = 2.3f;
        double d = 2.7;
        i = ((int) Math.ceil(f)) * ((int) Math.round(d));
        System.out.println(i);
    }


    static public class example {
        int i[] = {0};

        public static void main(String args[]) {
            int i[] = {1};
            change_i(i);
            System.out.println(i[0]);
        }

        public static void change_i(int i[]) {
            int j[] = {2};
            i = j;
        }
    }

    static class test {
        public static void main(String[] args) {
            Date d = new Date(2011343412345L);
            //DateFormat df = new DateFormat();
            //System.out.println(df.format(d));
        }
    }

    static public class X {
        public static void main(String[] args) {
            try {
                Tst023.main(null);
                badMethod();
                System.out.print("A");
            } catch (Exception ex) {
                System.out.print("B");
            } finally {
                System.out.print("C");
            }
            System.out.print("D");
        }

        public static void badMethod() {
            throw new Error(); /* Line 22 */
        }
    }

    /**
     * Throwable -> Exception ->  RuntimeException
     * |                |
     * ->Error          |
     * -> checked exceptions
     */
    static public class X1 {
        public static void main(String[] args) {
            try {
                badMethod();
                System.out.print("A");
            } catch (RuntimeException ex) /* Line 10 */ {
                System.out.print("B");
            } catch (Exception ex1) {
                System.out.print("C");
            } finally {
                System.out.print("D");
            }
            System.out.print("E");
        }

        public static void badMethod() {
            throw new RuntimeException();
        }
    }

    /**
     * CException in thread "main" java.lang.Error
     */
    static public class X2 {
        public static void main(String[] args) {
            try {
                badMethod();
                System.out.print("A");
            } catch (Exception ex) {
                System.out.print("B");
            } finally {
                System.out.print("C");
            }
            System.out.print("D");
        }

        public static void badMethod() {
            throw new Error(); /* Line 22 */
        }
    }

    static class Exc0 extends Exception {
    }

    static class Exc1 extends Exc0 {
    } /* Line 2 */

    static public class TestExeption {
        public static void main(String args[]) {
            try {
                throw new Exc1(); /* Line 9 */
            } catch (Exc0 e0) /* Line 11 */ {
                System.out.println("Ex0 caught");
            } catch (Exception e) {
                System.out.println("exception caught");
            }
        }
    }


    @Test
    public void tst01() {
        OuterClass outerClass = new OuterClass();
        System.out.println(outerClass.qq());
        OuterClass.StaticInerClass staticInerClass = new OuterClass.StaticInerClass();
        System.out.println(staticInerClass.qqq());
        System.out.println(staticInerClass.qqqq(outerClass));
    }

    @Test
    public void tst02() {
        int index = 1;
        Boolean[] test = new Boolean[3];
        Boolean data = test[index];
        System.out.println(data);
    }

    class DataBoolenTest {
        int index = 1;
        Boolean[] test = new Boolean[3];
        Boolean data = test[index];
    }

    @Test
    public void tst03() {
        DataBoolenTest tst = new DataBoolenTest();
        System.out.println(tst.data);
    }

    class Animal {}
    public class Dog extends Animal {}
    public class Cat extends Animal {}
    private void copy(List<? extends Animal> source, List<? super Animal> target) {
    }

    @Test
    public void tst04() {
        //copy(new ArrayList<Dog>(), new ArrayList<Dog>());         //compile time error
        //copy(new ArrayList<Dog>(), new ArrayList<Cat>());         //compile time error
        copy(new ArrayList<Dog>(), new ArrayList<Animal>());
        //copy(new ArrayList<Animal>(), new ArrayList<Dog>());      //compile time error
        copy(new ArrayList<Animal>(), new ArrayList<Animal>());
    }

}
