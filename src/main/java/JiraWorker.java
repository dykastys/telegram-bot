import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import io.atlassian.util.concurrent.Promise;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class JiraWorker {

    private final Logger logger = Logger.getLogger(JiraWorker.class);

    private static final JiraWorker instance = new JiraWorker();

    private JiraRestClient restClient;

    private JiraWorker() {
        try {
            this.restClient = getConnection();
            logger.info("jira connection success");
        } catch (URISyntaxException | IOException e) {
            logger.error("jira connection is failed", e);
        }
    }

    public static JiraWorker getInstance() {
        return instance;
    }

    public String getIssueInfo(String issueId) {
        IssueRestClient issueRestClient = restClient.getIssueClient();
        try {
            Promise<Issue> promise = issueRestClient.getIssue(issueId);
            Issue issue = promise.claim();
            return issue.getSummary();
        } catch (Exception e) {
            throw new RuntimeException(String.format("issue with id: %s not found", issueId), e);
        }
    }

    private JiraRestClient getConnection() throws URISyntaxException, IOException {
        Properties properties = Helper.getProperties();
        if(properties == null) {
            throw new RuntimeException("load properties is failed");
        }
        URI uri = new URI(properties.getProperty("URI_JIRA"));
        return new AsynchronousJiraRestClientFactory()
                .createWithBasicHttpAuthentication(uri,
                        properties.getProperty("JIRA_LOGIN"),
                        properties.getProperty("JIRA_PASSWORD"));
    }
}