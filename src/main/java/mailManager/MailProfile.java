package mailManager;

import java.util.ArrayList;

public class MailProfile {
    ArrayList<MailExpressionConfiguration<?>> expressionConfigurations;

    public MailProfile() {
        expressionConfigurations = new ArrayList<MailExpressionConfiguration<?>>();
    }

    /**
     * Add a mail expression in you MailManager configuration
     * @param c Class of your mail expression
    */
    protected <T> MailExpressionConfiguration<T> createMail(Class<T> c) {
        MailExpressionConfiguration<T> exp = new MailExpressionConfiguration<T>(c);
        expressionConfigurations.add(exp);
        return exp;
    }
}
