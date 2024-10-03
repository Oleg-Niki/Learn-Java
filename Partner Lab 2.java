
    /**
     * Checks if a given string is a pangram (contains all 26 letters of the alphabet).
     * @param sentence the string to check.
     * @return true if the string is a pangram, false otherwise.
     */
    public static boolean isPangram(String sentence) {
        String normalized = sentence.toLowerCase().replaceAll("[^a-zA-Z]", "");
        boolean[] alphabetArray = new boolean[26];

        for (int i = 0; i < normalized.length(); i++) {
            char currentChar = normalized.charAt(i);
            if ('a' <= currentChar && currentChar <= 'z') {
                alphabetArray[currentChar - 'a'] = true;
            }
        }

        for (boolean letterPresent : alphabetArray) {
            if (!letterPresent) {
                return false;
            }
        }
        return true;
    }

    /**
     * Capitalizes the first letter of the given string.
     * @param word the string to modify.
     * @return the modified string with the first letter capitalized.
     */
    public static String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    public static void main(String[] args) {
        System.out.println("isPalindrome Test:");
        System.out.println(isPalindrome("Racecar")); // true
        System.out.println(isPalindrome("hello"));   // false

        System.out.println("isAbecedarian Test:");
        System.out.println(isAbecedarian("almost")); // true
        System.out.println(isAbecedarian("hello"));  // false

        System.out.println("isPangram Test:");
        System.out.println(isPangram("The quick brown fox jumps over the lazy dog")); // true
        System.out.println(isPangram("hello world"));  // false

        System.out.println("capitalizeFirstLetter Test:");
        System.out.println(capitalizeFirstLetter("california"));  // California
        System.out.println(capitalizeFirstLetter("HELLO"));       // Hello
    }
}
