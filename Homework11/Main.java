  public static void main(String[] args) {
        ComplexNumber num1 = new ComplexNumber(2, 3);
        ComplexNumber num2 = new ComplexNumber(1, -2);

        System.out.println("Number 1: " + num1);
        System.out.println("Number 2: " + num2);

        System.out.println("Addition: " + num1.add(num2));
        System.out.println("Subtraction: " + num1.subtract(num2));
        System.out.println("Multiplication: " + num1.multiply(num2));
        System.out.println("Division: " + num1.divide(num2));
    }
