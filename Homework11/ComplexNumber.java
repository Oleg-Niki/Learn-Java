package Homework11;

public class ComplexNumber {

    private double real; // Real part of the complex number

    private double imag; // Imaginary part of the complex number

    // Constructor to initialize complex number
    public ComplexNumber(double r, double i){
        real = r;
        imag = i;
            }
//            public String toString(){
//               return String.format ("%.1f + %.1f i", this.real, this.imag);
//            }
    public String toString() {
        if (imag >= 0)
            return String.format("%.1f + %.1fi", real, imag);
        else
            return String.format("%.1f - %.1fi", real, -imag);
}
            public void update(double r, double i){
        real = r;
        imag = i;
            }
            public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(this.real + other.real, this.imag + other.imag);
            }
        public ComplexNumber subtract(ComplexNumber other) {
            return new ComplexNumber(this.real - other.real, this.imag - other.imag);
    }
            public ComplexNumber multiply(ComplexNumber other) {
        double r = this.real * other.real + this.imag * other.imag * (-1);
        double i = this.real * other.imag + this.imag * other.real;
        return new ComplexNumber(r, i);
            }
            public ComplexNumber divide(ComplexNumber other) {
        ComplexNumber conjugate = new ComplexNumber(other.real, other.imag * (-1) );
        ComplexNumber numerator = this.multiply(conjugate);
        ComplexNumber denominator = other.multiply((conjugate));//this is numerator other is denominator and conjugate when you are multiplying on "other" with opposite sign
                // Division formula: (a + bi) / (c + di) = ((a + bi) * (c - di)) / (c^2 + d^2)
                double realPart = numerator.real / (denominator.real);
                double imagPart = numerator.imag / (denominator.real);
                return new ComplexNumber(realPart, imagPart);
            }
}
