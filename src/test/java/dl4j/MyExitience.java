package dl4j;

import dl4j.mathfunctions.MathFunction;
import dl4j.mathfunctions.SinXDivXMathFunction;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.BackpropType;
import org.deeplearning4j.nn.conf.GradientNormalization;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.LSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.junit.Test;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;
import org.nd4j.linalg.learning.config.AdaGrad;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.util.Arrays;

public class MyExitience {

    @Test
    public void test0() throws InterruptedException {
        final MathFunction fn = new SinXDivXMathFunction();
        int rangeBaseCount = 3000;
        double[] firstPeriod = new double[rangeBaseCount];
        double rangeBase = 4 * Math.PI;
        double stepBase = 3 * rangeBase / rangeBaseCount;
        for (int i = 0; i < rangeBaseCount; i++) {
            firstPeriod[i] = -rangeBase + i * stepBase;
        }
        final INDArray x1 = Nd4j.create(firstPeriod);
        final INDArray y1 = fn.getFunctionValues(x1);
        MathFunctionsModel.plot(fn, x1, y1);
        Thread.sleep(120000);
    }

    @Test
    public void test1() throws InterruptedException {
        final MathFunction fn = new SinXDivXMathFunction();
        int rangeBaseCount = 100;
        double[] firstPeriod = new double[2 * rangeBaseCount];
        double[] secondPeriod = new double[2 * rangeBaseCount];
        double rangeBase = 4 * Math.PI;
        double stepBase = rangeBase / rangeBaseCount;
        for (int i = 0; i < rangeBaseCount * 2; i++) {
            firstPeriod[i] = -rangeBase + i * stepBase;
            secondPeriod[i] = -rangeBase + (i + 1) * stepBase;
        }

        final INDArray x1 = Nd4j.create(firstPeriod);
        final INDArray y1 = fn.getFunctionValues(x1);
        final INDArray x2 = Nd4j.create(secondPeriod);
        final INDArray y2 = fn.getFunctionValues(x2);

        double[][][] data_in = new double[][][]{{y1.toDoubleVector()}};
        INDArray datain = Nd4j.create(data_in);
        double[][][] data_traine = new double[][][]{{y2.toDoubleVector()}};
        INDArray dataTraine = Nd4j.create(data_traine);

        long t = System.currentTimeMillis();
        MultiLayerNetwork net = createNeuralNet();
        int epoch = 254;
        while (epoch > 0) {
            net.fit(datain, dataTraine);
            epoch--;
        }
        t = System.currentTimeMillis() - t;
        System.out.println("Time: " + (t / 1000) + " sec. and " + (t - t / 1000) + " msec.");

        INDArray out = net.rnnTimeStep(datain);

        System.out.println(out);

        out = net.rnnTimeStep(out);

        //double vl = out.getDouble(0, 0, 2 * rangeBaseCount - 1);
        //data_traine = new double[][][]{{{vl}}};
        //out = Nd4j.create(data_traine);

        double[] predictPeriod = new double[rangeBaseCount / 2];
        double[] predictValues = new double[rangeBaseCount / 2];
        for (int i = 0; i < rangeBaseCount / 2; i++) {
            predictPeriod[i] = rangeBase + i * stepBase;
            //out = net.rnnTimeStep(out);
            predictValues[i] = out.getDouble(0, 0, i);
        }

        final INDArray x3 = Nd4j.create(predictPeriod);
        final INDArray y3 = fn.getFunctionValues(x3);
        MathFunctionsModel.plot(fn, x3, y3, Nd4j.create(predictValues));

        Thread.sleep(120000);
    }

    @Test
    public void test2() throws InterruptedException {
        double[] firstPeriod = new double[]{0.01, 0.02, 0.03, 0.04, 0.05, 0.06};
        double[] firstShiftPeriod = new double[]{0.02, 0.03, 0.04, 0.05, 0.06, 0.07};

        double[] firstPeriod1 = new double[]{0.02, 0.03, 0.04, 0.05, 0.06, 0.07};
        double[] firstShiftPeriod1 = new double[]{0.03, 0.04, 0.05, 0.06, 0.07, 0.08};

        double[] predict01 = new double[]{0.07};
        double[] predict02 = new double[]{0.08};
        double[] predict03 = new double[]{0.10};

        final INDArray y0 = Nd4j.create(firstPeriod);
        final INDArray y0s = Nd4j.create(firstShiftPeriod);

        final INDArray y1 = Nd4j.create(firstPeriod1);
        final INDArray y1s = Nd4j.create(firstShiftPeriod1);

        final INDArray y01 = Nd4j.create(predict01);
        final INDArray y02 = Nd4j.create(predict02);
        final INDArray y03 = Nd4j.create(predict03);

        double[][][] data_1 = new double[][][]{{y0.toDoubleVector()}/*, {y1.toDoubleVector()}*/};
        INDArray data1 = Nd4j.create(data_1);
        double[][][] data_1s = new double[][][]{{y0s.toDoubleVector()}/*, {y1s.toDoubleVector()}*/};
        INDArray data1s = Nd4j.create(data_1s);

        double[][][] data_01 = new double[][][]{{y01.toDoubleVector()}};
        INDArray data01 = Nd4j.create(data_01);
        double[][][] data_02 = new double[][][]{{y02.toDoubleVector()}};
        INDArray data02 = Nd4j.create(data_02);
        double[][][] data_03 = new double[][][]{{y03.toDoubleVector()}};
        INDArray data03 = Nd4j.create(data_02);

        long t = System.currentTimeMillis();
        MultiLayerNetwork net = createNeuralNet();
        int epoch = 1024;
        while (epoch > 0) {
            net.fit(data1, data1s);
            epoch--;
        }
        t = System.currentTimeMillis() - t;
        System.out.println("Time: " + (t / 1000) + " sec. and " + (t - t / 1000) + " msec.");


        System.out.println("data1s");
        System.out.println(data1s);
        System.out.println("data1");
        System.out.println(data1);
        System.out.println("+++++");
        INDArray out = net.rnnTimeStep(data1);
        System.out.println(out);
        for (int i = 0; i < 10; i++) {
            out = net.rnnTimeStep(out);
            System.out.println(out);
        }

    }

    @Test
    public void test3() throws InterruptedException {
        MultiLayerNetwork net = createNeuralNet();
        double[] v = new double[]{0.01, 0.02, 0.03, 0.04, 0.05, 0.06};
        fitValues(net, v);
        extractedOut(net, v); //expect {0.07, 0.08, 0.09, 0.1, 0.11, 0.12} get {0.069, 0.079, 0.088, 0.097, 0.10, 0.11, 0.117} it`s ok
        //double[] vn = new double[]{0.05, 0.06};
        //extractedOut(net, vn); // expect {0.07, 0.08, 0.09} get {0.0299, 0.0398, 0.0496}  ???
        v = new double[]{0.07, 0.08, 0.09};  // retrain (add data to model)
        fitValues(net, v);
        extractedOut(net, v); // expect {0.1, 0.11, 0.12, 0.13} get {0.087, 0.0777, 0.067, 0.0577 }  ???
    }

    private void extractedOut(MultiLayerNetwork net, double[] v) {
        double[] p = nextValues(net, v);
        System.out.print(p[p.length-1]);
        Arrays.stream(nextValues(net, p)).forEach(e->System.out.print(", " + e));
        net.rnnClearPreviousState();
        System.out.println(" ");
    }

    public void fitValues(MultiLayerNetwork net, double... v) {
        double[] firstPeriod = Arrays.copyOf(v, v.length-1);
        INDArray y0 = Nd4j.create(firstPeriod);
        double[][][] data_1 = new double[][][]{{y0.toDoubleVector()}};
        INDArray data1 = Nd4j.create(data_1);

        double[] firstShiftPeriod = Arrays.copyOfRange(v, 1, v.length);
        final INDArray y0s = Nd4j.create(firstShiftPeriod);
        double[][][] data_1s = new double[][][]{{y0s.toDoubleVector()}};
        INDArray data1s = Nd4j.create(data_1s);

        long t = System.currentTimeMillis();
        int epoch = 1024;
        while (epoch > 0) {
            net.fit(data1, data1s);
            epoch--;
        }
        t = System.currentTimeMillis() - t;
        System.out.println("Time: " + (t / 1000) + " sec. and " + (t - t / 1000) + " msec.");
    }

    public double[] nextValues(MultiLayerNetwork net, double... v) {
        double[] firstPeriod = Arrays.copyOf(v, v.length);
        INDArray y0 = Nd4j.create(firstPeriod);
        double[][][] data_1 = new double[][][]{{y0.toDoubleVector()}};
        INDArray data1 = Nd4j.create(data_1);
        INDArray out = net.rnnTimeStep(data1);
        return out.get(NDArrayIndex.indexesFor(0L, 0L)).toDoubleVector();
    }

    public MultiLayerNetwork createNeuralNet() {
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .seed(12345)
                .weightInit(WeightInit.XAVIER)
                .updater(new AdaGrad(0.005))
                //.updater(new Nesterovs(MathFunctionsModel.learningRate, 0.9))
                .list()
                .layer(0, new LSTM.Builder()
                        .activation(Activation.TANH)
                        .nIn(1)
                        .nOut(100)
                        .gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue)
                        .gradientNormalizationThreshold(10)
                        .build())
                .layer(1, new LSTM.Builder()
                        .activation(Activation.TANH)
                        .nIn(100)
                        .nOut(100)
                        .gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue)
                        .gradientNormalizationThreshold(10)
                        .build())
                .layer(2, new LSTM.Builder()
                        .activation(Activation.TANH)
                        .nIn(100)
                        .nOut(100)
                        .gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue)
                        .gradientNormalizationThreshold(10)
                        .build())
                .layer(3, new RnnOutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.TANH)
                        .nIn(100)
                        .nOut(1)
                        .gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue)
                        .gradientNormalizationThreshold(10)
                        .build())
                .backpropType(BackpropType.TruncatedBPTT)
                .tBPTTLength(100)
                .build();
        MultiLayerNetwork net = new MultiLayerNetwork(conf);
        net.init();
        return net;
    }

}
