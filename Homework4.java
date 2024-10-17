import java.util.Scanner;

public class Homework4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! My name is " + args[0]);
        System.out.println("What is your name?");
        String name = scanner.nextLine();
        System.out.println("Nice to meet you" + name);
    }
}
