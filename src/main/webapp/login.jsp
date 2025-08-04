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
    body { font-family: Arial, sans-serif; background-color: #f5f5f5; }
    .login-container {
      width: 300px; margin: 100px auto; padding: 20px;
      background: white; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
    .form-group { margin-bottom: 15px; }
    label { display: block; margin-bottom: 5px; font-weight: bold; }
    input[type="text"], input[type="password"] {
      width: 100%; padding: 8px; box-sizing: border-box;
      border: 1px solid #ddd; border-radius: 4px;
    }
    button {
      background-color: #4CAF50; color: white; padding: 10px 15px;
      border: none; border-radius: 4px; cursor: pointer; width: 100%;
    }
    button:hover { background-color: #45a049; }
    .error {
      color: #d32f2f; margin-bottom: 15px; padding: 10px;
      background-color: #ffebee; border-radius: 4px; border: 1px solid #ef9a9a;
    }
    .info {
      color: #0277bd; margin-top: 15px; padding: 10px;
      background-color: #e1f5fe; border-radius: 4px; border: 1px solid #81d4fa;
    }
  </style>
</head>
<body>
<div class="login-container">
  <h2 style="text-align: center; color: #2e7d32;">Pahana Edu Login</h2>

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