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
    <title>Edit Customer - Pahana Edu</title>
    <style>
      :root {
        --primary-color: #4285F4;
        --secondary-color: #34A853;
        --accent-color: #EA4335;
        --light-bg: #F8F9FA;
        --dark-text: #202124;
        --light-text: #FFFFFF;
        --border-color: #DADCE0;
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
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
        padding: 20px;
        line-height: 1.6;
      }

      .container {
        width: 100%;
        max-width: 600px;
        background: var(--light-text);
        padding: 40px;
        border-radius: 10px;
        box-shadow: var(--card-shadow);
        animation: fadeIn 0.5s ease-out;
      }

      h2 {
        color: var(--primary-color);
        margin-bottom: 25px;
        font-weight: 500;
        padding-bottom: 10px;
        border-bottom: 2px solid var(--primary-color);
        text-align: center;
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
      input[type="tel"],
      input[type="submit"] {
        width: 100%;
        padding: 12px 15px;
        border: 1px solid var(--border-color);
        border-radius: 6px;
        font-size: 1rem;
        transition: all 0.3s ease;
      }

      input[type="text"]:focus,
      input[type="tel"]:focus {
        outline: none;
        border-color: var(--primary-color);
        box-shadow: 0 0 0 2px rgba(66,133,244,0.2);
      }

      input[type="tel"]:invalid {
        border-color: var(--accent-color);
      }

      input[type="tel"]:valid {
        border-color: var(--secondary-color);
      }

      .form-actions {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 30px;
      }

      .btn-submit {
        background-color: var(--primary-color);
        color: var(--light-text);
        border: none;
        padding: 12px 25px;
        border-radius: 6px;
        font-size: 1rem;
        font-weight: 500;
        cursor: pointer;
        transition: all 0.3s ease;
      }

      .btn-submit:hover {
        background-color: #3367D6;
        transform: translateY(-2px);
        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
      }

      .btn-back {
        display: inline-flex;
        align-items: center;
        color: var(--primary-color);
        text-decoration: none;
        font-weight: 500;
        transition: all 0.3s ease;
      }

      .btn-back:hover {
        color: #3367D6;
        text-decoration: underline;
      }

      .btn-back::before {
        content: "←";
        margin-right: 5px;
      }

      .phone-hint {
        font-size: 0.8rem;
        color: #666;
        margin-top: 5px;
      }

      @keyframes fadeIn {
        from { opacity: 0; transform: translateY(20px); }
        to { opacity: 1; transform: translateY(0); }
      }

      /* Responsive adjustments */
      @media (max-width: 600px) {
        .container {
          padding: 30px;
        }

        .form-actions {
          flex-direction: column;
          gap: 15px;
        }

        .btn-submit {
          width: 100%;
        }

        .btn-back {
          align-self: flex-start;
        }
      }
    </style>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
  </head>
  <body>
  <div class="container">
    <h2>Edit Customer</h2>

    <form action="customer-management" method="post">
      <input type="hidden" name="action" value="update" />
      <input type="hidden" name="id" value="<%= customer.getCustomerId() %>" />

      <div class="form-group">
        <label for="fullName">Full Name:</label>
        <input type="text" id="fullName" name="fullName" value="<%= customer.getFullName() %>" required />
      </div>

      <div class="form-group">
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" value="<%= customer.getAddress() %>" required />
      </div>

      <div class="form-group">
        <label for="telephone">Telephone:</label>
        <input type="tel" id="telephone" name="telephone"
               value="<%= customer.getTelephone() %>" required
               pattern="^\d{10}$"
               placeholder="10 digits only" />
        <p class="phone-hint">Please enter exactly 10 digits (no spaces or dashes)</p>
      </div>

      <div class="form-actions">
        <input type="submit" class="btn-submit" value="Update Customer" />
        <a href="customer-management" class="btn-back">Back to Customer List</a>
      </div>
    </form>
  </div>

  <script>
    // Client-side validation for telephone number
    document.getElementById('telephone').addEventListener('input', function() {
      const phoneRegex = /^\d{10}$/;
      const phoneInput = this;

      if (!phoneRegex.test(phoneInput.value)) {
        phoneInput.setCustomValidity('Please enter exactly 10 digits');
      } else {
        phoneInput.setCustomValidity('');
      }
    });
  </script>
  </body>
  </html>