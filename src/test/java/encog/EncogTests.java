package encog;

import org.encog.ConsoleStatusReportable;
import org.encog.Encog;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.MLRegression;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.data.versatile.NormalizationHelper;
import org.encog.ml.data.versatile.VersatileMLDataSet;
import org.encog.ml.data.versatile.columns.ColumnDefinition;
import org.encog.ml.data.versatile.columns.ColumnType;
import org.encog.ml.data.versatile.sources.CSVDataSource;
import org.encog.ml.data.versatile.sources.VersatileDataSource;
import org.encog.ml.factory.MLMethodFactory;
import org.encog.ml.model.EncogModel;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.util.csv.CSVFormat;
import org.encog.util.csv.ReadCSV;
import org.encog.util.simple.EncogUtility;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EncogTests {

    @Test
    public void test1() throws URISyntaxException {

        File irisFile;
        URL resource = getClass().getClassLoader().getResource("iris.data");
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            irisFile = new File(resource.toURI());
        }

        VersatileDataSource versatileDataSourcesource = new CSVDataSource(
                irisFile, false,
                CSVFormat.DECIMAL_POINT);
        VersatileMLDataSet data = new VersatileMLDataSet(versatileDataSourcesource);
        data.defineSourceColumn(
                "sepallength", 0, ColumnType.continuous);
        data.defineSourceColumn(
                "sepal−width", 1, ColumnType.continuous);
        data.defineSourceColumn(
                "petal−length", 2, ColumnType.continuous);
        data.defineSourceColumn(
                "petal−width", 3, ColumnType.continuous);
        ColumnDefinition outputColumn = data.defineSourceColumn(
                "species", 4, ColumnType.nominal);
        data.analyze();

        data.defineSingleOutputOthersInput(outputColumn);
        EncogModel model = new EncogModel(data);
        model.selectMethod(data, MLMethodFactory.TYPE_FEEDFORWARD);
        model.setReport(new ConsoleStatusReportable());
        data.normalize();

        model.holdBackValidation(0.3, true, 1001);
        model.selectTrainingType(data);
        MLRegression bestMethod = (MLRegression) model.crossvalidate(5,
                true);


        System.out.println("Trainingerror: "
                + EncogUtility.calculateRegressionError(bestMethod,
                model.getTrainingDataset()));
        System.out.println("Validationerror: "
                + EncogUtility.calculateRegressionError(bestMethod,
                model.getValidationDataset()));
        NormalizationHelper helper = data.getNormHelper();
        System.out.println(helper.toString());
        System.out.println("Finalmodel : " + bestMethod);

        ReadCSV csv = new ReadCSV(
                irisFile, false, CSVFormat.DECIMAL_POINT);


        String[] line = new String[4];
        MLData input = helper.allocateInputVector();
        while (csv.next()) {
            StringBuilder result = new StringBuilder();
            line[0] = csv.get(0);
            line[1] = csv.get(1);
            line[2] = csv.get(2);
            line[3] = csv.get(3);
            String correct = csv.get(4);
            helper.normalizeInputVector(line, input.getData(), false);
            MLData output = bestMethod.compute(input);
            String irisChosen =
                    helper.denormalizeOutputVectorToString(output)[0];
            result.append(Arrays.toString(line));
            result.append(" −> predicted : ");
            result.append(irisChosen);
            result.append(" (correct : ");
            result.append(correct);
            result.append(" ) ");
            System.out.println(result.toString());
        }
        Encog.getInstance().shutdown();

    }


    @Test
    public void test2() throws InterruptedException {

        VersatileMLDataSet data = new VersatileMLDataSet(ds);
        data.defineSourceColumn(
                "X", 0, ColumnType.continuous);
        ColumnDefinition outputColumn = data.defineSourceColumn(
                "Y", 1, ColumnType.continuous);
        data.analyze();


        data.defineSingleOutputOthersInput(outputColumn);
        EncogModel model = new EncogModel(data);
        model.selectMethod(data, MLMethodFactory.TYPE_FEEDFORWARD);
        model.setReport(new ConsoleStatusReportable());
        data.normalize();

        model.holdBackValidation(0.1, true, 1001);
        model.selectTrainingType(data);
        MLRegression bestMethod = (MLRegression) model.crossvalidate(5,
                true);


        System.out.println("Trainingerror: "
                + EncogUtility.calculateRegressionError(bestMethod,
                model.getTrainingDataset()));
        System.out.println("Validationerror: "
                + EncogUtility.calculateRegressionError(bestMethod,
                model.getValidationDataset()));
        NormalizationHelper helper = data.getNormHelper();
        System.out.println(helper.toString());
        System.out.println("Finalmodel : " + bestMethod);

        List<Double[]> l = new ArrayList<>();
        double dBegin = (-2.0) * Math.PI;
        double dStep = 0.01;
        String[] line;
        MLData input = helper.allocateInputVector();
        ds.rewind();
        line = ds.readLine();
        while (line != null) {
            StringBuilder result = new StringBuilder();
            String correct = line[1];
            helper.normalizeInputVector(line, input.getData(), false);
            MLData output = bestMethod.compute(input);
            String irisChosen =
                    helper.denormalizeOutputVectorToString(output)[0];
            result.append(Arrays.toString(line));
            result.append(" −> predicted : ");
            result.append(irisChosen);
            result.append(" (correct : ");
            result.append(correct);
            result.append(" ) ");
            System.out.println(result);
            line = ds.readLine();
            l.add(new Double[]{dBegin, output.getData(0), myFn(dBegin, 1, 1)});
            dBegin = dBegin + dStep;
        }
        Encog.getInstance().shutdown();

        new ShapeTest(l);
        Thread.sleep(1000 * 30);


    }


    @Test
    public void test3() throws InterruptedException {

        double dBegin = (-2.0) * Math.PI;
        double dStep = 0.01;

        int cnt = 0;
        while (true) {
            dBegin += dStep;
            if (dBegin <= 2.0 * Math.PI) {
                cnt++;
            } else {
                break;
            }
        }

        double[][] INPUT = new double[cnt][1];
        double[][] IDEAL = new double[cnt][1];

        dBegin = (-2.0) * Math.PI;
        dStep = 0.01;

        cnt = 0;
        while (true) {

            dBegin += dStep;
            if (dBegin <= 2.0 * Math.PI) {
                INPUT[cnt][0] = dBegin - dStep;
                IDEAL[cnt][0] = myFn(INPUT[cnt][0], 1, 1);
                cnt++;
            } else {
                break;
            }
        }


        // create a neural network, without using a factory
        BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(null, true, 1));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 5));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 10));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 20));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 10));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 5));
        network.addLayer(new BasicLayer(new ActivationTANH(), false, 1));
        network.getStructure().finalizeStructure();
        network.reset();

        MLDataSet trainingSet = new BasicMLDataSet(INPUT, IDEAL);

        // train the neural network
        final ResilientPropagation train = new ResilientPropagation(network, trainingSet);

        int epoch = 1;

        double lastError;

        do {
            lastError = train.getError();
            train.iteration();
            System.out.println("Epoch #" + epoch + " Error:" + train.getError());
            epoch++;
        } while ((train.getError() > 0.001) && (Math.abs(lastError - train.getError()) > 0.000000001));
        train.finishTraining();


        // test the neural network
        System.out.println("Neural Network Results:");
        for (MLDataPair pair : trainingSet) {
            final MLData output = network.compute(pair.getInput());
            System.out.println(pair.getInput().getData(0)
                    + ", actual=" + output.getData(0) + ",ideal=" + pair.getIdeal().getData(0));
        }

        MLDataPair pair = new BasicMLDataPair(new BasicMLData(new double[]{0.25}));
        MLData output = network.compute(pair.getInput());
        System.out.println(pair.getInput().getData(0)
                + ", actual=" + output.getData(0) + ",ideal=");

        pair = new BasicMLDataPair(new BasicMLData(new double[]{0.6}));
        output = network.compute(pair.getInput());
        System.out.println(pair.getInput().getData(0)
                + ", actual=" + output.getData(0) + ",ideal=");

        System.out.println("<----------------->");

        List<Double[]> l = new ArrayList<>();
        dBegin = (-2.0) * Math.PI;
        dStep = 0.015;
        cnt = 0;
        while (true) {
            dBegin += dStep;
            if (dBegin <= 4.0 * Math.PI) {
                pair = new BasicMLDataPair(new BasicMLData(new double[]{dBegin - dStep}));
                output = network.compute(pair.getInput());
                System.out.println(pair.getInput().getData(0)
                        + ", actual=" + output.getData(0) + ",ideal=" + String.format("%1.14f", myFn(dBegin - dStep, 1, 1)));
                l.add(new Double[]{dBegin - dStep, output.getData(0), myFn(dBegin - dStep, 1, 1)});
                cnt++;
            } else {
                break;
            }
        }


        Encog.getInstance().shutdown();

        new ShapeTest(l);
        Thread.sleep(1000 * 60);

    }

    @Test
    public void test3_1() throws InterruptedException {

        double dBegin = (-2.0) * Math.PI;
        double dStep = 0.01;

        int cnt = 0;
        while (true) {
            dBegin += dStep;
            if (dBegin <= 2.0 * Math.PI) {
                cnt++;
            } else {
                break;
            }
        }

        double[] x = new double[cnt];
        double[] y = new double[cnt];

        double[][] INPUT = new double[cnt - 10][10];
        double[][] IDEAL = new double[cnt - 10][1];

        dBegin = (-2.0) * Math.PI;
        dStep = 0.01;

        cnt = 0;
        while (true) {
            dBegin += dStep;
            if (dBegin <= 2.0 * Math.PI) {
                x[cnt] = dBegin - dStep;
                y[cnt] = myFn(dBegin - dStep, 1, 1);
                cnt++;
            } else {
                break;
            }
        }

        for (int j = 0; j < cnt - 10; j++) {
            System.arraycopy(y, j, INPUT[j], 0, 10);
            IDEAL[j][0] = y[j + 10];
        }

        // create a neural network, without using a factory
        BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 10));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 5));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 10));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 20));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 10));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 5));
        network.addLayer(new BasicLayer(new ActivationTANH(), false, 1));
        network.getStructure().finalizeStructure();
        network.reset();

        MLDataSet trainingSet = new BasicMLDataSet(INPUT, IDEAL);
        final ResilientPropagation train = new ResilientPropagation(network, trainingSet);
        int epoch = 1;
        double lastError;
        do {
            lastError = train.getError();
            train.iteration();
            System.out.println("Epoch #" + epoch + " Error:" + train.getError());
            epoch++;
        } while ((train.getError() > 0.001) && (Math.abs(lastError - train.getError()) > 0.000000001));
        train.finishTraining();


        List<Double[]> l = new ArrayList<>();

        for (int j = 0; j < 10; j++) {
            INPUT[0][j] = y[cnt - 10 + j];
        }


        dBegin = (-2.0) * Math.PI;
        dStep = 0.015;
        while (true) {
            dBegin += dStep;
            if (dBegin >= 2.0 * Math.PI) {
                network.compute(INPUT[0], IDEAL[0]);
                l.add(new Double[]{dBegin - dStep, IDEAL[0][0], myFn(dBegin - dStep, 1, 1)});
                for (int j = 0; j < 9; j++) {
                    INPUT[0][j] = INPUT[0][j + 1];
                }
                INPUT[0][9] = IDEAL[0][0];
            } else {
                l.add(new Double[]{dBegin - dStep, 0.0, myFn(dBegin - dStep, 1, 1)});
            }
            if (dBegin > 4.0 * Math.PI) {
                break;
            }
        }


        Encog.getInstance().shutdown();

        new ShapeTest(l);
        Thread.sleep(1000 * 60);

    }


    @Test
    public void test4() throws InterruptedException {

        // create a neural network, without using a factory
        BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(null, true, 2));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 10));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 15));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 20));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 15));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 10));
        network.addLayer(new BasicLayer(new ActivationTANH(), false, 1));
        network.getStructure().finalizeStructure();
        network.reset();

        double dBegin;
        double dEnd;
        double dStep;
        double[][] INPUT;
        double[][] IDEAL;
        int niter = 0;
        int cnt;
        double k;

        do {
            dBegin = (-2.0) * Math.PI;
            dStep = 0.01;
            dEnd = 4.0 * Math.PI;
            if (niter == 2) {
                dEnd = 2.0 * Math.PI;
            }

            cnt = 0;
            while (true) {
                dBegin += dStep;
                if (dBegin <= dEnd) {
                    cnt++;
                } else {
                    break;
                }
            }

            INPUT = new double[cnt][2];
            IDEAL = new double[cnt][1];

            dBegin = (-2.0) * Math.PI;
            k = 1;
            if (niter == 1) {
                k = 0.85;
            }
            if (niter == 2) {
                k = 0.7;
            }

            cnt = 0;
            while (true) {

                dBegin += dStep;
                if (dBegin <= dEnd) {
                    INPUT[cnt][0] = dBegin - dStep;
                    INPUT[cnt][1] = niter;
                    IDEAL[cnt][0] = myFn(INPUT[cnt][0], k, k);
                    cnt++;
                } else {
                    break;
                }
            }

            MLDataSet trainingSet = new BasicMLDataSet(INPUT, IDEAL);

            // train the neural network
            final ResilientPropagation train = new ResilientPropagation(network, trainingSet);
            int epoch = 1;
            double lastError;
            do {
                lastError = train.getError();
                train.iteration();
                System.out.println("Epoch #" + epoch + " Error:" + train.getError());
                epoch++;
            } while ((train.getError() > 0.0001) && (Math.abs(lastError - train.getError()) > 0.000000001));
            train.finishTraining();

            // test the neural network
            System.out.println("Neural Network Results:");
            for (MLDataPair pair : trainingSet) {
                final MLData output = network.compute(pair.getInput());
                System.out.println(pair.getInput().getData(0)
                        + ", actual=" + output.getData(0) + ",ideal=" + pair.getIdeal().getData(0));
            }

            niter++;
        } while (niter < 3);


        MLDataPair pair;
        MLData output;

        List<Double[]> l = new ArrayList<>();
        dBegin = (-2.0) * Math.PI;
        dStep = 0.015;
        cnt = 0;
        while (true) {
            dBegin += dStep;
            if (dBegin <= 4.0 * Math.PI) {
                pair = new BasicMLDataPair(new BasicMLData(new double[]{dBegin - dStep, 2}));
                output = network.compute(pair.getInput());
                System.out.println(pair.getInput().getData(0)
                        + ", actual=" + output.getData(0) + ",ideal=" + String.format("%1.14f", myFn(dBegin - dStep, 0.7, 0.7)));
                l.add(new Double[]{dBegin - dStep, output.getData(0), myFn(dBegin - dStep, 0.7, 0.7)});
                cnt++;
            } else {
                break;
            }
        }


        Encog.getInstance().shutdown();

        new ShapeTest(l);
        Thread.sleep(1000 * 60);

    }


    static Random rmd = new Random();


    public static double myFn(double x, double a, double f) {
        //return a * Math.sin(f * x);
        //return Math.sin(x) + rmd.nextDouble() * 0.2 - rmd.nextDouble() * 0.2;
        //return 0.59 * a * Math.sin( f * x ) + rmd.nextDouble() * 0.2 - rmd.nextDouble() * 0.2;
        //return a * (Math.sin(f * x) + Math.sin(f * 2 * x))/2;
        if (x > Math.PI) {
            a = 2 * a * Math.tanh(x);
        }
        double bs = 0.59 * (a * (Math.sin(f * x) + Math.sin(f * 2 * x)) / 2);
//        if (x > Math.PI) {
//            bs = bs + Math.tanh(1 + x - Math.PI) * Math.sin(f * 4 * x);
//            //bs = bs + Math.tanh(x) * Math.sin(f * 4 * x);
//            //bs = bs * Math.tanh(1 + x - Math.PI);
//        }
        double whiteNoise = rmd.nextDouble() * 0.2 - rmd.nextDouble() * 0.2;
        //bs = Math.abs( bs ) + whiteNoise;
        bs = bs + whiteNoise;
        if (x > 0.5 * Math.PI && x < 0.6 * Math.PI) {
            bs = 0.0;
        }
        if (x >= 0.6 * Math.PI && x < 0.8 * Math.PI) {
            bs = 1.0;
        }
        return bs;
    }


    VersatileDataSource ds = new VersatileDataSource() {

        double dBegin = (-2.0) * Math.PI;
        double dStep = 0.01;

        @Override
        public String[] readLine() {
            String[] result = new String[2];
            result[0] = String.format("%14.14f", dBegin);
            result[1] = String.format("%14.14f", myFn(dBegin, 1, 1));
            dBegin += dStep;
            if (dBegin <= 2.0 * Math.PI) {
                return result;
            } else {
                return null;
            }
        }

        @Override
        public void rewind() {
            dBegin = (-2.0) * Math.PI;
        }

        @Override
        public int columnIndex(String name) {
            if (name.equalsIgnoreCase("X")) {
                return 0;
            }
            if (name.equalsIgnoreCase("Y")) {
                return 1;
            }
            throw new RuntimeException("Oops!");
        }
    };

    @Test
    public void test6() throws InterruptedException {

        int bl = 6;
        int sz = 200;

        // create a neural network, without using a factory
        BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(null, true, sz));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 10));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 15));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 20));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 15));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 10));
        network.addLayer(new BasicLayer(new ActivationTANH(), false, sz));
        network.getStructure().finalizeStructure();
        network.reset();

        MLDataPair pair;
        MLData output;

        double dBegin;
        double dStep;
        double[][] INPUT;
        double[][] INPUTX;
        double[] OUTPUT = null;
        double[][] IDEAL;
        int niter = 0;
        int cnt;
        double k;

        dBegin = (-2.0) * Math.PI;
        dStep = 0.01;

        List<Double[]> l = new ArrayList<>();


        do {

            INPUTX = new double[1][sz];
            INPUT = new double[1][sz];
            IDEAL = new double[1][sz];

            cnt = 0;
            while (cnt < sz) {
                INPUTX[0][cnt] = dBegin + dStep * (sz * niter + cnt);
                INPUT[0][cnt] = myFn(dBegin + dStep * (sz * niter + cnt), 1, 1);
                IDEAL[0][cnt] = myFn(dBegin + dStep * (sz * (niter + 1) + cnt), 1, 1);
                cnt++;
            }

            if (niter < bl - 2) {

                MLDataSet trainingSet = new BasicMLDataSet(INPUT, IDEAL);

                // train the neural network
                ResilientPropagation train = new ResilientPropagation(network, trainingSet);
                int epoch = 1;
                double lastError;
                do {
                    lastError = train.getError();
                    train.iteration();
                    System.out.println("Epoch #" + epoch + " Error:" + train.getError());
                    epoch++;
                } while ((train.getError() > 0.0001) && (Math.abs(lastError - train.getError()) > 0.000000001));
                train.finishTraining();

            }

            if (niter == 0) {
                OUTPUT = INPUT[0];
            }
            for (int i = 0; i < sz; i++) {
                l.add(new Double[]{INPUTX[0][i], OUTPUT[i], INPUT[0][i]});
            }
            ;
            pair = new BasicMLDataPair(new BasicMLData(INPUT[0]));
            output = network.compute(pair.getInput());
            OUTPUT = output.getData();

            niter++;
        } while (niter < bl);

        Encog.getInstance().shutdown();

        new ShapeTest(l);
        Thread.sleep(1000 * 60);

    }


}
