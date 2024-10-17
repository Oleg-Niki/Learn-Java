import java.util.Scanner;
import java.util.Calendar;

public class Homework4 {
    public static void main(String[] args) {
        Calendar currentDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        System.out.println("Hello User!");
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is you name?");
        String name = scanner.nextLine();
        System.out.println("Nice to meet you, " + name);
        System.out.println("What is your year of birth?");
        int year = scanner.nextInt();
        System.out.println("Cool, so you are :" + (currentYear - year) + " years old");
    }
}
