public class Main {
    /**
     * *The starting point
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Hello world!");
        ComplexNumber a = new ComplexNumber(1,3);
        ComplexNumber b = new ComplexNumber(2,3);
        a.update(4,5);
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("a + b = " + a.add(b));
        System.out.println("a * b = " + a.multiply(b));
    }
}
