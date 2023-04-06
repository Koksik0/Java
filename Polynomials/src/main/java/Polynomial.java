public class Polynomial {
    private double[] polynomial;
    private int polynomialLength;

    public Polynomial(double[] polynomial) {
        this.polynomial = polynomial;
        this.polynomialLength = polynomial.length;
    }

    public double[] getPolynomial() {
        return polynomial;
    }

    public int getPolynomialLength() {
        return polynomialLength;
    }

}
