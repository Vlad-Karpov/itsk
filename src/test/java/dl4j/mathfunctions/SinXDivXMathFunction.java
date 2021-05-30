package dl4j.mathfunctions;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.ops.transforms.Transforms;

/**
 * Calculate function value of sine of x divided by x.
 */
public class SinXDivXMathFunction implements MathFunction {

    @Override
    public INDArray getFunctionValues(final INDArray x) {
        //return Transforms.sin(x, true); //.divi(x);
        //return Transforms.sin(x, true).add(Transforms.sin(x.mul(Math.PI))).mul(Transforms.exp(x));
        //return Transforms.sin(x, true).mul(0.3).add(Transforms.sin(x.mul(Math.PI)).mul(0.125)).add(Transforms.sin(x.mul(2 * Math.PI)).mul(0.125)).mul(Transforms.exp(Transforms.abs(x).mul(-1)));
        //return Transforms.exp(Transforms.abs(x).mul(-0.03125)).mul(Transforms.sin(x));
        return Transforms.exp(Transforms.abs(x).mul(-0.03125)).mul(Transforms.sin(x).add(Transforms.sin(x.mul(0.5 * Math.PI))).mul(0.5));
    }

    @Override
    public String getName() {
        return "SinXDivX";
    }
}