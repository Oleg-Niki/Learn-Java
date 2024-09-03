/**
 Homework 3
 Description: [Time Calculator]
 @author: Oleg Nikitashin
 @since: 08/29/2024
 */
public class Time {
    public static void main(String[] args) {
        // Ensure that exactly three arguments are provided
        if (args.length != 3) {
            System.out.println("Please provide exactly three args for hour, minute, and second.");
            return;
        }

        int hour = Integer.valueOf(args[0]);
        int minute = Integer.valueOf(args[1]);
        int second = Integer.valueOf(args[2]);

        // Output the converted values
        System.out.println("Hour: " + hour);
        System.out.println("Minute: " + minute);
        System.out.println("Second: " + second);

        // Calculate the total number of seconds in a day
        int totalSecondsInDay = 24 * 60 * 60;

        // Calculate the number of seconds since midnight
        int secondsSinceMidnight = hour * 3600 + minute * 60 + second;

        // Calculate the number of seconds remaining in the day
        int secondsRemainingInDay = totalSecondsInDay - secondsSinceMidnight;

        // Calculate the percentage of the day that has passed
        double percentageOfDayPassed = (secondsSinceMidnight / (double) totalSecondsInDay) * 100;

        System.out.println("Total seconds in a day: " + totalSecondsInDay);
        System.out.println("Seconds since midnight: " + secondsSinceMidnight);
        System.out.println("Seconds remaining in the day: " + secondsRemainingInDay);
        System.out.printf("Percentage of the day passed: %.2f%%\n", percentageOfDayPassed);
    }
}
