package vtb.tst;

/**
 * Какие из утверждений верны в контексте работы методов equals() и hashCode()
 * <p>
 *  *    вызов метода hashCode на immutable объекте должен возвращать одно и тоже значение в любой момент жиэни объекта
 *       Если два объекта НЕ равны согласно метода equals(), их методы hashCode() должны возвращать одинаковое значение
 *  *    Если два объекта равны согласно метода equals(), их методы hashCode() должны возвращать одинаковые значения
 *       Метод hashCode() используется в методе equals() для сравнения объектов
 */
public class Tst015 {

    static class QQQ0001 {

        interface DoMath {
            double getArea(int rad);
        }

        interface MathPlus {
            double getVol(int b, int h);
        }

//        Error:(23, 31) java: no interface expected here
//        class AllMath extends DoMath {
//            double getArea(int r);
//        }

//        Error:(28, 26) java: '{' expected
//        interface AllMath implements MathPlus {
//            double getVol(int x, int y);
//        }

        interface AllMath1 extends DoMath {
            float getAvg(int h, int l);
        }

//        Error:(37, 9) java: vtb.tst.Tst015.QQQ0001.AllMath is not abstract and does not override abstract method getVol(int,int) in vtb.tst.Tst015.QQQ0001.MathPlus
//        class AllMath implements MathPlus {
//            double getArea(int rad);
//        }

        abstract class AllMath implements DoMath, MathPlus {
            public double getArea(int rad) {
                return rad * rad * 3.14;
            }
        }

        public static void main(String[] args) {
            System.out.println("!");
        }

    }

    static class QQQ0002 {

        public static void main(String[] args) {
            int w = (int) 888.8;
            byte x = (byte) 1000L;
            long y = (byte) 100;
            byte z = (byte) 100L;
            System.out.println(w + ", " + x + ", " + y + ", " + z);
        }

    }

    static class QQQ0003 {

        public static void main(String[] args) {
            int I = 1;
            do while (I < 1)
                System.out.print("I is " + I);
            while (I > 1);
        }

    }

    static public class ExamQuestion7
    {
        static int j;
        static void methodA(int i)
        {
            boolean b;
            do
            {
                b = i<10 | methodB(4); /* Line 9 */
                b = i<10 || methodB(8);  /* Line 10 */
            }while (!b);
        }
        static boolean methodB(int i)
        {
            j += i;
            return true;
        }
        public static void main(String[] args)
        {
            methodA(0);
            System.out.println( "j = " + j );
        }
    }

}
