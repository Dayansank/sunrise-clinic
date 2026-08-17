<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Patient login · Sunrise Dental Clinic</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="login-body">
<%@ include file="/WEB-INF/jspf/public-header.jspf" %>
<div class="login-wrap">
<div class="login-shell">
    <section class="login-hero">
        <p class="eyebrow">Patient portal</p>
        <h1>Book your own visit</h1>
        <p>Sign in to choose a dentist, pick a live time slot, and manage your appointments.</p>
    </section>
    <section class="login-card">
        <h2>Welcome back</h2>
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
        <p class="hint">Demo patient: <strong>kamal@sunrise.lk</strong> / Patient@123</p>
        <p class="hint"><a href="${pageContext.request.contextPath}/patient-register">Create a new account</a> · <a href="${pageContext.request.contextPath}/index.jsp">Home</a></p>
    </section>
</div>
</div>
<%@ include file="/WEB-INF/jspf/public-footer.jspf" %>
</body>
</html>
