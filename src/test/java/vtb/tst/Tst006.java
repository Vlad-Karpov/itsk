package vtb.tst;

/**
 * Какие ссылки считаются безусловно достижимыми для сборщика мусора
 *      1)  Локальные примитывные переменные в работающем методе
 *      2)  Main класс
 *      3)  Статические переменные
 *      4)  Объекта нет в куче
 *
 *      4 вариант
 */
public class Tst006 {

    static class QQQ001 {
        static public class Fruit {
            public Fruit() {
                System.out.println("Constructor of Fruit");
            }
            void method() {
                System.out.println("Method of Fruit");
            }
            public static void main(String[] args) {
                Fruit f = new Apple();
                f.method();
            }
        }
        static class Apple extends Fruit {
            public Apple() {
                System.out.println("Constructor of Apple");
            }
            protected void method() {
                System.out.println("Method of Apple");
            }
        }
    }

}
