// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;             // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/nextQn")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class nextQn extends HttpServlet {

   private String[] QUESTIONS = new String[10];
   private int currentQuestionIndex = -1;
    
   // The doGet() runs once per HTTP GET request to this servlet.
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
         String sqlStr = "select * from questions";
         ResultSet rs = stmt.executeQuery(sqlStr);
         int i = 0;
         while(rs.next()){
            QUESTIONS[i] = rs.getString("question");
            ++i;
         }

         int count = 0;
         int qnNo = currentQuestionIndex;
         qnNo = qnNo + 1;

         //track the qnNo
         sqlStr = "INSERT INTO trackQn (qnNumber) VALUES (" + qnNo + ");";
         count = stmt.executeUpdate(sqlStr); 


         //Step 4: Get the mcq options of the currentQuestionIndex using SQL SELECT Query
         sqlStr = "select * from mcq where choiceID = " + qnNo ;
         rs = stmt.executeQuery(sqlStr);
         int j = 0;
         String[] options = new String[4];
         while(rs.next()){
            options[j] = rs.getString("choice");
            ++j;
         }

         if (currentQuestionIndex < QUESTIONS.length) {
            String currentQuestion = QUESTIONS[currentQuestionIndex];

            //Send the mcq options here
            request.setAttribute("currentQuestion", currentQuestion);
            request.setAttribute("optionA",options[0]);
            request.setAttribute("optionB",options[1]);
            request.setAttribute("optionC",options[2]);
            request.setAttribute("optionD",options[3]);
            request.setAttribute("qnNo",qnNo);
            request.getRequestDispatcher("questions.jsp").forward(request, response);
         } else {
            currentQuestionIndex = -1;
            //print out the quiz completed page
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel = \"stylesheet\" href=\"app.css\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class = \"container\">");
            out.println("<div id = \"home\" class=\"flex-center flex-column\"/>");
            out.println("<h1> Quiz Completed! </h1>");
            out.println("</div>");
            out.println("</div>");
            out.println("</body></html>");
            response.setHeader("Refresh","5; URL=http://localhost:9999/clicker/homepage.html");
         }


      } catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         ex.printStackTrace();
      }
 

      out.close();
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         currentQuestionIndex++;
         doGet(request, response);
    }
}