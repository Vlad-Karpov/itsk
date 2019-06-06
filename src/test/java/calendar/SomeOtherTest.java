package calendar;

import org.junit.Test;

import java.time.Instant;
import java.util.Calendar;

import static java.time.temporal.ChronoUnit.DAYS;

public class SomeOtherTest {

    @Test
    public void test1() {
        Calendar c = Calendar.getInstance();
        System.out.println(c);
        c.add(Calendar.MONTH, 3);
        System.out.println(c);

        Instant inst = Instant.now();
        System.out.println(inst);
        inst = inst.plus(10L, DAYS);
        System.out.println(inst);

    }

    /*
    Throwable
        Exception (checked)
            RuntimeException (unchecked) -> IndexOutOfBoundserror, ArithmeticException / by zero
        Error -> IOError, InternalError
     */
    @Test
    public void test2() {
        try {
            try {
                int i = 10 / 0;
            } catch (Throwable exception) {
                System.out.println("1");
                int j = 10 / 0; // This error is missed!
            } finally {
                System.out.println("2");
                int[] k = {1, 2};
                int l = k[2];
            }
        } catch (Throwable exception) {
            System.out.println("3");
            exception.printStackTrace();
        }
    }

}
