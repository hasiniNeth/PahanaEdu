<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.pahanaedubookshop.model.Book" %>
<%
    List<Book> bookList = (List<Book>) request.getAttribute("books");
    Book editingBook = (Book) request.getAttribute("editingBook");
    String message = (String) request.getAttribute("message");
    String error = (String) request.getAttribute("error");
    String role = (String) session.getAttribute("role");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Book Management</title>
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
            --warning-color: #F4B400;
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
            background: var(--light-text);
            padding: 30px;
            border-radius: 8px;
            box-shadow: var(--card-shadow);
        }

        h2 {
            color: var(--primary-color);
            margin-bottom: 25px;
            font-weight: 500;
            padding-bottom: 10px;
            border-bottom: 2px solid var(--primary-color);
        }

        h3 {
            color: var(--dark-text);
            margin: 20px 0 15px;
            font-weight: 500;
        }

        .success {
            color: var(--light-text);
            background-color: var(--success-color);
            padding: 12px 15px;
            border-radius: 6px;
            margin: 15px 0;
            display: inline-block;
        }

        .error {
            color: var(--light-text);
            background-color: var(--error-color);
            padding: 12px 15px;
            border-radius: 6px;
            margin: 15px 0;
            display: inline-block;
        }

        .form-section {
            background: var(--light-bg);
            padding: 25px;
            border-radius: 8px;
            margin-bottom: 30px;
            box-shadow: 0 1px 2px rgba(0,0,0,0.1);
            border: 1px solid var(--border-color);
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: var(--dark-text);
        }

        input[type="text"],
        input[type="number"],
        input[type="submit"] {
            width: 100%;
            padding: 12px;
            margin-bottom: 15px;
            border: 1px solid var(--border-color);
            border-radius: 6px;
            font-size: 1rem;
        }

        input[type="text"]:focus,
        input[type="number"]:focus {
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
            width: auto;
            padding: 12px 25px;
        }

        input[type="submit"]:hover {
            background-color: #3367D6;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        .btn-cancel {
            display: inline-block;
            padding: 12px 25px;
            background-color: var(--light-bg);
            color: var(--dark-text);
            border: 1px solid var(--border-color);
            border-radius: 6px;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
            margin-left: 10px;
        }

        .btn-cancel:hover {
            background-color: #e8e8e8;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
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

        .action-btn {
            color: var(--primary-color);
            text-decoration: none;
            font-weight: 500;
            transition: all 0.2s ease;
            margin-right: 10px;
        }

        .action-btn:hover {
            text-decoration: underline;
            color: #3367D6;
        }

        .btn-delete {
            background-color: transparent;
            color: var(--accent-color);
            border: none;
            font-weight: 500;
            cursor: pointer;
            padding: 0;
            font-size: 1rem;
        }

        .btn-delete:hover {
            text-decoration: underline;
        }

        .empty-state {
            text-align: center;
            padding: 40px;
            color: var(--dark-text);
            opacity: 0.7;
            font-style: italic;
        }

        .action-separator {
            color: var(--border-color);
            margin: 0 10px;
        }

        /* Responsive adjustments */
        @media (max-width: 768px) {
            body {
                padding: 15px;
            }

            .container {
                padding: 20px;
            }

            table {
                display: block;
                overflow-x: auto;
            }

            .form-section {
                padding: 15px;
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
    <h2>Book Management</h2>

    <% if (message != null) { %>
    <p class="success"><%= message %></p>
    <% } %>
    <% if (error != null) { %>
    <p class="error"><%= error %></p>
    <% } %>

    <!-- Add/Edit Book Form -->
    <div class="form-section">
        <h3><%= editingBook != null ? "Edit Book" : "Add New Book" %></h3>
        <form method="post" action="book-management">
            <input type="hidden" name="action" value="<%= editingBook != null ? "update" : "add" %>" />
            <% if (editingBook != null) { %>
            <input type="hidden" name="bookId" value="<%= editingBook.getBookId() %>" />
            <% } %>

            <label for="title">Title:</label>
            <input type="text" id="title" name="title" value="<%= editingBook != null ? editingBook.getTitle() : "" %>" required />

            <label for="author">Author:</label>
            <input type="text" id="author" name="author" value="<%= editingBook != null ? editingBook.getAuthor() : "" %>" />

            <label for="isbn">ISBN:</label>
            <input type="text" id="isbn" name="isbn" value="<%= editingBook != null ? editingBook.getIsbn() : "" %>" />

            <label for="price">Price:</label>
            <input type="number" id="price" name="price" step="0.01" value="<%= editingBook != null ? editingBook.getPrice() : "" %>" required />

            <label for="stock">Stock:</label>
            <input type="number" id="stock" name="stock" value="<%= editingBook != null ? editingBook.getStock() : "" %>" required />

            <input type="submit" value="<%= editingBook != null ? "Update Book" : "Add Book" %>" />
            <% if (editingBook != null) { %>
            <a href="book-management" class="btn-cancel">Cancel</a>
            <% } %>
        </form>
    </div>

    <!-- Book List Table -->
    <h3>Available Books</h3>
    <% if (bookList == null || bookList.isEmpty()) { %>
    <div class="empty-state">No books found.</div>
    <% } else { %>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Author</th>
            <th>ISBN</th>
            <th>Price</th>
            <th>Stock</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (Book book : bookList) { %>
        <tr>
            <td><%= book.getBookId() %></td>
            <td><%= book.getTitle() %></td>
            <td><%= book.getAuthor() %></td>
            <td><%= book.getIsbn() %></td>
            <td>$<%= String.format("%.2f", book.getPrice()) %></td>
            <td><%= book.getStock() %></td>
            <td>
                <a href="book-management?action=edit&id=<%= book.getBookId() %>" class="action-btn">Edit</a>
                <% if ("admin".equals(role)) { %>
                <span class="action-separator">|</span>
                <form method="post" action="book-management" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this book?');">
                    <input type="hidden" name="action" value="delete" />
                    <input type="hidden" name="bookId" value="<%= book.getBookId() %>" />
                    <button type="submit" class="btn-delete">Delete</button>
                </form>
                <% } %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } %>
</div>
</body>
</html>