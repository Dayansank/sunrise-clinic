<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Create account</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="login-body">
<div class="login-wrap">
<div class="login-shell">
    <section class="login-hero">
        <p class="eyebrow">New patient</p>
        <h1>Create account</h1>
        <p>Then you can book online and see your bills.</p>
    </section>
    <section class="login-card">
        <h2>Register</h2>
        <c:if test="${not empty error}"><div class="alert error">${error}</div></c:if>
        <form class="form" method="post" action="${pageContext.request.contextPath}/patient-register">
            <label>Full name
                <input type="text" name="name" value="${name}" required minlength="3">
            </label>
            <label>Address
                <input type="text" name="address" value="${address}" required minlength="5">
            </label>
            <label>Contact number
                <input type="text" name="contactNumber" value="${contactNumber}" required placeholder="0771234567">
            </label>
            <label>Email
                <input type="email" name="email" value="${email}" required>
            </label>
            <label>Password
                <input type="password" name="password" required minlength="6">
            </label>
            <button type="submit" class="btn orange">Create account</button>
        </form>
        <p class="hint"><a href="${pageContext.request.contextPath}/patient-login">I already have an account</a></p>
    </section>
</div>
</div>
</body>
</html>
