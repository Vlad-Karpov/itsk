package dl4j;

import dl4j.mathfunctions.MathFunction;
import dl4j.mathfunctions.SinXDivXMathFunction;
import org.deeplearning4j.datasets.iterator.INDArrayDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.GradientNormalization;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.LSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.conf.preprocessor.CnnToRnnPreProcessor;
import org.deeplearning4j.nn.conf.preprocessor.RnnToCnnPreProcessor;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.junit.Test;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;
import org.nd4j.linalg.indexing.SpecifiedIndex;
import org.nd4j.linalg.learning.config.AdaGrad;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class MyExitience {

    @Test
    public void test0() throws InterruptedException {
        final MathFunction fn = new SinXDivXMathFunction();

        //Generate the training data
        int rangeBaseCount = 3000;
        double[] firstPeriod = new double[rangeBaseCount];
        double rangeBase = 4 * Math.PI;
        double stepBase = 3 * rangeBase / rangeBaseCount;
        for (int i = 0; i < rangeBaseCount; i++) {
            firstPeriod[i] = - rangeBase + i * stepBase;
        }

        final INDArray x1 = Nd4j.create(firstPeriod);
        final INDArray y1 = fn.getFunctionValues(x1);
        MathFunctionsModel.plot(fn, x1, y1);

        Thread.sleep(120000);
    }

    @Test
    public void test1() throws InterruptedException {
        final MathFunction fn = new SinXDivXMathFunction();
        //Generate the training data
        int rangeBaseCount = 1000;
        double[] firstPeriod = new double[rangeBaseCount];
        double[] secondPeriod = new double[rangeBaseCount];
        double[] thirdPeriod = new double[rangeBaseCount];
        double[] firthPeriod = new double[rangeBaseCount];
        double rangeBase = 4 * Math.PI;
        double stepBase = rangeBase / rangeBaseCount;
        for (int i = 0; i < rangeBaseCount; i++) {
            firstPeriod[i] = - rangeBase + i * stepBase;
            secondPeriod[i] = i * stepBase;
            thirdPeriod[i] = rangeBase + i * stepBase;
            firthPeriod[i] = 2 * rangeBase + i * stepBase;
        }

        final INDArray x1 = Nd4j.create(firstPeriod);
        final INDArray y1 = fn.getFunctionValues(x1);
        final INDArray x2 = Nd4j.create(secondPeriod);
        final INDArray y2 = fn.getFunctionValues(x2);


        double[][][] data_in = new double[][][]{{y1.toDoubleVector()}};
        INDArray datain = Nd4j.create(data_in);
        double[][][] data_out = new double[][][]{{y2.toDoubleVector()}};
        INDArray dataout = Nd4j.create(data_out);

        MultiLayerNetwork net = createNeuralNet();
        int epoch = 8;
        while (epoch > 0) {
            net.fit(datain, dataout);
            epoch --;
        }

        //INDArray out = net.rnnTimeStep(dataout);
        INDArray out = net.output(dataout, true);

        final INDArray x3 = Nd4j.create(thirdPeriod);
        final INDArray y3 = fn.getFunctionValues(x3);
        MathFunctionsModel.plot(fn, x3, y3, out.get(new SpecifiedIndex(0, 0)));

        out = net.rnnTimeStep(out.dup());

        final INDArray x4 = Nd4j.create(firthPeriod);
        final INDArray y4 = fn.getFunctionValues(x4);
        MathFunctionsModel.plot(fn, x4, y4, out.get(new SpecifiedIndex(0, 0)));


        Thread.sleep(120000);
    }

    @Test
    public void test2() throws InterruptedException {
        final MathFunction fn = new SinXDivXMathFunction();
        MultiLayerNetwork net = createNeuralNet();
        for(int j = 0; j < 4; j++) {
            int rangeBaseCount = 250;
            double[] firstPeriod = new double[rangeBaseCount];
            double[] secondPeriod = new double[rangeBaseCount];
            double rangeBase = Math.PI;
            double stepBase = rangeBase / rangeBaseCount;
            for (int i = 0; i < rangeBaseCount; i++) {
                firstPeriod[i] = - 4 * Math.PI + j * rangeBase + i * stepBase;
                secondPeriod[i] = - 4 * Math.PI + (j+1) * rangeBase + i * stepBase;;
            }

            INDArray x1 = Nd4j.create(firstPeriod);
            INDArray y1 = fn.getFunctionValues(x1);
            INDArray x2 = Nd4j.create(secondPeriod);
            INDArray y2 = fn.getFunctionValues(x2);


            double[][][] data_in = new double[][][]{{y1.toDoubleVector()}};
            INDArray datain = Nd4j.create(data_in);
            double[][][] data_out = new double[][][]{{y2.toDoubleVector()}};
            INDArray dataout = Nd4j.create(data_out);

            int epoch = 8;
            while (epoch > 0) {
                net.fit(datain, dataout);
                epoch--;
            }
        }

        int rangeBaseCount = 1000;
        double[] secondPeriod = new double[rangeBaseCount];
        double[] thirdPeriod = new double[rangeBaseCount];
        double rangeBase = 4 * Math.PI;
        double stepBase = rangeBase / rangeBaseCount;
        for (int i = 0; i < rangeBaseCount; i++) {
            secondPeriod[i] = i * stepBase;
            thirdPeriod[i] = rangeBase + i * stepBase;
        }
        INDArray x2 = Nd4j.create(secondPeriod);
        INDArray y2 = fn.getFunctionValues(x2);
        double[][][] data_out = new double[][][]{{y2.toDoubleVector()}};
        INDArray dataout = Nd4j.create(data_out);
        INDArray out = net.rnnTimeStep(dataout);
        final INDArray x3 = Nd4j.create(thirdPeriod);
        final INDArray y3 = fn.getFunctionValues(x3);
        MathFunctionsModel.plot(fn, x3, y3, out.get(new SpecifiedIndex(0, 0)));
        Thread.sleep(120000);
    }



    public MultiLayerNetwork createNeuralNet() {
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .seed(12345)
                .weightInit(WeightInit.XAVIER)
                //.updater(new AdaGrad(0.005))
                .updater(new Nesterovs(MathFunctionsModel.learningRate, 0.9))
                .list()
//                .layer(0, new ConvolutionLayer.Builder(kernelSize, kernelSize)
//                        .nIn(1) //1 channel
//                        .nOut(7)
//                        .stride(2, 2)
//                        .activation(Activation.RELU)
//                        .build())
                .layer(0, new LSTM.Builder()
                        .activation(Activation.TANH)
                        .nIn(1)
                        .nOut(100)
                        //.gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue)
                        //.gradientNormalizationThreshold(10)
                        .build())
                .layer(1, new LSTM.Builder()
                        .activation(Activation.TANH)
                        .nIn(100)
                        .nOut(100)
                        //.gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue)
                        //.gradientNormalizationThreshold(10)
                        .build())
                .layer(2, new LSTM.Builder()
                        .activation(Activation.TANH)
                        .nIn(100)
                        .nOut(100)
                        //.gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue)
                        //.gradientNormalizationThreshold(10)
                        .build())
                .layer(3, new RnnOutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(100)
                        .nOut(1)
                        //.gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue)
                        //.gradientNormalizationThreshold(10)
                        .build())
                //.inputPreProcessor(0, new RnnToCnnPreProcessor(V_HEIGHT, V_WIDTH, numChannels))
                //.inputPreProcessor(1, new CnnToRnnPreProcessor(6, 2, 7 ))
                .build();

        MultiLayerNetwork net = new MultiLayerNetwork(conf);
        net.init();
        return net;
    }

}
