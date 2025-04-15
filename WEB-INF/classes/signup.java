// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;             // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/signup")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class signup extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();

      try (
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/disney_trivia?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");  

         Statement stmt = conn.createStatement();
      ) {
         //Declare variables
         String sqlStr = "";
         int count = 0;
         String validate = "";

         //get parameters fullname, username, email and password from mobile app
         String fullname = request.getParameter("fullname");
         String username = request.getParameter("username");
         String email = request.getParameter("email");
         String password = request.getParameter("password");

         //Insert into sql database user table 
         sqlStr = "insert into users(fullname, username, email,password) values ('" + fullname + "', '" + username + "', '" + email + "', '" + password + "');";
         count = stmt.executeUpdate(sqlStr);
         if(count!=0){
            //if the record is inserted successfully
            validate="successful";
         }else{
            validate="not successful";
         }
         out.write(validate);
      } catch(Exception ex) {
         ex.printStackTrace();
      } 
      out.close();
   }
}