package be.aca.oauth2.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Util {

    public static URL getAbsoluteUrl(HttpServletRequest request, String relativePath) throws MalformedURLException {
        URL requestUrl = new URL(request.getRequestURL().toString());
        return new URL(requestUrl, relativePath);
    }

    public static String sendHttpGet(String url) throws IOException {
        return sendHttpRequest(new HttpGet(url));
    }

    public static String sendHttpPost(String url, Collection<NameValuePair> parameters) throws IOException {
        HttpPost post = new HttpPost(url);
        post.setEntity(new UrlEncodedFormEntity(parameters));

        return sendHttpRequest(post);
    }

    public static String sendHttpRequest(HttpUriRequest request) throws IOException {
        HttpResponse response = new DefaultHttpClient().execute(request);
        if (response.getStatusLine().getStatusCode() != 200) {
            System.err.println(response.getStatusLine());
            response.getEntity().writeTo(System.err);
            return null;
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        response.getEntity().writeTo(output);
        return new String(output.toByteArray());
    }

    public static Map<String, String> parseJson(String json) {
        Map<String, String> result = new HashMap<String, String>();

        if (StringUtils.isBlank(json)) {
            return result;
        }

        JsonObject jsonObject = (JsonObject) new JsonParser().parse(json);
        for(Map.Entry<String, JsonElement> entry: jsonObject.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getAsString());
        }

        return result;
    }

    public static Map<String, String> parseFormEncoding(String body) {
        Map<String, String> result = new HashMap<String, String>();

        String[] parameters = body.split("&");
        for(String parameter : parameters) {
            String[] nameValue = parameter.split("=");
            result.put(nameValue[0], nameValue[1]);
        }

        return result;
    }
}
