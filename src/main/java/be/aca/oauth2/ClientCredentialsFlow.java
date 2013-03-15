package be.aca.oauth2;

import be.aca.oauth2.constants.OAuth2Provider;
import be.aca.oauth2.util.Util;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Client Credentials Flow implementation.
 */
public class ClientCredentialsFlow {

    public static void main(String[] args) throws Exception {
        // Prepare the parameters
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
        parameters.add(new BasicNameValuePair("client_id", OAuth2Provider.FACEBOOK_CLIENT_ID));
        parameters.add(new BasicNameValuePair("client_secret", OAuth2Provider.FACEBOOK_CLIENT_SECRET));

        // Call it
        String body = Util.sendHttpPost(OAuth2Provider.FACEBOOK_TOKEN_URL, parameters);
        Map<String, String> values = Util.parseFormEncoding(body);
        System.out.println(values);

        // Call API
        getInsights(OAuth2Provider.FACEBOOK_CLIENT_ID, values.get("access_token"));
    }

    private static void getInsights(String clientId, String accessToken) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder("https://graph.facebook.com/" + clientId + "/insights");
        builder.addParameter("access_token", accessToken);

        HttpGet request = new HttpGet(builder.build());
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(request);

        System.out.println(response.getStatusLine());
        response.getEntity().writeTo(System.out);
    }

}
