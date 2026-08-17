<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<section class="hero-panel">
    <div>
        <p class="eyebrow">Search</p>
        <h1>Display Appointment Details</h1>
        <p>Enter the unique appointment number to view the full record.</p>
    </div>
</section>

<form class="card form inline" method="post" action="${pageContext.request.contextPath}/search">
    <label>Appointment number
        <input type="text" name="appointmentNumber" value="${appointmentNumber}" placeholder="APT-2026-0001" required>
    </label>
    <button type="submit" class="btn primary">Search</button>
</form>

<c:if test="${not empty error}"><div class="alert error">${error}</div></c:if>

<c:if test="${not empty appointment}">
    <section class="card details">
        <h3>${appointment.appointmentNumber}</h3>
        <dl>
            <div><dt>Patient name</dt><dd>${appointment.patient.name}</dd></div>
            <div><dt>Address</dt><dd>${appointment.patient.address}</dd></div>
            <div><dt>Contact number</dt><dd>${appointment.patient.contactNumber}</dd></div>
            <div><dt>Dentist</dt><dd>${appointment.dentist.name} (${appointment.dentist.specialization})</dd></div>
            <div><dt>Treatment type</dt><dd>${appointment.treatment.typeName}</dd></div>
            <div><dt>Date</dt><dd>${appointment.appointmentDate}</dd></div>
            <div><dt>Time</dt><dd>${appointment.appointmentTime}</dd></div>
            <div><dt>Booked by</dt><dd>${appointment.bookedBy}</dd></div>
            <div><dt>Status</dt><dd>${appointment.status}</dd></div>
        </dl>
        <img class="qr" src="${pageContext.request.contextPath}/qr?number=${appointment.appointmentNumber}" alt="Appointment QR">
    </section>
</c:if>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
