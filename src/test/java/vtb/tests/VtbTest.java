package vtb.tests;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class VtbTest {

    @Test
    public void tst() throws IOException {
        Stream<Integer> s = IntStream.rangeClosed(10, 15).boxed();
        Object o = s.collect(Collectors.partitioningBy(x -> x % 2 == 0));
        System.out.println(Integer.MAX_VALUE + Integer.MAX_VALUE);
        System.out.println(IntStream.of(1, 2, 4).max().getAsInt());
        new Boolean("TRUE");
    }

    class AA {
        public void qq() {

        }
    }

    class BB extends AA {
        @Override
        public void qq() {
            super.qq();
        }
    }



}
