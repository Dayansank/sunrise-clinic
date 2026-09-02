<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<c:set var="admin" value="${sessionScope.staffUser.role == 'ADMIN'}"/>
<section class="hero-panel">
    <div>
        <p class="eyebrow">${admin ? 'Admin portal' : 'Reception desk'}</p>
        <h1>Hello, ${sessionScope.staffUser.fullName}</h1>
        <c:choose>
            <c:when test="${admin}">
                <p>Reports, staff users and the dentist list. Reception still does walk-ins and bills.</p>
            </c:when>
            <c:otherwise>
                <p>Register walk-in, search number, print bill.</p>
            </c:otherwise>
        </c:choose>
    </div>
</section>
<section class="menu-grid">
    <c:if test="${not admin}">
        <a class="menu-card" href="${pageContext.request.contextPath}/appointment">
            <span>01</span>
            <h3>Register New Appointment</h3>
            <p>Add a walk-in patient, dentist, treatment, date and time.</p>
        </a>
        <a class="menu-card" href="${pageContext.request.contextPath}/search">
            <span>02</span>
            <h3>Display Appointment Details</h3>
            <p>Search using the appointment number.</p>
        </a>
        <a class="menu-card" href="${pageContext.request.contextPath}/bill">
            <span>03</span>
            <h3>Calculate and Print Bill</h3>
            <p>Treatment cost plus consultation fee.</p>
        </a>
        <a class="menu-card" href="${pageContext.request.contextPath}/help">
            <span>04</span>
            <h3>Help</h3>
            <p>Step-by-step guide for the reception desk.</p>
        </a>
    </c:if>
    <c:if test="${admin}">
        <a class="menu-card" href="${pageContext.request.contextPath}/reports">
            <span>01</span>
            <h3>Reports</h3>
            <p>Daily appointments, charts and income by dentist.</p>
        </a>
        <a class="menu-card" href="${pageContext.request.contextPath}/staff">
            <span>02</span>
            <h3>Staff accounts</h3>
            <p>Add or delete admin and reception logins.</p>
        </a>
        <a class="menu-card" href="${pageContext.request.contextPath}/catalog">
            <span>03</span>
            <h3>Clinic catalogue</h3>
            <p>Dentists, treatments and consultation fee from MySQL.</p>
        </a>
        <a class="menu-card" href="${pageContext.request.contextPath}/search">
            <span>04</span>
            <h3>Display Appointment Details</h3>
            <p>Look up any visit by appointment number.</p>
        </a>
        <a class="menu-card" href="${pageContext.request.contextPath}/help">
            <span>05</span>
            <h3>Help</h3>
            <p>How the admin portal is used.</p>
        </a>
    </c:if>
    <a class="menu-card exit" href="${pageContext.request.contextPath}/logout">
        <span>${admin ? '06' : '05'}</span>
        <h3>Exit System</h3>
        <p>Log out and close this session safely.</p>
    </a>
</section>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
