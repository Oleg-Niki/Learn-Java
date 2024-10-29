/**
 * Antics.java - A class for checking if a word is a palindrome, an abecedarian, a pangram, and
 * capitalizing the first letter of a word.
 *
 * @author Gerardo Calle (isPalindrome and isAbecedarian)
 * @author Oleg Nikitashin (isPangram and capitalizeFirstLetter)
 */
public class PartnerLab2 {
    /**
     * Checks if a given string is a palindrome.
     *
     * @param str the input string
     * @return true if the string is a palindrome, false if it is not
     */
    public static boolean isPalindrome(String str) {
        str = str.toLowerCase();

        // Initialize two pointers, one at the start and one at the end of the string
        int left = 0;
        int right = str.length() - 1;

        // Compare characters from both ends, moving towards the center
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false; // not a palindrome
            }
            left++;
            right--;
        }

        return true; // palindrome
    }

    /**
     *
     * @param str the input string
     * @return true if the string is an abecedarian, false it is not
     */
    public static boolean isAbecedarian(String str) {
        str = str.toLowerCase();


        char prevChar = str.charAt(0);

        // Iterate through the string, checking if each character is in alphabetical order
        for (int i = 1; i < str.length(); i++) {
            char currChar = str.charAt(i);
            if (currChar < prevChar) {
                return false; // not an abecedarian
            }
            prevChar = currChar;
        }

        return true; // abecedarian
    }
    /**
     * Method to check if a given string is a pangram using a for loop.
     *
     * @param text The string to be checked.
     * @return true if the string is a pangram, false otherwise.
     */
    public static boolean isPangram(String text) {
        boolean[] alphabetFlags = new boolean[26];
        text = text.toLowerCase();

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (currentChar >= 'a' && currentChar <= 'z') { // Check if character is a letter
                alphabetFlags[currentChar - 'a'] = true; // Mark the letter as found
            }
        }

        // Check if all letters are present.
        for (int i = 0; i < 26; i++) {
            if (!alphabetFlags[i]) { // If any letter is missing, return false
                return false;
            }
        }
        return true; // All letters found, return true
    }

    /**
     * Method to capitalize the first letter of a given string using a for loop.
     *
     * @param text The string to be modified.
     * @return The string with its first letter capitalized.
     */
    public static String capitalizeFirstLetter(String text) {
        if (text == null || text.isEmpty()) {
            return text; // Return empty or null string as is
        }

        // Convert the input string to a character array.
        char[] charArray = text.toCharArray();

        // Capitalize the first alphabetic character in the array using a for loop.
        for (int i = 0; i < charArray.length; i++) {
            if (Character.isLetter(charArray[i])) { // Check if character is a letter
                charArray[i] = Character.toUpperCase(charArray[i]); // Capitalize the first letter found
                break; // Break after capitalizing the first letter
            }
        }

        // Convert the character array back to a string and return.
        return new String(charArray);
    }
}  // End of class Antics
