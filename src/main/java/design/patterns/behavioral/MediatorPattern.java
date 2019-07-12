package design.patterns.behavioral;

/**
 * "Посредник" определяет интерфейс для обмена информацией с объектами "Коллеги", "Конкретный посредник" координирует
 * действия объектов "Коллеги". Каждый класс "Коллеги" знает о своем объекте "Посредник", все "Коллеги" обмениваются
 * информацией только с посредником, при его отсутствии им пришлось бы обмениваться информацией напрямую. "Коллеги"
 * посылают запросы посреднику и получают запросы от него. "Посредник" реализует кооперативное поведение, пересылая
 * каждый запрос одному или нескольким "Коллегам".
 */
public class MediatorPattern {

    static abstract class Colleague {

        protected Mediator mediator;

        public Colleague(Mediator mediator) {
            this.mediator = mediator;
        }

        public void send(String message) {
            mediator.send(message, this);
        }

        public abstract void notify(String message);
    }

    static abstract class Mediator {

        public abstract void send(String message, Colleague sender);
    }

    static class ConcreteColleague1 extends Colleague {

        public ConcreteColleague1(Mediator mediator) {
            super(mediator);
        }

        @Override
        public void notify(String message) {
            System.out.println("Colleague1 gets message: " + message);
        }
    }

    static class ConcreteColleague2 extends Colleague {

        public ConcreteColleague2(Mediator mediator) {
            super(mediator);
        }

        @Override
        public void notify(String message) {
            System.out.println("Colleague2 gets message: " + message);
        }
    }

    static class ConcreteMediator extends Mediator {

        private ConcreteColleague1 colleague1;
        private ConcreteColleague2 colleague2;

        public void setColleague1(ConcreteColleague1 colleague) {
            this.colleague1 = colleague;
        }

        public void setColleague2(ConcreteColleague2 colleague) {
            this.colleague2 = colleague;
        }

        @Override
        public void send(String message, Colleague sender) {
            if (sender.equals(colleague2)) {
                colleague1.notify(message);
            } else {
                colleague2.notify(message);
            }
        }
    }

    public static void main(String[] args) {
        ConcreteMediator m = new ConcreteMediator();

        ConcreteColleague1 c1 = new ConcreteColleague1(m);
        ConcreteColleague2 c2 = new ConcreteColleague2(m);

        m.setColleague1(c1);
        m.setColleague2(c2);

        c1.send("How are you?");
        c2.send("Fine, thanks");
    }


}
