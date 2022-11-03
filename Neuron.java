import java.util.Random;

public class Neuron {
    private final double lerngeschwindigkeit = 0.2;
    private final double[] gewichte;
    private double vorherAusgabe, tatsaechlicheAusgabe, delta;

    public Neuron(int anzGewichte) {
        gewichte = new double[anzGewichte];
        Random random = new Random();
        for (int i = 0; i < anzGewichte; i++) {
            gewichte[i] = random.nextDouble();
        }
    }

    public double getAusgabe(double[] eingaben) {
        vorherAusgabe = this.skalarprodukt(gewichte, eingaben);
        return this.aktivierungsfunktion(vorherAusgabe);
    }

    private double skalarprodukt(double[] a, double[] b) {
        double erg = 0;
        for (int i = 0; i < a.length; i++) {
            erg = erg + b[i] * a[i];
        }
        return erg;
    }

    private double aktivierungsfunktion(double x) {
        tatsaechlicheAusgabe = 1.0 / (1.0 + Math.exp(-x)); // sigmoid-Funktion
        return tatsaechlicheAusgabe;
    }

    private double ableitungAktivierungsfunktion(double x) {
        double fVonX = aktivierungsfunktion(x);
        return fVonX * (1 - fVonX);
    }

    public double getDeltaAusgabeschicht(double erwarteteAusgabe) {
        return this.delta = (erwarteteAusgabe - tatsaechlicheAusgabe) * ableitungAktivierungsfunktion(vorherAusgabe);
    }

    public double getDelta(double[] deltasNaechsteSchicht, double[] gewichteNaechste) {
        return this.delta = this.skalarprodukt(deltasNaechsteSchicht, gewichteNaechste) * this.ableitungAktivierungsfunktion(vorherAusgabe);
    }

    public void gradientenabstiegsverfahren(double[] vorherAusgaben) {
        for (int i = 0; i < gewichte.length; i++) {
            gewichte[i] = gewichte[i] + vorherAusgaben[i] * delta * lerngeschwindigkeit;
        }
    }

    public double getTatsaechlicheAusgabe() {
        return tatsaechlicheAusgabe;
    }

    public double[] getGewichte() {
        return gewichte;
    }
}
