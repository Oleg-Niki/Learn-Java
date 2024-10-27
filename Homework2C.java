/** Description of program: This is my second program (Homework2C) prints a hello Oleg message on the screen
 @author: Oleg Nikitashin
 @since: 08/25/24
 */
public class Homework2C {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Hello World! Please write a name as a command-line argument.");
        } else {
            String name = args[0];
            System.out.println("Hello World! My name is " + name);
        }
    }
}
