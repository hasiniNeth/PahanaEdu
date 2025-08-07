<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 8/6/2025
  Time: 2:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.pahanaedubookshop.model.*" %>
<%
  Customer customer = (Customer) request.getAttribute("customer");
  List<Book> searchResults = (List<Book>) request.getAttribute("searchResults");
  List<BillItem> billItems = (List<BillItem>) session.getAttribute("billItems");
  String message = (String) request.getAttribute("message");
  String error = (String) request.getAttribute("error");
  double total = (billItems != null) ? billItems.stream().mapToDouble(BillItem::getSubtotal).sum() : 0.0;
%>

<!DOCTYPE html>
<html>
<head>
  <title>Calculate and Print Bill</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 20px; }
    .form-section, .bill-section, .search-results { margin-bottom: 30px; border: 1px solid #ccc; padding: 20px; border-radius: 5px; background: #f9f9f9; }
    table { width: 100%; border-collapse: collapse; margin-top: 10px; }
    th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
    th { background-color: #eee; }
    .success { color: green; }
    .error { color: red; }
  </style>
</head>
<body>
<h2>Calculate and Print Bill</h2>

<% if (message != null) { %>
<p class="success"><%= message %></p>
<% } %>
<% if (error != null) { %>
<p class="error"><%= error %></p>
<% } %>

<!-- Customer Search -->
<div class="form-section">
  <h3>1. Customer Account</h3>
  <form action="bill-create" method="get">
    <input type="hidden" name="action" value="searchCustomer" />
    <input type="text" name="account" placeholder="Enter customer account number" required />
    <input type="submit" value="Find Customer" />
  </form>
  <% if (customer != null) { %>
  <p><strong>Customer:</strong> <%= customer.getFullName() %> | <%= customer.getAddress() %> | <%= customer.getTelephone() %></p>
  <% } %>
</div>

<!-- Book Search -->
<% if (customer != null) { %>
<div class="form-section">
  <h3>2. Book Search</h3>
  <form action="bill-create" method="get">
    <input type="hidden" name="action" value="searchBooks" />
    <input type="hidden" name="account" value="<%= customer.getAccountNumber() %>" />
    <input type="text" name="query" placeholder="Search by title, ISBN or author" required />
    <input type="submit" value="Search Book" />
  </form>
</div>
<% } %>

<!-- Book Search Results -->
<% if (searchResults != null && !searchResults.isEmpty()) { %>
<div class="search-results">
  <h3>Search Results</h3>
  <table>
    <tr><th>Title</th><th>Author</th><th>ISBN</th><th>Price</th><th>Stock</th><th>Quantity</th><th>Action</th></tr>
    <% for (Book book : searchResults) { %>
    <tr>
      <form action="bill-create" method="post">
        <input type="hidden" name="action" value="add" />
        <input type="hidden" name="bookId" value="<%= book.getBookId() %>" />
        <input type="hidden" name="account" value="<%= customer.getAccountNumber() %>" />
        <td><%= book.getTitle() %></td>
        <td><%= book.getAuthor() %></td>
        <td><%= book.getIsbn() %></td>
        <td><%= book.getPrice() %></td>
        <td><%= book.getStock() %></td>
        <td><input type="number" name="quantity" min="1" max="<%= book.getStock() %>" value="1" required /></td>
        <td><input type="submit" value="Add to Bill" /></td>
      </form>
    </tr>
    <% } %>
  </table>
</div>
<% } %>

<!-- Current Bill -->
<% if (billItems != null && !billItems.isEmpty()) { %>
<div class="bill-section">
  <h3>3. Current Bill</h3>
  <form action="bill-create" method="post">
    <input type="hidden" name="action" value="save" />
    <table>
      <tr><th>Title</th><th>Quantity</th><th>Price</th><th>Subtotal</th><th>Action</th></tr>
      <% for (BillItem item : billItems) { %>
      <tr>
        <td><%= item.getBookTitle() %></td>
        <td><%= item.getQuantity() %></td>
        <td><%= item.getPrice() %></td>
        <td><%= item.getSubtotal() %></td>
        <td>
          <form action="bill-create" method="post" style="display:inline;">
            <input type="hidden" name="action" value="remove" />
            <input type="hidden" name="bookId" value="<%= item.getBookId() %>" />
            <input type="submit" value="Remove" />
          </form>
        </td>
      </tr>
      <% } %>
      <tr><td colspan="3" style="text-align:right;"><strong>Total:</strong></td><td><%= total %></td><td></td></tr>
    </table>
    <br/>
    <input type="submit" value="Print & Save Bill" />
  </form>
</div>
<% } %>

</body>
</html>
