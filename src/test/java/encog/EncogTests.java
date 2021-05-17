package encog;

import org.encog.ConsoleStatusReportable;
import org.encog.Encog;
import org.encog.engine.network.activation.ActivationBipolarSteepenedSigmoid;
import org.encog.engine.network.activation.ActivationSigmoid;
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

        ReadCSV csv = new ReadCSV (
                irisFile , false , CSVFormat.DECIMAL_POINT) ;


        String [] line = new String [ 4 ] ;
        MLData input = helper.allocateInputVector();
        while ( csv.next()) {
            StringBuilder result = new StringBuilder();
            line [0] = csv.get(0);
            line [1] = csv.get(1);
            line [2] = csv.get(2);
            line [3] = csv.get(3);
            String correct = csv.get(4);
            helper.normalizeInputVector(line, input.getData(), false);
            MLData output = bestMethod.compute(input);
            String irisChosen =
                    helper.denormalizeOutputVectorToString ( output ) [ 0 ] ;
            result.append ( Arrays.toString (line) ) ;
            result.append ( " −> predicted : " ) ;
            result.append ( irisChosen ) ;
            result.append ( " (correct : " ) ;
            result.append ( correct ) ;
            result.append ( " ) " ) ;
            System.out.println (result.toString()) ;
        }
        Encog.getInstance ( ).shutdown( ) ;

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
        String [] line;
        MLData input = helper.allocateInputVector();
        ds.rewind();
        line = ds.readLine();
        while ( line != null) {
            StringBuilder result = new StringBuilder();
            String correct = line[1];
            helper.normalizeInputVector(line, input.getData(), false);
            MLData output = bestMethod.compute(input);
            String irisChosen =
                    helper.denormalizeOutputVectorToString ( output ) [ 0 ] ;
            result.append ( Arrays.toString (line) ) ;
            result.append ( " −> predicted : " ) ;
            result.append ( irisChosen ) ;
            result.append ( " (correct : " ) ;
            result.append ( correct ) ;
            result.append ( " ) " ) ;
            System.out.println (result) ;
            line = ds.readLine();
            l.add(new Double[]{dBegin, output.getData(0), myFn(dBegin)});
            dBegin = dBegin + dStep;
        }
        Encog.getInstance ( ).shutdown( ) ;

        new ShapeTest(l);
        Thread.sleep(1000 * 30);


    }



    @Test
    public void test3() throws InterruptedException {

        double dBegin = (-2.0) * Math.PI;
        double dStep = 0.01;

        int cnt = 0;
        while (true) {
            dBegin+=dStep;
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

            dBegin+=dStep;
            if (dBegin <= 2.0 * Math.PI) {
                INPUT[cnt][0] = dBegin - dStep;
                IDEAL[cnt][0] = myFn(INPUT[cnt][0]);
                cnt++;
            } else {
                break;
            }
        }



        // create a neural network, without using a factory
        BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(null,true,1));
        network.addLayer(new BasicLayer(new ActivationTANH(),true,5));
        network.addLayer(new BasicLayer(new ActivationTANH(),true,10));
        network.addLayer(new BasicLayer(new ActivationTANH(),true,20));
        network.addLayer(new BasicLayer(new ActivationTANH(),true,10));
        network.addLayer(new BasicLayer(new ActivationTANH(),true,5));
        network.addLayer(new BasicLayer(new ActivationTANH(),false,1));
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
        } while( (train.getError() > 0.001) && (Math.abs(lastError - train.getError()) > 0.000000001) );
        train.finishTraining();


        // test the neural network
        System.out.println("Neural Network Results:");
        for(MLDataPair pair: trainingSet ) {
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
            dBegin+=dStep;
            if (dBegin <= 4.0 * Math.PI) {
                pair = new BasicMLDataPair(new BasicMLData(new double[]{dBegin - dStep}));
                output = network.compute(pair.getInput());
                System.out.println(pair.getInput().getData(0)
                        + ", actual=" + output.getData(0) + ",ideal=" + String.format("%1.14f", myFn(dBegin - dStep)));
                l.add(new Double[]{dBegin - dStep, output.getData(0), myFn(dBegin - dStep)});
                cnt++;
            } else {
                break;
            }
        }


        Encog.getInstance().shutdown();

        new ShapeTest(l);
        Thread.sleep(1000 * 60);

    }

    public double myFn(double x) {
        //return Math.sin(x);
        return (Math.sin(x) + Math.sin(2*x))/2;
    }

    VersatileDataSource ds = new VersatileDataSource() {

        double dBegin = (-2.0) * Math.PI;
        double dStep = 0.01;

        @Override
        public String[] readLine() {
            String[] result = new String[2];
            result[0] = String.format("%14.14f", dBegin);
            result[1] = String.format("%14.14f", myFn(dBegin));
            dBegin+=dStep;
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

}
