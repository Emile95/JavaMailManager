package mailManager;

import java.util.Properties;
import java.util.function.Function;

import javax.mail.*;
import javax.mail.internet.*;

class MailExpression<T> {

    private Function<T,String> subjectExpression;
    private Function<T,String> bodyExpression;

    MailExpression(Function<T,String> subjectExpression, Function<T,String> bodyExpression) {
        this.subjectExpression = subjectExpression;
        this.bodyExpression = bodyExpression;
    }

    void sendMail(T data, Properties prop, Session session, String from, String[] to) {
        try {
            Message message = new MimeMessage(session);
            if(from != null) message.setFrom(new InternetAddress(from));
            for(String address : to) message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
            message.setSubject(subjectExpression.apply(data));
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(bodyExpression.apply(data), "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
