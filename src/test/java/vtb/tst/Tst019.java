package vtb.tst;

import org.junit.Test;

/**
 * Для чего используется аннотация @Override
 *
 *          Как необходимый оператор при переопределении (override) любых методов интерфейса
 *          Как необходимый оператор при переопределении (override) финальных (final) любого класса
 *     *    Как вспомогательная аннотация, помогающая компилятору отследить ошибки в сигнатурах переопределенных методов
 *          Как вспомогательная аннотация, отключающая предупреждения об ошибках на этапе компиляции для переопределяемых (override) методов
 */
public class Tst019 {

    static class QQ<T extends Comparable<T>> {
        T cmpObject;
    }

    @Test
    public void tst() {
        QQ<Integer> qq = new QQ<>();
    }

    @Test
    public void tst1() {
        if ("Welcome".trim() == "Welcome".trim())
            System.out.println("Equal");
        else
            System.out.println("Not Equal");
    }

}
