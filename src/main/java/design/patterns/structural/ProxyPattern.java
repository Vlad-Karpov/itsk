package design.patterns.structural;

/**
 * Заместитель (англ. Proxy) — структурный шаблон проектирования, предоставляющий объект, который контролирует доступ к
 * другому объекту, перехватывая все вызовы (выполняет функцию контейнера).
 *
 *  1 Адаптер обеспечивает отличающийся интерфейс к объекту.
 *  2 Прокси обеспечивает тот же самый интерфейс.
 *  3 Декоратор обеспечивает расширенный интерфейс.
 */
public class ProxyPattern {

    /**
     * "Subject"
     */
    interface IMath {
        double add(double x, double y);
        double sub(double x, double y);
        double mul(double x, double y);
        double div(double x, double y);
    }

    /**
     * "Real Subject"
     */
    static class Math implements IMath {

        public double add(double x, double y) {
            return x + y;
        }

        public double sub(double x, double y) {
            return x - y;
        }

        public double mul(double x, double y) {
            return x * y;
        }

        public double div(double x, double y) {
            return x / y;
        }
    }

    /**
     * "Proxy Object"
     */
    static class MathProxy implements IMath {

        private Math math;

        public double add(double x, double y) {
            lazyInitMath();
            return math.add(x, y);
        }

        public double sub(double x, double y) {
            lazyInitMath();
            return math.sub(x, y);
        }

        public double mul(double x, double y) {
            lazyInitMath();
            return math.mul(x, y);
        }

        public double div(double x, double y) {
            lazyInitMath();
            return math.div(x, y);
        }

        private void lazyInitMath() {
            if (math == null) {
                math = new Math();
            }
        }
    }

    public static void main(String[] args) {
        // Create math proxy
        IMath p = new MathProxy();

        // Do the math
        System.out.println("4 + 2 = " + p.add(4, 2));
        System.out.println("4 - 2 = " + p.sub(4, 2));
        System.out.println("4 * 2 = " + p.mul(4, 2));
        System.out.println("4 / 2 = " + p.div(4, 2));
    }


}
