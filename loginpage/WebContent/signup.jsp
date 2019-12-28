<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>signup</title>
</head>
<body>
<% 
Class.forName("com.mysql.jdbc.Driver");
Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/login","root","aravind");
PreparedStatement ps1=con.prepareStatement("select * from details where mobileno=?");
ps1.setString(1,request.getParameter("mobileno"));
ResultSet rs=ps1.executeQuery();
if (rs.isBeforeFirst()) {    
    out.println("account exists already please login");
    %><%@ include file="login.html"%>
   <%}
else
{
	String pass=request.getParameter("pass");
	String repass=request.getParameter("repass");
	if(!pass.equals(repass))
	{
		out.println("password and repassword not match");%>
		<%@ include file="signup.html"%>
   <%}
	else
	{
	PreparedStatement ps2=con.prepareStatement("insert into details values(?,?,?,?)");
	ps2.setString(1,request.getParameter("name"));
	ps2.setString(2,request.getParameter("mail"));
	ps2.setString(3,request.getParameter("mobileno"));
	ps2.setString(4,request.getParameter("pass"));
	ps2.executeUpdate();
	}
}
%>	
</body>
</html>