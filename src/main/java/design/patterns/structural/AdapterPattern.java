package design.patterns.structural;

/**
 * Адаптер (англ. Adapter) — структурный шаблон проектирования, предназначенный для организации использования функций
 * объекта, недоступного для модификации, через специально созданный интерфейс. Другими словами — это структурный
 * паттерн проектирования, который позволяет объектам с несовместимыми интерфейсами работать вместе.
 *
 * 1 Адаптер обеспечивает отличающийся интерфейс к объекту.
 * 2 Прокси обеспечивает тот же самый интерфейс.
 * 3 Декоратор обеспечивает расширенный интерфейс.
 */
public class AdapterPattern {

    static class ByExtends {

        // Target
        interface Chief
        {
            Object makeBreakfast();
            Object makeLunch();
            Object makeDinner();
        }

        // Adaptee
        static class Plumber {
            Object getScrewNut() { return new Object(); };
            Object getPipe() { return new Object(); };
            Object getGasket() { return new Object(); };
        }

        // Adapter
        static class ChiefAdapter extends Plumber implements Chief {
            public Object makeBreakfast() {
                return getGasket();
            }
            public Object makeLunch() {
                return getPipe();
            }
            public Object makeDinner() {
                return getScrewNut();
            }
        }

        static void eat(Object dish)
        { System.out.println("dish"); }

        public static void main(String[] args)
        {
            Chief ch = new ChiefAdapter();
            Object dish = ch.makeBreakfast();
            eat(dish);
            dish = ch.makeLunch();
            eat(dish);
            dish = ch.makeDinner();
            eat(dish);
            callAmbulance();
        }

        private static void callAmbulance() {
            System.out.println("Ambulance");
        }

    }

    static class ByComposition {

        interface Chief {

            Object makeBreakfast();
            Object makeDinner();
            Object makeSupper();

        }

        // Файл Plumber.java
        static class Plumber {

            Object getPipe(){
                return new Object();
            }

            Object getKey(){
                return new Object();
            }

            Object getScrewDriver(){
                return new Object();
            }

        }

        // Файл ChiefAdapter.java
        static class ChiefAdapter implements Chief{

            private Plumber plumber = new Plumber();

            @Override
            public Object makeBreakfast() {
                return plumber.getKey();
            }

            @Override
            public Object makeDinner() {
                return plumber.getScrewDriver();
            }

            @Override
            public Object makeSupper() {
                return plumber.getPipe();
            }

        }

        public static void main (String [] args){
            Chief chief = new ChiefAdapter();
            Object key = chief.makeDinner();
        }


    }

}
