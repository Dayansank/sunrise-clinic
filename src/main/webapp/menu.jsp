<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<section class="hero-panel">
    <div>
        <p class="eyebrow">${sessionScope.staffUser.role == 'ADMIN' ? 'Admin' : 'Reception desk'}</p>
        <h1>Good day, ${sessionScope.staffUser.fullName}</h1>
        <p>${sessionScope.staffUser.role == 'ADMIN' ? 'Reports and appointment lookup.' : 'Walk-in booking, search, bill, help and exit.'}</p>
    </div>
</section>
<section class="menu-grid">
    <c:if test="${sessionScope.staffUser.role != 'ADMIN'}">
        <a class="menu-card" href="${pageContext.request.contextPath}/appointment">
            <span>01</span>
            <h3>Register New Appointment</h3>
            <p>Walk-in patient, dentist, treatment, date and time.</p>
        </a>
    </c:if>
    <a class="menu-card" href="${pageContext.request.contextPath}/search">
        <span>02</span>
        <h3>Display Appointment Details</h3>
        <p>Search by appointment number.</p>
    </a>
    <c:if test="${sessionScope.staffUser.role != 'ADMIN'}">
        <a class="menu-card" href="${pageContext.request.contextPath}/bill">
            <span>03</span>
            <h3>Calculate and Print Bill</h3>
            <p>Treatment cost plus consultation fee.</p>
        </a>
    </c:if>
    <c:if test="${sessionScope.staffUser.role == 'ADMIN'}">
        <a class="menu-card" href="${pageContext.request.contextPath}/reports">
            <span>01</span>
            <h3>Reports</h3>
            <p>Charts and todays appointments.</p>
        </a>
    </c:if>
    <a class="menu-card" href="${pageContext.request.contextPath}/help">
        <span>04</span>
        <h3>Help</h3>
        <p>How to use this desk.</p>
    </a>
    <a class="menu-card exit" href="${pageContext.request.contextPath}/logout">
        <span>05</span>
        <h3>Exit</h3>
        <p>Log out.</p>
    </a>
</section>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
