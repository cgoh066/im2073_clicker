// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;             // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/select")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class select extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      
      PrintWriter out = response.getWriter();

      try (
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/disney_trivia?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");  

         Statement stmt = conn.createStatement();
      ) {
         // Step 3 and 4 of the database servlet
         // Assume that the URL is http://ip-addr:port/clicker/select?choice = x
         // Assume that the questionNo is 8
         int count;
         String sqlStr = "";
         ResultSet rs;
         String choice = request.getParameter("choice");

         //Check current qnNo
         sqlStr = "select * from trackQn ORDER BY ID DESC LIMIT 1;";
         rs = stmt.executeQuery(sqlStr);
         if (rs.next()==false){
            sqlStr = "INSERT INTO trackQn (qnNumber) values (1);";
            count = stmt.executeUpdate(sqlStr);
            sqlStr = "INSERT INTO responses (questionNo,choice) VALUES (1,'" + choice + "')";
            count = stmt.executeUpdate(sqlStr);
         }else{
            int newQnNo = rs.getInt("qnNumber");
            sqlStr = "INSERT INTO responses (questionNo,choice) VALUES (" + newQnNo + ",'" + choice + "')";
            count = stmt.executeUpdate(sqlStr);
         }
         //count = stmt.executeUpdate(sqlStr); 
      } catch(Exception ex) {
         ex.printStackTrace();
      } 
      out.close();
   }
}