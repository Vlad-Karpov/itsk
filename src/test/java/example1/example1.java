package example1;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 1) Неграмотная реализация double check lock.
 * 2) HashMap не ThreadSafe, первый get в многопоточной среде выдаст непредсказуемый результат,
 *    либо непредсказуемую ошибку.
 * 3) Ключевой массив byte[] в HashMap можно менять внутри, в частности в функции doGenerated.
 *    Hash код от этого, правда, не зависит, и, целостность кэша не пострадает, но повторный
 *    вызов doGenerate по этому же ключу выдаст уже другой результат.
 * 4) Разные массивы с одинаковыми значениями в качестве ключа выдадут разный hash код, но
 *    одинаковое сгенеренное значение. Потенциально можно забить этим caсhe до out of memory.
 * 5) Hash код ключа зависит только от адареса массива, вопрос: как из кэша доставать элементы?
 * 6) Сравнение equals массивов byte[] тоже сравнивает их только по адресу, поэтому в случае
 *    колизий ключа элементы ВСЕГДА будут добавлять в кэш
 * 7) Операция cache.get(src) ВСЕГДА будет выдавть null.
 *
 */
public class example1 {

    private static final Map<byte[], byte[]> cache = new HashMap<byte[], byte[]>();

    public static byte[] generate(byte[] src) {
        byte[] generated = cache.get(src);
        if (generated == null) {
            synchronized (cache) {
                generated = cache.get(src);
                if (generated == null) {
                    generated = doGenerate(src);
                    cache.put(src, generated);
                }
            }
        }
        return generated;
    }

    private static byte[] doGenerate(byte[] src) {
        src[0] = 25;  //!!!
        return new byte[5];
    }

    @Test
    public void test1() {
        byte[] b;
        b = new byte[5];
        System.out.println(generate(b));
        b = new byte[3];
        System.out.println(generate(b));
        b = new byte[3];
        System.out.println(generate(b));
    }

}
