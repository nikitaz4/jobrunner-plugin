package sample.plugin;
import java.net.*;
import java.io.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import javax.xml.bind.DatatypeConverter;

/**
 * Says "Hi" to a user given in a parameters
 */
@Mojo(name = "sayhiTo")
public class GreetingMojoWithParameters extends AbstractMojo {

    /**
     * The user to display.
     * The parameter annotation identifies the variable as a mojo parameter.
     *
     * The default value can include expressions which reference the project, such as "${project.version}"
     * See http://maven.apache.org/ref/current/maven-core/apidocs/org/apache/maven/plugin/PluginParameterExpressionEvaluator.html
     *
     * The property parameter can be used to allow configuration of the mojo parameter from the command line by referencing a system property that the user sets via the -D option.
     */
    @Parameter( property = "sayhiTo.jenkinsServer", defaultValue = "http://localhost:8080/job/buildFromMavenPlugin/build?delay=0sec" )
    private String jenkinsServer;

	@Parameter( property = "sayhiTo.jobName", defaultValue = "http://localhost:8080/job/buildFromMavenPlugin/build?delay=0sec" )
	private String jobName;

	@Parameter( property = "sayhiTo.postFix", defaultValue = "http://localhost:8080/job/buildFromMavenPlugin/build?delay=0sec" )
	private String postFix;

    public void execute() throws MojoExecutionException {
        getLog().info("going to trigger jenkins job...");
	try{
		getLog().info("POST request: " + jenkinsServer + jobName + postFix);
		URL url = new URL(jenkinsServer + jobName + postFix);
		String user = "nikitaz"; // username
		String pass = "a73b16653b78aecd0c3b5b418b3c7b30"; // password or API token
		String authStr = user +":"+  pass;
		String encoding = DatatypeConverter.printBase64Binary(authStr.getBytes("utf-8"));

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
//		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("POST");
//		connection.setRequestProperty("Content-Type", "text/plain");
//		connection.setRequestProperty("charset", "utf-8");
		connection.setRequestProperty("Authorization", "Basic " + encoding);
		InputStream content = connection.getInputStream();
		BufferedReader in   =
				new BufferedReader (new InputStreamReader (content));
		String line;
		while ((line = in.readLine()) != null) {
			System.out.println(line);
		}
//		connection.connect();
//		connection.connect();
//		System.out.println(connection);
	}catch(Exception e){
			e.printStackTrace();
        }
    }
}
