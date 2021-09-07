package mailManager;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Session;

public class MailManagerConfiguration {
    ArrayList<MailProfile> profiles;
    Properties props;
    Session session;

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

    public void addProfile(MailProfile profile) {
        profiles.add(profile);
    }
}
