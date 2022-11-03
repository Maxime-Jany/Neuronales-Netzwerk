import java.util.Arrays;
import java.util.List;

public class Netzwerk {
    private final Schicht[] schichten;
    private final List<double[]> trainingsdaten;
    private final List<double[]> testdaten;

    /**
     * @param anzNeuronen    Anzahl der Neuronen der Schichten
     * @param trainingsdaten Daten zum Trainieren des Netzes
     * @param testdaten      Daten zum Testen, ob das Training erfolgreich war und das Netz funktioniert
     * @param anzTrainings   Wie oft mit den 'trainingsdaten' trainiert werden soll
     */
    public Netzwerk(List<Integer> anzNeuronen, List<double[]> trainingsdaten, List<double[]> testdaten, int anzTrainings) {
        this.trainingsdaten = trainingsdaten;
        this.testdaten = testdaten;

        schichten = new Schicht[anzNeuronen.size()];
        schichten[0] = new Eingabeschicht();
        for (int i = 1; i < anzNeuronen.size(); i++) {
            schichten[i] = new Schicht(anzNeuronen.get(i), anzNeuronen.get(i - 1));
        }

        for (int i = 0; i < anzTrainings; i++) {
            this.trainieren();
        }
        this.testen();
    }

    private void trainieren() {
        for (double[] eingaben : trainingsdaten) { // backpropagation
            double[] lsg = this.getLoesung(eingaben, this.getAusgaben(eingaben).length);

            // backpropagation
            double[] deltas = schichten[schichten.length - 1].getDeltasAusgabeschicht(lsg);
            for (int i = schichten.length - 2; i >= 0; i--) {
                deltas = schichten[i].getDeltas(deltas, schichten[i + 1].getGewichte());
            }

            for (int i = 1; i < schichten.length; i++) {
                schichten[i].gradientenabstiegsverfahren(schichten[i - 1].getTatsaechlicheAusgaben());
            }
        }
    }

    private void testen() {
        double richtig = 0; // double, damit unten bei Math.round() double und nicht int rauskommt
        for (double[] eingaben : testdaten) {
            double[] ausgaben = this.getAusgaben(eingaben);
            double[] lsg = this.getLoesung(eingaben, ausgaben.length);

            if ((this.getIndexVonMaxWert(lsg) == this.getIndexVonMaxWert(ausgaben))) {
                richtig += 1;
            }
            for (int i = 0; i < ausgaben.length; i++) {
                ausgaben[i] = Math.round(ausgaben[i] * 100.0) / 100.0;
            }
            System.out.println((this.getIndexVonMaxWert(lsg) == this.getIndexVonMaxWert(ausgaben)) + ": " + Arrays.toString(lsg) + " -> " + Arrays.toString(ausgaben));
        }
        System.out.println();
        System.out.println(Math.round(richtig / testdaten.size() * 100.0) + " Prozent richtig!");
    }

    private double[] getAusgaben(double[] eingaben) {
        for (Schicht schicht : schichten) {
            eingaben = schicht.getAusgaben(eingaben);
        }
        return eingaben;
    }

    private double[] getLoesung(double[] eingaben, int anzAusgaben) {
        double[] lsg = new double[anzAusgaben];
        for (int i = 0; i < anzAusgaben; i++) {
            lsg[i] = eingaben[eingaben.length - 1 - i];
        }
        return lsg;
    }

    private int getIndexVonMaxWert(double[] array) {
        int max = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[max]) {
                max = i;
            }
        }
        return max;
    }
}
