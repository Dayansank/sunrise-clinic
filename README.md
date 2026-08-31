# Sunrise Dental Clinic

CIS6003 Advanced Programming coursework. This is a Java web system for a private dental clinic in Colombo.

I built it with JSP + Servlets (Jakarta), MySQL and Tomcat 10. Patients can book online. Reception handles walk-in visits and bills. Admin looks after reports, staff accounts and the clinic catalogue.

## How to run it

1. JDK 17
2. MySQL 8 — import `database/sunrise_clinic.sql`
3. Copy `src/main/resources/db.properties` and set your MySQL user/password
4. `mvn package`
5. Deploy `target/sunrise-clinic.war` to Tomcat 10
6. Open http://localhost:8080/sunrise-clinic

## Logins I used for testing

- Admin: `admin` / `Admin@123`
- Reception: `reception` / `Staff@123`
- Demo patient: `kamal@sunrise.lk` / `Patient@123`

## What is in the project

- 3-tier: JSP → Servlets/services → MySQL DAOs
- REST under `/api/`
- Design patterns in the Java code (Singleton, Factory, Builder, Strategy, Observer, and more)
- JUnit tests (`mvn test`)

Admin and reception do **not** share the same menu. If I change dentists or the consultation fee in MySQL (or on the admin catalogue page), the website shows it after refresh.

## Later extras (empty branches, not built yet)

I left GitHub branches for work I might add after the assignment:

- `feature/future-sms-email` — real SMS/email, not only a log
- `feature/future-password-reset` — forgot password
- `feature/future-card-payment` — pay the bill by card
- `feature/future-reminders` — remind the patient before the visit
- `feature/future-report-pdf` — download reports as PDF
- `feature/future-dentist-leave` — block a dentist who is on leave
- `feature/future-treatment-notes` — dentist notes on a visit

