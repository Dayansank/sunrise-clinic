<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<section class="hero-panel no-print">
    <div>
        <p class="eyebrow">Billing</p>
        <h1>Calculate and Print Bill</h1>
        <p>Total = treatment cost + consultation fee (LKR ${consultationFee}).</p>
    </div>
</section>

<form class="card form inline no-print" method="post" action="${pageContext.request.contextPath}/bill">
    <label>Appointment number
        <input type="text" name="appointmentNumber" value="${appointmentNumber}" placeholder="APT-2026-0001" required>
    </label>
    <button type="submit" class="btn primary">Calculate bill</button>
</form>

<c:if test="${not empty error}"><div class="alert error no-print">${error}</div></c:if>
<c:if test="${not empty success}"><div class="alert success no-print">${success}</div></c:if>

<c:if test="${not empty bill}">
    <section class="card receipt print-area" id="receipt">
        <header class="receipt-head">
            <h2>Sunrise Dental Clinic</h2>
            <p>Colombo · Patient receipt</p>
        </header>
        <p><strong>Receipt No:</strong> BILL-${bill.appointment.appointmentNumber}</p>
        <p><strong>Appointment:</strong> ${bill.appointment.appointmentNumber}</p>
        <p><strong>Patient:</strong> ${bill.appointment.patient.name}</p>
        <p><strong>Address:</strong> ${bill.appointment.patient.address}</p>
        <p><strong>Contact:</strong> ${bill.appointment.patient.contactNumber}</p>
        <p><strong>Dentist:</strong> ${bill.appointment.dentist.name}</p>
        <p><strong>Date / Time:</strong> ${bill.appointment.appointmentDate} ${bill.appointment.appointmentTime}</p>
        <table>
            <thead>
            <tr><th>Description</th><th>Amount (LKR)</th></tr>
            </thead>
            <tbody>
            <tr><td>${bill.appointment.treatment.typeName}</td><td>${bill.treatmentCost}</td></tr>
            <tr><td>Consultation fee</td><td>${bill.consultationFee}</td></tr>
            </tbody>
            <tfoot>
            <tr><th>Total</th><th>${bill.totalAmount}</th></tr>
            </tfoot>
        </table>
        <p class="muted">Thank you for visiting Sunrise Dental Clinic.</p>
        <button type="button" class="btn primary no-print" onclick="window.print()">Print bill</button>
    </section>
</c:if>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
