package mailManager;

import java.util.function.Function;

public class MailExpressionConfiguration<T> {

    private Function<T,String> subjectExpression;
    private Function<T,String> bodyExpression;
    private Function<T,String[]> toExpresion;
    Class<T> type;

    MailExpressionConfiguration(Class<T> type) {
        this.type = type;
    }

    public MailExpressionConfiguration<T> forTo(Function<T,String[]> toExpresion) {
        this.toExpresion = toExpresion;
        return this;
    }

    public MailExpressionConfiguration<T> forSubject(Function<T,String> subjectExpression) {
        this.subjectExpression = subjectExpression;
        return this;
    }
    
    public MailExpressionConfiguration<T> forBody(Function<T,String> bodyExpression) {
        this.bodyExpression = bodyExpression;
        return this;
    }

    MailExpression<T> createMailExpression() {
        return new MailExpression<T>(toExpresion,subjectExpression,bodyExpression);
    }
}
