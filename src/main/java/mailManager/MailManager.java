package mailManager;

import java.util.HashMap;
import java.util.Properties;
import java.util.function.Consumer;

import javax.mail.*;

import mailManager.exception.*;

public class MailManager {

    private HashMap<Class<?>,MailExpression<?>> mailExpressions;
    private Properties props;
    private Session session;
    private String from;
    
    public MailManager(Consumer<MailManagerConfiguration> consumer) {

        MailManagerConfiguration config = new MailManagerConfiguration();
        consumer.accept(config);

        props = config.props;
        session = config.session;
        from = config.from;

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

    public <T> void sendMail(T data) {
        MailExpression<T> expression = (MailExpression<T>)(mailExpressions.get(data.getClass()));
        expression.sendMail(data, props, session, from);
    }
}
