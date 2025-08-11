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
  if (customer == null) {
    customer = (Customer) session.getAttribute("selectedCustomer");
  }

  List<Book> searchResults = (List<Book>) request.getAttribute("searchResults");
  List<BillItem> billItems = (List<BillItem>) session.getAttribute("cart");
  String message = (String) request.getAttribute("message");
  String error = (String) request.getAttribute("error");
  double total = (billItems != null) ? billItems.stream().mapToDouble(BillItem::getSubtotal).sum() : 0.0;
%>

<!DOCTYPE html>
<html>
<head>
  <title>Calculate and Print Bill</title>
  <style>
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
      --card-shadow: 0 1px 2px 0 rgba(60,64,67,0.3), 0 2px 6px 2px rgba(60,64,67,0.15);
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
      padding: 30px;
      line-height: 1.6;
    }

    .container {
      max-width: 1200px;
      margin: 0 auto;
    }

    h2 {
      color: var(--primary-color);
      margin-bottom: 25px;
      font-weight: 500;
      padding-bottom: 10px;
      border-bottom: 2px solid var(--primary-color);
    }

    .success {
      color: var(--light-text);
      background-color: var(--success-color);
      padding: 12px 15px;
      border-radius: 6px;
      margin-bottom: 25px;
      display: inline-block;
    }

    .error {
      color: var(--light-text);
      background-color: var(--error-color);
      padding: 12px 15px;
      border-radius: 6px;
      margin-bottom: 25px;
      display: inline-block;
    }

    .form-section, .bill-section, .search-results {
      background: var(--light-text);
      padding: 30px;
      border-radius: 8px;
      margin-bottom: 30px;
      box-shadow: var(--card-shadow);
    }

    h3 {
      color: var(--dark-text);
      margin-bottom: 20px;
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
    }

    input[type="text"],
    input[type="number"],
    input[type="submit"],
    select {
      padding: 12px 15px;
      border: 1px solid var(--border-color);
      border-radius: 6px;
      font-size: 1rem;
      transition: all 0.3s ease;
    }

    input[type="text"],
    input[type="number"],
    select {
      width: 100%;
      margin-bottom: 15px;
    }

    input[type="text"]:focus,
    input[type="number"]:focus,
    select:focus {
      outline: none;
      border-color: var(--primary-color);
      box-shadow: 0 0 0 2px rgba(66,133,244,0.2);
    }

    .btn {
      background-color: var(--primary-color);
      color: var(--light-text);
      border: none;
      padding: 12px 25px;
      border-radius: 6px;
      font-size: 1rem;
      font-weight: 500;
      cursor: pointer;
      transition: all 0.3s ease;
      text-decoration: none;
      display: inline-block;
    }

    .btn:hover {
      background-color: #3367D6;
      transform: translateY(-2px);
      box-shadow: 0 4px 8px rgba(0,0,0,0.1);
    }

    .btn-danger {
      background-color: var(--accent-color);
    }

    .btn-danger:hover {
      background-color: #D33426;
    }

    .btn-success {
      background-color: var(--secondary-color);
    }

    .btn-success:hover {
      background-color: #2D8E4A;
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

    .total-row {
      font-weight: bold;
      background-color: rgba(52, 168, 83, 0.1) !important;
    }

    .customer-info {
      background-color: rgba(66,133,244,0.1);
      padding: 15px;
      border-radius: 6px;
      margin: 15px 0;
    }

    .customer-info p {
      margin-bottom: 5px;
    }

    .customer-info strong {
      color: var(--primary-color);
    }

    .action-form {
      display: inline;
    }

    /* Responsive adjustments */
    @media (max-width: 768px) {
      body {
        padding: 20px;
      }

      .form-section, .bill-section, .search-results {
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
      <div class="form-group">
        <input type="text" name="account" placeholder="Enter customer account number" required />
      </div>
      <input type="submit" class="btn" value="Find Customer" />
    </form>
    <% if (customer != null) { %>
    <div class="customer-info">
      <p><strong>Customer:</strong> <%= customer.getFullName() %></p>
      <p><strong>Address:</strong> <%= customer.getAddress() %></p>
      <p><strong>Telephone:</strong> <%= customer.getTelephone() %></p>
    </div>
    <% } %>
  </div>

  <!-- Book Search -->
  <% if (customer != null) { %>
  <div class="form-section">
    <h3>2. Book Search</h3>
    <form action="bill-create" method="get">
      <input type="hidden" name="action" value="searchBooks" />
      <input type="hidden" name="account" value="<%= customer.getAccountNumber() %>" />
      <div class="form-group">
        <input type="text" name="keyword" placeholder="Search by title, ISBN or author" required />
      </div>
      <input type="submit" class="btn" value="Search Book" />
    </form>
  </div>
  <% } %>

  <!-- Book Search Results -->
  <% if (searchResults != null && !searchResults.isEmpty()) { %>
  <div class="search-results">
    <h3>Search Results</h3>
    <table>
      <thead>
      <tr>
        <th>Title</th>
        <th>Author</th>
        <th>ISBN</th>
        <th>Price</th>
        <th>Stock</th>
        <th>Quantity</th>
        <th>Action</th>
      </tr>
      </thead>
      <tbody>
      <% for (Book book : searchResults) { %>
      <tr>
        <td><%= book.getTitle() %></td>
        <td><%= book.getAuthor() %></td>
        <td><%= book.getIsbn() %></td>
        <td>$<%= String.format("%.2f", book.getPrice()) %></td>
        <td><%= book.getStock() %></td>
        <td>
          <form action="bill-create" method="post" class="action-form">
            <input type="hidden" name="action" value="addToBill" />
            <input type="hidden" name="bookId" value="<%= book.getBookId() %>" />
            <input type="number" name="quantity" min="1" max="<%= book.getStock() %>" value="1" required />
        </td>
        <td>
          <input type="submit" class="btn" value="Add to Bill" />
          </form>
        </td>
      </tr>
      <% } %>
      </tbody>
    </table>
  </div>
  <% } %>

  <!-- Current Bill -->
  <% if (billItems != null && !billItems.isEmpty()) { %>
  <div class="bill-section">
    <h3>3. Current Bill</h3>
    <table>
      <thead>
      <tr>
        <th>Title</th>
        <th>Quantity</th>
        <th>Price</th>
        <th>Subtotal</th>
        <th>Action</th>
      </tr>
      </thead>
      <tbody>
      <% for (int i = 0; i < billItems.size(); i++) {
        BillItem item = billItems.get(i);
      %>
      <tr>
        <td><%= item.getBookTitle() %></td>
        <td><%= item.getQuantity() %></td>
        <td>$<%= String.format("%.2f", item.getPrice()) %></td>
        <td>$<%= String.format("%.2f", item.getSubtotal()) %></td>
        <td>
          <form action="bill-create" method="post" class="action-form">
            <input type="hidden" name="action" value="removeItem" />
            <input type="hidden" name="index" value="<%= i %>" />
            <input type="submit" class="btn btn-danger" value="Remove" />
          </form>
        </td>
      </tr>
      <% } %>
      <tr class="total-row">
        <td colspan="3" style="text-align:right;"><strong>Total:</strong></td>
        <td>$<%= String.format("%.2f", total) %></td>
        <td></td>
      </tr>
      </tbody>
    </table>
    <br/>
    <form action="bill-create" method="post">
      <input type="hidden" name="action" value="printBill" />
      <input type="submit" class="btn btn-success" value="Print & Save Bill" />
    </form>
  </div>
  <% } %>
</div>
</body>
</html>