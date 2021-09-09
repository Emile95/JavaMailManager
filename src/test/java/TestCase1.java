import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.List;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import java.lang.Iterable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mailManager.FileAttachment;
import mailManager.MailManager;
import mailManager.MailManagerConfiguration;
import mailManager.MailProfile;

class Mail {
    String subject;
    String body;

    public Mail(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }
}

class Profile extends MailProfile {
    public Profile() {
        createMail(Mail.class)
            .forSubject(data -> data.subject)
            .forBody(data -> data.body);
    }
}

public class TestCase1 {
    Properties props;
    Session session;

    public TestCase1() {
        props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                List<String> lines = null;
                try {
                    lines = Files.readAllLines(Paths.get("mailCredential.txt"), StandardCharsets.UTF_8);
                } catch(Exception e) {}
                return new PasswordAuthentication(lines.get(0), lines.get(1));
            }
        });
    }

    @Test                                               
    @DisplayName("Test 1")   
    void test1() {
        MailManager mailManager = new MailManager(config -> {
            config.forPropeties(props);
            config.forSession(session);
            config.addProfile(new Profile());
        });

        mailManager.sendMail(new Mail(
            "Test 1",
            "BROUTILLE !!!!"
        ), new String[] { "desjardins.e@outlook.com" });
    }

    @Test                                               
    @DisplayName("Test 2")   
    void test2() {
        MailManager mailManager = new MailManager(config -> {
            config.forPropeties(props);
            config.forSession(session);
            config.addProfile(new Profile());
        });

        mailManager.sendMail(new Mail(
                "Test 2",
                "BROUTILLE !!!!"
            ), 
            new String[] { "desjardins.e@outlook.com" },
            new DataSource[] { new FileDataSource("lettre_motivation.pdf"), new FileDataSource("CV 2021_Émile Desjardins.pdf") }
        );
    }

    @Test                                               
    @DisplayName("Test 3")   
    void test3() {
        MailManager mailManager = new MailManager(config -> {
            config.forPropeties(props);
            config.forSession(session);
            config.addProfile(new Profile());
        });
        
        mailManager.sendMail(new Mail(
                "Test 3",
                "BROUTILLE !!!!"
            ), 
            new String[] { "desjardins.e@outlook.com" },
            new FileAttachment[] { 
                new FileAttachment(new FileDataSource("lettre_motivation.pdf"),"fileName.pdf"), 
                new FileAttachment(new FileDataSource("CV 2021_Émile Desjardins.pdf"), "fileName2.pdf") 
            }
        );
    }
}

