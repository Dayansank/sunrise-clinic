<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<section class="hero-panel">
    <div>
        <p class="eyebrow">Live MySQL data</p>
        <h1>Clinic catalogue</h1>
        <p>Changes here are saved in the database and show on booking and billing pages immediately.</p>
    </div>
</section>

<c:if test="${not empty error}"><div class="alert error">${error}</div></c:if>
<c:if test="${not empty success}"><div class="alert success">${success}</div></c:if>

<form class="card form inline" method="post" action="${pageContext.request.contextPath}/catalog">
    <input type="hidden" name="action" value="fee">
    <label>Consultation fee (LKR)
        <input type="number" name="consultationFee" min="1" step="0.01" value="${consultationFee}" required>
    </label>
    <button class="btn orange" type="submit">Save fee</button>
</form>

<div class="split">
    <section>
        <form class="card form" method="post" action="${pageContext.request.contextPath}/catalog">
            <h3>Add dentist</h3>
            <input type="hidden" name="action" value="add-dentist">
            <label>Name
                <input type="text" name="dentistName" required minlength="3">
            </label>
            <label>Specialization
                <input type="text" name="specialization" required minlength="3">
            </label>
            <button class="btn primary" type="submit">Add dentist</button>
        </form>
        <section class="card">
            <h3>Dentists in database</h3>
            <table>
                <thead><tr><th>Name</th><th>Specialization</th><th></th></tr></thead>
                <tbody>
                <c:forEach items="${dentists}" var="d">
                    <tr>
                        <td>${d.name}</td>
                        <td>${d.specialization}</td>
                        <td>
                            <form method="post" action="${pageContext.request.contextPath}/catalog" onsubmit="return confirm('Delete this dentist?');">
                                <input type="hidden" name="action" value="delete-dentist">
                                <input type="hidden" name="dentistId" value="${d.dentistId}">
                                <button class="btn outline" type="submit">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </section>
    </section>

    <section>
        <form class="card form" method="post" action="${pageContext.request.contextPath}/catalog">
            <h3>Add treatment</h3>
            <input type="hidden" name="action" value="add-treatment">
            <label>Type
                <input type="text" name="typeName" required minlength="3">
            </label>
            <label>Cost (LKR)
                <input type="number" name="cost" min="1" step="0.01" required>
            </label>
            <button class="btn primary" type="submit">Add treatment</button>
        </form>
        <section class="card">
            <h3>Treatments in database</h3>
            <table>
                <thead><tr><th>Type</th><th>Cost</th><th></th></tr></thead>
                <tbody>
                <c:forEach items="${treatments}" var="t">
                    <tr>
                        <td>${t.typeName}</td>
                        <td>${t.cost}</td>
                        <td>
                            <form method="post" action="${pageContext.request.contextPath}/catalog" onsubmit="return confirm('Delete this treatment?');">
                                <input type="hidden" name="action" value="delete-treatment">
                                <input type="hidden" name="treatmentId" value="${t.treatmentId}">
                                <button class="btn outline" type="submit">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </section>
    </section>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
