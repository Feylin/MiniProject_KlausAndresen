package Controllers;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by spole_000 on 22/05/2015.
 */
public class URLEncoder {
    public static URL getEncodedURLString(String source) throws MalformedURLException, URISyntaxException {
        URL url = new URL(source);
        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());

        return new URL(uri.toASCIIString());
    }
}
