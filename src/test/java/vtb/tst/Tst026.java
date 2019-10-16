package vtb.tst;

/**
 * Какие из условий должны выполняться для правильной работы метода <pre>{@link  java.util.Collections#sort(java.util.List) Collections.sort(...)}</pre>
 *   <ul>
 *    <li>Входящий список должен содержать объекты в который переписаны методы <pre>{@link Object#equals(Object) equals(Object)} и  {@link Object#hashCode() hashCode()}</pre></li>
 *    <li>* Входящий список должен содержать объекты, которые имплементируют интерфейс <pre>{@link Comparable Comparable}</pre></li>
 *    <li>* Входящий список может содержать любые объекты, при условии, что вторым объектом передается объект типа <pre>{@link java.util.Comparator Comparator}</pre></li>
 *    <li>Входящий список может содержать любые объекты, которые имплементируют интерфейс <pre>{@link java.io.Serializable Serializable}</pre></li>
 *   </ul>
 */
public class Tst026 {
}
