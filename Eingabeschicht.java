public class Eingabeschicht extends Schicht {
    private double[] eingaben;

    public Eingabeschicht() {
        super(0, 0);
    }

    @Override
    public double[] getAusgaben(double[] eingaben) {
        return this.eingaben = eingaben;
    }

    @Override
    public double[] getDeltas(double[] deltasNaechsteSchicht, double[][] gewichteNaechsteSchicht) {
        return new double[0];
    }

    @Override
    public void gradientenabstiegsverfahren(double[] vorherAusgabe) {
    }

    @Override
    public double[] getTatsaechlicheAusgaben() {
        return eingaben;
    }

    @Override
    public double[][] getGewichte() {
        return new double[0][0];
    }
}
