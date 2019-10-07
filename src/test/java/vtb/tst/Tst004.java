package vtb.tst;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Какие из следующих частей кода будут успешно скомпилированны
 *      *    1)  Integer iWrap = new Integer(100);
 *      *    2)  Integer iWrap = 100;
 *      *    3)  List<Integer> listOfNumbers = new ArrayList<Integer>();
 *               listOfNumbers.add(3)
 *           4)  List<int> listOfNumbers = new ArrayList<int>();
 *               listOfNumbers.add(3)
 */
public class Tst004 {
    @Test
    public void tst01() {
        Integer iWrap = new Integer(100);
    }
    @Test
    public void tst02() {
        Integer iWrap = 100;
    }
    @Test
    public void tst03() {
        List<Integer> listOfNumbers = new ArrayList<Integer>();
        listOfNumbers.add(3);
    }
    @Test
    public void tst04() {
//        List<int> listOfNumbers = new ArrayList<int>();     //Compile time error "Type argument cannot be of primitive type"
//        listOfNumbers.add(3);
    }
}
