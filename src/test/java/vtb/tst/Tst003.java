package vtb.tst;

/**
 * Можно ли вызвать garbage collector програмно, чтобы произошла сборка мусора?
 *       1)  Нет такого API, только через JMX
 *       2)  Можно, со 100% вероятностью
 *   *   3)  Со 100% вероятностью garbage collector вызвать нельзя
 */
public class Tst003 {

    public static class QQQQ001 {
        interface DeclareStuff {
            public static final int EASY = 3;
            void doStuff(int t);
        }

        static class TestDeclare implements DeclareStuff {
            public static void main(String[] args) {
                int x = 5;
                new TestDeclare().doStuff(++x);
            }

            //void doStuff(int s) {  //ошибка компиляции - понижение уровня видимости
            public void doStuff(int s) {
                s += EASY + ++s;
                System.out.println("s " + s);
            }
        }
    }

    /**
     * Бля! Область видимости методов понижать нельзя!
     */
    public static class QQQQ002 {

        static class TestDeclare1 {
            public void tst01() {};
        }
        static class TestDeclare2 extends TestDeclare1 {
            //protected void tst01() {};
        }

    }

    public static class QQQQ003 {
        public static void main(String[] args) {
            int i = 0;
            int j = 0;
            int ipp = i++;
            int ppj = ++j;
            System.out.println("i = " + i);
            System.out.println("j = " + j);
            System.out.println("ipp = " + ipp);
            System.out.println("ppj = " + ppj);
        }
    }

    public static class QQQQ004 {
        public static void main(String[] args) {
            Integer i = new Integer(42);
            Long l = new Long(42);
            Double d = new Double(42.0);
            //System.out.println(i == l);
            //System.out.println(i == d);
            //System.out.println(d == l);
            System.out.println(i.equals(d));
            System.out.println(d.equals(i));
            System.out.println(i.equals(42));

            //
            System.out.println((Number)i == (Number)l);
            System.out.println(((Number)i).equals((Number)l));
            //
        }
    }

    public static class QQQQ005 {
        public static void main(String[] args) {
            Integer i = 42;
            Long l = 42L;
            Double d = 42.0d;
            Number k = 42;
            //System.out.println(i == l);
            //System.out.println(i == d);
            //System.out.println(d == l);
            System.out.println(i.equals(d));
            System.out.println(d.equals(i));
            System.out.println(i.equals(42));

            //
            System.out.println((Number)i == (Number)l);
            System.out.println(((Number)i).equals((Number)l));
            System.out.println(i.equals(k));
            //
        }
    }


}
