package vtb.tst;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Выберите верные утверждения
 *
 *     *     внутри generic метода нельзя узнать тип параметра generic
 *     *     параметрами generic шаблона могут быть любые типы
 *     *     внутри generic шаблона нельзя создать экземпляр класса параметра
 *     *     generic классы не могут использоваться без параметра
 *
 *     по ходу все..., но это не точно!
 *
 */
public class Tst020 {

    static class SomeGeneric<T extends Object> {
        T generic;
        public SomeGeneric(T theGeneric) {
            generic = theGeneric;
        }
        T getInstT() {
            //return new T();  //compile time error
            return generic;
        }
        Class<T> getClassT() {
            //return T.class;  //compile time error
            return (Class<T>) generic.getClass();
        }
    }

    @Test
    public void tst() {
        SomeGeneric<Object> someGeneric1 = new SomeGeneric<>("");
        SomeGeneric<String> someGeneric2 = new SomeGeneric<>("");
        SomeGeneric someGeneric3 = new SomeGeneric("");
        SomeGeneric<List<? super Number>> someGeneric4 = new SomeGeneric<>(new ArrayList<Number>());
        SomeGeneric<Object> someGeneric5 = new SomeGeneric<>(new ArrayList<Number>());
    }

}
