<%-- 
    Document   : register
    Created on : Jun 7, 2024, 11:30:00 AM
    Author     : Udara
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
        <!-- Bootstrap CSS -->
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                background-color: #f0f2f5;
            }
            .register-container {
                background-color: #fff;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                width: 100%;
                max-width: 400px;
            }
            .register-container .form-group {
                margin-bottom: 1rem;
            }
            .register-container .btn-primary {
                width: 100%;
                padding: 10px;
                font-size: 16px;
            }
            .register-container .footer {
                margin-top: 20px;
                font-size: 14px;
                color: #777;
                text-align: center;
            }
            .register-container .login-link {
                margin-top: 10px;
                font-size: 14px;
                text-align: center;
            }
            .nav-item{
                margin-left: 20px;
            }
        </style>
    </head>
    <body>
        <div class="register-container">
            <h2 class="text-center">Register</h2>
            <form action="register" method="post">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" class="form-control" id="username" name="username" placeholder="Enter username" required>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="Enter email" required>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Enter password" required>
                </div>
                <button type="submit" class="btn btn-primary">Register</button>
            </form>
            <div class="login-link">
                <p>Already have an account? <a href="login.jsp">Login here</a></p>
            </div>
            <%
                String message = (String) request.getAttribute("message");
                if (message != null) {
                    if (message == "success") {
            %>
            <div class="alert alert-success" role="alert">
                User Register Successfully!
            </div>
            <%
                    }else if(message == "faild"){
            %>  
            <div class="alert alert-danger" role="alert">
                User Register Faild!
            </div>
            <%
                    }else if(message == "exists"){
            %>  
            <div class="alert alert-danger" role="alert">
                User Already Exists!
            </div>
            <%
                    }
                }
            %>
            <div class="footer">
                &copy; 2024 Your Company
            </div>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
