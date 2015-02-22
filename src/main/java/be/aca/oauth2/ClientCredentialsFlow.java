package be.aca.oauth2;

import be.aca.oauth2.constants.OAuth2ProviderConstants;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

public class ClientCredentialsFlow {

    public static void main(String[] args) throws Exception {
        // Call API
        String accessToken = null; // TODO provide
        getInsights(OAuth2ProviderConstants.FACEBOOK_CLIENT_ID, accessToken);
    }

    private static void getInsights(String clientId, String accessToken) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder("https://graph.facebook.com/" + clientId + "/insights");
        builder.addParameter("access_token", accessToken);

        HttpGet request = new HttpGet(builder.build());
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(request);

        System.out.println(response.getStatusLine());
        response.getEntity().writeTo(System.out);
    }

}
