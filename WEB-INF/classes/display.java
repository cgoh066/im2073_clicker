// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import java.util.*;
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;             // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/display")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class display extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {

      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      // Print an HTML page as the output of the query

      try (
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/disney_trivia?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx"); 
         Statement stmt = conn.createStatement();
      ) {

         String[] options = new String[4];
         Integer[] responsesNo = new Integer[4];
         int correctAns = 0;
         int qnNo = 0;
         ResultSet rs;
         String sqlStr = "";

         // Send sql query for the numbers of responses for A, B , C and D option for each question and run the statement
         sqlStr = "select * from trackQn ORDER BY ID DESC LIMIT 1;";
         rs = stmt.executeQuery(sqlStr);
         if (rs.next()==false){
            sqlStr = "select choice, COUNT(*) as count FROM responses WHERE questionNo = 1 GROUP BY choice ORDER BY choice ASC";
            qnNo = 1;
         }else{
            int newQnNo = rs.getInt("qnNumber");
            qnNo = newQnNo;
            sqlStr = "select choice, COUNT(*) as count FROM responses WHERE questionNo = " + newQnNo + " GROUP BY choice ORDER BY choice ASC";
         }

         rs = stmt.executeQuery(sqlStr);
         //get result from sql query
         int i = 0;
         while(rs.next()){
            options[i] = rs.getString("choice");
            responsesNo[i] = rs.getInt("count");
            i++;
         }

         //Get the correctAnsIndex by sending sql query
         if(qnNo==1){
            correctAns = 1;
         }else{
            sqlStr = "select * from questions where qnID=" + qnNo;
            rs = stmt.executeQuery(sqlStr);
            if(rs.next()){
               correctAns = rs.getInt("correctAns");
            }
         }

         //close database connection
         rs.close();
         stmt.close();
         conn.close();

         //re-edit to print out bar chart here
         out.println("<!DOCTYPE html>");
         out.println("<html>");
         out.println("<head>");
         out.println("<title>Bar Chart Example</title>");
         out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js'></script>");
         out.println("</head>");
         out.println("<body>");
         out.println("<canvas id='myChart' style='height:100%'></canvas>");
         out.println("<script>");
         out.println("var ctx = document.getElementById('myChart').getContext('2d');");
         out.print("var labels = [");
         for (int j = 0; j < options.length; j++) {
            out.print("'" + options[j] + "',");
         }
         out.println("];");
         out.print("var data = [");
         for (int k = 0; k < responsesNo.length; k++) {
            out.print(responsesNo[k] + ",");
         }
         out.println("];");
         out.println("var myChart = new Chart(ctx, {");
         out.println("    type: 'bar',");
         out.println("    data: {");
         out.println("        labels: labels,");
         out.println("        datasets: [{");
         out.println("            label: 'Result Statistics',");
         out.println("            data: data,");
         out.println("            backgroundColor: [");
         for(int b = 1;b<=4;++b){
            if(b==correctAns){
               out.println("                'rgba(75, 192, 192, 0.2)',");
            }else{
               out.println("                'rgba(255, 99, 132, 0.2)',");
            }
         }
         //out.println("                'rgba(255, 99, 132, 0.2)',"); //red
         //out.println("                'rgba(54, 162, 235, 0.2)',"); //blue
         //out.println("                'rgba(255, 206, 86, 0.2)',"); //yellow
         //out.println("                'rgba(75, 192, 192, 0.2)',"); //cyan
         //out.println("                'rgba(153, 102, 255, 0.2)',"); //purple
         //out.println("                'rgba(255, 159, 64, 0.2)',"); //orange
         //out.println("                'rgba(255, 99, 132, 0.2)'"); //red
         out.println("            ],");
         out.println("            borderColor: [");
         for(int l = 1;l<=4;++l){
            if(l==correctAns){
               out.println("                'rgba(75, 192, 192, 1)',");
            }else{
               out.println("                'rgba(255, 99, 132, 1)',");
            }
         }
         //out.println("                'rgba(255, 99, 132, 1)',");
         //out.println("                'rgba(54, 162, 235, 1)',");
         //out.println("                'rgba(255, 206, 86, 1)',");
         //out.println("                'rgba(75, 192, 192, 1)',");
         //out.println("                'rgba(153, 102, 255, 1)',");
         //out.println("                'rgba(255, 159, 64, 1)',");
         //out.println("                'rgba(255, 99, 132, 1)'");
         out.println("            ],");
         out.println("            borderWidth: 1");
         out.println("        }]");
         out.println("    },");
         out.println("    options: {");
         out.println("        animation: {");
         out.println("            animation: false");
         out.println("        },");
         out.println("        scales: {");
         out.println("            yAxes: [{");
         out.println("                ticks: {");
         out.println("                    beginAtZero: true");
         out.println("                }");
         out.println("            }]");
         out.println("        }");
         out.println("    }");
         out.println("});");
         out.println("</script>");
         out.println("</body>");
         out.println("</html>");

      } catch(Exception ex) {
         ex.printStackTrace();
         out.println("Error: " + ex.getMessage());
      } 
      //out.println("</body></html>");
      out.close();
   }
}