import java.io.*;
import java.sql.*;
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.util.Random;

@WebServlet("/validateCode")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class validateCode extends HttpServlet {

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
         String sqlStr = "";
         String validate = "";
         
         //Get the value from the database and cross check with the code from the android app
         sqlStr= "select * from databaseCodes ORDER BY id DESC LIMIT 1;";
         ResultSet rs = stmt.executeQuery(sqlStr);
         if(rs.next()){
            if(rs.getInt("code") == Integer.parseInt(code)){
               validate = "Correct";
            }else{
               validate = "Incorrect";
            }
         }

         out.write(validate);
      } catch(Exception ex) {
         ex.printStackTrace();
      } 
      out.close();

  }
}