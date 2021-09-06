package mailManager;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Session;

public class MailManagerConfiguration {
    ArrayList<MailProfile> profiles;
    Properties props;
    Session session;
    String from;

    MailManagerConfiguration() {
        profiles = new ArrayList<MailProfile>();
    }

    public MailManagerConfiguration forPropeties(Properties props) {
        this.props = props;
        return this;
    }

    public MailManagerConfiguration forSession(Session session) {
        this.session = session;
        return this;
    }

    public MailManagerConfiguration forFrom(String from) {
        this.from = from;
        return this;
    }

    public void addProfile(MailProfile profile) {
        profiles.add(profile);
    }
}
