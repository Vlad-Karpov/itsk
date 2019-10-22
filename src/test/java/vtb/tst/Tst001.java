package vtb.tst;

import java.util.Set;
import java.util.TreeSet;

public class Tst001 {

    public static class QQQ0001 {

        static public class QTest {
            {
                System.out.print("1");
            }

            public static void main(String[] args) {
                System.out.print("2");
                new QTest();
            }

            static {
                System.out.print("3");
            }
        }

    }

    public static class QQQ0002 {
        public static  class Main {

            private static class A1 {
                private String test() {
                    return "A1";
                }
            }

            public static class A2 extends A1 {
                public String test() {
                    return "A2";
                }
            }

            public static class A3 extends A2 {
                public String test() {
                    return "A3";
                }
            }

            public static void main(String ... arg) {
                A1 a1 = new A1();
                System.out.println(a1.test());
                a1 = new A2();
                System.out.println(a1.test());
                a1 = new A3();
                System.out.println(a1.test());
            }

        }
    }

    public static class QQQ0003 {
        public static class Main {
            public static void main(String [] str) {
                Boolean b1 = new Boolean("true");
                System.out.println(b1);
                b1 = new Boolean("tRuE");
                System.out.println(b1);
                b1 = new Boolean("test");
                System.out.println(b1);
                b1 = new Boolean(true);
                System.out.println(b1);
                b1 = true;
                System.out.println(b1);
            }
        }
    }

    public static class QQQ0005 {

    }

    public static class QQQ0004 {
        static class TestClass {
            int i = getInt();
            int k = 20;
            public int getInt() {  return k+1;  }
            public static void main(String[] args) {
                TestClass t = new TestClass();
                System.out.println(t.i + "  " + t.k);
            }
        }
    }

    public static class QQQ0006 {
        static class TreeTest {
            private static Set<String> set = new TreeSet<String>();
            public static void main(String[] args) {
                set.add("one");
                set.add("two");
                set.add("three");
                set.add("/u000a");
                set.add("/u000d");
                set.add("/u000c");
                set.add("1");
                set.add("2");
                set.add("3");
                for (String string : set) {
                    System.out.print(string + " ");
                }
            }
        }
    }


}
