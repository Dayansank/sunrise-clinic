<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jspf/patient-header.jspf" %>
<section class="hero-panel">
    <p class="eyebrow">Patient portal</p>
    <h1>Hello, ${sessionScope.patientUser.name}</h1>
    <p>Book a visit in a few steps. Live slots keep two people from taking the same dentist at the same time.</p>
</section>
<div class="stat-row">
    <div class="stat"><span>Portal</span><strong>Online booking</strong></div>
    <div class="stat"><span>Hours</span><strong>09:00 – 17:00</strong></div>
    <div class="stat"><span>Closed</span><strong>Sunday</strong></div>
</div>
<section class="menu-grid">
    <a class="menu-card" href="${pageContext.request.contextPath}/patient-book">
        <span>New visit</span>
        <h3>Book appointment</h3>
        <p>Choose dentist, treatment and a free time.</p>
    </a>
    <a class="menu-card" href="${pageContext.request.contextPath}/patient-appointments">
        <span>History</span>
        <h3>My appointments</h3>
        <p>View or cancel a booked visit.</p>
    </a>
    <a class="menu-card" href="${pageContext.request.contextPath}/patient-bills">
        <span>Accounts</span>
        <h3>My bills</h3>
        <p>Treatment cost plus consultation.</p>
    </a>
</section>
<c:if test="${not empty appointments}">
    <section class="card">
        <h3 class="serif">Upcoming & recent</h3>
        <table>
            <thead><tr><th>Number</th><th>Dentist</th><th>Date</th><th>Time</th><th>Status</th></tr></thead>
            <tbody>
            <c:forEach items="${appointments}" var="a" end="4">
                <tr>
                    <td>${a.appointmentNumber}</td>
                    <td>${a.dentist.name}</td>
                    <td>${a.appointmentDate}</td>
                    <td>${a.appointmentTime}</td>
                    <td><span class="pill">${a.status}</span></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
</c:if>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
