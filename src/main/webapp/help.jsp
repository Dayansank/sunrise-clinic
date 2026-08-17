<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<section class="hero-panel">
    <div>
        <p class="eyebrow">New staff guide</p>
        <h1>Help</h1>
        <p>Follow these steps to use the Sunrise Dental Clinic system.</p>
    </div>
</section>
<ol class="help-list">
    <c:forEach items="${steps}" var="step" varStatus="st">
        <li>
            <span>${st.count}</span>
            <p>${step}</p>
        </li>
    </c:forEach>
</ol>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
