package figaro;

import com.cra.figaro.language.Element;

public class ScalaElementSupplier extends scala.runtime.AbstractFunction1<com.cra.figaro.language.Element, com.cra.figaro.language.Element> {
    Element v1;

    public ScalaElementSupplier(Element v1) {
        this.v1 = v1;
    }

    @Override
    public Element apply(Element v1) {
        return this.v1;
    }
}
