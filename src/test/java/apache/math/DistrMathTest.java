package apache.math;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.jfree.data.xy.XYSeries;
import org.junit.Test;
import java.time.Duration;
import java.time.Instant;
import org.apache.commons.math3.distribution.fitting.MultivariateNormalMixtureExpectationMaximization;
import static apache.math.ApachMathTests.loadFunc;
import static apache.math.ApachMathTests.plot;

public class DistrMathTest {

    @Test
    public void test0() throws InterruptedException {

        Instant now = Instant.now();
        Instant end = now.plus(Duration.ofDays(7));
        double b = now.toEpochMilli() / 1000.00;
        double e = end.toEpochMilli() / 1000.00;
        double s = 120.00;

        int l = (int) ((e - b) / s);
        double[] rawX = new double[l];
        double[] rawY = new double[l];
        int cnt = 0;
        for (double x = b; x < e; x += s) {
            double y = loadFunc(x);
            rawX[cnt] = x;
            rawY[cnt] = y;
            cnt++;
        }

        final XYSeries seriaSource = new XYSeries("a*sin(bx+c)+d");
        cnt = 0;
        for (double x = b; x < e; x += s) {
            seriaSource.add(rawX[cnt], rawY[cnt]);
            cnt++;
        }

        plot(seriaSource);
        Thread.sleep(1000 * 120);

    }

    @Test
    public void test1() throws InterruptedException {
        UniformRealDistribution q1 = new UniformRealDistribution(-10,  10);
        UniformRealDistribution q0 = new UniformRealDistribution(-10,  10);
    }

    private RealDistribution fx(Double x, RealDistribution... q) {
        return new NormalDistribution(0, 0); //q[0] * x + q[1];
    }

}
