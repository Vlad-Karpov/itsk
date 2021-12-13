package apache.math;

import encog.ShapeTest;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.FiniteDifferencesDifferentiator;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.analysis.function.HarmonicOscillator;
import org.apache.commons.math3.analysis.integration.MidPointIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.apache.commons.math3.analysis.interpolation.*;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.HarmonicCurveFitter;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.fitting.leastsquares.*;
import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.util.Pair;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.Test;

import javax.swing.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static encog.EncogTests.myFn;

public class ApachMathTests {


    @Test
    public void test0() throws InterruptedException {

        Instant now = Instant.now();
        Instant end = now.plus(Duration.ofDays(1));
        double b = now.toEpochMilli() / 1000.00;
        double e = end.toEpochMilli() / 1000.00;
        double s = 120.00;

        int l = (int) ((e - b) / s);
        double[] rawX = new double[l];
        double[] rawY = new double[l];
        double[] rawX90 = new double[90];
        double[] rawY90 = new double[90];
        double[] predictX = new double[l + 90];
        double[] predictY = new double[l + 90];
        int cnt = 0;
        for (double x = b; x < e; x += s) {
            predictX[cnt] = x;
            double y = loadFunc(x);
            rawX[cnt] = x;
            rawY[cnt] = y;
            cnt++;
            if (cnt % 90 == 0) {
                for (int i = 90; i > 0; i--) {
                    rawX90[90 - i] = rawX[cnt - i];
                    rawY90[90 - i] = rawY[cnt - i];
                }
                UnivariateInterpolator interpolator = new LoessInterpolator();
                final UnivariateFunction interpolatorS = interpolator.interpolate(rawX90, rawY90);
                WeightedObservedPoints obs = new WeightedObservedPoints();
                for (int i = 0; i < 90; i++) {
                    obs.add(rawX90[i], interpolatorS.value(rawX90[i]));
                }
                PolynomialCurveFitter fitter = PolynomialCurveFitter.create(3);
                double[] coeff = fitter.fit(obs.toList());
                //double[] coeff = fit(2, rawX90, rawY90);
                PolynomialFunction pf = new PolynomialFunction(coeff);
                for (int i = 0; i < 90; i++) {
                    predictX[cnt + i] = x + s * i;
                    predictY[cnt + i] = pf.value(x + s * i);
                }
            }
        }

        assert cnt == l;

//        final WeightedObservedPoints obs = new WeightedObservedPoints();
//        cnt = 0;
//        for (double x = b; x < e; x += s) {
//            //double y = loadFunc(x);
//            obs.add(rawX[cnt], rawY[cnt]);
//            cnt++;
//        }
//        RealVector rvx = new ArrayRealVector(cnt);
//        RealVector rvy = new ArrayRealVector(cnt);
//        cnt = 0;
//        for (double x = b; x < e; x += s) {
//            double y = loadFunc(x);
//            rvx.setEntry(cnt, x);
//            rvy.setEntry(cnt, y);
//            cnt++;
//        }

//        PolynomialCurveFitter fitter;
//        double[] coeff;
//        int degree = 14;
//        do {
//            try {
//                fitter = PolynomialCurveFitter.create(degree);
//                coeff = fitter.fit(obs.toList());
//                break;
//            } catch (Exception exception) {
//                System.out.println(exception.getMessage());
//                if (degree == 2) throw new RuntimeException("Ops!");
//                degree--;
//                System.out.println("degree = " + degree);
//            }
//        } while (true);

        //double[] coeff = fit(3, rawX, rawY);
        //final PolynomialFunction pf = new PolynomialFunction(coeff);

        UnivariateInterpolator interpolator = new LoessInterpolator(); //new SplineInterpolator();
        final UnivariateFunction pf = interpolator.interpolate(rawX, rawY);

        FiniteDifferencesDifferentiator differentiator = new FiniteDifferencesDifferentiator(5, 0.01);
        UnivariateDifferentiableFunction completeF  = differentiator.differentiate(pf);


//        UnivariateInterpolator interpolator = new LoessInterpolator();
//        UnivariateFunction pf = interpolator.interpolate(rawX, rawY);


//
//        RealVector vStart = new ArrayRealVector(new double[] {1.00, 2.00, 3.00, 4.00});
//        LeastSquaresProblem prb = LeastSquaresFactory.create(new modelFunc(), rv, vStart, null, 100, 100000);

//        LeastSquaresProblem prb = new LeastSquaresBuilder().
//                start(new double[]{
//                        100.00,
//                        0.00007,
//                        0.00001,
//                        100.00}).
//                model(new modelFunc(rvx, rvy)).
//                target(rvy).
//                checker(new EvaluationRmsChecker(0.0000000001, 0.0000000001)).
//                lazyEvaluation(false).
//                maxEvaluations(10000).
//                maxIterations(1000000).
//                build();


//        LeastSquaresOptimizer optimizer = new GaussNewtonOptimizer().
//                withDecomposition(GaussNewtonOptimizer.Decomposition.QR);
//        LeastSquaresOptimizer.Optimum optimum = optimizer.optimize(prb);
//        double q0 = optimum.getPoint().getEntry(0);
//        double q1 = optimum.getPoint().getEntry(1);
//        double q2 = optimum.getPoint().getEntry(2);
//        double q3 = optimum.getPoint().getEntry(3);

        final XYSeries seriaSource = new XYSeries("a*sin(bx+c)+d");
        final XYSeries seriaFitRaw = new XYSeries("fit raw Y");
        final XYSeries derivativFitRaw = new XYSeries("deriv raw Y");
//        final XYSeries seriaLS = new XYSeries("LS");
        final XYSeries s90 = new XYSeries("s90");
        cnt = 0;
        for (double x = b; x < e; x += s) {
            seriaSource.add(rawX[cnt], rawY[cnt]);
//            seriaLS.add(x, loadFunc1(x));
            seriaFitRaw.add(x, pf.value(x));
//            if (x > b + s * 3 && x < e - s * 3) {
//                derivativFitRaw.add(x, completeF.value(new DerivativeStructure(1, 1, 0, x)).getPartialDerivative(1));
//            }
//            seriaLS.add(x, loadFunc(x, q0, q1, q2, q3));
            s90.add(predictX[cnt], predictY[cnt]);
            cnt++;
        }

        //plot(seriaSource, s90);
        //plot(seriaFitRaw, derivativFitRaw, s90);
        plot(seriaSource, seriaFitRaw, s90);
        //plot(derivativFitRaw);
        Thread.sleep(1000 * 120);

    }

    private static Random rnd = new Random();

    private static double whiteNoise() {
        if (rnd.nextDouble() > 0.6) {
            double sign = 1.0;
            if (rnd.nextDouble() > 0.5) {
                sign = - 1.0;
            }
            return sign * rnd.nextDouble() * 10.0;
        } else {
            return 0.0;
        }
    }

    public static double loadFunc(double x) {

        double q0 = 200.00;
        double q1 = (1.00 / (240.00 * 7 * 24 * 1.1)) * Math.PI;
        double q2 = q1;
        double q3 = 300.00;

        double q01 = 50.00;
        double q11 = (1.00 / (240.00 * 7 * 24 * 3.3)) * Math.PI;
        double q21 = q11;
        double q31 = 0.00;

        double q02 = 50.00;
        double q12 = (1.00 / (240.00 * 7 * 24 * 14.14)) * Math.PI;
        double q22 = q12;
        double q32 = 0.00;

        double z = loadFunc1(x);
        if (z < 0) {
            return 0.0;
        }

        return whiteNoise() + loadFunc(x, q0, q1, q2, q3, q01, q11, q21, q31, q02, q12, q22, q32);
    }
    private static double loadFunc1(double x) {

        double q0 = 50.00;
        double q1 = (1.00 / (240.00 * 7 * 24 * 3.3)) * Math.PI;
        double q2 = - (1.00 / 2) * Math.PI;
        double q3 = 49.90;

        return loadFunc(x, q0, q1, q2, q3);
    }

    private static double loadFunc(double... x) {
        double q0;
        double q1;
        double q2;
        double q3;
        double ix = x[0];
        double s = 0.0;
        for (int i = 0; i < (x.length - 1) / 4; i++ ) {
            q0 = x[i * 4 + 1];
            q1 = x[i * 4 + 2];
            q2 = x[i * 4 + 3];
            q3 = x[i * 4 + 4];
            s += q0 * Math.sin(q1 * ix + q2) + q3;
        }
        return s;
    }

    private static double loadFuncDQ0(double... x) {
        double q0 = x[1];
        double q1 = x[2];
        double q2 = x[3];
        double q3 = x[4];
        double ix = x[0];
        return Math.sin(q1 * ix + q2);
    }
    private static double loadFuncDQ1(double... x) {
        double q0 = x[1];
        double q1 = x[2];
        double q2 = x[3];
        double q3 = x[4];
        double ix = x[0];
        return q0 * Math.cos(q1 * ix + q2) * ix;
    }
    private static double loadFuncDQ2(double... x) {
        double q0 = x[1];
        double q1 = x[2];
        double q2 = x[3];
        double q3 = x[4];
        double ix = x[0];
        return q0 * Math.cos(q1 * ix + q2);
    }
    private static double loadFuncDQ3(double... x) {
        double q0 = x[1];
        double q1 = x[2];
        double q2 = x[3];
        double q3 = x[4];
        double ix = x[0];
        return 1.00;
    }

    private static class modelFunc implements MultivariateJacobianFunction {

        RealVector rvx;
        RealVector rvy;

        public modelFunc(RealVector rvx, RealVector rvy) {
            this.rvx = rvx;
            this.rvy = rvy;
        }

        @Override
        public Pair<RealVector, RealMatrix> value(RealVector point) {
            double q0 = point.getEntry(0);
            double q1 = point.getEntry(1);
            double q2 = point.getEntry(2);
            double q3 = point.getEntry(3);
            RealVector y = new ArrayRealVector(rvy.getDimension());
            RealMatrix m = new Array2DRowRealMatrix(rvy.getDimension(), 4);
            for (int i = 0; i < rvx.getDimension(); i++) {
                y.setEntry(i, loadFunc(rvx.getEntry(i), q0, q1, q2, q3));
                m.setEntry(i, 0, loadFuncDQ0(rvx.getEntry(i), q0, q1, q2, q3));
                m.setEntry(i, 1, loadFuncDQ1(rvx.getEntry(i), q0, q1, q2, q3));
                m.setEntry(i, 2, loadFuncDQ2(rvx.getEntry(i), q0, q1, q2, q3));
                m.setEntry(i, 3, loadFuncDQ3(rvx.getEntry(i), q0, q1, q2, q3));
            }
            return new Pair<>(y, m);
        }
    }

    //Plot the data
    public static void plot(XYSeries... s) {
        final XYSeriesCollection dataSet = new XYSeriesCollection();
        Arrays.stream(s).forEach(dataSet::addSeries);

        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Regression Example",      // chart title
                "X",                        // x axis label
                "Y", // y axis label
                dataSet,                    // data
                PlotOrientation.VERTICAL,
                true,                       // include legend
                true,                       // tooltips
                false                       // urls
        );
        final ChartPanel panel = new ChartPanel(chart);
        final JFrame f = new JFrame();
        f.add(panel);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }


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

        //final PolynomialCurveFitter fitter = PolynomialCurveFitter.create(32);
        final HarmonicCurveFitter fitter = HarmonicCurveFitter.create();
        final double[] coeff = fitter.fit(obs.toList());
        //final PolynomialFunction pf = new PolynomialFunction(coeff);
        final HarmonicOscillator pf = new HarmonicOscillator(coeff[0], coeff[1], coeff[2]);

        SumOfTheSeries sumOfTheSeries = new SumOfTheSeries(10, pf);

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
                //lst.add(new Double[]{x, pf.value(x), myFn(x, k, k), sumOfTheSeries.value(x)});
                lst.add(new Double[]{x, pf.value(x), myFn(x, k, k)});
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
        for (int i = 0; i < x.size(); i++) {
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
                } catch (Exception e) {
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

    private static double theSumOfTheSeries(int series, UnivariateFunction pf, double x) {
        //UnivariateIntegrator integrator = new RombergIntegrator();
        UnivariateIntegrator integrator = new MidPointIntegrator(0.001, 0.001, 11, 64);
        //UnivariateIntegrator integrator = new IterativeLegendreGaussIntegrator(10, 0.1, 0.1, 16, 64);
        double l = 2.0 * Math.PI;
        double a0 = (1 / l) * integrator.integrate(Integer.MAX_VALUE, pf, -l, l);
        int n = 1;
        double theSum = a0 / 2;
        double inc;
        while (true) {
            AnFunction an1f = new AnFunction(pf, n, l);
            double an = (1 / l) * integrator.integrate(Integer.MAX_VALUE, an1f, -l, l);
            BnFunction bn1f = new BnFunction(pf, n, l);
            double bn = (1 / l) * integrator.integrate(Integer.MAX_VALUE, bn1f, -l, l);
            inc = an * Math.cos((Math.PI * n * x) / l) + bn * Math.sin((Math.PI * n * x) / l);
            theSum += inc;
            if (n > series) {
                break;
            }
            n++;
        }
        return theSum;
    }

    public static class SumOfTheSeries implements UnivariateFunction {

        int series;
        UnivariateFunction pf;

        public SumOfTheSeries(int series, UnivariateFunction pf) {
            super();
            this.series = series;
            this.pf = pf;
        }

        @Override
        public double value(double x) {
            return theSumOfTheSeries(series, pf, x);
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
            return baseFunc.value(x) * Math.cos((Math.PI * n * x) / l);
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
            return baseFunc.value(x) * Math.sin((Math.PI * n * x) / l);
        }
    }

    public double[] fit(int m, double[] x, double[] y) {
        int n = x.length;
        double[][] mRow = new double[m + 1][m + 1];
        double[] vRow = new double[m + 1];
        for (int k = 0; k <= m; k++) {
            for (int j = m; j >= 0; j--) {
                double s = 0.0;
                for (double v : x) {
                    s += Math.pow(v, j + k);
                }
                mRow[k][j] = s;
            }
            double s = 0.0;
            for (int i = 0; i < n; i++) {
                s += Math.pow(x[i], k) * y[i];
            }
            vRow[k] = s;
        }
        RealMatrix rm = new Array2DRowRealMatrix(mRow, false);
        RealVector rv = new ArrayRealVector(vRow, false);
        DecompositionSolver solver = new LUDecomposition(rm).getSolver();
        //DecompositionSolver solver = new QRDecomposition(rm).getSolver();
        //DecompositionSolver solver = new EigenDecomposition(rm).getSolver();
        //DecompositionSolver solver = new  SingularValueDecomposition(rm).getSolver();
        //DecompositionSolver solver = new  CholeskyDecomposition(rm).getSolver();
        RealVector sv = solver.solve(rv);
        return sv.toArray();
    }

}
