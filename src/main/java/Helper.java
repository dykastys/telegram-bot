import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Helper {

    private static Properties properties;

    public static Properties getProperties() throws IOException {
        if(properties != null) {
            return properties;
        }
        properties = new Properties();
        try(FileInputStream fis = new FileInputStream("props.properties")) {
            properties.load(fis);
            return properties;
        }catch (IOException e) {
            properties = null;
            throw new IOException(e);
        }
    }

    public static String[] getTextToAnswer(String text) {
        if(text.contains("\n")) {
            return text.split("\n");
        } if(text.contains(" ")) {
            return text.split(" ");
        }else {
            return new String[]{text};
        }
    }

    public static boolean needToAnswer(String text) {
        if(text.isEmpty()) {
            return false;
        }
        return  (text.matches("^[a-zA-Z]+-\\d+:")) ||
                (!text.contains("/") && text.matches("[a-zA-Z]+-\\d+$")) ||
                (text.matches(".+jira.inttrust.ru:8443/browse/[a-zA-Z]+-\\d+$"));
    }

    public static String makeAnswer(String text) throws IOException {
        String issueId = processText(text);
        String issueInfo = JiraWorker
                                .getInstance()
                                .getIssueInfo(issueId);
        return "<a href=\"" + getProperties().getProperty("URL") + issueId + "\">" + issueId.toUpperCase() + "</a>" + ": " + issueInfo;
    }

    private static String processText(String text) {
        if (!text.contains("/") && text.matches("[a-zA-Z]+-\\d+$")) {
            return text;
        } else if (text.matches(".+jira.inttrust.ru:8443/browse/[a-zA-Z]+-\\d+$")) {
            text = text.substring(text.lastIndexOf("/") + 1);
            return text;
        } else if (text.matches("^[a-zA-Z]+-\\d+:")) {
            return text.substring(0, text.indexOf(":"));
        } else {
            return text;
        }
    }
}
