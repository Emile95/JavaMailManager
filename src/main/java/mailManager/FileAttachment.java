package mailManager;

import javax.activation.DataSource;

public class FileAttachment {
    
    private DataSource source;
    public DataSource getSource() { return source; }
    
    private String fileName;
    public String getFileName() { return fileName; }

    public FileAttachment(DataSource source, String fileName) {
        this.source = source;
        this.fileName = fileName;
    }

}
