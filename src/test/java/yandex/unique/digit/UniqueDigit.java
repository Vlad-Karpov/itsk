package yandex.unique.digit;

import org.junit.Test;

import java.util.Calendar;

/**
 *
 * Напишите код, который для заданного диапазона целых положительных чисел min..max возвращает только те числа,
 * в которых не повторяется ни одна цифра. Например, для диапазона 98..103 он возвращает числа (98, 102, 103) и
 * не возвращает (99, 100, 101). Рассматриваются решения, оптимальные как по объему кода (наиболее краткие),
 * так и по скорости выполнения.
 * Оптимизированное по скорости решение должно за разумное время выдать ВСЕ возможные числа с неповторяющимися цифрами
 * (от 0 до 9876543210).
 * Для кратких решений разрешена реализация на языках, отличных от Java.
 */
public class UniqueDigit {

    private long uniqueDigit(long theStart, long theEnd) {
        long div10;
        long digit;
        long ind;
        long cnum;
        long msk;
        long cnt = 0;
        for (long num = theStart; num < theEnd; num ++) {
            cnum = num;
            msk = 0;
            while (cnum > 0) {
                div10 = cnum / 10;
                digit = cnum - div10 * 10;
                cnum = div10;
                ind = 1 << digit;
                if ( (msk & ind) > 0) {
                    msk = -1;
                    break;
                } else {
                    msk = msk | ind;
                }
            }
            if (msk != -1) {
                cnt++;
                //System.out.println(num);
                if (cnt % 1000000 == 0) {
                    System.out.println(cnt);
                }
            }
        }
        return cnt;
    }

    @Test
    public void test1() {
        Calendar c1 = Calendar.getInstance();
        long cnt = uniqueDigit(0L, 9876543210L);
        Calendar c2 = Calendar.getInstance();
        System.out.println("Total: " + cnt + " Time: " + (c2.getTimeInMillis() - c1.getTimeInMillis())/1000 + " c.");
    }

}
