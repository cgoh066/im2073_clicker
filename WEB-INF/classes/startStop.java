// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;             // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/startStop")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class startStop extends HttpServlet {

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
         int count = 0,qnNo = 0;
         String sqlStr = "",question="";
         ResultSet rs;
         String startBtn = request.getParameter("start_btn");
         String stopBtn = request.getParameter("stop_btn");


         //check which buttons were pressed and insert the status into the duration table 
         if(startBtn!=null && stopBtn==null){
            sqlStr = "INSERT INTO duration (status) values('Start');";
         }else if (startBtn==null && stopBtn!=null){
            sqlStr = "INSERT INTO duration (status) values ('Stop');";
         }
         count = stmt.executeUpdate(sqlStr);

         //get current qnIndex
         sqlStr = "select * from trackqn ORDER BY ID DESC LIMIT 1;";
         rs = stmt.executeQuery(sqlStr);
         if(rs.next()){
            qnNo = rs.getInt("qnNumber");
         }

         //Get the question and mcq choices of that qnNo
         String[] options = new String[4];

         if(qnNo == 1){
            question = "Are you ready?";
            options[0] = "Yes";
            options[1] = "No";
            options[2] = " ";
            options[3] = " ";
         }else{
            sqlStr = "select * from questions where qnID = " + qnNo;
            rs = stmt.executeQuery(sqlStr);
            if(rs.next()){
               question = rs.getString("question");
            }

            sqlStr = "select * from mcq where choiceID = " + qnNo ;
            rs = stmt.executeQuery(sqlStr);
            int j = 0;
            while(rs.next()){
               options[j] = rs.getString("choice");
               ++j;
            }
         }

         //send it to jsp
         request.setAttribute("currentQuestion", question);
         request.setAttribute("optionA",options[0]);
         request.setAttribute("optionB",options[1]);
         request.setAttribute("optionC",options[2]);
         request.setAttribute("optionD",options[3]);
         request.getRequestDispatcher("questions.jsp").forward(request, response);
         response.sendRedirect("http://localhost:9999/disney_trivia/questions.jsp");

      } catch(Exception ex) {
         ex.printStackTrace();
      } 
      out.close();
   }
}