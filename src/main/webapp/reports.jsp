<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<section class="hero-panel">
    <div>
        <p class="eyebrow">Decision support</p>
        <h1>Clinic Reports</h1>
        <p>Today’s bookings and income by dentist.</p>
    </div>
</section>

<section class="chart-grid">
    <article class="card">
        <h3>Status mix · doughnut</h3>
        <canvas id="statusChart"></canvas>
    </article>
    <article class="card">
        <h3>Income by dentist · bar</h3>
        <canvas id="incomeChart"></canvas>
    </article>
    <article class="card">
        <h3>Visits last 7 days · line</h3>
        <canvas id="weeklyChart"></canvas>
    </article>
</section>

<form class="card form inline" method="get" action="${pageContext.request.contextPath}/reports">
    <label>Appointment date
        <input type="date" name="date" value="${reportDate}">
    </label>
    <button type="submit" class="btn primary">Show</button>
</form>

<section class="card">
    <h3>Appointments on ${reportDate}</h3>
    <table>
        <thead>
        <tr>
            <th>Number</th>
            <th>Patient</th>
            <th>Dentist</th>
            <th>Treatment</th>
            <th>Time</th>
            <th>Booked by</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${dailyAppointments}" var="a">
            <tr>
                <td>${a.appointmentNumber}</td>
                <td>${a.patient.name}</td>
                <td>${a.dentist.name}</td>
                <td>${a.treatment.typeName}</td>
                <td>${a.appointmentTime}</td>
                <td>${a.bookedBy}</td>
                <td>${a.status}</td>
            </tr>
        </c:forEach>
        <c:if test="${empty dailyAppointments}">
            <tr><td colspan="7">No appointments for this date.</td></tr>
        </c:if>
        </tbody>
    </table>
</section>

<section class="card">
    <h3>Income by dentist</h3>
    <table>
        <thead>
        <tr><th>Dentist</th><th>Bills</th><th>Total income (LKR)</th></tr>
        </thead>
        <tbody>
        <c:forEach items="${incomeRows}" var="row">
            <tr>
                <td>${row.dentistName}</td>
                <td>${row.billsCount}</td>
                <td>${row.totalIncome}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>
<script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.1/dist/chart.umd.min.js"></script>
<script>
    const statusData = ${statusChartJson};
    const incomeData = ${incomeChartJson};
    const weeklyData = ${weeklyChartJson};
    const navy = "#23143a";
    const orange = "#ff7a1a";
    const sky = "#ffd9a8";

    new Chart(document.getElementById("statusChart"), {
        type: "doughnut",
        data: {
            labels: statusData.labels,
            datasets: [{ data: statusData.values, backgroundColor: [navy, orange, "#b42318"] }]
        },
        options: { plugins: { legend: { position: "bottom" } } }
    });
    new Chart(document.getElementById("incomeChart"), {
        type: "bar",
        data: {
            labels: incomeData.labels,
            datasets: [{ label: "Income (LKR)", data: incomeData.values, backgroundColor: navy }]
        },
        options: { scales: { y: { beginAtZero: true } } }
    });
    new Chart(document.getElementById("weeklyChart"), {
        type: "line",
        data: {
            labels: weeklyData.labels,
            datasets: [{
                label: "Appointments",
                data: weeklyData.values,
                borderColor: orange,
                backgroundColor: sky,
                fill: true,
                tension: 0.35
            }]
        },
        options: { scales: { y: { beginAtZero: true, ticks: { precision: 0 } } } }
    });
</script>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
