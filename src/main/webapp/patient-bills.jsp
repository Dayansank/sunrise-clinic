<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jspf/patient-header.jspf" %>
<section class="hero-panel">
    <p class="eyebrow">Accounts</p>
    <h1>My bills</h1>
</section>
<section class="card">
    <table>
        <thead>
        <tr><th>Appointment</th><th>Treatment</th><th>Consultation</th><th>Total</th></tr>
        </thead>
        <tbody>
        <c:forEach items="${bills}" var="b">
            <tr>
                <td>${b.appointment.appointmentNumber}</td>
                <td>${b.treatmentCost}</td>
                <td>${b.consultationFee}</td>
                <td><strong>${b.totalAmount}</strong></td>
            </tr>
        </c:forEach>
        <c:if test="${empty bills}">
            <tr><td colspan="4">No bills yet. Reception will generate a bill after treatment.</td></tr>
        </c:if>
        </tbody>
    </table>
</section>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
