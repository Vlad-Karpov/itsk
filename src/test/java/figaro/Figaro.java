package figaro;

import com.cra.figaro.algorithm.factored.VariableElimination;
import com.cra.figaro.language.AtomicFlip;
import com.cra.figaro.language.Name;
import com.cra.figaro.language.Select;
import com.cra.figaro.library.compound.If;
import com.cra.figaro.patterns.learning.ModelParameters;
import org.junit.Test;
import scala.Tuple2;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.compat.java8.functionConverterImpls.FromJavaSupplier;

public class Figaro {

    @Test
    public void test1() {

        /*
  val sunnyToday: AtomicFlip = Flip(0.2)
  val greetingToday: If[String] = If(sunnyToday,
    Select(0.6 -> "Здpaвcтвyй, мир!", 0.4 -> "Здравствуй, вселенная!"),
    Select(0.2 -> "Здpaвcтвyй, мир!", 0.8 -> "О нет, только не это!"))
  val sunnyTomorrow: If[Boolean] = If(sunnyToday, Flip(0.8), Flip(0.05))
  val greetingTomorrow: If[String] = If(sunnyTomorrow,
    Select(0.6 -> "Здpaвcтвyй, мир!", 0.4 -> "Здравствуй, вселенная!"),
    Select(0.2 -> "Здpaвcтвyй, мир!", 0.8 -> "О нет, только не это!"))
  val goodМood = Chain(greetingTomorrow, (s : String) => if (s == "good") Flip(0.9)
  else if (s == "average") Flip(0.6)
  else Flip(0.1))
         */


        final AtomicFlip sunnyToday = new AtomicFlip(new Name<>("sunnyToday"), 0.2, new ModelParameters());

//        FromJavaSupplier js = new FromJavaSupplier(() -> sunnyToday);
//
//        List nil = Nil$.MODULE$;
//        final $colon$colon lst1 = colon$colon$.MODULE$.apply(new Tuple2(
//                new FromJavaSupplier(0.6 -> "Здpaвcтвyй, мир!"),
//        new FromJavaSupplier(0.4 -> "Здравствуй, вселенная!")
//                ), nil);
//
//        final Select s1 = new Select(
//                new Name("s1"),
//                ,
//                new ModelParameters()
//        );
//        final Select s2 = new Select(
//                new Name("s2"),
//                new FromJavaSupplier((0.2) -> "Здравствуй, вселенная!"),
//            new FromJavaSupplier((0.8) -> "О нет, только не это!"),
//        new ModelParameters()
//        );

//        final If greetingToday = new If(
//                new Name("greetingToday"),
//                sunnyToday,
//                js,
//                js,
//                new ModelParameters());

        //sunnyToday, Select(0.6 -> "Здравствуй, мир!", 0.4 -> "Здравствуй, вселенная ! ")

        final AtomicFlip flip1 = new AtomicFlip(new Name("flip1"), 0.8, new ModelParameters());
        final AtomicFlip flip2 = new AtomicFlip(new Name("flip2"), 0.05, new ModelParameters());
        If sunnyTomorrow = new If(
                new Name("sunnyTomorrow"),
                sunnyToday,
                new FromJavaSupplier(() -> flip1),
                new FromJavaSupplier(() -> flip2),
                new ModelParameters());

        double result = VariableElimination.probability(sunnyTomorrow, "Здpaвcтвyй, мир!");
        System.out.println("Ecли сегодня произнесено приветствие \"Здpaвcтвyй, мир!\", то завтра будет сказано \"Здpaвcтвyй, мир!\" с вероятностью " + result + ".");

    }
}
