<%@ page language="java" import="java.net.*,java.io.*" %>
<%
 URL yahoo = new URL("http://in.yahoo.com/");
 URLConnection yc = yahoo.openConnection();
 BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
 String inputLine;
 StringBuilder sb=new StringBuilder();
%>
<html>
<head>
<title>How to get source code of website</title>
</head>

<body>
<%
while ((inputLine = in.readLine()) != null) 
{
   sb.append(inputLine);
} 

   in.close();
%>
<textarea name="scode" rows="25" cols="100"><%=sb%></textarea>
</body>
</html>
