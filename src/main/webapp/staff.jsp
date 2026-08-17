<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<section class="hero-panel">
    <div>
        <p class="eyebrow">Admin only</p>
        <h1>Staff accounts</h1>
        <p>Add another admin or reception login, or remove an account.</p>
    </div>
</section>

<c:if test="${not empty error}"><div class="alert error">${error}</div></c:if>
<c:if test="${not empty success}"><div class="alert success">${success}</div></c:if>

<div class="split">
    <form class="card form" method="post" action="${pageContext.request.contextPath}/staff">
        <h3>Add staff</h3>
        <label>Full name
            <input type="text" name="fullName" required minlength="3" maxlength="100">
        </label>
        <label>Username
            <input type="text" name="username" required minlength="3" maxlength="50">
        </label>
        <label>Password
            <input type="password" name="password" required minlength="6">
        </label>
        <label>Role
            <select name="role" required>
                <option value="RECEPTION">Reception</option>
                <option value="ADMIN">Admin</option>
            </select>
        </label>
        <button type="submit" class="btn orange">Create account</button>
    </form>

    <section class="card">
        <h3>Current staff</h3>
        <table>
            <thead>
            <tr><th>Name</th><th>Username</th><th>Role</th><th>Status</th><th></th></tr>
            </thead>
            <tbody>
            <c:forEach items="${staffList}" var="s">
                <tr>
                    <td>${s.fullName}</td>
                    <td>${s.username}</td>
                    <td><span class="pill">${s.role}</span></td>
                    <td>${s.active ? 'Active' : 'Disabled'}</td>
                    <td>
                        <c:if test="${s.userId != sessionScope.staffUser.userId && s.active}">
                            <form method="post" action="${pageContext.request.contextPath}/staff" onsubmit="return confirm('Delete or disable this account?');">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="userId" value="${s.userId}">
                                <button class="btn outline" type="submit">Delete</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
