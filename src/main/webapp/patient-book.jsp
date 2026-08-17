<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jspf/patient-header.jspf" %>
<section class="hero-panel">
    <p class="eyebrow">Channel a dentist</p>
    <h1>Book your appointment</h1>
    <p>Your name and contact are already on your account. Pick a dentist, a treatment, then a free slot.</p>
</section>
<c:if test="${not empty error}"><div class="alert error">${error}</div></c:if>
<c:if test="${not empty success}"><div class="alert success">${success}</div></c:if>
<div class="split">
    <form class="card form" method="post" action="${pageContext.request.contextPath}/patient-book">
        <label>Dentist
            <select name="dentistId" id="dentistId" required>
                <option value="">Select dentist</option>
                <c:forEach items="${dentists}" var="d">
                    <option value="${d.dentistId}" ${dentistId == d.dentistId ? 'selected' : ''}>${d.name} · ${d.specialization}</option>
                </c:forEach>
            </select>
        </label>
        <label>Treatment
            <select name="treatmentId" required>
                <option value="">Select treatment</option>
                <c:forEach items="${treatments}" var="t">
                    <option value="${t.treatmentId}" ${treatmentId == t.treatmentId ? 'selected' : ''}>${t.typeName} · LKR ${t.cost}</option>
                </c:forEach>
            </select>
        </label>
        <div class="two">
            <label>Date
                <input type="date" name="appointmentDate" id="appointmentDate" required>
            </label>
            <label>Time slot
                <select name="appointmentTime" id="appointmentTime" required>
                    <option value="">Select time</option>
                    <c:forEach items="${openSlots}" var="slot">
                        <option value="${slot}">${slot}</option>
                    </c:forEach>
                </select>
            </label>
        </div>
        <button type="submit" class="btn orange">Confirm booking</button>
    </form>
    <c:if test="${not empty savedAppointment}">
        <aside class="card">
            <h3 class="serif">Confirmed</h3>
            <p class="big-number">${savedAppointment.appointmentNumber}</p>
            <p>${savedAppointment.dentist.name}</p>
            <p>${savedAppointment.appointmentDate} at ${savedAppointment.appointmentTime}</p>
            <img class="qr" src="${qrImage}" alt="Appointment QR code">
            <p class="hint">Show this QR at reception. SMS and email confirmation were also logged.</p>
        </aside>
    </c:if>
</div>
<script>
    const context = "${pageContext.request.contextPath}";
    const dentist = document.getElementById("dentistId");
    const date = document.getElementById("appointmentDate");
    const time = document.getElementById("appointmentTime");
    async function refreshSlots() {
        if (!dentist.value || !date.value) return;
        const res = await fetch(context + "/api/slots?dentistId=" + dentist.value + "&date=" + date.value);
        const json = await res.json();
        time.innerHTML = '<option value="">Select time</option>';
        (json.data || []).forEach(slot => {
            const opt = document.createElement("option");
            opt.value = slot;
            opt.textContent = slot;
            time.appendChild(opt);
        });
    }
    dentist.addEventListener("change", refreshSlots);
    date.addEventListener("change", refreshSlots);
</script>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
