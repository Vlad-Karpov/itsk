package design.patterns.behavioral;

/**
 * Шаблон Хранитель используется двумя объектами: «Создателем» (originator) и «Опекуном» (caretaker). «Создатель» — это
 * объект, у которого есть внутреннее состояние. Объект «Опекун» может производить некоторые действия с «Создателем»,
 * но при этом необходимо иметь возможность откатить изменения. Для этого «Опекун» запрашивает у «Создателя» объект
 * «Хранителя». Затем выполняет запланированное действие (или последовательность действий). Для выполнения отката
 * «Создателя» к состоянию, которое предшествовало изменениям, «Опекун» возвращает объект «Хранителя» его «Создателю».
 * «Хранитель» является непрозрачным (то есть таким, который не может или не должен изменяться «Опекуном»).
 */
public class MementoPattern {

    static class Memento {
        private final String state;

        public Memento(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }
    }

    static class Caretaker {
        private Memento memento;

        public Memento getMemento() {
            return memento;
        }

        public void setMemento(Memento memento) {
            this.memento = memento;
        }
    }

    static class Originator {
        private String state;

        public void setState(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }

        public Memento saveState() {
            return new Memento(state);
        }

        public void restoreState(Memento memento) {
            this.state = memento.getState();
        }
    }

    static public void applyPattern() {
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();

        originator.setState("on");
        System.out.printf("State is %s\n", originator.getState());
        caretaker.setMemento(originator.saveState());

        originator.setState("off");
        System.out.printf("State is %s\n", originator.getState());

        originator.restoreState(caretaker.getMemento());
        System.out.printf("State is %s\n", originator.getState());
    }

    static public void main(String[] args) {
        applyPattern();
    }


}
