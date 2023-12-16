import javax.mail.internet.AddressException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.mail.internet.InternetAddress;
public class SpamDetector extends JFrame{
    private JTextArea messageTextArea;
    private JTextField senderField;
    private JTextField subjectField;
    private JTextField recipientField;
    private JButton checkSpamButton;
    private static final String[] validDomains = {"gmail.com", "yahoo.com", "hotmail.com"};
    public SpamDetector() {
        setTitle("Spam Detector");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel();

        JLabel senderLabel = new JLabel("Sender:");
        JLabel recipientLabel = new JLabel("Recipient:");
        JLabel subjectLabel = new JLabel("Subject:");

        senderField = new JTextField(30);
        recipientField = new JTextField(30);
        subjectField = new JTextField(30);

        messageTextArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(messageTextArea);

        checkSpamButton = new JButton("Check Spam");

        checkSpamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sender = senderField.getText();
                String recipient = recipientField.getText();
                String subject = subjectField.getText();
                String message = messageTextArea.getText();

                if (isValidDomain(sender) && isValidDomain(recipient)) {
                    if(!isSpamSubject(subject)) {
                        if (isSpam(message)) {
                            JOptionPane.showMessageDialog(null, "Warning: This message appears to be spam!");
                        } else {
                            JOptionPane.showMessageDialog(null, "The message is not identified as spam.");
                        }
                    } else {
                        int option = JOptionPane.showConfirmDialog(null, "This message appears to be spam based on the subject. Do you want to investigate further?",
                                "Spam Detection", JOptionPane.YES_NO_OPTION);

                        if (option == JOptionPane.YES_OPTION) {
                            if (isSpam(message)) {
                                JOptionPane.showMessageDialog(null, "Further investigation: The message is confirmed as spam!");
                            } else {
                                JOptionPane.showMessageDialog(null, "Further investigation: The message is not identified as spam.");
                            }
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid sender or recipient domain.");
                }
            }
        });

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(senderLabel)
                                .addComponent(recipientLabel)
                                .addComponent(subjectLabel)
                                .addComponent(scrollPane))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(senderField)
                                .addComponent(recipientField)
                                .addComponent(subjectField)
                                .addComponent(checkSpamButton))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(senderLabel)
                                .addComponent(senderField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(recipientLabel)
                                .addComponent(recipientField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(subjectLabel)
                                .addComponent(subjectField))
                        .addComponent(scrollPane)
                        .addComponent(checkSpamButton)
        );

        add(panel);
    }
    private boolean isValidDomain(String email) {
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();


            String[] parts = email.split("@");
            if (parts.length == 2) {
                String domain = parts[1].toLowerCase();
                for (String validDomain : validDomains) {
                    if (domain.equals(validDomain)) {
                        return true;
                    }
                }
            }
        } catch (AddressException ex) {
            return false;
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SpamDetector();
            }
        });
    }


    private static boolean isSpamSubject(String subject) {

        String lowerCaseSubject = subject.trim().toLowerCase();
        boolean containsSpamKeywords = isSpam(lowerCaseSubject);

        System.out.println("Original Subject: " + subject);
        System.out.println("Lowercase Subject: " + lowerCaseSubject);
        System.out.println("Contains Spam Keywords: " + containsSpamKeywords);

        return containsSpamKeywords;
    }

    private static boolean isSpam(String message) {

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
                "satisfaction guaranteed", "save big money", "save up to", "special promotion",

                "% satisfied", "% off", "acceptance", "accordingly", "act", "now", "affordable", "all", "new", "as", "seen on",
                "auto email removal", "avoid", "bargain", "be amazed", "bonus", "brand new pager", "bulk email", "buy",
                "buy direct", "buying judgments", "cable converter", "call", "call free", "call now", "can’t live without",
                "cancel at any time", "casino", "celebrity", "chance", "claims not to be selling anything", "clearance",
                "click", "click below", "click here", "click to remove", "compare", "compare rates", "compete for your business",
                "copy accurately", "copy DVDs", "deal", "direct email", "direct marketing", "do it today", "don’t delete",
                "don’t hesitate!", "dormant", "email harvest", "email marketing", "explode your business", "f r e e",
                "fantastic deal", "for just $(insert whatever amount)", "for only", "form", "free", "free consultation",
                "free DVD", "free hosting", "freedom", "get", "get it away", "get it now", "gift certificate", "give it away",
                "guarantee", "guaranteed", "have you been turned down?", "here", "hidden", "home", "increase sales",
                "increase traffic", "increase your sales", "incredible deal", "internet market", "internet marketing",
                "it’s effective", "join millions", "join millions of Americans", "laser printer", "leave", "lifetime",
                "limited time", "limited time offer", "long distance phone offer", "lose", "luxury car", "mail in order form",
                "maintained", "marketing", "marketing solutions", "mass email", "medium", "member", "miracle", "month trial offer",
                "more internet traffic", "multi level marketing", "never", "new domain extensions", "no age restrictions", "no cost",
                "no disappointment", "no gimmick", "no inventory", "no middleman", "no obligation", "no purchase necessary",
                "no questions asked", "no selling", "no strings attached", "no-obligation", "notspam", "now", "now only",
                "one hundred percent free", "one hundred percent guaranteed", "one time mailing", "online marketing", "open",
                "opportunity", "opt in", "order", "order now", "order shipped by", "passwords", "performance", "print form signature",
                "print out and fax", "priority mail", "prize", "prizes", "problem", "produced and sent out", "promise you",
                "quote", "quotes", "removal instructions", "remove", "reserves the right", "reverses", "rolex", "sale", "sales",
                "sample", "satisfaction", "save $", "save big money", "search engine listings", "search engines", "see for yourself",
                "serious only", "shopping spree", "sign up free today", "solution", "stainless steel", "stop", "strong buy",
                "stuff on sale", "subscribe", "success", "teen", "the best rates", "the following form", "they’re just giving it away",
                "this isn’t a scam", "this isn't junk", "this isn't spam", "this won’t last", "undisclosed recipient", "unlimited",
                "unsolicited", "unsubscribe", "urgent", "vacation", "vacation offers", "visit our website", "warranty", "we hate spam",
                "we honor all", "web traffic", "weekend getaway", "while supplies last", "while you sleep", "who really wins?",
                "will not believe your eyes", "win", "winner", "winning", "won", "you are a winner!", "you have been selected",
                "% free", "accept credit cards", "access", "ad", "amazing", "auto email removal", "being a member", "best price",
                "billing address", "bulk email", "cancel at any time", "cannot be combined with any other offer", "cards accepted",
                "dear [email/friend/somebody]", "dig up dirt on friends", "direct email", "direct marketing", "email harvest",
                "email marketing", "free gift", "free leads", "free preview", "free sample", "free trial", "full refund", "get started now",
                "gift certificate", "give it away", "great offer", "if only it were that easy", "important information regarding",
                "increase traffic", "info you requested", "information you requested", "internet market", "internet marketing",
                "maintained", "marketing", "marketing solutions", "mass email", "member", "member stuff", "message contains",
                "message contains disclaimer", "mlm", "multi level marketing", "multi-level marketing", "name brand", "no catch",
                "no disappointment", "no hidden", "not intended", "obligation", "offer", "online marketing", "order status", "please read",
                "safeguard notice", "satisfaction", "satisfaction guaranteed", "search engine listings", "search engines", "sent in compliance",
                "solution", "special promotion", "supplies are limited", "talks about hidden charges", "tells you it’s an ad",
                "terms and conditions", "the following form", "this isn’t junk", "this isn’t spam", "trial", "we hate spam", "web traffic",
                "weekend getaway", "as seen on", "bargain", "be amazed", "bonus", "buy direct", "buying judgments", "call", "call free",
                "can’t live without", "cheap", "claims not to be selling anything", "clearance", "click to remove", "collect",
                "compare rates", "compete for your business", "deal", "don’t hesitate!", "explode your business", "f r e e", "fantastic deal",
                "for just $(insert whatever amount)", "for only", "free DVD", "free hosting", "get it now", "guarantee", "guaranteed",
                "have you been turned down?", "increase sales", "increase your sales", "incredible deal", "it’s effective", "join millions",
                "join millions of Americans", "lifetime", "limited time", "limited time offer", "long distance phone offer", "mail in order form",
                "money back", "new domain extensions", "no cost", "no gimmick", "no inventory", "no middleman", "no obligation", "no questions asked",
                "no selling", "no strings attached", "no-obligation", "now", "now only", "one hundred percent free", "one hundred percent guaranteed",
                "opportunity", "order shipped by", "problem", "promise you", "quote", "quotes", "sale", "sales", "save $", "save big money",
                "see for yourself", "serious only", "sign up free today", "strong buy", "stuff on sale", "success", "the best rates",
                "they’re just giving it away", "this isn’t a scam", "this won’t last", "unlimited", "urgent", "warranty", "we honor all",
                "while supplies last", "while you sleep", "will not believe your eyes"
        };

        String[] lowercaseKeywords = new String[spamKeywords.length];
        for (int i = 0; i < spamKeywords.length; i++) {
            lowercaseKeywords[i] = spamKeywords[i].toLowerCase();
        }

        for (String keyword : lowercaseKeywords) {
            if (message.toLowerCase().contains(keyword)) {
                return true;
            }
        }

        return false;
    }
}
