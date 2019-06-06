package example3;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by KARPOVVV on 20.07.17.
 */
public class example3 implements Runnable {
    @Test
    public void test1() {
        SomeClass sc = new SomeClass();
        RetObj rto = sc.createUser("1");
        System.out.println(rto.log);
        rto = sc.createUser("2");
        System.out.println(rto.log);
        rto = sc.createUser("3");
        System.out.println(rto.log);
        rto = sc.createUser("4");
        System.out.println(rto.log);
    }

    @Test
    public void test2() {
        for (int i = 1; i<1000000; i++){
            new Thread(this).start();
        }
    }

    SomeClass instSomeClass = new SomeClass();
    Integer cnt = 0;

    @Override
    public void run() {
        Integer threadNum;
        synchronized(cnt) {
            cnt++;
            threadNum = cnt;
        }
        RetObj rto = instSomeClass.createUser(threadNum.toString());
        if (threadNum == 1) {
            Assert.assertEquals(rto.log, "2");
        } else {
            Assert.assertEquals(rto.log, threadNum.toString());
        }
    }
}
