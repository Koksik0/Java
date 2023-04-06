public class Test {
    public static void main(String[] args) {
//        double[] firstPolynomial = {4.0,4.0,4.0,0.0};
//        double[] secondPolynomial = {2.0,0.0,0.0,0.0,2.0,0.0};
//        Polynomial first = new Polynomial(firstPolynomial);
//        Polynomial second = new Polynomial(secondPolynomial);


//        Dodawanie dwóch wielomianaów
//        Polynomial aaa = operationsOnPolynomials.addingPolynomials(first,second);
//        for(int x = aaa.getPolynomialLength()-1;x>= 0;x--)
//        {
//            System.out.println(aaa.getPolynomial()[x]);
//        }
//        operationsOnPolynomials.printPolynomial(operationsOnPolynomials.addingPolynomials(first,second));

//        Wartość wielomianu w punkcie
        OperationsOnPolynomials operationsOnPolynomials = new OperationsOnPolynomials();
        double[] thirdPolynomial = {-1.0,7.0,-1.0,0.0};
        Polynomial third = new Polynomial(thirdPolynomial);
        double result = operationsOnPolynomials.polynomialValueHorner(third,-3.0);
        System.out.println(result);
    }
}
