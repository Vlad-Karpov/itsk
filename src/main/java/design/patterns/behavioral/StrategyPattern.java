package design.patterns.behavioral;

/**
 * Стратегия (англ. Strategy) — поведенческий шаблон проектирования, предназначенный для определения семейства
 * алгоритмов, инкапсуляции каждого из них и обеспечения их взаимозаменяемости. Это позволяет выбирать алгоритм путём
 * определения соответствующего класса. Шаблон Strategy позволяет менять выбранный алгоритм независимо от
 * объектов-клиентов, которые его используют.
 */
public class StrategyPattern {

    interface Strategy {
        int method1();
        void method2();
    }

    static class ConcreatStrategy1 implements Strategy {

        @Override
        public int method1() {
            return 1;
        }

        @Override
        public void method2() {

        }
    }

    static class ConcreatStrategy2 implements Strategy {

        @Override
        public int method1() {
            return 2;
        }

        @Override
        public void method2() {

        }
    }

    static class ClientStrategyClass implements Strategy {

        Strategy strategy;

        public void setStrategy(Strategy strategy) {
            this.strategy = strategy;
        }

        @Override
        public int method1() {
            return strategy.method1();
        }

        @Override
        public void method2() {
            strategy.method2();
        }
    }

    public static void main(String[] args) {
        ConcreatStrategy1 s1 = new ConcreatStrategy1();
        ConcreatStrategy2 s2 = new ConcreatStrategy2();
        ClientStrategyClass clientStrategyClass = new ClientStrategyClass();
        clientStrategyClass.setStrategy(s1);
        System.out.println(clientStrategyClass.method1());
        clientStrategyClass.setStrategy(s2);
        System.out.println(clientStrategyClass.method1());

    }

}
