package design.patterns.behavioral;

/**
 * Iterator — поведенческий шаблон проектирования. Представляет собой объект, позволяющий получить последовательный
 * доступ к элементам объекта-агрегата без использования описаний каждого из агрегированных объектов.
 */
public class IteratorPattern {

    interface Iterator<T> {
        T next();
    }

    interface Aggregator<T> {
        Iterator<T> iterator();
    }

    static class ConcreteIterator implements Iterator<Integer> {

        int[] values;
        int index;

        public ConcreteIterator(int[] values) {
            this.values = values;
            index = 0;
        }

        @Override
        public Integer next() {
            if (index < values.length) {
                return values[index++];
            } else
                return null;
        }
    }

    static class ConcreteAggregator implements Aggregator<Integer> {

        int[] values = {1, 2, 3, 4, 5};

        @Override
        public Iterator<Integer> iterator() {
            return new ConcreteIterator(values);
        }
    }

    static class IteratorClient {
        static void iterateIt() {
            Aggregator aggregator = new ConcreteAggregator();
            Iterator<Integer> it = aggregator.iterator();
            for (Integer i = it.next(); i != null; i = it.next()) {
                System.out.println(i);
            }
        }
    }

    static public void main(String[] args) {
        IteratorClient.iterateIt();
    }

}
