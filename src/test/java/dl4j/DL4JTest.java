package dl4j;

import apache.math.ApachMathTests;
import encog.ShapeTest;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
import org.deeplearning4j.nn.conf.BackpropType;
import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.LSTM;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.junit.Test;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.cpu.nativecpu.buffer.DoubleBuffer;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.DataSetPreProcessor;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.nativeblas.Nd4jCpu;
import  org.nd4j.linalg.cpu.nativecpu.NDArray;

import java.util.ArrayList;
import java.util.List;

import static encog.EncogTests.myFn;

public class DL4JTest {

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
        //final WeightedObservedPoints obs = new WeightedObservedPoints();

        k = 1;
        cnt = 0;
        while (true) {
            dBegin += dStep;
            if (dBegin <= dEnd) {
                //obs.add(dBegin - dStep, myFn(dBegin - dStep, k, k));
                cnt++;
            } else {
                break;
            }
        }



        //Set up network configuration:
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(12345)
                .l2(0.0001)
                .weightInit(WeightInit.XAVIER)
                .updater(new Adam(0.005))
                .gradientNormalizationThreshold(0.5)
                .list()
                .layer(0, new LSTM.Builder().activation(Activation.TANH).nIn(1).nOut(10).build())
                .layer(1, new LSTM.Builder().activation(Activation.TANH).nIn(10).nOut(20).build())
                .layer(2, new LSTM.Builder().activation(Activation.TANH).nIn(20).nOut(10).build())
                .layer(3, new OutputLayer.Builder(LossFunctions.LossFunction.KL_DIVERGENCE)
                        .activation(Activation.TANH).nIn(10).nOut(1).build())
                .build();


        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.setListeners(new ScoreIterationListener(20));

        double[] qq = {0.1, 0.2};
        int[] ii = {0, 0};
        model.fit(new NDArray(new DoubleBuffer(qq)), ii);

        double[] qq1 = {0.3, 0.4};
        INDArray output = model.output(new NDArray(new DoubleBuffer(qq1)));

        System.out.println(output.getDouble(0L));
        System.out.println(output.getDouble(1L));

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
                lst.add(new Double[]{x, 0.0, myFn(x, k, k)});
                cnt++;
            } else {
                break;
            }
        }

        new ShapeTest(lst);
        Thread.sleep(1000 * 60);

    }

}
