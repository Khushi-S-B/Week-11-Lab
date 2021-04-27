package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class GmailService {

    public static void sendMailTemplate(String to, String subject, String template, HashMap<String, String> tags, String escape) throws Exception {

        String body = "";

        try {
        
            BufferedReader br = new BufferedReader(new FileReader(new File(template)));
            String line = br.readLine();

            while (line != null) {
                body += line;
                line = br.readLine();
            }

            if (escape.equals("[[")) {
                for (String key : tags.keySet()) {
                    body = body.replace("[[" + key + "]]", tags.get(key));
                }
            } else if (escape.equals("{{")) {
                for (String key : tags.keySet()) {
                    body = body.replace("{{" + key + "}}", tags.get(key));
                }
            }

        } catch (Exception e) {
            Logger.getLogger(GmailService.class.getName()).log(Level.SEVERE, null, e);
        }

        sendMail(to, subject, body, true);

    }

    public static void sendMail(String to, String subject, String body, boolean bodyIsHTML) throws MessagingException, NamingException {

        Context env = (Context) new InitialContext().lookup("java:comp/env");

        String username = (String) env.lookup("webmail-username");
        String password = (String) env.lookup("webmail-password");

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", "smtp.gmail.com");
        props.put("mail.smtps.port", 465);
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false");
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        // create a message
        Message message = new MimeMessage(session);
        message.setSubject(subject);
        if (bodyIsHTML) {
            message.setContent(body, "text/html");
        } else {
            message.setText(body);
        }

       
        Address fromAddress = new InternetAddress(username);
        Address toAddress = new InternetAddress(to);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);

   
        Transport transport = session.getTransport();
        transport.connect(username, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
