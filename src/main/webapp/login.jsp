<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Staff login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="login-body">
<div class="login-wrap">
<div class="login-shell">
    <section class="login-hero">
        <p class="eyebrow">Staff</p>
        <h1>Sunrise Dental Clinic</h1>
        <p>Reception books walk-ins and prints bills.</p>
    </section>
    <section class="login-card">
        <h2>Staff login</h2>
        <c:if test="${not empty error}"><div class="alert error">${error}</div></c:if>
        <form class="form" method="post" action="${pageContext.request.contextPath}/login" autocomplete="off">
            <label>Username
                <input type="text" name="username" required maxlength="50">
            </label>
            <label>Password
                <input type="password" name="password" required maxlength="50">
            </label>
            <button type="submit" class="btn orange">Sign in</button>
        </form>
        <p class="hint">reception / Staff@123<br>admin / Admin@123</p>
        <p class="hint"><a href="${pageContext.request.contextPath}/patient-login">Patient portal</a></p>
    </section>
</div>
</div>
</body>
</html>
