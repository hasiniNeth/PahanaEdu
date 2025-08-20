# 📚 Pahana Edu Bookshop Management System

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
![Servlet](https://img.shields.io/badge/Apache-6DB33F?style=for-the-badge&logo=apache-tomcat&logoColor=white)
![JSP](https://img.shields.io/badge/JSP-007396?style=for-the-badge&logo=java&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=junit5&logoColor=white)

*A comprehensive bookshop management solution built for educational institutions*

</div>

## 🌟 Overview

The **Pahana Edu Bookshop Management System** is a sophisticated Java-based web application designed specifically for educational bookshops. This comprehensive solution streamlines all aspects of bookshop operations with a specialized focus on **educational materials, storybooks, and reading aids** - excluding stationery and non-book items.

Built with modern software engineering practices including Test-Driven Development (TDD) and a structured Git workflow, this system ensures reliability, scalability, and maintainability for educational institutions of all sizes.

---

## ✨ Key Features

### 🔐 Secure Authentication System
- Role-based access control (Admin & Staff privileges)
- Secure session management
- Password protection mechanisms

### 📖 Comprehensive Book Management
- Complete CRUD operations for book inventory
- Advanced ISBN validation and duplicate prevention
- Real-time stock tracking and management
- Multi-criteria search (title, author, ISBN)

### 👥 Customer & Staff Management
- Customer account management with auto-generated IDs
- Staff administration (admin-only access)
- Telephone number validation and data integrity checks

### 🧾 Billing System
- Customer lookup by account number
- Dynamic cart management with real-time totals
- Automated stock deduction on purchase
- Professional receipt generation
- Sales transaction history

### ✅ Validation & Error Handling
- Comprehensive form validation
- Business rule enforcement
- User-friendly error messages
- Database integrity protection

### 🧪 Quality Assurance
- Test-Driven Development approach
- Comprehensive JUnit test coverage
- Automated regression testing
- Continuous integration readiness

---

## 🛠️ Technology Stack

| Layer | Technology |
|-------|------------|
| **Frontend** | JSP, HTML5, CSS3, JavaScript |
| **Backend** | Java Servlets, Java 8+ |
| **Database** | MySQL 5.7+ |
| **Server** | Apache Tomcat 9+ |
| **Build Tool** | Maven 3.6+ |
| **Testing** | JUnit 4.13.2 |
| **Version Control** | Git & GitHub |

---

## 🚀 Quick Start Guide

### Prerequisites
- Java JDK 8 or higher
- Apache Tomcat 9+
- MySQL 5.7 or higher
- Maven 3.6+

### Installation Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/pahana-edu-bookshop.git
   cd pahana-edu-bookshop
   ```

2. **Database Setup**
   ```sql
   CREATE DATABASE pahana_edu;
   USE pahana_edu;
   
   -- Execute the SQL script from database/schema.sql
   -- Import sample data from database/sample_data.sql (optional)
   ```

3. **Configure Database Connection**
   Update `DatabaseConnection.java` with your MySQL credentials:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/pahana_edu";
   private static final String USER = "root";
   private static final String PASSWORD = "";
   ```
   **Configure Database (MySQL)**
Run the following SQL:

sql
Copy
Edit
CREATE DATABASE pahana_edu;
USE pahana_edu;

-- Users table (for staff/admin login)
CREATE TABLE users (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(100) NOT NULL,
  role ENUM('admin','staff') NOT NULL,
  full_name VARCHAR(100) NOT NULL
);

-- Customers table
CREATE TABLE customers (
  customer_id INT AUTO_INCREMENT PRIMARY KEY,
  account_number VARCHAR(20) UNIQUE NOT NULL,
  full_name VARCHAR(100) NOT NULL,
  address VARCHAR(255),
  telephone VARCHAR(15)
);

-- Books table
CREATE TABLE books (
  book_id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
  author VARCHAR(100),
  isbn VARCHAR(20) UNIQUE,
  price DECIMAL(10,2) NOT NULL,
  stock INT NOT NULL DEFAULT 0
);

-- Bills table
CREATE TABLE bills (
  bill_id INT AUTO_INCREMENT PRIMARY KEY,
  customer_id INT NOT NULL,
  total_amount DECIMAL(10,2) NOT NULL,
  bill_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- Bill Items table
CREATE TABLE bill_items (
  bill_item_id INT AUTO_INCREMENT PRIMARY KEY,
  bill_id INT NOT NULL,
  book_id INT NOT NULL,
  quantity INT NOT NULL,
  unit_price DECIMAL(10,2) NOT NULL,
  total_price DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (bill_id) REFERENCES bills(bill_id),
  FOREIGN KEY (book_id) REFERENCES books(book_id)
);

4. **Build and Deploy**
   ```bash
   mvn clean package
   # Deploy the generated WAR file to your Tomcat server
   ```

5. **Access the Application**
   Navigate to: `http://localhost:8080/PahanaEduBookshop`

### Default Login Credentials

| Role | Username | Password | Access Level |
|------|----------|----------|-------------|
| Admin | `admin` | `admin123` | Full system access |
| Staff | `staff` | `staff123` | Limited access (customers, billing, books) |

---

▶️ Usage
Login as Admin or Staff.

Manage Customers – add, update, or delete accounts.

Manage Staff – admin can add/update/delete staff users.

Manage Books – add/update/delete books, ISBN validation enforced.

Generate Bills – search for customers, add books with quantities, calculate totals, update stock, and print receipt.

---

## 🔄 Git Workflow


1. **development** - Active feature development
2. **qa** - Quality assurance and testing
3. **regression** - Bug fixes and regression testing
4. **production** - Stable release versions

---


📬 Contact
👩‍💻 Author: Hasini Nethmini
📧 Email: shasininethmini2005@gmail.com
🌐 GitHub: hasiniNeth


---

<div align="center">

</div>
