  <%--
    Created by IntelliJ IDEA.
    User: Lenovo
    Date: 8/5/2025
    Time: 12:25 PM
    To change this template use File | Settings | File Templates.
  --%>
  <%@ page contentType="text/html;charset=UTF-8" %>
  <%@ page import="com.pahanaedubookshop.model.Customer" %>
  <%
    Customer customer = (Customer) request.getAttribute("customer");
  %>

  <!DOCTYPE html>
  <html>
  <head>
    <title>Edit Customer</title>
  </head>
  <body>
  <h2>Edit Customer</h2>
  <form action="customer-management" method="post">
    <input type="hidden" name="action" value="update" />
    <input type="hidden" name="id" value="<%= customer.getCustomerId() %>" />
    Full Name: <input type="text" name="fullName" value="<%= customer.getFullName() %>" required /><br/>
    Address: <input type="text" name="address" value="<%= customer.getAddress() %>" required /><br/>
    Telephone: <input type="tel" name="telephone" id="telephone"
                      value="<%= customer.getTelephone() %>" required
                      pattern="^\d{10}$"
                      placeholder="10 digits only" />
    <br/>
    <input type="submit" value="Update Customer" />
  </form>
  <br/>
  <a href="customer-management">Back to Customer List</a>
  </body>
  </html>
