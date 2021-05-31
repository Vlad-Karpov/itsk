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
import org.nd4j.linalg.learning.config.AdaGrad;
import org.nd4j.linalg.lossfunctions.LossFunctions;

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

        double vl = out.getDouble(0, 0, 2 * rangeBaseCount - 1);
        data_traine = new double[][][]{{{vl}}};
        out = Nd4j.create(data_traine);

        double[] predictPeriod = new double[rangeBaseCount / 2];
        double[] predictValues = new double[rangeBaseCount / 2];
        for (int i = 0; i < rangeBaseCount / 2; i++) {
            predictPeriod[i] = rangeBase + i * stepBase;
            out = net.rnnTimeStep(out);
            predictValues[i] = out.getDouble(0, 0, 0);
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
        double[] secondPeriod = new double[]{0.07};
        double[] thirdPeriod = new double[]{0.08};

        final INDArray y1 = Nd4j.create(firstPeriod);
        final INDArray y1s = Nd4j.create(firstShiftPeriod);
        final INDArray y2 = Nd4j.create(secondPeriod);
        final INDArray y3 = Nd4j.create(thirdPeriod);

        double[][][] data_1 = new double[][][]{{y1.toDoubleVector()}};
        INDArray data1 = Nd4j.create(data_1);
        double[][][] data_1s = new double[][][]{{y1s.toDoubleVector()}};
        INDArray data1s = Nd4j.create(data_1s);
        double[][][] data_2 = new double[][][]{{y2.toDoubleVector()}};
        INDArray data2 = Nd4j.create(data_2);
        double[][][] data_3 = new double[][][]{{y3.toDoubleVector()}};
        INDArray data3 = Nd4j.create(data_3);

        long t = System.currentTimeMillis();
        MultiLayerNetwork net = createNeuralNet();
        int epoch = 1024;
        while (epoch > 0) {
            net.fit(data1, data1s);
            epoch--;
        }
        t = System.currentTimeMillis() - t;
        System.out.println("Time: " + (t / 1000) + " sec. and " + (t - t / 1000) + " msec.");

        INDArray out = net.rnnTimeStep(data1);
        System.out.println(out);

        out = data2;
        for (int i = 0; i < 10; i++) {
            out = net.rnnTimeStep(out);
            System.out.println(out);
        }

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
