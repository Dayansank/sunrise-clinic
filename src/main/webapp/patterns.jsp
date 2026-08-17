<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<section class="hero-panel">
    <p class="eyebrow">Software design</p>
    <h1>Design patterns used</h1>
    <p>These patterns are implemented in the Sunrise Dental Clinic system.</p>
</section>
<section class="card">
    <table>
        <thead>
        <tr><th>Pattern</th><th>Type</th><th>Where it is used</th></tr>
        </thead>
        <tbody>
        <tr><td>Singleton</td><td>Creational</td><td>DBConnection, ConsultationFeeConfig, QrCodeService</td></tr>
        <tr><td>Factory</td><td>Creational</td><td>AppointmentFactory creates appointment objects</td></tr>
        <tr><td>Abstract Factory</td><td>Creational</td><td>ClinicNotificationFactory creates email and SMS channels together</td></tr>
        <tr><td>Builder</td><td>Creational</td><td>QrTicketBuilder builds QR ticket text</td></tr>
        <tr><td>Prototype</td><td>Creational</td><td>Appointment.cloneForRebook() copies dentist and treatment for Book again</td></tr>
        <tr><td>Adapter</td><td>Structural</td><td>EmailChannelAdapter and SmsChannelAdapter write to the notifications table</td></tr>
        <tr><td>Decorator</td><td>Structural</td><td>QrConfirmationDecorator adds a QR code to the basic confirmation</td></tr>
        <tr><td>Facade</td><td>Structural</td><td>QrCodeService hides the ZXing library</td></tr>
        <tr><td>Proxy</td><td>Structural</td><td>AuditedAppointmentService logs cancel actions</td></tr>
        <tr><td>Template Method</td><td>Behavioral</td><td>BookingTemplate shared by staff and patient booking</td></tr>
        <tr><td>Chain of Responsibility</td><td>Behavioral</td><td>Validation chain: name → address → phone → date → time</td></tr>
        <tr><td>Observer</td><td>Behavioral</td><td>NotificationService runs after a booking</td></tr>
        <tr><td>Strategy</td><td>Behavioral</td><td>StandardBillingStrategy calculates the bill</td></tr>
        <tr><td>Command</td><td>Behavioral</td><td>CancelAppointmentCommand cancels a patient visit</td></tr>
        <tr><td>State</td><td>Behavioral</td><td>AppointmentState allows cancel/bill only when BOOKED</td></tr>
        <tr><td>Multilevel inheritance</td><td>OOP</td><td>Person → ClinicUser → StaffUser → AdminUser</td></tr>
        <tr><td>MVC + DAO</td><td>Architecture</td><td>JSP, Servlets, Services, MySQL DAOs</td></tr>
        </tbody>
    </table>
</section>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
