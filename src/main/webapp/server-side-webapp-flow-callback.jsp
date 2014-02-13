<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="org.apache.http.NameValuePair" %>
<%@ page import="org.apache.http.client.methods.HttpGet" %>
<%@ page import="org.apache.http.client.utils.URIBuilder" %>
<%@ page import="org.apache.http.message.BasicNameValuePair" %>
<%@ page import="be.aca.oauth2.constants.OAuth2Provider" %>
<%@ page import="be.aca.oauth2.util.Util" %>

<%
    // Verify the state
    String incomingState = request.getParameter("state");
    String expectedState = (String) session.getAttribute("oauth2-state");
    if (!StringUtils.equals(incomingState, expectedState)) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid state");
        return;
    }

    // Build parameter list
    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    parameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
    parameters.add(new BasicNameValuePair("client_id", OAuth2Provider.GOOGLE_CLIENT_ID));
    parameters.add(new BasicNameValuePair("client_secret", OAuth2Provider.GOOGLE_CLIENT_SECRET));
    parameters.add(new BasicNameValuePair("code", request.getParameter("code")));
    parameters.add(new BasicNameValuePair("redirect_uri", request.getRequestURL().toString()));

    // Send post to token service
    String output = Util.sendHttpPost(OAuth2Provider.GOOGLE_TOKEN_URL, parameters);
    if (output == null) {
        return;
    }

    // Extract JSON
    Map<String, String> values = Util.parseJson(output);
%>

<html>
<head>
    <title>Server Side Web Application Flow Callback</title>
</head>

<body>
<p>
    Returned values: <%= values %>
</p>

<%
    // Validate the user id
    URIBuilder builder = new URIBuilder(OAuth2Provider.GOOGLE_TOKENINFO_URI);
    builder.addParameter("id_token", values.get("id_token"));

    String tokenInfo = Util.sendHttpGet(builder.toString());
%>

<p>
    Token info: <%= tokenInfo %>
</p>

<%
    // Get the user info
    HttpGet get = new HttpGet(OAuth2Provider.GOOGLE_USERINFO_URI);
    get.setHeader("Authorization", "Bearer " + values.get("access_token"));
    String userInfo = Util.sendHttpRequest(get);
%>

<p>
    User info: <%= userInfo %>
</p>
</body>
</html>