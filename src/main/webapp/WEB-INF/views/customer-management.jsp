<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 8/5/2025
  Time: 12:31 AM
--%>
<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 8/5/2025
  Time: 12:31 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.pahanaedubookshop.model.Customer, com.pahanaedubookshop.model.User" %>
<%
  List<Customer> customerList = (List<Customer>) request.getAttribute("customerList");
  String success = (String) request.getAttribute("success");
  String error = (String) request.getAttribute("error");
  User user = (User) session.getAttribute("user");
  String userRole = (user != null) ? user.getRole() : "";
%>

<!DOCTYPE html>
<html>
<head>
  <title>Customer Management</title>
  <style>
    .error { color: red; }
    .success { color: green; }
    table { border-collapse: collapse; width: 100%; }
    th, td { border: 1px solid #ddd; padding: 8px; }
    tr:nth-child(even) { background-color: #f2f2f2; }
    :root {
      --primary-color: #4285F4;
      --secondary-color: #34A853;
      --accent-color: #EA4335;
      --light-bg: #F8F9FA;
      --dark-text: #202124;
      --light-text: #FFFFFF;
      --border-color: #DADCE0;
      --success-color: #0F9D58;
      --error-color: #DB4437;
      --warning-color: #F4B400;
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
      padding: 20px;
      line-height: 1.6;
    }

    .container {
      max-width: 1200px;
      margin: 0 auto;
      background: var(--light-text);
      padding: 30px;
      border-radius: 10px;
      box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    }

    h2 {
      color: var(--primary-color);
      margin-bottom: 20px;
      font-weight: 500;
      border-bottom: 2px solid var(--primary-color);
      padding-bottom: 10px;
    }

    h3 {
      color: var(--dark-text);
      margin: 25px 0 15px;
      font-weight: 500;
    }

    .success {
      color: var(--success-color);
      padding: 12px 15px;
      border-radius: 6px;
      margin: 15px 0;
      display: inline-block;
    }

    .error {
      color: var(--error-color);
      padding: 12px 15px;
      border-radius: 6px;
      margin: 15px 0;
      display: inline-block;
    }

    form {
      background: var(--light-bg);
      padding: 20px;
      border-radius: 8px;
      margin-bottom: 30px;
    }

    label {
      display: block;
      margin-bottom: 8px;
      font-weight: 500;
      color: var(--dark-text);
    }

    input[type="text"],
    input[type="tel"],
    input[type="submit"] {
      width: 100%;
      padding: 12px;
      margin-bottom: 15px;
      border: 1px solid var(--border-color);
      border-radius: 6px;
      font-size: 1rem;
    }

    input[type="text"]:focus,
    input[type="tel"]:focus {
      outline: none;
      border-color: var(--primary-color);
      box-shadow: 0 0 0 2px rgba(66,133,244,0.2);
    }

    input[type="submit"] {
      background-color: var(--primary-color);
      color: var(--light-text);
      border: none;
      font-weight: 500;
      cursor: pointer;
      transition: all 0.3s ease;
    }

    input[type="submit"]:hover {
      background-color: #3367D6;
      transform: translateY(-2px);
      box-shadow: 0 4px 8px rgba(0,0,0,0.1);
    }

    input[type="submit"]:active {
      transform: translateY(0);
    }

    small.error {
      color: var(--error-color);
      display: block;
      margin-top: -10px;
      margin-bottom: 15px;
      font-size: 0.85rem;
    }

    hr {
      border: 0;
      height: 1px;
      background: var(--border-color);
      margin: 30px 0;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin: 20px 0;
      box-shadow: 0 1px 3px rgba(0,0,0,0.1);
      overflow: hidden;
      border-radius: 8px;
    }

    th, td {
      padding: 12px 15px;
      text-align: left;
      border-bottom: 1px solid var(--border-color);
    }

    th {
      background-color: var(--primary-color);
      color: var(--light-text);
      font-weight: 500;
      text-transform: uppercase;
      font-size: 0.85rem;
      letter-spacing: 0.5px;
    }

    tr:nth-child(even) {
      background-color: rgba(66,133,244,0.05);
    }

    tr:hover {
      background-color: rgba(66,133,244,0.1);
    }

    a {
      color: var(--primary-color);
      text-decoration: none;
      font-weight: 500;
      transition: all 0.2s ease;
    }

    a:hover {
      text-decoration: underline;
      color: #3367D6;
    }

    .action-separator {
      color: var(--border-color);
      margin: 0 5px;
    }

    .empty-state {
      text-align: center;
      padding: 40px;
      color: var(--dark-text);
      opacity: 0.7;
    }

    .back-btn {
      display: inline-flex;
      align-items: center;
      gap: 8px;
      margin-bottom: 20px;
      padding: 10px 20px;
      background-color: var(--primary-color);
      color: white;
      border-radius: 6px;
      text-decoration: none;
      font-weight: 500;
      transition: all 0.3s ease;
    }

    .back-btn:hover {
      background-color: #126ba7;
      color: white;
      transform: translateY(-2px);
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      text-decoration: none;
    }

    /* Responsive adjustments */
    @media (max-width: 768px) {
      .container {
        padding: 20px;
      }

      table {
        display: block;
        overflow-x: auto;
      }
    }

    /* Animation for table rows */
    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(10px); }
      to { opacity: 1; transform: translateY(0); }
    }

    tr {
      animation: fadeIn 0.3s ease forwards;
    }

    tr:nth-child(1) { animation-delay: 0.1s; }
    tr:nth-child(2) { animation-delay: 0.2s; }
    tr:nth-child(3) { animation-delay: 0.3s; }
    tr:nth-child(4) { animation-delay: 0.4s; }
    tr:nth-child(5) { animation-delay: 0.5s; }
  </style>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
</head>
<body>
<div class="container">
  <h2>Customer Management</h2>

  <!-- Back to Dashboard Button -->
  <a class="back-btn" href="<%= request.getContextPath() %>/<%= "admin".equalsIgnoreCase(userRole) ? "adminDashboard.jsp" : "staffDashboard.jsp" %>">← Back to Dashboard</a>

  <% if (success != null) { %>
  <p class="success"><%= success %></p>
  <% } %>
  <% if (error != null) { %>
  <p class="error"><%= error %></p>
  <% } %>

  <h3>Add New Customer</h3>
  <form action="customer-management" method="post" onsubmit="return validateForm()">
    <input type="hidden" name="action" value="add" />

    <label for="fullName">Full Name:</label>
    <input type="text" id="fullName" name="fullName" required />
    <small id="nameHelp" class="error"></small>

    <label for="address">Address:</label>
    <input type="text" id="address" name="address" required />
    <small id="addressHelp" class="error"></small>

    <label for="telephone">Telephone:</label>
    <input type="tel" id="telephone" name="telephone" required placeholder="10 digits only" />
    <small id="phoneHelp" class="error"></small>

    <input type="submit" value="Add Customer" />
  </form>

  <hr/>

  <h3>Existing Customers</h3>
  <% if (customerList == null || customerList.isEmpty()) { %>
  <div class="empty-state">
    <p>No customers found.</p>
  </div>
  <% } else { %>
  <table>
    <thead>
    <tr>
      <th>ID</th>
      <th>Account No</th>
      <th>Full Name</th>
      <th>Address</th>
      <th>Telephone</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <% for (Customer c : customerList) { %>
    <tr>
      <td><%= c.getCustomerId() %></td>
      <td><%= c.getAccountNumber() %></td>
      <td><%= c.getFullName() %></td>
      <td><%= c.getAddress() %></td>
      <td><%= c.getTelephone() %></td>
      <td>
        <a href="customer-management?action=edit&id=<%= c.getCustomerId() %>">Edit</a>
        <% if ("admin".equalsIgnoreCase(userRole)) { %>
        <span class="action-separator">|</span>
        <a href="customer-management?action=delete&id=<%= c.getCustomerId() %>"
           onclick="return confirm('Are you sure you want to delete this customer?')">Delete</a>
        <% } %>
      </td>
    </tr>
    <% } %>
    </tbody>
  </table>
  <% } %>
</div>

<script>
  function validateForm() {
    let valid = true;

    // Full Name Validation
    const name = document.getElementById('fullName').value.trim();
    const nameHelp = document.getElementById('nameHelp');
    const nameRegex = /^[A-Za-z\s]{3,}$/;
    if (!nameRegex.test(name)) {
      nameHelp.textContent = 'Full name must be at least 3 letters and contain only letters and spaces.';
      valid = false;
    } else {
      nameHelp.textContent = '';
    }

    // Address Validation
    const address = document.getElementById('address').value.trim();
    const addressHelp = document.getElementById('addressHelp');
    if (address.length < 5) {
      addressHelp.textContent = 'Address must be at least 5 characters long.';
      valid = false;
    } else {
      addressHelp.textContent = '';
    }

    // Telephone Validation
    const phone = document.getElementById('telephone').value.trim();
    const phoneHelp = document.getElementById('phoneHelp');
    const phoneRegex = /^\d{10}$/;
    if (!phoneRegex.test(phone)) {
      phoneHelp.textContent = 'Please enter exactly 10 digits (no spaces or dashes).';
      valid = false;
    } else {
      phoneHelp.textContent = '';
    }

    return valid;
  }
</script>
</body>
</html>
