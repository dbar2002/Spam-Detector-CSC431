import java.util.Scanner;

public class SpamDetector {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a message:");
        String message = scanner.nextLine();

        if (isSpam(message)) {
            System.out.println("Warning: This message appears to be spam!");
        } else {
            System.out.println("The message is not identified as spam.");
        }
    }

    private static boolean isSpam(String message) {
        // Simple rules for demonstration purposes
        // You can enhance and customize these rules based on your research and requirements

        // Rule 1: Check for excessive use of capital letters
        if (countUpperCaseLetters(message) > 10) {
            return true;
        }

        // Rule 2: Check for the presence of specific spam keywords
        String[] spamKeywords = {"earn money", "lose weight", "free", "click here"};
        for (String keyword : spamKeywords) {
            if (message.toLowerCase().contains(keyword)) {
                return true;
            }
        }

        return false;
    }

    private static int countUpperCaseLetters(String text) {
        int count = 0;
        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c)) {
                count++;
            }
        }
        return count;
    }
}
