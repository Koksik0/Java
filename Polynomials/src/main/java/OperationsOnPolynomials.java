public class OperationsOnPolynomials {
    private Polynomial add(Polynomial longer, Polynomial smaller) {
        for (int x = 0; x < smaller.getPolynomialLength(); x++) {
            longer.getPolynomial()[x + longer.getPolynomialLength() - smaller.getPolynomialLength()] += smaller.getPolynomial()[x];
        }
        return longer;
    }

    public Polynomial addingPolynomials(Polynomial first, Polynomial second) {
        if (first.getPolynomialLength() >= second.getPolynomialLength()) {
            return add(first, second);
        } else {
            return add(second, first);
        }
    }

    public Polynomial subtractingPolynomials(Polynomial first, Polynomial second) {
        int longer = 0;
        int temp = 0;
        if (first.getPolynomialLength() >= second.getPolynomialLength()) {
            longer = first.getPolynomialLength();
            temp = first.getPolynomialLength() - second.getPolynomialLength();
            if (temp < 0) {
                temp *= -1;
            }
        } else {
            longer = second.getPolynomialLength();
            temp = second.getPolynomialLength() - first.getPolynomialLength();
            if (temp < 0) {
                temp *= -1;
            }
        }
        Polynomial result = new Polynomial(new double[longer]);
        for (int x = 0; x < longer; x++) {
            if (first.getPolynomialLength() >= second.getPolynomialLength()) {
                result.getPolynomial()[x] += first.getPolynomial()[x];
                if (x + temp < longer) {
                    result.getPolynomial()[x + temp] -= second.getPolynomial()[x];
                }

            } else {
                result.getPolynomial()[x] -= second.getPolynomial()[x];
                if (x + temp < longer) {
                    result.getPolynomial()[x + temp] += first.getPolynomial()[x];
                }

            }
        }
        return result;
    }

    public Polynomial multiplicationPolynomials(Polynomial first, Polynomial second) {
        Polynomial result = new Polynomial(new double[first.getPolynomialLength() + second.getPolynomialLength() - 1]);
        for (int x = 0; x < first.getPolynomialLength(); x++) {
            for (int y = 0; y < second.getPolynomialLength(); y++) {
                result.getPolynomial()[x + y] += first.getPolynomial()[x] * second.getPolynomial()[y];
            }
        }
        return result;
    }

    public double polynomialValueHorner(Polynomial polynomial, double argument) {
        double result = polynomial.getPolynomial()[0];
        for (int x = 1; x < polynomial.getPolynomialLength(); x++) {
            result = result * argument + polynomial.getPolynomial()[x];
        }
        return result;
    }

    public void printPolynomial(Polynomial polynomial) {
        int power = polynomial.getPolynomialLength() - 1;
        for (int x = 0; x < polynomial.getPolynomialLength(); x++) {
            if (polynomial.getPolynomial()[x] != 0) {
                if (x == 0) {
                    System.out.print(polynomial.getPolynomial()[x] + "x^" + power);
                }
                if (polynomial.getPolynomial()[x] > 0 && x > 0) {
                    System.out.print(" + " + polynomial.getPolynomial()[x] + "x^" + power);
                } else if (polynomial.getPolynomial()[x] < 0 && x > 0) {
                    System.out.print(" - " + Math.abs(polynomial.getPolynomial()[x]) + "x^" + power);
                }
            }
            power--;
        }
    }
}
