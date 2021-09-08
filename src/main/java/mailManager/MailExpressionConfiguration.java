package mailManager;

import java.util.function.Function;

public class MailExpressionConfiguration<T> {

    private Function<T,String> subjectExpression;
    private Function<T,String> bodyExpression;
    Class<T> type;

    MailExpressionConfiguration(Class<T> type) {
        this.type = type;
    }

    /**
     * Define the expression that create your subject String depending on object
     * @param subjectExpression expression who create subject String
    */
    public MailExpressionConfiguration<T> forSubject(Function<T,String> subjectExpression) {
        this.subjectExpression = subjectExpression;
        return this;
    }
    
    /**
     * Define the expression that create your body String depending on object
     * @param bodyExpression expression who create body String
    */
    public MailExpressionConfiguration<T> forBody(Function<T,String> bodyExpression) {
        this.bodyExpression = bodyExpression;
        return this;
    }

    MailExpression<T> createMailExpression() {
        return new MailExpression<T>(subjectExpression,bodyExpression);
    }
}
