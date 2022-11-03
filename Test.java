import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Test {
    public static void main(String[] args) {
        ArrayList<double[]> daten = irisDatenEinlesen();
        new Netzwerk(Arrays.asList(4, 5, 5, 3), daten.subList(0, 130), daten.subList(130, 150), 100);
    }

    private static ArrayList<double[]> irisDatenEinlesen() {
        ArrayList<double[]> daten = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("iris.data"))) {
            String zeile = "";
            double[] doubleDaten;
            String[] stringDaten;
            while ((zeile = br.readLine()) != null) {
                stringDaten = zeile.split(",");
                doubleDaten = new double[stringDaten.length + 2];
                for (int i = 0; i < stringDaten.length; i++) {
                    switch (stringDaten[i]) {
                        case "" -> {
                        }
                        case "Iris-setosa" -> {
                            doubleDaten = datenNormalisieren(doubleDaten);
                            doubleDaten[i] = 1.0;
                            doubleDaten[i + 1] = 0.0;
                            doubleDaten[i + 2] = 0.0;
                        }
                        case "Iris-versicolor" -> {
                            doubleDaten = datenNormalisieren(doubleDaten);
                            doubleDaten[i] = 0.0;
                            doubleDaten[i + 1] = 1.0;
                            doubleDaten[i + 2] = 0.0;
                        }
                        case "Iris-virginica" -> {
                            doubleDaten = datenNormalisieren(doubleDaten);
                            doubleDaten[i] = 0.0;
                            doubleDaten[i + 1] = 0.0;
                            doubleDaten[i + 2] = 1.0;
                        }
                        default -> doubleDaten[i] = Double.parseDouble(stringDaten[i]);
                    }
                }
                daten.add(doubleDaten);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Collections.shuffle(daten);
        return daten;
    }

    private static double[] datenNormalisieren(double[] daten) {
        double[] sortiert = daten.clone();
        Arrays.sort(sortiert);
        double max = sortiert[sortiert.length - 1];
        double min = sortiert[0];
        double[] r = new double[daten.length];
        for (int i = 0; i < daten.length; i++) {
            r[i] = (daten[i] - min) / (max - min);
        }
        return r;
    }
}
