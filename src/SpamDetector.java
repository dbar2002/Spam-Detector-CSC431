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
        // Common spam keywords
        String[] spamKeywords = {
                "100% more", "100% free", "100% satisfied", "additional income", "be your own boss",
                "best price", "big bucks", "billion", "cash bonus", "cents on the dollar",
                "consolidate debt", "double your cash", "double your income", "earn extra cash",
                "earn money", "eliminate bad credit", "extra cash", "extra income", "expect to earn",
                "fast cash", "financial freedom", "free access", "free consultation", "free gift",
                "free hosting", "free info", "free investment", "free membership", "free money",
                "free preview", "free quote", "free trial", "full refund", "get out of debt",
                "get paid", "giveaway", "guaranteed", "increase sales", "increase traffic",
                "incredible deal", "lower rates", "lowest price", "make money", "million dollars",
                "miracle", "money back", "once in a lifetime", "one time", "pennies a day",
                "potential earnings", "prize", "promise", "pure profit", "risk-free",
                "satisfaction guaranteed", "save big money", "save up to", "special promotion"
        };

        // Check if the message contains any spam keywords
        for (String keyword : spamKeywords) {
            if (message.toLowerCase().contains(keyword)) {
                return true;
            }
        }

        return false;
    }
}
