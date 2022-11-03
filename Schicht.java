public class Schicht {
    protected final Neuron[] neuronen;

    public Schicht(int anzNeuronen, int anzGewichte) {
        neuronen = new Neuron[anzNeuronen];
        for (int i = 0; i < anzNeuronen; i++) {
            neuronen[i] = new Neuron(anzGewichte);
        }
    }

    public double[] getAusgaben(double[] eingaben) {
        double[] ausgaben = new double[neuronen.length];
        for (int i = 0; i < neuronen.length; i++) {
            ausgaben[i] = neuronen[i].getAusgabe(eingaben);
        }
        return ausgaben;
    }

    public double[] getDeltas(double[] deltasNaechsteSchicht, double[][] gewichteNaechsteSchicht) {
        double[] deltas = new double[neuronen.length];
        for (int i = 0; i < neuronen.length; i++) {
            double[] gewichteNaechste = new double[gewichteNaechsteSchicht.length];
            for (int j = 0; j < gewichteNaechsteSchicht.length; j++) {
                gewichteNaechste[j] = gewichteNaechsteSchicht[j][i];
            }
            deltas[i] = neuronen[i].getDelta(deltasNaechsteSchicht, gewichteNaechste);
        }
        return deltas;
    }

    public double[] getDeltasAusgabeschicht(double[] erwarteteAusgaben) {
        double[] deltas = new double[neuronen.length];
        for (int i = 0; i < neuronen.length; i++) {
            deltas[i] = neuronen[i].getDeltaAusgabeschicht(erwarteteAusgaben[i]);
        }
        return deltas;
    }

    public void gradientenabstiegsverfahren(double[] vorherAusgabe) {
        for (Neuron neuron : neuronen) {
            neuron.gradientenabstiegsverfahren(vorherAusgabe);
        }
    }

    public double[] getTatsaechlicheAusgaben() {
        double[] ausg = new double[neuronen.length];
        for (int i = 0; i < neuronen.length; i++) {
            ausg[i] = neuronen[i].getTatsaechlicheAusgabe();
        }
        return ausg;
    }

    public double[][] getGewichte() {
        double[][] gewichte = new double[neuronen.length][neuronen[0].getGewichte().length];
        for (int i = 0; i < neuronen.length; i++) {
            gewichte[i] = neuronen[i].getGewichte();
        }
        return gewichte;
    }
}
