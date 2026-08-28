# sunrise clinic

CIS6003 coursework. Appointment system for Sunrise Dental Clinic, Colombo.

JSP, Servlets, MySQL, Tomcat 10.

## how to run

1. JDK 17
2. import `database/sunrise_clinic.sql` in MySQL
3. set user/password in `src/main/resources/db.properties`
4. `mvn package`
5. put `target/sunrise-clinic.war` in Tomcat 10
6. open http://localhost:8080/sunrise-clinic

## logins

- admin / Admin@123
- reception / Staff@123
- patient: kamal@sunrise.lk / Patient@123

reception does walk-in booking, search and bills.
admin does reports, staff accounts and the catalogue.
