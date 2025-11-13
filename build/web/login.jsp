<%-- 
    Document   : index
    Created on : Jun 7, 2024, 11:14:09 AM
    Author     : Udara
--%>
<%
    if (session != null && session.getAttribute("UN") != null) {
        response.sendRedirect("home");
    }

%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
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
            .login-container {
                background-color: #fff;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                width: 100%;
                max-width: 400px;
            }
            .login-container .form-group {
                margin-bottom: 1rem;
            }
            .login-container .btn-primary {
                width: 100%;
                padding: 10px;
                font-size: 16px;
            }
            .login-container .footer {
                margin-top: 20px;
                font-size: 14px;
                color: #777;
                text-align: center;
            }
            .login-container .register-link {
                margin-top: 10px;
                font-size: 14px;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <div class="login-container">
            <h2 class="text-center">Login</h2>
            <form action="login" method="post">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="Enter email" required value="admin@udarax.me">
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Enter password" required value="123456">
                </div>
                <button type="submit" class="btn btn-primary">Login</button>
            </form>
            <div class="register-link">
                <p>Don't have an account? <a href="/MusicJ2EE/register">Register here</a></p>
            </div>
            <%
                String message = (String) request.getAttribute("message");
                if (message != null && message == "faild") {
            %>
            <div class="alert alert-danger" role="alert">
                User Login Faild!
            </div>
            <%
                }else if(message != null && message == "empty"){

            %>
            <div class="alert alert-danger" role="alert">
                Email and Password Cannot Empty!
            </div>
            <%
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
