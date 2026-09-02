# Sunrise Dental Clinic

CIS6003 coursework. Java website for a dental clinic in Colombo.

JSP + Servlets, MySQL, Tomcat 10. Patients book online. Reception does walk-in and bills. Admin does reports, staff and dentists/fees.

## Run it

1. JDK 17
2. MySQL 8 - import `database/sunrise_clinic.sql`
3. Copy `src/main/resources/db.properties` and put your mysql password
4. `mvn package`
5. Put `target/sunrise-clinic.war` on Tomcat 10
6. http://localhost:8080/sunrise-clinic

## Logins I used

- admin / Admin@123
- reception / Staff@123
- kamal@sunrise.lk / Patient@123

## Notes

Admin and reception menus are different. If I change a dentist or the fee in MySQL it shows on the site after refresh.

`mvn test` for JUnit.

REST is under `/api/`.

## Later (empty branches, not coded)

- `feature/future-sms-email` - real sms/email
- `feature/future-password-reset`
- `feature/future-card-payment`
- `feature/future-reminders`
- `feature/future-report-pdf`
- `feature/future-dentist-leave`
- `feature/future-treatment-notes`
