<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<section class="hero-panel">
    <div>
        <p class="eyebrow">Walk-in desk</p>
        <h1>Register New Appointment</h1>
        <p>Fill all the boxes. The number is made by MySQL, dont type it.</p>
    </div>
</section>

<c:if test="${not empty error}"><div class="alert error">${error}</div></c:if>
<c:if test="${not empty success}"><div class="alert success">${success}</div></c:if>

<div class="split">
    <form class="card form" method="post" action="${pageContext.request.contextPath}/appointment">
        <label>Patient name
            <input type="text" name="patientName" value="${patientName}" required minlength="3" maxlength="100">
        </label>
        <label>Address
            <input type="text" name="address" value="${address}" required minlength="5" maxlength="255">
        </label>
        <label>Contact number
            <input type="text" name="contactNumber" value="${contactNumber}" required placeholder="0771234567">
        </label>
        <label>Dentist
            <select name="dentistId" required>
                <option value="">Select dentist</option>
                <c:forEach items="${dentists}" var="d">
                    <option value="${d.dentistId}" ${dentistId == d.dentistId ? 'selected' : ''}>${d.name} · ${d.specialization}</option>
                </c:forEach>
            </select>
        </label>
        <label>Treatment type
            <select name="treatmentId" required>
                <option value="">Select treatment</option>
                <c:forEach items="${treatments}" var="t">
                    <option value="${t.treatmentId}" ${treatmentId == t.treatmentId ? 'selected' : ''}>${t.typeName} · LKR ${t.cost}</option>
                </c:forEach>
            </select>
        </label>
        <div class="two">
            <label>Appointment date
                <input type="date" name="appointmentDate" value="${appointmentDate}" required>
            </label>
            <label>Appointment time
                <input type="time" name="appointmentTime" value="${appointmentTime}" required min="09:00" max="16:30">
            </label>
        </div>
        <button type="submit" class="btn primary">Save appointment</button>
    </form>

    <c:if test="${not empty savedAppointment}">
        <aside class="card receipt">
            <h3>Saved</h3>
            <p class="big-number">${savedAppointment.appointmentNumber}</p>
            <p>${savedAppointment.patient.name}</p>
            <p>${savedAppointment.dentist.name}</p>
            <p>${savedAppointment.appointmentDate} at ${savedAppointment.appointmentTime}</p>
        </aside>
    </c:if>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
