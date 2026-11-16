<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Turf Booking</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background: #111827;
            color: #fff;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Poppins', sans-serif;
        }
        .error-card {
            background: rgba(30, 41, 59, 0.95);
            border-radius: 16px;
            padding: 40px;
            max-width: 500px;
            width: 100%;
            text-align: center;
            box-shadow: 0 10px 30px rgba(0,0,0,0.5);
            border: 1px solid rgba(239, 68, 68, 0.3);
        }
        .error-icon {
            font-size: 4rem;
            color: #ef4444;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="error-card">
        <div class="error-icon">
            <i class="bi bi-exclamation-triangle-fill"></i>
        </div>
        <h2 class="mb-3">Oops! Something went wrong</h2>
        <div class="alert alert-danger text-start">
            <%
                String errorMsg = (String) request.getAttribute("errorMessage");
                if (errorMsg == null) {
                    errorMsg = (String) request.getAttribute("error");
                }
                if (errorMsg == null && exception != null) {
                    errorMsg = exception.getMessage();
                }
                if (errorMsg == null) {
                    errorMsg = "An unexpected error occurred. Please try again.";
                }
            %>
            <%= errorMsg %>
        </div>
        <div class="d-flex justify-content-center gap-2 mt-4">
            <a href="javascript:history.back()" class="btn btn-outline-light">Go Back</a>
            <a href="index.jsp" class="btn btn-primary">Home Page</a>
        </div>
    </div>
</body>
</html>
