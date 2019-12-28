

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/otpvalidation")
public class otpvalidation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     public otpvalidation() {
        super();
        
    }

		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				HttpSession s=request.getSession(true);
				Class.forName("com.mysql.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/login","root","aravind");
			    PreparedStatement ps1=con.prepareStatement("select count(*) from  otp where email=? and otp=?");
			    ps1.setString(1,(String)s.getAttribute("mail"));
			    ps1.setInt(2,Integer.parseInt(request.getParameter("otp")));
		        ResultSet rs=ps1.executeQuery();
		        rs.next();
		        if(rs.getInt(1)>0)
		        {
		        	response.sendRedirect("changepassword.html");
		        }
		        else
		        {
		        	PrintWriter obj=response.getWriter();
		        	response.setContentType("text/html");
		        	obj.println("wrong OTP?");
		        	RequestDispatcher o=request.getRequestDispatcher("forgotpassword.html");
		        	o.include(request,response);
		        	
		        }
		        
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    	 
		}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
