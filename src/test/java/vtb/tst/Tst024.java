package vtb.tst;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Какая реализация является предпочтительной для хранения большого количества объектов в списке, если при этом часто
 * производиться выборка по индексу
 *
 * <ul>
 * <li>          LinkedList</li>
 * <li>          </li>
 * <li>          </li>
 * <li>          </li>
 * </ul>
 */
public class Tst024 {

    static class TestClass {
        int i = getInt();
        int k = 20;
        public int getInt() {  return k+1;  }
        public static void main(String[] args) {
            TestClass t = new TestClass();
            System.out.println(t.i + "  " + t.k);
        }
    }

    @Test
    public void tst01() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(0);
        Integer[] array = null;
        list.toArray(array);    //run time error
        System.out.println(list.get(1));
    }

    @Test
    public void tst02() {
        int i = 0;
        i++;
        System.out.print(i);
        i = i++;
        System.out.println(i);
    }

    static public class Z {
        public void print( Object o ) {
            System.out.println( "Object" );
        }

        public void print( String str ) {
            System.out.println( "String" );
        }

        public void print( Integer i ) {
            System.out.println( "Integer" );
        }

        public static void main(String[] args) {
            Z z = new Z();
            //z.print( null ); //compile time error
            z.print( (Integer) null );
        }
    }

}
