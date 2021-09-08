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

    /**
     * Define the properties of your client
     * @param props properties for client
    */
    public MailManagerConfiguration forPropeties(Properties props) {
        this.props = props;
        return this;
    }

    /**
     * Define the session of your client
     * @param session session for client
    */
    public MailManagerConfiguration forSession(Session session) {
        this.session = session;
        return this;
    }

    /**
     * Add an mail profile in your MailManager configuration
     * @param profile New mail profile
    */
    public void addProfile(MailProfile profile) {
        profiles.add(profile);
    }
}
