/**
 * Midterm CIS 254: [Midterm assignment]
 * @author Oleg Nikitashin
 */
import java.util.Random;
import java.util.Arrays;

public class Midterm {

    // Main method: DO NOT CHANGE
    public static void main(String[] args) {

        Random rand = new Random();
        rand.setSeed(10);
        int[] numberSet = generateRandomNumbers(rand, 10);
        printAllNumbers(numberSet);
        double avg = average(numberSet);
        double med = median(numberSet);
        System.out.println("Average of numbers: " + avg);
        System.out.printf("%d numbers above average\n", above(numberSet, avg));
        System.out.printf("%d numbers below average\n", below(numberSet, avg));
        System.out.printf("Median: %.2f\n", med);
        System.out.printf("%d numbers above median\n", above(numberSet, med));
        System.out.printf("%d numbers below median\n", below(numberSet, med));
    }

    // Replace this comment with a javadoc comment as requested
    /**
     * Method return randomly generated integers of a specified size
     * @param random rand generate randim numbers, int size: This define the size of the array
     * @return array of numbers that was generated
     */
    private static int[] generateRandomNumbers(Random rand, int size) {
        int [] numbers = new int[size];

        // Write code to fill the array with randomly generated numbers
        for (int i = 0; i < size; i++) {
            numbers[i] = rand.nextInt();
        }
        return numbers;
    }

    // Replace this comment with a javadoc comment as requested
    /**
     * Method print all the numbers in the provided array
     * @param numberSet the array of integers printed
     * @return nothing (it is void)
     */
    private static void printAllNumbers(int[] numberSet) {

        // Write code to print all numbers in the number set
        // all in one line separated by space
        // Use the for-each (enhanced) loop
        for (int number : numberSet) {
            System.out.print(number + " ");
        }

        System.out.println(); // keep this line
    }

    // Replace this comment with a javadoc comment as requested
    /**
     * Method counts how many numbers in the array are greater than var. n
     * @param n the value to compare
     * @return number of elements in the array > n
     */
    private static int above(int[] numberSet, double n) {

        // Write code to compute how many number are above n in the number set
        int count = 0;
        for (int number : numberSet) {
            if (number > n) {
                count++;
            }
        }
        return count;

    }

    // Replace this comment with a javadoc comment as requested
    /**
     * Method counts how many numbers in the array are less than var. n
     * @param n the value to compare
     * @return number of elements in the array < n
     */
    private static int below(int[] numberSet, double n) {

        // Write code to compute how many number are below n in the number set
        int count = 0;
        for (int number : numberSet) {
            if (number < n) {
                count++;
            }

        }
        return count;
    }

    // Replace this comment with a javadoc comment as requested
    /**
     * Method does the median in array
     * @param numberSet the array for which the median we are looking for
     * @return median value
     */
    private static double median(int[] numberSet) {
        Arrays.sort(numberSet);

        // Write code to compute the median of the numbers in the number set
        int length = numberSet.length;

        if (length % 2 == 0) {
            return (numberSet[length / 2 - 1] + numberSet[length / 2]) / 2.0;
        } else {
            return numberSet[length / 2];
        }

    }

    // Replace this comment with a javadoc comment as requested
    /**
     * Method calculate the sum of the elements in the array
     * @param numberSet the array for which we need to find the sum
     * @return the sum of elements
     */
    private static int sum(int[] numberSet) {

        // Write code to compute the sum of the numbers in the number set
        int sum = 0;
        for (int number : numberSet) {
            sum += number;
        }

        return sum;

    }

    // Replace this comment with a javadoc comment as requested
    /**
     * Method calculate the avg value of all elements in the array
     * @param numberSet the array for which we need to find the sum
     * @return the sum of elements
     */
    private static double average(int[] numberSet) {

        // Write code to compute the average of the numbers in the number set
        // Use the sum() method here!
        int sum = sum(numberSet);
        return sum / (double) numberSet.length;

    }
}
