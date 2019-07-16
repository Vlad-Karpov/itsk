package learnenum;

/**
 * <P>
 * An enum is a special "class" that represents a group of constants (unchangeable variables, like final variables).
 * To create an enum, use the enum keyword (instead of class or interface), and separate the constants with a comma.
 * Note that they should be in uppercase letters:
 * </P>
 *
 * <p>{@code
 * enum Level {
 *   LOW,
 *   MEDIUM,
 *   HIGH
 * }
 *
 * Level myVar = Level.MEDIUM;
 *
 * }
 *
 * <P>You can also have an enum inside a class:</P>
 *
 * <p>{@code
 * public class MyClass {
 *   enum Level {
 *     LOW,
 *     MEDIUM,
 *     HIGH
 *   }
 *
 *   public static void main(String[] args) {
 *     Level myVar = Level.MEDIUM;
 *     System.out.println(myVar);
 *   }
 * }
 * }
 *
 * <P>Enums are often used in switch statements to check for corresponding values.</P>
 *
 * <P>The enum type has a values() method, which returns an array of all enum constants. This method is useful when you
 * want to loop through the constants of an enum:</P>
 *
 * <p>{@code
 * for (Level myVar : Level.values()) {
 *   System.out.println(myVar);
 * }
 * }
 *
 *
 * <p></p>https://www.sitepoint.com/fundamentals-of-java-enum-types-tutorial/
 *
 * <P>Extending java.lang.Enum
 * All enums implicitly extend java.lang.Enum. In Java, a class can only extend one parent and therefore an enum cannot
 * extend any other class (but implement interfaces – see below).</P>
 *
 * <P>Extending Enum means that every enum has a few methods that make it more usable:</P>
 *
 * <p>{@code
 * static values()
 * static valueOf(String)
 * name()
 * ordinal()
 * compareTo(Enum)
 * }
 *
 * <P>
 * Enum‘s values() Method
 * The values() method returns an array of enum-type variables containing all of the enumeration constants.
 * Here is an example:
 *</P>
 *
 * <p>{@code
 * SitePointChannel[] channels = SitePointChannel.values();
 * for (SitePointChannel channel : channels) {
 *     System.out.println(channel + " Channel");
 * }
 * }
 * <p>
 * And the output:
 *
 * JAVA Channel
 * MOBILE Channel
 * WEB Channel
 * PHP Channel
 * WORDPRESS Channel
 * JAVASCRIPT Channel
 * DESIGN Channel
 *
 * <P>As you can see, the values() methods provides a nice solution to loop over all constants of an enum.</P>
 *
 * <p>Enum‘s valueOf(String) Method
 *
 * <P>
 * The valueOf(String) method returns an enumeration constant whose value corresponds to the string passed to it or
 * throws an IllegalArgumentException if no constant with the specified name was found. Careful, the strings passed to
 * valueOf are case sensitive!
 * </P>
 *
 * <p>{@code
 * // returns SitePointChannel.JAVA enum
 * SitePointChannel.valueOf("JAVA");
 *
 *
 * // throws IllegalArgumentException
 * SitePointChannel.valueOf("java");
 * }
 *
 *
 * <P>As pointed out previously, all enumerations automatically inherit java.lang.Enum. This class defines several methods,
 * available for all the enumerations.</P>
 *
 *<P>The name() method
 * This method returns the name of this enum constant, exactly as declared in its enum declaration. Here is an example:
 * </P>
 *
 * <p>{@code
 * SitePointChannel channel = SitePointChannel.JAVA;
 * System.out.println(channel.name());
 * }
 * <p>The output of this example is the String JAVA.
 *
 * <P>
 * The ordinal() method
 * This method is used to obtain an enumeration constant’s position in the list of constants. This is called the ordinal
 * value. Here is an example:
 * </P>
 *
 * SitePointChannel channel = SitePointChannel.JAVA;
 * System.out.println(channel.ordinal());
 *
 * <P>
 * The output of this example is “0” since JAVA is the first constant of the Sitepoint enum and the initial constant is
 * assigned an ordinal of zero. Hence, the following outputs “3”:
 * </P>
 *
 * SitePointChannel channel = SitePointChannel.PHP;
 * System.out.println(channel.ordinal());
 *
 * <P>
 * The compareTo(Enum) method
 * This method is used to compare the ordinal value of two constants of the same enumeration. This method returns a
 * negative integer, zero, or a positive integer based on the ordinal positions of the two instances that are being
 * compared.
 * </P>
 *
 * <P>
 * This is how this works. Let’s take, for example, as a reference enum instance SitePointChannel.PHP.
 * </P>
 *
 * SitePointChannel channel  = SitePointChannel.PHP;
 *
 * <P>
 * Surely if we compare the channel instance with SitePointChannel.PHP, the compareTo() method will return the value 0.
 * </P>
 *
 * // returns 0
 * channel.compareTo(SitePointChannel.PHP);
 *
 * <P>
 * Since the reference enum instance is in the fourth position in the enum class and has both elements prior and
 * subsequent to its position, it can return both positive and negative outputs with this method.
 * </P>
 *
 * <P>
 * For example, if it is compared with SitePointChannel.JAVA, the output of the compareTo() method will be positive
 * since SitePointChannel.JAVA is located before the reference instance:
 * </P>
 *
 * // actually returns 3
 * channel.compareTo(SitePointChannel.JAVA);
 *
 * <P>
 * If the reference enum instance is compared with instances that come after it in the enum class, it will return a
 * negative value. For example, comparing it to SitePointChannel.JAVASCRIPT will return the value -2.
 * </P>
 *
 * // returns -2
 * channel.compareTo(SitePointChannel.JAVASCRIPT);
 * Enums with Fields
 *
 */
public class LearnEnum {

    enum Level {
        LOW,
        MEDIUM,
        HIGH
    }

    enum Level1 {


        LEVEL1(123),
        LEVEL2(456),
        LEVEL3(789);

        private int level;

        Level1(int theLevel) {
            level = theLevel;
        }

    }

    enum Level2 {


        LEVEL1(Level.LOW),
        LEVEL2(Level.MEDIUM),
        LEVEL3(Level.HIGH);

        private Level level;

        Level2(Level theLevel) {
            level = theLevel;
        }

    }


    public static void main(String[] args) {
        Level myVar = Level.MEDIUM;
        System.out.println(myVar);
        iterateLevel(myVar);
        iterateEnum(myVar);
        iterateEnum(Level2.LEVEL1);
    }

    public static void iterateLevel(Level theLevel) {
        for (Level levelVar : theLevel.values()) {
            System.out.println(levelVar);
        }
    }

    //только по рефлекшн, метод values implicit
    //PECS producer extends consumer super
    public static <E extends Enum<E>> void iterateEnum(Enum<E> theEnum) {
        System.out.println(theEnum);
        for (Enum enumVar: theEnum.getClass().getEnumConstants()) {
            System.out.println(enumVar);

        }
    }

}
