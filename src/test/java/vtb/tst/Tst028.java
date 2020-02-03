package vtb.tst;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * Что сделает программа
 *
 *   <ul>
 *    <li>* 100 байтов из файла fname в переменную bytes</li>
 *    <li>все байты из файла fname в переменную bytes</li>
 *    <li>50 байт в переменную moreBytes после чтения в переменную bytes</li>
 *    <li>* 20 байт в переменную moreBytes после чтения в переменную bytes</li>
 *   </ul>
 *
 */
public class Tst028 {

    @Test
    public void tst01() {
        byte b;
        byte bytes[] = new byte[100];
        byte moreBytes[] = new byte[50];
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\User\\IdeaProjects\\itsk\\src\\test\\java\\vtb\\tst\\Tst028.java");
            b = (byte)fis.read();
            fis.read(bytes);
            fis.read(moreBytes, 0, 20);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tst02() {
        Stream<Integer> numStream = Stream.of(10, 20, 30);
        numStream.map(n -> n + 10).peek(s -> System.out.print(s));
        //numStream.forEach(s -> System.out.println(s));
    }

}
