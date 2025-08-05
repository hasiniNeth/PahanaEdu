<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 8/5/2025
  Time: 12:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.pahanaedubookshop.model.Customer" %>
<%
  List<Customer> customerList = (List<Customer>) request.getAttribute("customerList");
  String success = (String) request.getAttribute("success");
  String error = (String) request.getAttribute("error");
  String userRole = (String) session.getAttribute("role");
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
  </style>
</head>
<body>
<h2>Customer Management</h2>

<% if (success != null) { %>
<p class="success"><%= success %></p>
<% } %>

<% if (error != null) { %>
<p class="error"><%= error %></p>
<% } %>

<h3>Add New Customer</h3>
<form action="customer-management" method="post" onsubmit="return validateForm()">
  <input type="hidden" name="action" value="add" />
  Full Name: <input type="text" name="fullName" required /><br/>
  Address: <input type="text" name="address" required /><br/>
  Telephone: <input type="tel" name="telephone" id="telephone" required
                    placeholder="10 digits only" />
  <small id="phoneHelp" class="error"></small><br/>
  <input type="submit" value="Add Customer" />
</form>

<hr/>

<h3>Existing Customers</h3>
<% if (customerList.isEmpty()) { %>
<p>No customers found.</p>
<% } else { %>
<table>
  <tr>
    <th>ID</th>
    <th>Account No</th>
    <th>Full Name</th>
    <th>Address</th>
    <th>Telephone</th>
    <th>Actions</th>
  </tr>
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
      | <a href="customer-management?action=delete&id=<%= c.getCustomerId() %>"
           onclick="return confirm('Are you sure?')">Delete</a>
      <% } %>
    </td>
  </tr>
  <% } %>
</table>
<% } %>

<script>
  function validateForm() {
    const phone = document.getElementById('telephone').value;
    const phoneRegex = /^\d{10}$/;
    const helpText = document.getElementById('phoneHelp');

    if (!phoneRegex.test(phone)) {
      helpText.textContent = 'Please enter exactly 10 digits (no spaces or dashes)';
      return false;
    }
    return true;
  }
</script>
</body>
</html>