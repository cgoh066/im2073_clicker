<!DOCTYPE html>
<html lang="en">
  <head>
    <link rel="stylesheet" href="app.css" />
    <link rel="stylesheet" href="quizQn.css" />
    <style>
      body {
          background: linear-gradient(135deg, #e5def8 0%, #9e75f5 100%);
          font-size: 62.5%;
          font-family: "Arial", sans-serif;
      }

      h2 {
          font-size: 3.2rem;
          color: #a485dd;
          text-align: center;
          margin-bottom: 2rem;
          animation: fadeIn 0.6s ease-in-out;
      }

      .container {
          width: 100vw;
          height: 100vh;
          display: flex;
          justify-content: center;
          align-items: center;
          padding: 2rem;
      }

      #questions {
          background-color: #ffffff;
          border-radius: 1.6rem;
          box-shadow: 0 8px 24px rgba(174, 153, 230, 0.2);
          padding: 3rem 4rem;
          width: 90%;
          max-width: 600px;
          animation: fadeIn 0.5s ease;
      }

      .choice-container {
          display: flex;
          align-items: center;
          margin: 1.2rem 0;
          padding: 1.4rem 2rem;
          background-color: #f5efff;
          border-radius: 1.2rem;
          box-shadow: 0 4px 12px rgba(174, 153, 230, 0.1);
          transition: transform 0.2s ease, box-shadow 0.3s ease;
          cursor: pointer;
      }

      .choice-container:hover {
          transform: scale(1.05);
          box-shadow: 0 6px 16px rgba(174, 153, 230, 0.2);
      }

      .choice-prefix {
          font-weight: bold;
          font-size: 1.8rem;
          color: #a485dd;
          margin-right: 1rem;
      }

      .choice-text {
          font-size: 1.6rem;
          color: #5d4b8b;
      }

      .button-group {
          margin-top: 2rem;
          display: flex;
          gap: 1.2rem;
          justify-content: center;
          flex-wrap: wrap;
      }

      .submit-btn, .qn-btn {
          background-color: #8c5ed6;
          color: #fff;
          padding: 1rem 2rem;
          border: none;
          border-radius: 1rem;
          font-size: 1.6rem;
          transition: background-color 0.3s ease, transform 0.2s ease;
          cursor: pointer;
      }

      .submit-btn:hover, .qn-btn:hover {
          background-color: #ac89ef;
          transform: scale(1.05);
      }

      @keyframes fadeIn {
          from {
              opacity: 0;
              transform: translateY(10px);
          }
          to {
              opacity: 1;
              transform: translateY(0);
          }
      }

      #timer {
          font-size: 2rem;
          color: red;
          text-align: center;
          margin-bottom: 2rem;
      }
  </style>
    <script>
      // Timer function
      let timeLeft = 20; // Set countdown time in seconds

      function startTimer() {
        const timerElement = document.getElementById("timer");
        const interval = setInterval(() => {
          if (timeLeft <= 0) {
            clearInterval(interval);
            document.getElementById("quizForm").submit(); // Auto-submit the form when time is up
          } else {
            timerElement.innerText = timeLeft + " seconds remaining";
            timeLeft--;
          }
        }, 1000); // Update every second
      }

      // Start the timer when the page loads
      window.onload = startTimer;
    </script>
  </head>
  <body>
    <div class="container">
      <div id="questions" class="justify-center flex-column">
        <% String currentQn = (String) request.getAttribute("currentQuestion"); %>
        <% String optionA = (String) request.getAttribute("optionA"); %>
        <% String optionB = (String) request.getAttribute("optionB"); %>
        <% String optionC = (String) request.getAttribute("optionC"); %>
        <% String optionD = (String) request.getAttribute("optionD"); %>

        <!-- Timer Display -->
        <h3 id="timer" style="color: red;">Loading timer...</h3>

        <% if (currentQn != null) { %>
          <h2 id="question"><%= request.getAttribute("currentQuestion") %></h2>
          <form method="GET" action="startStop">
            <div class="start-stop-group">
              <input type="submit" class="submit-btn" style="background-color: #56a5eb; color: white; border: 0.1rem solid white; border-radius: 8px" name="start_btn" value="Start">
              <input type="submit" class="submit-btn" style="background-color: #56a5eb; color: white; border: 0.1rem solid white; border-radius: 8px" name="stop_btn" value="Stop">
            </div>
          </form>
        <% } else { %>
          <h2 id="question">Are you ready?</h2>
          <form method="GET" action="startStop">
            <div class="start-stop-group">
              <input type="submit" class="submit-btn" style="background-color: #56a5eb; color: white; border: 0.1rem solid white; border-radius: 8px" name="start_btn" value="Start">
              <input type="submit" class="submit-btn" style="background-color: #56a5eb; color: white; border: 0.1rem solid white; border-radius: 8px" name="stop_btn" value="Stop">
            </div>
          </form>
        <% } %>

        <!-- Options -->
        <form method="POST" action="nextQn" id="quizForm">
          <div class="choice-container">
            <p class="choice-prefix">A</p>
            <% if (optionA != null) { %>
              <p class="choice-text"><%= request.getAttribute("optionA") %></p>
            <% } else { %>
              <p class="choice-text">Yes</p>
            <% } %>
          </div>
          <div class="choice-container">
            <p class="choice-prefix">B</p>
            <% if (optionB != null) { %>
              <p class="choice-text"><%= request.getAttribute("optionB") %></p>
            <% } else { %>
              <p class="choice-text">No</p>
            <% } %>
          </div>
          <div class="choice-container">
            <p class="choice-prefix">C</p>
            <% if (optionC != null) { %>
              <p class="choice-text"><%= request.getAttribute("optionC") %></p>
            <% } else { %>
              <p class="choice-text"> </p>
            <% } %>
          </div>
          <div class="choice-container">
            <p class="choice-prefix">D</p>
            <% if (optionD != null) { %>
              <p class="choice-text"><%= request.getAttribute("optionD") %></p>
            <% } else { %>
              <p class="choice-text"> </p>
            <% } %>
          </div>
          <div style="width:100%;">
            <button type="submit" class="qn-btn" style="border-radius:8px;">Next Question</button>
          </div>
        </form>

        <!-- Result Statistics -->
        <form method="GET" action="display">
          <button type="submit" class="qn-btn" style="border-radius:8px">Result Statistics</button>
        </form>
      </div>
    </div>
  </body>
</html>