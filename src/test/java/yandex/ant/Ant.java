package yandex.ant;

import org.junit.Test;

/**
 * Есть таблица M на N. В левой верхней клетке (1,1) находится муравей. За один ход муравей может передвигаться либо на
 * одну клетку вниз, либо на одну клетку вправо. Напишите, пожалуйста, программу, которая считает количество всех путей
 * муравья из точки (1,1) в точку (M,N).
 */
public class Ant {

    static final int M = 3;
    static final int N = 3;
    int cnt;

    public void mn(){
        cnt = 0;
        mn(0, 0);
    }

    public void mn(int x, int y) {
        if (x < M - 1) mn(x + 1, y);
        if (y < N - 1) mn(x, y + 1);
        if (x == M - 1 && y == N - 1) {
            cnt++;
        }
    }

    @Test
    public void tst1(){
        mn();
        System.out.println(cnt);
    }

}
