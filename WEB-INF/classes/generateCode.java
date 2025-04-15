import java.io.*;
import java.sql.*;
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.util.Random;

@WebServlet("/generateCode")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class generateCode extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    // Retrieve the code from the hidden input field
    String code = request.getParameter("code");

    PrintWriter out = response.getWriter();

    try (
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/disney_trivia?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");  

         Statement stmt = conn.createStatement();
      ) {
         int count=0;
         String sqlStr = "";
         
         //check the last status of the sql duration table and send back a response corresponding to start/stop to android app
         sqlStr= "insert into databaseCodes (code) values(" + code + ");";
         count = stmt.executeUpdate(sqlStr);
      } catch(Exception ex) {
         ex.printStackTrace();
      } 
      out.close();

  }
}