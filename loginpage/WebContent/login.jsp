<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
Class.forName("com.mysql.jdbc.Driver");
Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/login","root","aravind");
PreparedStatement ps1=con.prepareStatement("select * from details where email=?");
ps1.setString(1,request.getParameter("mail"));
ResultSet rs=ps1.executeQuery();
if (!rs.isBeforeFirst()) {
	out.println("create account");
	%><%@ include file="signup.html"%>
	   <%}
else
{
	PreparedStatement ps2=con.prepareStatement("select * from details where email=? and password=?");
	ps2.setString(1,request.getParameter("mail"));
	ps2.setString(2,request.getParameter("pass"));
	ResultSet rs1=ps2.executeQuery();
	if(!rs1.isBeforeFirst())
	{
		out.println("password and username donot match");
		%><%@ include file="login.html"%>
		   <%}
	else
	{
		out.println("welcome");
		
	}
	
}

    
    

%>

</body>
</html>