<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Patient login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="login-body">
<div class="login-wrap">
<div class="login-shell">
    <section class="login-hero">
        <p class="eyebrow">Patient</p>
        <h1>Book your visit</h1>
        <p>Sign in, pick a dentist and a free slot.</p>
    </section>
    <section class="login-card">
        <h2>Patient login</h2>
        <c:if test="${not empty error}"><div class="alert error">${error}</div></c:if>
        <form class="form" method="post" action="${pageContext.request.contextPath}/patient-login">
            <label>Email
                <input type="email" name="email" required placeholder="you@email.com">
            </label>
            <label>Password
                <input type="password" name="password" required>
            </label>
            <button type="submit" class="btn orange">Sign in</button>
        </form>
        <p class="hint">kamal@sunrise.lk / Patient@123</p>
        <p class="hint"><a href="${pageContext.request.contextPath}/patient-register">Create account</a>
            · <a href="${pageContext.request.contextPath}/login.jsp">Staff login</a></p>
    </section>
</div>
</div>
</body>
</html>
