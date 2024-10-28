public class ComplexNumber {

    private double real;

    private double imag;

    public ComplexNumber(double r, double i){
        real = r;
        imag = i;
            }
            public String toString(){
               return String.format ("%.1f + %.1f i", this.real, this.imag);
            }
            public void update(double r, double i){
        real = r;
        imag = i;
            }
            public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(this.real + other.real, this.imag + other.imag);
            }
            public ComplexNumber multiply(ComplexNumber other) {
        double r = this.real * other.real + this.imag * other.imag * (-1);
        double i = this.real * other.imag + this.imag * other.real;
        return new ComplexNumber(r, i);
            }
            public ComplexNumber divide(ComplexNumber other) {
        ComplexNumber conjugate = new ComplexNumber((other.real, other.imag * (-1) ));
        ComplexNumber numerator = this.multiply(conjugate);
        ComplexNumber denominator = other.multiply((conjugate));//this is numerator other is denominator and conjugate when you are multiplying on "other" with opposite sign
            }
}
