// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;             // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/start")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class start extends HttpServlet {

   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();


      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/disney_trivia?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ) {
         // Step 3: Execute a SQL SELECT Query
         String sqlStr = "";
         int count = 0;

         //reset the database tables of responses and mcqQn
         sqlStr = "DELETE FROM responses;";
         count = stmt.executeUpdate(sqlStr);
         
         //track the qnNo
         sqlStr = "INSERT INTO trackQn (qnNumber) VALUES (1);";
         count = stmt.executeUpdate(sqlStr); 
         response.sendRedirect("http://localhost:9999/clicker/questions.jsp");


      } catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         ex.printStackTrace();
      }
      out.close();
   }
}