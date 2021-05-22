package apache.math;

import encog.ShapeTest;
import org.apache.commons.math3.analysis.DifferentiableUnivariateFunction;
import org.apache.commons.math3.analysis.FunctionUtils;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.analysis.function.HarmonicOscillator;
import org.apache.commons.math3.analysis.integration.IterativeLegendreGaussIntegrator;
import org.apache.commons.math3.analysis.integration.MidPointIntegrator;
import org.apache.commons.math3.analysis.integration.RombergIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.apache.commons.math3.analysis.interpolation.*;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.fitting.HarmonicCurveFitter;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static encog.EncogTests.myFn;

public class ApachMathTests {

    @Test
    public void test1() throws InterruptedException {

        double dBegin;
        double dEnd;
        double dStep;
        int cnt;
        double k;

        dBegin = (-2.0) * Math.PI;
        dStep = 0.01;
        dEnd = 2.0 * Math.PI;

        // Collect data.
        final WeightedObservedPoints obs = new WeightedObservedPoints();

        k = 1;
        cnt = 0;
        while (true) {
            dBegin += dStep;
            if (dBegin <= dEnd) {
                obs.add(dBegin - dStep, myFn(dBegin - dStep, k, k));
                cnt++;
            } else {
                break;
            }
        }

        final PolynomialCurveFitter fitter = PolynomialCurveFitter.create(15);
        //final HarmonicCurveFitter fitter = HarmonicCurveFitter.create();
        final double[] coeff = fitter.fit(obs.toList());
        final PolynomialFunction pf = new PolynomialFunction(coeff);
        //final HarmonicOscillator pf = new HarmonicOscillator(coeff[0], coeff[1], coeff[2]);

        SumOfTheSeries sumOfTheSeries = new SumOfTheSeries(Integer.MAX_VALUE, pf, 0.00001);

        List<Double[]> lst = new ArrayList<>();

        dBegin = (-2.0) * Math.PI;
        dStep = 0.01;
        dEnd = 4.0 * Math.PI;

        k = 1;
        cnt = 0;
        while (true) {
            dBegin += dStep;
            if (dBegin <= dEnd) {
                double x = dBegin - dStep;
                lst.add(new Double[]{x, pf.value(x), myFn(x, k, k), sumOfTheSeries.value(x)});
                cnt++;
            } else {
                break;
            }
        }

        new ShapeTest(lst);
        Thread.sleep(1000 * 60);

    }

    @Test
    public void test2() throws InterruptedException {

        Double[] dobj = {};
        double dBegin;
        double dEnd;
        double dStep;
        int cnt;
        double k;

        dBegin = (-2.0) * Math.PI;
        dStep = 0.01;
        dEnd = 2.0 * Math.PI;

        // Collect data.
        final WeightedObservedPoints obs = new WeightedObservedPoints();
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();

        k = 1;
        cnt = 0;
        while (true) {
            dBegin += dStep;
            if (dBegin <= dEnd) {
                x.add(dBegin - dStep);
                y.add(myFn(dBegin - dStep, k, k));
                cnt++;
            } else {
                break;
            }
        }

        UnivariateInterpolator interpolator = new SplineInterpolator();
        //UnivariateInterpolator interpolator = new AkimaSplineInterpolator();
        //UnivariateInterpolator interpolator = new DividedDifferenceInterpolator();
        //UnivariateInterpolator interpolator = new LinearInterpolator();
        //UnivariateInterpolator interpolator = new LoessInterpolator();
        //UnivariateInterpolator interpolator = new NevilleInterpolator();
        //UnivariateInterpolator interpolator = new UnivariatePeriodicInterpolator();
        double[] targetX = new double[x.size()];
        double[] targetY = new double[y.size()];
        for(int i = 0 ; i < x.size(); i++) {
            targetX[i] = x.get(i);
            targetY[i] = y.get(i);
        }
        UnivariateFunction pf = interpolator.interpolate(targetX, targetY);



        List<Double[]> l = new ArrayList<>();

        dBegin = (-2.0) * Math.PI;
        dStep = 0.01;
        dEnd = 4.0 * Math.PI;

        k = 1;
        cnt = 0;
        while (true) {
            dBegin += dStep;
            if (dBegin <= dEnd) {
                try {
                    l.add(new Double[]{dBegin - dStep, pf.value(dBegin - dStep), myFn(dBegin - dStep, k, k)});
                } catch(Exception e) {
                    l.add(new Double[]{dBegin - dStep, 0.0, myFn(dBegin - dStep, k, k)});
                }
                cnt++;
            } else {
                break;
            }
        }

        new ShapeTest(l);
        Thread.sleep(1000 * 60);

    }

    private static double theSumOfTheSeries(int series, UnivariateFunction pf, double x, double accuracy) {
        //UnivariateIntegrator integrator = new RombergIntegrator();
        UnivariateIntegrator integrator = new MidPointIntegrator(0.1, 0.1, 8, 64);
        double l = 2.0 * Math.PI;
        double a0 = (1/l) * integrator.integrate(Integer.MAX_VALUE, pf, -l, l);
        int n = 1;
        double theSum = a0/2;
        double inc;
        while (true) {
            AnFunction an1f = new AnFunction(pf, n, l);
            double an = (1 / l) * integrator.integrate(Integer.MAX_VALUE, an1f, -l, l);
            BnFunction bn1f = new BnFunction(pf, n, l);
            double bn = (1 / l) * integrator.integrate(Integer.MAX_VALUE, bn1f, -l, l);
            inc = an * Math.cos((Math.PI * n * x)/l) + bn * Math.sin((Math.PI * n * x)/l);
            theSum += inc;
            if (Math.abs(inc) < accuracy || n > series) {
                break;
            }
            n++;
        }
        return theSum;
    }

    public static class SumOfTheSeries implements UnivariateFunction {

        int series;
        UnivariateFunction pf;
        double accuracy;

        public SumOfTheSeries(int series, UnivariateFunction pf, double accuracy) {
            super();
            this.series = series;
            this.pf = pf;
            this.accuracy = accuracy;
        }

        @Override
        public double value(double x) {
            return theSumOfTheSeries(series, pf, x, accuracy);
        }

    }

    public static class AnFunction implements UnivariateFunction {

        UnivariateFunction baseFunc;
        double n;
        double l;

        public AnFunction(UnivariateFunction baseFunc, double n, double l) {
            super();
            this.baseFunc = baseFunc;
            this.n = n;
            this.l = l;
        }

        @Override
        public double value(double x) {
            return baseFunc.value(x) * Math.cos((Math.PI * n * x)/l);
        }
    }

    public static class BnFunction implements UnivariateFunction {

        UnivariateFunction baseFunc;
        double n;
        double l;

        public BnFunction(UnivariateFunction baseFunc, double n, double l) {
            super();
            this.baseFunc = baseFunc;
            this.n = n;
            this.l = l;
        }

        @Override
        public double value(double x) {
            return baseFunc.value(x) * Math.sin((Math.PI * n * x)/l);
        }
    }


}
