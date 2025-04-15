// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;             // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/login")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class login extends HttpServlet {

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
         String validate = "";
         ResultSet rs;

         //get parameters username and password from mobile app
         String username = request.getParameter("username");
         String password = request.getParameter("password");

         //Cross check with sql to validate
         sqlStr = "select * from users WHERE username = '" + username + "' and password='" + password +"';";
         rs = stmt.executeQuery(sqlStr);
         if(rs.next()!=false){
            //if the user is found
            validate="found";
         }else{
            validate="not found";
         }
         out.write(validate);
      } catch(Exception ex) {
         ex.printStackTrace();
      } 
      out.close();
   }
}