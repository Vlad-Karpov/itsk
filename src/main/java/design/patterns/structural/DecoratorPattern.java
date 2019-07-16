package design.patterns.structural;

/**
 * Декоратор (англ. Decorator) — структурный шаблон проектирования, предназначенный для динамического подключения
 * дополнительного поведения к объекту. Шаблон Декоратор предоставляет гибкую альтернативу практике создания подклассов
 * с целью расширения функциональности.
 *
 * 1 Адаптер обеспечивает отличающийся интерфейс к объекту.
 * 2 Прокси обеспечивает тот же самый интерфейс.
 * 3 Декоратор обеспечивает расширенный интерфейс.
 */
public class DecoratorPattern {

    interface InterfaceComponent {
        void doOperation();
    }

    static class MainComponent implements InterfaceComponent {

        @Override
        public void doOperation() {
            System.out.print("World!");
        }
    }

    abstract static class Decorator implements InterfaceComponent {
        protected InterfaceComponent component;

        public Decorator (InterfaceComponent c) {
            component = c;
        }

        @Override
        public void doOperation() {
            component.doOperation();
        }

        public void newOperation() {
            System.out.println("Do Nothing");
        }
    }

    static class DecoratorSpace extends Decorator {

        public DecoratorSpace(InterfaceComponent c) {
            super(c);
        }

        @Override
        public void doOperation() {
            System.out.print(" ");
            super.doOperation();
        }

        @Override
        public void newOperation() {
            System.out.println("New space operation");
        }
    }

    static class DecoratorComma extends Decorator {

        public DecoratorComma(InterfaceComponent c) {
            super(c);
        }

        @Override
        public void doOperation() {
            System.out.print(",");
            super.doOperation();
        }

        @Override
        public void newOperation() {
            System.out.println("New comma operation");
        }
    }

    static class DecoratorHello extends Decorator {

        public DecoratorHello(InterfaceComponent c) {
            super(c);
        }

        @Override
        public void doOperation() {
            System.out.print("Hello");
            super.doOperation();
        }

        @Override
        public void newOperation() {
            System.out.println("New hello operation");
        }
    }

    public static void main (String... s) {
        Decorator c = new DecoratorHello(new DecoratorComma(new DecoratorSpace(new MainComponent())));
        c.doOperation(); // Результат выполнения программы "Hello, World!"
        System.out.println("");
        c.newOperation(); // New hello operation
    }

}
