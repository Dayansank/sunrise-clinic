<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jspf/patient-header.jspf" %>
<section class="hero-panel">
    <p class="eyebrow">Your diary</p>
    <h1>My appointments</h1>
</section>
<section class="card">
    <table>
        <thead>
        <tr>
            <th>Number</th>
            <th>Dentist</th>
            <th>Treatment</th>
            <th>Date</th>
            <th>Time</th>
            <th>Status</th>
            <th>QR</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${appointments}" var="a">
            <tr>
                <td>${a.appointmentNumber}</td>
                <td>${a.dentist.name}</td>
                <td>${a.treatment.typeName}</td>
                <td>${a.appointmentDate}</td>
                <td>${a.appointmentTime}</td>
                <td><span class="pill">${a.status}</span></td>
                <td>
                    <c:if test="${a.status != 'CANCELLED'}">
                        <img class="qr-mini" src="${pageContext.request.contextPath}/qr?number=${a.appointmentNumber}" alt="QR">
                    </c:if>
                </td>
                <td>
                    <a class="btn outline" href="${pageContext.request.contextPath}/patient-book?repeat=${a.appointmentId}">Book again</a>
                    <c:if test="${a.status == 'BOOKED'}">
                        <form method="post" action="${pageContext.request.contextPath}/patient-cancel">
                            <input type="hidden" name="appointmentId" value="${a.appointmentId}">
                            <button class="btn primary" type="submit">Cancel</button>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty appointments}">
            <tr><td colspan="8">You have no appointments yet.</td></tr>
        </c:if>
        </tbody>
    </table>
</section>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
