<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 8/4/2025
  Time: 8:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Pahana Edu - Login</title>
  <style>
    :root {
      --primary-color: #4285F4; /* Google Blue */
      --secondary-color: #34A853; /* Google Green */
      --accent-color: #EA4335; /* Google Red */
      --light-bg: #F8F9FA;
      --dark-text: #202124;
      --light-text: #FFFFFF;
      --border-color: #DADCE0;
      --error-color: #D33426;
      --info-color: #1A73E8;
    }

    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
      font-family: 'Roboto', 'Arial', sans-serif;
    }

    body {
      background-color: var(--light-bg);
      color: var(--dark-text);
      min-height: 100vh;
      display: flex;
      justify-content: center;
      align-items: center;
      background-image: linear-gradient(rgba(255,255,255,0.95), rgba(255,255,255,0.95)),
      url('https://images.unsplash.com/photo-1524995997946-a1c2e315a42f?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');
      background-size: cover;
      background-position: center;
      background-attachment: fixed;
    }

    .login-container {
      width: 380px;
      padding: 40px;
      background: var(--light-text);
      border-radius: 12px;
      box-shadow: 0 10px 25px rgba(0,0,0,0.08);
      transition: transform 0.3s ease, box-shadow 0.3s ease;
    }

    .login-container:hover {
      transform: translateY(-5px);
      box-shadow: 0 15px 30px rgba(0,0,0,0.1);
    }

    h2 {
      text-align: center;
      color: var(--primary-color);
      margin-bottom: 30px;
      font-size: 1.8rem;
      font-weight: 500;
    }

    .form-group {
      margin-bottom: 20px;
    }

    label {
      display: block;
      margin-bottom: 8px;
      font-weight: 500;
      color: var(--dark-text);
      font-size: 0.95rem;
    }

    input[type="text"],
    input[type="password"] {
      width: 100%;
      padding: 12px 15px;
      border: 1px solid var(--border-color);
      border-radius: 8px;
      font-size: 1rem;
      transition: all 0.3s ease;
      background-color: var(--light-bg);
    }

    input[type="text"]:focus,
    input[type="password"]:focus {
      outline: none;
      border-color: var(--primary-color);
      box-shadow: 0 0 0 2px rgba(66,133,244,0.2);
    }

    button {
      width: 100%;
      padding: 14px;
      background-color: var(--primary-color);
      color: var(--light-text);
      border: none;
      border-radius: 8px;
      font-size: 1rem;
      font-weight: 500;
      cursor: pointer;
      transition: all 0.3s ease;
      margin-top: 10px;
    }

    button:hover {
      background-color: #3367D6;
      transform: translateY(-2px);
      box-shadow: 0 4px 8px rgba(0,0,0,0.1);
    }

    button:active {
      transform: translateY(0);
    }

    .error {
      color: var(--light-text);
      background-color: var(--error-color);
      padding: 12px 15px;
      border-radius: 8px;
      margin-bottom: 20px;
      font-size: 0.9rem;
      display: flex;
      align-items: center;
      gap: 10px;
    }

    .error:before {
      content: "!";
      font-weight: bold;
      background: var(--light-text);
      color: var(--error-color);
      width: 20px;
      height: 20px;
      border-radius: 50%;
      display: flex;
      justify-content: center;
      align-items: center;
      font-size: 0.8rem;
    }

    .info {
      color: var(--dark-text);
      background-color: #E8F0FE;
      padding: 15px;
      border-radius: 8px;
      margin-top: 25px;
      font-size: 0.9rem;
      border-left: 4px solid var(--info-color);
    }

    .info p {
      margin-bottom: 8px;
    }

    .info p:last-child {
      margin-bottom: 0;
    }

    /* Responsive adjustments */
    @media (max-width: 480px) {
      .login-container {
        width: 90%;
        padding: 30px;
      }

      h2 {
        font-size: 1.5rem;
      }
    }

    /* Animation for form elements */
    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(10px); }
      to { opacity: 1; transform: translateY(0); }
    }

    .form-group, button, .error, .info {
      animation: fadeIn 0.4s ease forwards;
    }

    .form-group:nth-child(1) { animation-delay: 0.1s; }
    .form-group:nth-child(2) { animation-delay: 0.2s; }
    button { animation-delay: 0.3s; }
    .error, .info { animation-delay: 0.4s; }
  </style>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
</head>
<body>
<div class="login-container">
  <h2>Pahana Edu Login</h2>

  <% if (request.getAttribute("error") != null) { %>
  <div class="error">
    <strong>Error:</strong> <%= request.getAttribute("error") %>
  </div>
  <% } %>

  <form action="login" method="post">
    <div class="form-group">
      <label for="username">Username:</label>
      <input type="text" id="username" name="username" required
             value="<%= request.getParameter("username") != null ?
                                request.getParameter("username") : "" %>">
    </div>
    <div class="form-group">
      <label for="password">Password:</label>
      <input type="password" id="password" name="password" required>
    </div>
    <button type="submit">Login</button>
  </form>

  <div class="info">
    <p>For assistance, please contact IT support.</p>
    <p>Phone: +94 11 123 4567</p>
  </div>
</div>
</body>
</html>