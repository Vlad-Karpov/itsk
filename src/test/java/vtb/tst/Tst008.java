package vtb.tst;

import org.junit.Test;

import java.util.Arrays;

/**
 * Дан следующий код (см.ниже). Что будет выведено на консоль,
 * {@code
 * <p>
 * class Person {
 * private int id;
 * private String name;
 * //getter and setters
 * }
 * <p>
 * class PersoneService {
 * public Persone create(int id, String name) {
 * return new Person() {
 * setId(id);
 * setName(name);
 * }
 * }
 * }
 * <p>
 * }
 * <p>
 * 1)  Ошибка компиляции
 * 2)  PersonService
 * 3)  PersonService$1
 * 4)  Person
 * 5)  String
 *
 * Ошибка компиляции
 *
 */
public class Tst008 {

    static class Person {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static class PersonService {
        public static Person create(int id, String name) {
//            return new Person[] [{
//                setId(id);
//                setName(name);
//            }];
            return new Person();
        }
    }

    static public class Main {
        private static PersonService personService = new PersonService();
        public static void main(String[] args) {
            System.out.println(PersonService.create(12, "Ivan").getClass().getName());
        }
    }

    @Test
    public void tst01() {
        Main main = new Main();
        String[] arg = {"1", "2"};
        main.main(arg);
    }

}
