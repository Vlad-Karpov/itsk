package vtb.streams;

import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

/**
 * Terminal operation:
 * forEach
 * toArray
 * reduce
 * collect
 * min
 * max
 * count
 * anyMatch
 * allMatch
 * noneMatch
 * findFirst
 * findAny
 * <p>
 * {Каждый forEach} {элемент массива toArray} {упрощает reduce} {коллекцию collect}
 * из 3 групп:
 * max, min, count
 * anyMatch, allMatch, noneMatch
 * findFirst, findAny
 * <p>
 * <p>
 * <p>
 * Stream intermediate operations
 * filter
 * map
 * flatMap
 * peek
 * distinct
 * sorted
 * limit
 * <p>
 *     Фильтрованные карты цепляют уникально сортированный лимит
 */
public class StreamsTest {

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    terminal
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void terminal_operation_foreach() {
        Stream.of("Hello", "World").forEach(p -> System.out.println(p));
    }

    @Test
    public void terminal_operation_toArray() {
        Object[] objects = Stream.of(1, 2).toArray();
        assertTrue(objects.length == 2);
    }

    @Test
    public void terminal_operation_reduce() {
        int sum = IntStream.of(1, 2, 3, 4).reduce(0, (a, b) -> a + b);
        assertEquals(10, sum);
    }

    @Test
    public void terminal_operation_collect() {
        Set<String> stringSet = Stream.of("some", "one", "some", "one")
                .collect(Collectors.toSet());
        assertThat(stringSet, contains("some", "one"));
        assertEquals(2, stringSet.size());
    }

    @Test
    public void terminal_operation_min() {
        OptionalInt minimum = IntStream.of(1, 2, 3).min();
        assertEquals(1, minimum.getAsInt());
    }

    @Test
    public void terminal_operation_max() {
        OptionalDouble max = Stream.of(1d, 2d, 3d)
                .mapToDouble(Double::doubleValue).max();
        assertEquals(3, max.getAsDouble(), 0);
    }

    @Test
    public void terminal_operation_count() {
        long count = Stream.of("one").count();
        assertEquals(1, count);
    }

    @Test
    public void terminal_operation_anymatch() {
        boolean lengthOver5 = Stream.of("two", "three", "eighteen").anyMatch(
                s -> s.length() > 5);
        assertTrue(lengthOver5);
    }

    @Test
    public void terminal_operation_allmatch() {
        List<String> cookies = Lists.newArrayList("Peanut Butter Cookies",
                "Oatmeal-Raisin Cookies", "Basic Chocolate Chip Cookies");
        boolean containCookies = cookies.stream().allMatch(
                p -> p.contains("Cookies"));
        assertTrue(containCookies);
    }

    @Test
    public void terminal_operation_nonematch() {
        boolean noElementEqualTo5 = IntStream.of(1, 2, 3)
                .noneMatch(p -> p == 5);
        assertTrue(noElementEqualTo5);
    }

    @Test
    public void terminal_operation_findfirst() {
        Optional<String> val = Stream.of("one", "two").findFirst();
        assertEquals("one", val.get());
    }

    @Test
    public void terminal_operation_findany() {
        Optional<String> val = Stream.of("one", "two").findAny();
        assertEquals("one", val.get());
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    intermediate
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    public void intermediate_filter() {
        long elementsLessThanThree = Stream.of(1, 2, 3, 4)
                .filter(p -> p.intValue() < 3).count();
        assertEquals(2, elementsLessThanThree);
    }

    @Test
    public void intermediate_map() {
        List<String> strings = Stream.of("one", null, "three").map(s -> {
            if (s == null)
                return "[unknown]";
            else
                return s;
        }).collect(Collectors.toList());
        assertThat(strings, contains("one", "[unknown]", "three"));
    }

    /**
     * Stream.flatmap will transform each element into zero or more elements by a way of another stream.
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void count_distinct_words_java8() throws IOException, URISyntaxException {
        File file = new File(Resources.getResource("collections.java").toURI());
        long uniqueWords = java.nio.file.Files
                .lines(Paths.get(file.toURI()), Charset.defaultCharset())
                .flatMap(line -> Arrays.stream(line.split(" ."))).distinct()
                .count();
        assertEquals(362, uniqueWords);
    }

    /**
     * The Stream.peek is extremely useful during debugging. It allows you to peek into the stream before an action is
     * encountered. In this snippet we will filter any strings with a size of great than four then call the peek at the
     * stream before the map is called. The peek operation will print out the elements of 'Badgers', 'finals' and 'four'.
     */
    @Test
    public void intermediate_peek() {
        List<String> strings = Stream.of("Badgers", "finals", "four")
                .filter(s -> s.length() >= 4).peek(s -> System.out.println(s))
                .map(s -> s.toUpperCase()).collect(Collectors.toList());
        assertThat(strings, contains("BADGERS", "FINALS", "FOUR"));
    }

    @Test
    public void intermediate_distinct() {
        List<Integer> distinctIntegers = IntStream.of(5, 6, 6, 6, 3, 2, 2)
                .distinct()
                .boxed()
                .collect(Collectors.toList());
        assertEquals(4, distinctIntegers.size());
        assertThat(distinctIntegers, contains(
                5, 6, 3, 2));
    }

    @Test
    public void intermediate_sorted() {
        List<Integer> sortedNumbers = Stream.of(5, 3, 1, 3, 6).sorted()
                .collect(Collectors.toList());
        assertThat(sortedNumbers, contains(1, 3, 3, 5, 6));
    }

    @Test
    public void intermediate_limit() {
        List<String> vals = Stream.of("limit", "by", "two").limit(2)
                .collect(Collectors.toList());
        assertThat(vals, contains("limit", "by"));
    }

    @Test
    public void qmain() {
            System.out.println(14 ^ 23);
    }

}
