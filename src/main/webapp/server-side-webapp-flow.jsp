<%@ page import="java.net.URL" %>
<%@ page import="org.apache.commons.lang3.RandomStringUtils" %>
<%@ page import="org.apache.http.client.utils.URIBuilder" %>
<%@ page import="be.aca.oauth2.constants.OAuth2ProviderConstants" %>
<%@ page import="be.aca.oauth2.util.Util" %>

<%
    // Build URI
    URIBuilder builder = new URIBuilder(OAuth2ProviderConstants.GOOGLE_AUTH_URL);

    // Add state
    String state = RandomStringUtils.random(32, true, true);
    session.setAttribute("oauth2-state", state);
    builder.addParameter("state", state);

    // Add url
    URL redirectUrl = Util.getAbsoluteUrl(request, "server-side-webapp-flow-callback.jsp");
    builder.addParameter("redirect_uri", redirectUrl.toString());

    // Add other parameters
    builder.addParameter("client_id", OAuth2ProviderConstants.GOOGLE_CLIENT_ID);
    builder.addParameter("response_type", "code");
    builder.addParameter("scope", "profile email");
    builder.addParameter("approval_prompt", "force");

    String oauthUrl = builder.toString();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Server Side Web Application Flow</title>
</head>

<body>
<p>
    <a href="<%=oauthUrl%>">Log in with Google.</a>
</p>
</body>

</html>