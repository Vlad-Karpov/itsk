package example1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 1.	Как бы вы оптимизировали/упростили конструкцию используя принципы ООП: Map<Map<String,Map>, List<Map<String, String>>> benfAndCatToCtx = new HashMap();
 *
 * Вынес бы управление Map<String,Map> и Map<String, String> в отдельные классы.
 * Кода гораздо больше, но элегнтно выглядит новое определение переменной:
 * private Map<classA, classB> benfAndCatToCtx2 = new HashMap<classA, classB>();
 * И в отдельно взятых классах появляется возможность описать методы манипулирования Map<String,Map> и Map<String, String> и отдельно можно
 * написать функции хэш кода и сравнения.
 * А сами переменные инкапсулируются.
 *
 */
public class example {
    private Map<Map<String,Map>, List<Map<String, String>>> benfAndCatToCtx = new HashMap();
    private Map<classA, classB> benfAndCatToCtx2 = new HashMap<classA, classB>();
}
