package vtb;

import com.google.common.collect.Lists;
import org.hamcrest.CustomMatcher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


/**
 * @see <a href="https://habr.com/ru/company/sberbank/blog/416413/">Hubr: Пришел, увидел, обобщил: погружаемся в Java Generics</a>
 */
public class genericlearn {

    @Test
    public void arrays_is_covarint() {
        Number[] numberArray = new Number[5];
        numberArray[0] = 1;
        numberArray[1] = 1.2d;
        numberArray[2] = 1.2f;
        numberArray[3] = 0255;
        numberArray[3] = 0x2557;
    }

    @Test
    public void jeneric_is_invarint() {
        //List<Number> numberArray = new ArrayList<Integer>();  //compile error
    }


    @Test
    public void jeneric_wildcard_extended_is_covarint_but_only_produsers() {
        List<Integer> lst = Lists.newArrayList(1, 2, 3, 4);
        List<? extends Number> numberArray;
        numberArray = lst;
        assertEquals(1, numberArray.get(0));
        //numberArray.add(10);   //compile error
        List<? extends Integer> integerArray;
        integerArray = lst;
        //integerArray.add(10);   //Compile time error
        assertEquals(1, (int) integerArray.get(0)); //Ambiguous type assertEquals(Object, Object) and assertEquals(int, int)
        //add null, - that`s ok
        numberArray.add(null);
        integerArray.add(null);
    }

    @Test
    public void jeneric_wildcard_super_is_contrvarint_but_only_consumers() {
        List<Integer> lst = Lists.newArrayList(1, 2, 3, 4);
        List<? super Integer> numberArray;
        numberArray = lst;
        //Integer int0 = numberArray.get(0);  //Compile time error !
        Object int0 = numberArray.get(0);   //Object ok!
        assertEquals(1, numberArray.get(0));
        numberArray.add(1);
        //numberArray.add(1.2d);    //Compile time error
    }

    public static <T> T getFirstCompileTimeError(List<? super T> list) {
        //return list.get(0); // compile-time error
        return null;
    }

    public static <T> Object getFirstOk(List<? super T> list) {
        return list.get(0);
    }

    @Test
    public void pecs() {
        List<Integer> src = Lists.newArrayList(1, 2, 3, 4, 5);
        List<Number> dest = new ArrayList<>(Lists.newArrayList(null, null, null, null, null));
        // dest List<? super T>     List<? super Number>
        // src List<? extends T>    List<? extends Integer>
        // copy(List<? super T> dest, List<? extends T> src)
        Collections.copy(dest, src);
        assertThat(dest, contains(1, 2, 3, 4, 5));
        //Collections.copy(src, dest); // Compile-time error
    }

    /**
     * {@code
     * <?>     =       <? extends Object>
     * }
     */
    @Test
    public void wildcard() {
        List<Integer> lst = Lists.newArrayList(1, 2, 3, 4);
        List<?> wildcardArray;
        wildcardArray = lst;
        assertEquals(1, wildcardArray.get(0));
        //wildcardArray.add(10);   //compile error
    }

    /**
     * Row type - welcome java 1.1
     */
    @Test
    public void rowType() {
        List<Integer> lst = Lists.newArrayList(1, 2, 3, 4);
        List rowArray;
        rowArray = lst;
        assertEquals(1, rowArray.get(0));
        rowArray.add(10);   //ok, but worning "Unchecked call"
        rowArray.add("Hello!"); // Heap Pollution "загрязнение кучу"
        assertThat(rowArray, new CustomMatcher<List>("Row List to concatinated string") {
            @Override
            public boolean matches(Object item) {
                return ((List) item).stream().map(Object::toString).collect(Collectors.joining(":")).equals("1:2:3:4:10:Hello!");
            }
        });
    }


    /**
     * Wildcard capture
     */
    @Test
    public void reversTest() {
        List<String> lst = Lists.newArrayList("1", "2", "3");
        revers(lst);
        assertThat(lst, contains("3", "2", "1"));
    }

    private void revers(List<?> src) {
        int size = src.size();
        int lm = size >> 1;
        for(int i = 0; i < lm; i++) {
            int index = size - 1 - i;
            /*
            Object item = src.get(i);
            src.set(i, src.get(index)); //compile time error
            src.set(index, item);    //compile time error
            */
            swapItems(src, i, index);   //That`s ok, because now <T> = String captured from src and call swapItems with List<String> src
        }
    }

    private <T> void swapItems(List<T> lst, int i1, int i2) {
        T item = lst.get(i1);
        lst.set(i1, lst.get(i2));
        lst.set(i2, item);
    }

/*
    compile time exception
    static class MyException<T> extends Exception {
        T t;
    }
*/


/*
Type Erasure
Reifiable types
Type Inference
    List<Integer> list = new ArrayList<Integer>();
    ->
    List<Integer> list = new ArrayList<>();
*/


}
