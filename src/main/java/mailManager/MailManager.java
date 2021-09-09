package mailManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.function.Consumer;

import javax.activation.DataSource;
import javax.mail.*;

import mailManager.exception.*;

public class MailManager {

    private HashMap<Class<?>,MailExpression<?>> mailExpressions;
    private Properties props;
    private Session session;
    
    public MailManager(Consumer<MailManagerConfiguration> consumer) {

        MailManagerConfiguration config = new MailManagerConfiguration();
        consumer.accept(config);

        props = config.props;
        session = config.session;

        mailExpressions = new HashMap<Class<?>,MailExpression<?>>();
        for(MailProfile profile : config.profiles) {
            for(MailExpressionConfiguration<?> expressionConfiguration : profile.expressionConfigurations){
                Class<?> c = expressionConfiguration.type;
                if(mailExpressions.containsKey(c))
                    throw new DuplicateTypeException(c);
                mailExpressions.put(c, expressionConfiguration.createMailExpression());
            }
        }
    }

    /**
     * Send mail with default sender and no attachs
     * @param data Data object of your mail
     * @param to List of mail you are sending to
    */
    public <T> void sendMail(T data, String[] to) {
        send(data, to, null, null);
    }

    /**
     * Send mail with no attachs
     * @param data Data object of your mail
     * @param to List of email you are sending to
     * @param from Sender email
    */
    public <T> void sendMail(T data, String[] to, String from ) {
        send(data, to, from, null);
    }

    /**
     * Send mail with default sender mail and datasources as attach default fileName
     * @param data Data object of your mail
     * @param to List of mail you are sending to
     * @param sources List of file to attach
    */
    public <T> void sendMail(T data, String[] to, DataSource[] sources) {
        sendMail(data, to, null, sources);
    }

    /**
     * Send mail with default sender mail and datasources as attach and list of file name
     * @param data Data object of your mail
     * @param to List of mail you are sending to
     * @param sources List of file to attach
     * @param fileNames List of file name
    */
    public <T> void sendMail(T data, String[] to, DataSource[] sources, String[] fileNames) {
        sendMail(data, to, null, sources, fileNames);
    }

    /**
     * Send mail with and datasources as attach default fileName
     * @param data Data object of your mail
     * @param to List of mail you are sending to
     * @param from Sender email
     * @param sources List of file to attach
    */
    public <T> void sendMail(T data, String[] to, String from, DataSource[] sources) {
        ArrayList<String> fileNames = new ArrayList<String>();
        for(DataSource source : sources) fileNames.add(source.getName());
        sendMail(data, to, from, sources, fileNames.toArray(new String[0]));
    }

    /**
     * Send mail with and datasources and list of file name
     * @param data Data object of your mail
     * @param to List of mail you are sending to
     * @param from Sender email
     * @param sources List of file to attach
     * @param fileNames List of file name
    */
    public <T> void sendMail(T data, String[] to, String from, DataSource[] sources, String[] fileNames) {
        ArrayList<FileAttachment> attachs = new ArrayList<FileAttachment>();
        for(int i = 0; i < sources.length; i++) attachs.add(new FileAttachment(sources[i], fileNames[i]));
        send(data, to, from, attachs.toArray(new FileAttachment[0]));
    }

    /**
     * Send mail with default sender and attach files
     * @param data Data object of your mail
     * @param to List of mail you are sending to
     * @param attachs List of attachs file
    */
    public <T> void sendMail(T data, String[] to, FileAttachment[] attachs) {
        send(data,to,null,attachs);
    }

    /**
     * Send mail with attach files
     * @param data Data object of your mail
     * @param to List of mail you are sending to
     * @param from Sender email
     * @param attachs List of attachs file
    */
    public <T> void sendMail(T data, String[] to, String from, FileAttachment[] attachs) {
        send(data,to,from,attachs);
    }

    private <T> void send(T data, String[] to, String from, FileAttachment[] attachs) {
        MailExpression<T> expression = (MailExpression<T>)(mailExpressions.get(data.getClass()));
        expression.sendMail(data, props, session, from, to, attachs);
    }

}
