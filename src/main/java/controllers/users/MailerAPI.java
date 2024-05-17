package controllers.users;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailerAPI {
    static Session sesh;
    static Properties prop = new Properties();

    public MailerAPI() {
    }

    public static void Mail(final String UN, final String PW, String to, String sub, String cont) {
        try {
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
            sesh = Session.getInstance(prop, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(UN, PW);
                }
            });
            Message m = new MimeMessage(sesh);
            m.setFrom(new InternetAddress(UN));
            m.setRecipients(RecipientType.TO, InternetAddress.parse(to));
            m.setSubject(sub);
            m.setSentDate(new Date());
            m.setContent(cont, "text/plain");
            m.setHeader("EMAIL HEAD", "Reclamation");
            System.out.println("\n \n \n \t >> ??????? " + m.getContentType());
            System.out.println("\n \n \n \t >> ??????? " + m.getDataHandler());
            System.out.println("\n \n \n \t >> ??????? " + m.getSubject());
            Transport t = sesh.getTransport("smtp");
            t.connect(UN, PW); // Connect using your Gmail credentials
            t.sendMessage(m, m.getAllRecipients()); // Send the message
            t.close(); // Close the transport after sending
            System.out.println("Email sent successfully!");
        } catch (MessagingException var7) {
            throw new RuntimeException(var7);
        }
    }
}
