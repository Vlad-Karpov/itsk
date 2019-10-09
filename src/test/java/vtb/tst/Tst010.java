package vtb.tst;

import org.junit.Test;

/**
 * Какие из утверждений верны
 * *1)  В java запрещено множественное наследование. Только один класс может быть унаследован при помощи ключевого слова extends
 * 2)  В java присутствует множественное наследование. При помощи ключевого слова extends может быть унаследовано более одного класса
 * *3)  Класс может имплементировать несколько интерфейсов.
 * *4)  Наследование невозможно, если калассы с модификатором класса default находятся в разных пакетах
 * *5)  final классы невозможно наследовать
 */
public class Tst010 {

    @Test
    public void main11() {
        int i = 1, j = 1;
        try {
            i++;
            j--;
            if (i == j)
                i++;
        } catch (ArithmeticException e) {
            System.out.println(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(1);
        } catch (Exception e) {
            System.out.println(2);
        } finally {
            System.out.println(3);
        }
        System.out.println(4);
    }

    @Test
    public void trst01() {
        int i = -1;
        i = i >> 1;
        System.out.println(i);  // print -1
    }

}
