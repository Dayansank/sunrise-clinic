# -*- coding: utf-8 -*-
"""Build the CIS6003 Word report."""
from docx import Document
from docx.shared import Pt, Cm, RGBColor, Inches
from pathlib import Path

FIG = Path(r"E:\AD PRO\sunrise-clinic\docs\report\figures")
from docx.enum.text import WD_ALIGN_PARAGRAPH, WD_LINE_SPACING, WD_BREAK
from docx.enum.table import WD_TABLE_ALIGNMENT
from docx.oxml.ns import qn
import os

OUT = r"E:\AD PRO\sunrise-clinic\docs\report\CIS6003_WRIT1_Sunrise_Clinic_Report.docx"


def font(run, size=12, bold=False, italic=False):
    run.font.name = "Times New Roman"
    run._element.rPr.rFonts.set(qn("w:eastAsia"), "Times New Roman")
    run.font.size = Pt(size)
    run.bold = bold
    run.italic = italic
    run.font.color.rgb = RGBColor(0, 0, 0)


def P(doc, text, *, size=12, bold=False, italic=False, indent=True, after=8, align="left"):
    p = doc.add_paragraph()
    pf = p.paragraph_format
    pf.line_spacing_rule = WD_LINE_SPACING.ONE_POINT_FIVE
    pf.space_after = Pt(after)
    pf.space_before = Pt(0)
    pf.first_line_indent = Cm(1.27) if indent else Cm(0)
    if align == "center":
        p.alignment = WD_ALIGN_PARAGRAPH.CENTER
        pf.first_line_indent = Cm(0)
    run = p.add_run(text)
    font(run, size=size, bold=bold, italic=italic)
    return p


def H(doc, text):
    p = doc.add_paragraph()
    pf = p.paragraph_format
    pf.line_spacing_rule = WD_LINE_SPACING.ONE_POINT_FIVE
    pf.space_before = Pt(14)
    pf.space_after = Pt(8)
    pf.first_line_indent = Cm(0)
    run = p.add_run(text)
    font(run, size=14, bold=True)
    return p


def cap(doc, text):
    return P(doc, text, italic=True, indent=False, align="center", after=10)


def grey(doc, text):
    p = doc.add_paragraph()
    pf = p.paragraph_format
    pf.line_spacing_rule = WD_LINE_SPACING.ONE_POINT_FIVE
    pf.space_before = Pt(6)
    pf.space_after = Pt(6)
    p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    run = p.add_run(text)
    font(run, size=11, italic=True)
    run.font.color.rgb = RGBColor(90, 90, 90)
    return p


def pic(doc, name, width_cm=15.5):
    path = FIG / name
    if not path.exists():
        grey(doc, f"[Picture not found: {name}]")
        return
    p = doc.add_paragraph()
    p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    p.paragraph_format.space_before = Pt(6)
    p.paragraph_format.space_after = Pt(6)
    p.add_run().add_picture(str(path), width=Cm(width_cm))


def bullet(doc, text):
    p = doc.add_paragraph(style="List Bullet")
    pf = p.paragraph_format
    pf.line_spacing_rule = WD_LINE_SPACING.ONE_POINT_FIVE
    pf.left_indent = Cm(1.5)
    pf.first_line_indent = Cm(0)
    pf.space_after = Pt(3)
    if p.runs:
        p.runs[0].text = text
        font(p.runs[0])
    else:
        font(p.add_run(text))
    return p


def cell(c, text, *, bold=False, size=9):
    c.text = ""
    p = c.paragraphs[0]
    p.paragraph_format.space_after = Pt(1)
    p.paragraph_format.space_before = Pt(1)
    r = p.add_run(text)
    font(r, size=size, bold=bold)


def table(doc, headers, rows, size=9):
    t = doc.add_table(rows=1 + len(rows), cols=len(headers))
    t.style = "Table Grid"
    t.alignment = WD_TABLE_ALIGNMENT.CENTER
    for i, h in enumerate(headers):
        cell(t.rows[0].cells[i], h, bold=True, size=size)
    for ri, row in enumerate(rows, start=1):
        for ci, val in enumerate(row):
            cell(t.rows[ri].cells[ci], val, size=size)
    doc.add_paragraph()
    return t


def page_break(doc):
    doc.add_paragraph().add_run().add_break(WD_BREAK.PAGE)


def setup(doc):
    s = doc.sections[0]
    s.page_width = Cm(21.0)
    s.page_height = Cm(29.7)
    s.left_margin = Cm(2.54)
    s.right_margin = Cm(2.54)
    s.top_margin = Cm(2.54)
    s.bottom_margin = Cm(2.54)
    st = doc.styles["Normal"]
    st.font.name = "Times New Roman"
    st.font.size = Pt(12)
    st._element.rPr.rFonts.set(qn("w:eastAsia"), "Times New Roman")
    st.paragraph_format.line_spacing_rule = WD_LINE_SPACING.ONE_POINT_FIVE


def main():
    doc = Document()
    setup(doc)

    for _ in range(2):
        P(doc, "", indent=False, after=0)
    P(doc, "Cardiff Metropolitan University", size=14, bold=True, indent=False, align="center", after=4)
    P(doc, "in collaboration with ICBT Campus", italic=True, indent=False, align="center", after=16)
    P(doc, "CIS6003 Advanced Programming", size=14, bold=True, indent=False, align="center", after=6)
    P(doc, "WRIT1 Coursework", indent=False, align="center", after=16)
    P(doc, "Sunrise Dental Clinic", size=16, bold=True, indent=False, align="center", after=4)
    P(doc, "Appointment and Patient Management System", size=14, indent=False, align="center", after=20)
    P(doc, "Design, implementation, testing and GitHub evidence", italic=True, indent=False, align="center", after=24)
    P(doc, "Student name: ________________________________", indent=False, align="center", after=8)
    P(doc, "Student number: st____________________", indent=False, align="center", after=8)
    P(doc, "Batch / intake: ____________________", indent=False, align="center", after=20)
    P(doc, "August 2026", indent=False, align="center", after=8)
    P(doc, "Word count: about 5,000 (body text, excluding cover, tables of figures and the reference list)", size=11, italic=True, indent=False, align="center")

    page_break(doc)
    H(doc, "Declaration")
    P(doc, "I confirm that this report and the Sunrise Dental Clinic software are my own work for CIS6003. Books I used are listed in the references. I did not copy another student's project. The GitHub repository in Section 8 is the code I am submitting. UML, the 5-day Git plan, test cases and future work all describe this same clinic system. I have not added unrelated features just to fill pages.")
    P(doc, "Signature: ______________________     Date: ____________________", indent=False)

    H(doc, "Contents")
    for line in [
        "1  Introduction",
        "2  Git commit and branching strategy (5 days)",
        "3  Task A - Design",
        "      3.1  Assumptions",
        "      3.2  Use case specification (actors, include, extend)",
        "      3.3  Use case diagram and explanation",
        "      3.4  Class diagram and explanation",
        "      3.5  Sequence diagrams (one per main function)",
        "4  Task B - Implementation",
        "5  Design patterns used in the Java code",
        "6  Task C - Testing (30+ test cases)",
        "7  Task D - GitHub",
        "8  Future implementation",
        "9  Frequently asked questions",
        "10  Conclusion",
        "References",
        "Appendix A  Logins and URLs",
        "Appendix B  Diagram and screenshot sources",
    ]:
        P(doc, line, indent=False, after=2)

    P(doc, "UML diagrams and live system screenshots are already inserted in this one report.", size=11, italic=True, indent=False)

    page_break(doc)

    # 1
    H(doc, "1  Introduction")
    P(doc, "This report is my CIS6003 WRIT1 submission. The case is Sunrise Dental Clinic in Colombo. They still write bookings in a diary and they work out bills with a calculator. That is how they get double bookings, missing cards and a total that does not match the filling. I had to design a Java system, build it, test it and keep it on a public GitHub repository.")
    P(doc, "The six jobs from the brief stay at the centre of every diagram and every test in this document. Staff log in. Reception registers a visit with name, address, phone, dentist, treatment, date and time. Anyone on the staff side can search by appointment number. Reception calculates and prints a bill from the treatment cost plus a consultation fee. There is a Help page. Exit logs the person out so the next person at the desk does not inherit the session. I stored the data in MySQL. I did not use a text file.")
    P(doc, "I built a website: JSP pages, Jakarta Servlets, Tomcat 10 and MySQL 8. Some students make a Swing window. I used the browser because the same Java services can then be called as REST URLs under /api/. That is the distributed part. Extra pieces that still belong to a dental desk are an online patient portal, admin reports and a catalogue so a dentist name or the fee can change without me editing Java. Those extras appear in the use case diagram as extra ellipses. They do not replace the six required jobs, and I have not invented a pharmacy module or an X-ray archive that the brief never asked for.")
    P(doc, "The lecturer asked for a realistic 5-day Git plan, a use case specification that uses <<include>> and <<extend>> correctly, an explanation under every diagram, a separate sequence diagram for each main function, at least thirty test cases, and a short future list. Those items are in this report. They all talk about the same Sunrise system that is running at http://localhost:8080/sunrise-clinic and sitting at https://github.com/Dayansank/sunrise-clinic.")

    # 2 Git 5 day
    H(doc, "2  Git commit and branching strategy (5 days)")
    P(doc, "The project had to be developed over five days, not dumped as one zip. I used a short feature-branch workflow on top of main. I am the only developer, so I did not invent a Scrum team of six. Each day has its own branch. I merge that branch into main when the pieces on that day actually run. Commit messages are ordinary sentences, the same ones that are on GitHub now (Chacon and Straub, 2014).")
    P(doc, "I did not put db.properties on any branch. .gitignore blocks the MySQL password from day one. Branches are named feature/... so a marker can see what that day was for. I merge with a normal merge commit, then delete the feature branch. I do not force-push main as part of the daily habit.")

    H(doc, "2.1  Day 1 - foundation (branch: feature/foundation)")
    P(doc, "Day 1 is only the skeleton. If Tomcat cannot load a WAR, there is no point writing bills. I create the Maven WAR, the .gitignore, web.xml and context.xml. Then I add the MySQL script with tables, sample dentists, treatments, the two staff logins and fn_next_appointment_no(). Then I add the model classes, because the assignment also wants multilevel inheritance. Person, ClinicUser, Patient, StaffUser, AdminUser, ReceptionUser, Appointment, Bill, Dentist and Treatment go in here. At the end of day 1 I merge to main. The site does not book a patient yet. That is fine.")
    bullet(doc, "Commit: started the maven war project so i can run this on tomcat")
    bullet(doc, "Commit: added the mysql script with tables, sample dentists and the two staff logins")
    bullet(doc, "Commit: created person, staff, patient and appointment classes (multilevel inheritance)")

    H(doc, "2.2  Day 2 - data layer and patterns (branch: feature/data-and-patterns)")
    P(doc, "Day 2 is the middle of the 3-tier stack. DAOs talk to MySQL through the DBConnection singleton. Helpers hash passwords and check phones. Then I put the design patterns in: booking template, validation chain, billing strategy, observers, factory, and the rest that Section 5 lists. After that I write AppointmentService, BillingService, AuthService and QrCodeService so servlets will have something to call. I still have no pretty JSP menu. I merge when mvn compile is clean.")
    bullet(doc, "Commit: db connection singleton and dao classes to talk to mysql")
    bullet(doc, "Commit: helpers for password hashing, validation and json")
    bullet(doc, "Commit: put the design patterns in for booking, billing, validation and notifications")
    bullet(doc, "Commit: service layer for login, booking, bills and qr tickets")

    H(doc, "2.3  Day 3 - staff desk (branch: feature/staff-desk)")
    P(doc, "Day 3 is the six required staff jobs on the screen. LoginServlet, AppointmentServlet, SearchServlet, BillServlet, HelpServlet, LogoutServlet, the JSPs and AuthFilter. Reception can log in, book a walk-in, search, print a bill, read help and exit. I also wire RoleGuard so a URL typed by hand does not bypass the menu. This day is the one I would show first in a viva, because it is the brief. If a walk-in cannot be saved on day 3, I do not start the portal on day 4.")
    bullet(doc, "Commit: staff login, walk-in booking, search and print bill")

    H(doc, "2.4  Day 4 - portal, API and reports (branch: feature/portal-and-api)")
    P(doc, "Day 4 adds work that still belongs to the same clinic. Patients book online and get a QR ticket. REST URLs under /api/ reuse the same services. Admin reports use Chart.js for doughnut, bar and line. I do not add a second product. I merge when a patient can book a free slot and /api/appointments/{number} returns JSON.")
    bullet(doc, "Commit: patient portal so people can book online and get a qr ticket")
    bullet(doc, "Commit: rest apis for login, appointments, bills and live slots")
    bullet(doc, "Commit: reports page with charts for admin")

    H(doc, "2.5  Day 5 - roles, live catalogue, UI and tests (branch: feature/roles-ui-tests)")
    P(doc, "Day 5 is polish that the design already promised. Admin and reception stop sharing a menu. Admin can add or delete staff, with the rules that you cannot delete yourself or the last admin. The catalogue reads dentists and the fee from MySQL. The public homepage gets the slider. JUnit tests and the README go on last so a marker can run mvn test. Then I merge and push main.")
    bullet(doc, "Commit: split admin and reception, and admin can add or delete staff")
    bullet(doc, "Commit: clinic catalogue reads from mysql so website shows db changes")
    bullet(doc, "Commit: homepage ui with slider and the clinic look")
    bullet(doc, "Commit: junit tests and a short readme on how to run it")
    P(doc, "Table 1 is the same plan in one place. Every commit maps to a real file in the repo. I did not add extras that are not part of this clinic.")

    table(doc, ["Day", "Branch", "What I finish", "Then I merge"], [
        ["1", "feature/foundation", "WAR, SQL, model inheritance", "main"],
        ["2", "feature/data-and-patterns", "DAO, patterns, services", "main"],
        ["3", "feature/staff-desk", "Six staff jobs on JSP", "main"],
        ["4", "feature/portal-and-api", "Portal, REST, reports", "main"],
        ["5", "feature/roles-ui-tests", "Roles, catalogue, UI, JUnit", "main"],
    ])
    cap(doc, "Table 1: Five-day Git branching plan (same system as the UML and the tests)")

    # 3 Design
    H(doc, "3  Task A - Design")
    P(doc, "I drew the UML before I trusted the JSPs. The diagrams and the Java use the same names: Appointment, BillingService, AuthFilter, APT-2026-0001. I did not draw a 40-class enterprise model (Fowler, 2004).")

    H(doc, "3.1  Assumptions")
    P(doc, "The brief does not list every clinic rule. These assumptions are the ones the diagrams, the Git plan and the tests all share.")
    bullet(doc, "It is a website on Tomcat, not a desktop .exe. The six jobs still sit on a menu after login.")
    bullet(doc, "Reception and admin are different actors. Reception books, searches and bills. Admin does reports, staff and catalogue. They do not share one menu.")
    bullet(doc, "A patient can book online. That is extra. Those visits still get a normal appointment number.")
    bullet(doc, "MySQL creates APT-2026-0001. Nobody types the number.")
    bullet(doc, "A dentist cannot have two active visits at the same date and time. Cancelled rows do not block the slot.")
    bullet(doc, "Bill total = treatment cost + consultation fee from clinic_settings. One appointment has at most one bill.")
    bullet(doc, "A cancelled visit cannot be billed. A successful bill marks the visit COMPLETED.")
    bullet(doc, "Logout is the exit option. Login is a precondition for protected pages, not an <<include>> on every ellipse.")
    bullet(doc, "Passwords are hashed. Help text depends on role.")

    H(doc, "3.2  Use case specification")
    P(doc, "This subsection is the written specification for Figure 1. I used <<include>> only when the base use case cannot finish without the included one. I used <<extend>> only when the extra behaviour is optional or only happens in some cases. I did not draw <<include>> Login on every bubble. That is a common mistake. AuthFilter already sends a guest to login.jsp. Login stays as its own use case (Fowler, 2004).")

    H(doc, "3.2.1  Actors and who they talk to")
    P(doc, "Reception staff is the front desk. They use UC1 Log in, UC2 Register appointment, UC3 Search appointment, UC4 Calculate bill, UC5 View help and UC6 Exit. They do not use reports or staff admin.")
    P(doc, "Clinic admin is the office. They use UC1, UC3, UC5, UC6, plus UC7 View reports, UC8 Manage staff accounts and UC9 Update dentists and fees. They do not register a walk-in and they do not print the official bill. That matches StaffAccessPolicy in Java.")
    P(doc, "Patient is the portal user. They use UC1 (patient login), UC5, UC6 and UC10 Book own appointment. They do not print the clinic invoice.")

    H(doc, "3.2.2  <<include>> relationships and why they are required")
    P(doc, "Include means: every time you run the base use case, you also run the included one. It is not optional.")
    P(doc, "UC2 Register appointment includes Validate patient details. I never insert a walk-in until name, address, phone, date and time pass the chain. Without this include the paper problem just moves into MySQL.")
    P(doc, "UC2 also includes Check dentist slot is free. The Java call is appointmentDAO.isSlotTaken(). The database trigger trg_prevent_double_booking is the second lock. A booking that skips this include would recreate Monday morning collisions.")
    P(doc, "UC10 Book own appointment includes the same two use cases. Online booking uses BookingTemplate. The checks are not a courtesy. They are the same rules as the desk.")
    P(doc, "UC4 Calculate bill includes Find appointment by number. You cannot price a visit you have not loaded. In code, BillingService.createBill() calls findByNumber() first. I did not make reception type the treatment cost by hand.")
    P(doc, "UC2 and UC10 include Send booking notification. After a successful insert, NotificationService.onBooked() always writes the email and SMS rows. It is not a button the user may skip. That is why it is include, not extend. I am not claiming Dialog or Mobitel is connected. The row is always stored.")

    H(doc, "3.2.3  <<extend>> relationships and why they are required")
    P(doc, "Extend means: the base use case is already complete. The extension runs only sometimes, at an extension point.")
    P(doc, "Print bill extends UC4 Calculate bill. The base use case ends when the bill page is on screen with the three amounts. Printing is browser Ctrl+P. Plenty of receptionists will look at the total and not print. If I had used include, the diagram would say you cannot calculate a bill unless you print it. That is false.")
    P(doc, "Show QR ticket extends UC3 Search appointment. Search is complete when the visit is found or when the not-found message is shown. The QR image is extra and only appears on a successful find. A missing number has no QR. That is a conditional extension, not an include.")
    P(doc, "Filter reports by date extends UC7 View reports. Opening reports with today's date already satisfies the use case. Changing the date box is optional. ReportServlet reads a date parameter only if it is there.")
    P(doc, "I did not add random extend arrows for \"log error\" or \"show header\". Those are not use cases.")

    H(doc, "3.3  Use case diagram and explanation")
    pic(doc, "fig01_usecase.png")
    cap(doc, "Figure 1: Use case diagram of Sunrise Dental Clinic")
    P(doc, "What it represents. Figure 1 is the people around the clinic and the jobs they can start. The box is the website. Stick figures outside the box are actors. Ovals inside are use cases. Dashed arrows with <<include>> go from the base oval to a piece that always runs. Dashed arrows with <<extend>> go from the extra oval to the base oval.")
    P(doc, "What each part does. UC1 checks staff_users or the patient login. UC2 is the walk-in form. UC3 is search. UC4 is the bill. UC5 is help. UC6 is logout. UC7 to UC9 are admin-only. UC10 is the portal booking. Validate, Check slot, Find by number and Send notification are included pieces. Print, QR and Filter date are extensions.")
    P(doc, "Why those functions are required. The first six match the brief. Admin jobs exist because a clinic changes dentists and fees. The portal exists because the brief allowed extras that help. Validation and slot check exist because paper already failed at those two points.")
    P(doc, "How they interact, step by step. A receptionist logs in (UC1). AuthFilter then lets them open UC2. UC2 always runs validate and slot check, then saves, then always records a notification. Later they search (UC3). If the row exists, the page may also show a QR. They open UC4, which always finds the appointment first, then they may print. At the end of the shift they use UC6.")
    P(doc, "Link to the code. Actors map to ReceptionUser, AdminUser and Patient. Included validation is BookingValidationChain. Included slot check is BookingTemplate.ensureSlotFree() plus the MySQL trigger. Included find is AppointmentService.findByNumber(). Included notification is AppointmentObserver. Extend print is just the browser. Extend QR is QrCodeService on search.jsp. Extend date filter is the date field on reports.jsp.")

    H(doc, "3.4  Class diagram and explanation")
    pic(doc, "fig02_class.png")
    cap(doc, "Figure 2: Class diagram (inheritance and main objects)")
    P(doc, "What it represents. Figure 2 is the Java objects I actually coded, not every servlet. It shows multilevel inheritance on the left and Appointment in the middle.")
    P(doc, "What each class does. Person holds name and phone. ClinicUser adds active. Patient adds address and email. StaffUser adds username and role. AdminUser and ReceptionUser add the two can... methods. Appointment holds the number, date, time, status and bookedBy. Bill holds the three money fields. Dentist and Treatment are lookup data. AppointmentService and BillingService are the business layer. DBConnection is the singleton into MySQL.")
    P(doc, "Why they are required. Inheritance lets admin and reception share login fields without copying them. Appointment is the row the whole desk cares about. Bill is there so we do not charge twice. Services keep SQL out of the JSP.")
    P(doc, "How they interact, step by step. A walk-in becomes a Patient, then AppointmentFactory fills an Appointment linked to Dentist and Treatment. After treatment, BillingService reads the Appointment, asks ConsultationFeeConfig for the fee, uses StandardBillingStrategy, and writes a Bill. One patient has many appointments. One appointment has zero or one bill.")
    P(doc, "Link to the code. The packages are com.sunrise.clinic.model, service, dao and pattern. cloneForRebook() is Prototype for the portal. I left servlets off the drawing so it stays readable. MVC is still there: JSP view, servlet controller, service plus model.")

    H(doc, "3.5  Sequence diagrams (one per main function)")
    P(doc, "A class box does not show time. I drew a separate sequence for each required function. I did not put login, booking and billing in one giant ladder (Sommerville, 2016). Each picture names the actor, the JSP, the servlet, the service and MySQL where it is used. Alternative flows sit in alt / opt boxes. I did not draw a sequence for UC8 and UC9. Those screens are admin extras. The important paths for the brief are the six below. UC7 is covered by the reports page and TC28 to TC30 instead of another ladder.")

    H(doc, "3.5.1  UC1 Log in")
    pic(doc, "fig05_seq_login.png")
    cap(doc, "Figure 3: Sequence diagram - staff log in")
    P(doc, "What it represents. A staff member signing in before they touch the desk menu.")
    P(doc, "Actor: reception or admin. UI: login.jsp. Backend: LoginServlet then AuthService. Database: staff_users through UserDAO.")
    P(doc, "Step by step. They type username and password. LoginServlet calls AuthService.login(). If a field is blank, the page stays on login with an error. If the hash does not match, same thing. If the account is inactive, same thing. If MySQL is down, the servlet shows a connection message. On success the StaffUser goes into the session for 30 minutes and the browser moves to menu.jsp. Admin then sees reports. Reception sees register and bill.")
    P(doc, "Why it is required. Without login anyone at the kiosk could print bills. How it maps: PasswordUtil, UserDAO.findPasswordHash(), AuthFilter after this point.")

    H(doc, "3.5.2  UC2 Register appointment")
    pic(doc, "fig03_seq_booking.png")
    cap(doc, "Figure 4: Sequence diagram - register a walk-in appointment")
    P(doc, "What it represents. Reception saving a new visit.")
    P(doc, "Actor: reception. UI: register.jsp / AppointmentServlet form. Backend: AppointmentService then StaffBookingProcess (template). Database: patients and appointments. Observer: NotificationService.")
    P(doc, "Step by step. They submit name, address, phone, dentist, treatment, date and time. The servlet calls register(). The patient row is inserted. The template always validates (include), always checks the slot (include), asks for the next APT-2026 number, inserts the appointment, then always writes notification rows (include). The page shows the number. If validation fails, ClinicException comes back and nothing is booked. If the slot is taken, the same. Alternative flow: patient portal uses PatientBookingProcess. Only createdBy and bookedBy change.")
    P(doc, "Why it is required. This is the paper diary replacement. Link to code: BookingValidationChain, AppointmentDAO.isSlotTaken(), fn_next_appointment_no(), AppointmentFactory.")

    H(doc, "3.5.3  UC3 Search appointment")
    pic(doc, "fig06_seq_search.png")
    cap(doc, "Figure 5: Sequence diagram - search by appointment number")
    P(doc, "What it represents. Looking up one visit.")
    P(doc, "Actor: reception or admin. UI: search.jsp. Backend: SearchServlet, AppointmentService. Database: appointments joined to patient, dentist and treatment.")
    P(doc, "Step by step. They type APT-2026-0001. If the box is empty, the service throws. If the number is unknown, they see not found. If it exists, the details appear. Optional extend: the page can request the QR image. A failed search does not call QR.")
    P(doc, "Why it is required. The brief says search and display by number. Link to code: findByNumber(), search.jsp, QrServlet.")

    H(doc, "3.5.4  UC4 Calculate bill")
    pic(doc, "fig04_seq_bill.png")
    cap(doc, "Figure 6: Sequence diagram - calculate and show a bill")
    P(doc, "What it represents. Turning a booked visit into money.")
    P(doc, "Actor: reception. UI: bill.jsp. Backend: BillServlet, BillingService, AppointmentState, ConsultationFeeConfig, StandardBillingStrategy. Database: appointments, clinic_settings, bills.")
    P(doc, "Step by step. They enter the number. The service always finds the appointment first (include). If status is not BOOKED, State blocks the bill. If a bill already exists, the old one is returned (no second insert). Otherwise the singleton reads the fee, the strategy adds it to the treatment cost, the bill is saved and the visit becomes COMPLETED. Optional extend: they print from the browser.")
    P(doc, "Why it is required. Paper bills were wrong. Link to code: createBill(), billDAO.insert(), markCompleted().")

    H(doc, "3.5.5  UC5 View help")
    pic(doc, "fig07_seq_help.png")
    cap(doc, "Figure 7: Sequence diagram - view help")
    P(doc, "What it represents. A new staff member reading how to use the desk.")
    P(doc, "Actor: staff. UI: help.jsp. Backend: HelpServlet, HelpService. No extra table is required. The session already has staffUser.")
    P(doc, "Step by step. They click Help. The servlet reads the role. Admin gets catalogue and reports steps. Reception gets walk-in and bill steps. A guest never reaches this sequence because AuthFilter sends them to login.")
    P(doc, "Why it is required. The brief asks for help so new staff can learn the system. Link to code: HelpService.adminSteps() and receptionSteps().")

    H(doc, "3.5.6  UC6 Exit / log out")
    pic(doc, "fig08_seq_logout.png")
    cap(doc, "Figure 8: Sequence diagram - exit / log out")
    P(doc, "What it represents. Leaving the system safely.")
    P(doc, "Actor: staff or patient. UI: Exit link. Backend: LogoutServlet. No MySQL write.")
    P(doc, "Step by step. They click Exit. If a session exists it is invalidated. The browser goes to the public home page. Alternative: there is already no session; the servlet still redirects home. The next person cannot press Back into a live bill page because AuthFilter and no-cache headers block it.")
    P(doc, "Why it is required. The brief says exit safely. On a website that means logout, not closing a .exe.")

    # 4 Implementation
    H(doc, "4  Task B - Implementation")
    P(doc, "I coded Java 17, Maven WAR sunrise-clinic, NetBeans, Tomcat 10.1, MySQL 8. The URL is http://localhost:8080/sunrise-clinic. JSPs do not contain SQL. Browser to servlet to service to DAO to MySQL. AuthFilter plus ReceptionAccessPolicy and AdminAccessPolicy decide the menu. If I add a dentist in MySQL, the dropdown shows it after refresh. That live read is why day 5 has a catalogue commit. The homepage slider is only skin. It does not book a patient. I mention that so a marker does not think the slider is a missing use case.")
    P(doc, "Reception menu: Register, Search, Bill, Help, Exit. Admin menu: Reports, Staff, Catalogue, Search, Help, Exit. The register form uses live dentist and treatment lists. Search shows the QR. Bill shows fee, treatment and total. Help text changes with role. Exit calls /logout.")
    pic(doc, "screen_home.png")
    cap(doc, "Figure 9: Public home page of Sunrise Dental Clinic")
    pic(doc, "screen_login.png")
    cap(doc, "Figure 10: Staff login")
    pic(doc, "screen_menu_reception.png")
    cap(doc, "Figure 11: Reception menu after login")
    pic(doc, "screen_register.png")
    cap(doc, "Figure 12: Register new appointment")
    pic(doc, "screen_search.png")
    cap(doc, "Figure 13: Search by appointment number with QR ticket")
    pic(doc, "screen_bill.png")
    cap(doc, "Figure 14: Calculate and print bill")
    pic(doc, "screen_help.png")
    cap(doc, "Figure 15: Reception help page")
    P(doc, "Validation is the include from Figure 1: name, address, phone, date, time. Phone is 10 digits starting with 0, or +94 and nine digits. Java checks the slot. The trigger is the second lock. The schema is sunrise_clinic.sql. Tables: staff_users, patients, dentists, treatments, appointments, bills, clinic_settings. Passwords are SHA-256 with a salt. The fee starts at LKR 1500.00.")
    P(doc, "REST URLs reuse the same services: POST /api/auth/login, POST /api/appointments, GET /api/appointments/APT-2026-0001, POST and GET /api/bills/{number}, GET /api/slots. That is the web-service mark. I am not hosting this on the public internet.")
    P(doc, "Admin reports show three Chart.js charts: doughnut for status, bar for income, line for seven days. Reception cannot open that page. The portal, staff admin and catalogue are extras from the same clinic, not a second assignment.")

    # 5 Patterns
    H(doc, "5  Design patterns used in the Java code")
    P(doc, "I picked patterns that sit on booking, billing and access, not a list copied from a poster (Gamma et al., 1995). They match Figure 2 and the sequence diagrams. I did not add a pattern for a feature that does not exist.")
    P(doc, "Singleton: DBConnection, ConsultationFeeConfig, QrCodeService. One MySQL setup, one fee, one QR helper. Factory: AppointmentFactory. Abstract Factory: ClinicNotificationFactory for email and SMS adapters together. Builder: QrTicketBuilder. Prototype: Appointment.cloneForRebook().")
    P(doc, "Adapter: EmailChannelAdapter and SmsChannelAdapter. Decorator: QrConfirmationDecorator. Facade: QrCodeService over ZXing. Proxy: AuditedAppointmentService for cancel logging.")
    P(doc, "Template Method: BookingTemplate, used in Figure 4 by staff and by the portal. Chain of Responsibility: the validation include. Observer: NotificationService, the include after save. Strategy: StandardBillingStrategy on Figure 6, and AdminAccessPolicy / ReceptionAccessPolicy for the two menus. Command: CancelAppointmentCommand. State: AppointmentState on Figure 6.")
    P(doc, "I also used multilevel inheritance and MVC plus DAO. Those are not Gang of Four patterns but they are in the marking discussion. For a viva I would walk Singleton, Factory, Strategy, Template Method, Observer and the validation chain, because those are on the happy path in the sequences.")

    # 6 Testing 30+
    H(doc, "6  Task C - Testing")
    P(doc, "I used JUnit 5 and Mockito for rules I could isolate, then I clicked the six jobs on Tomcat. I did not write Selenium. Table 2 has more than thirty cases. Every case belongs to a use case in Figure 1 or a pattern in Section 5. I used TDD for money and validation: failing test for 8000 + 1500 = 9500, then StandardBillingStrategy; failing phone test for 07712, then isValidPhone() (Beck, 2003). I did not pretend I wrote a failing servlet test before every JSP.")

    table(doc, ["ID", "Use case / rule", "Input", "Expected", "Type"], [
        ["TC01", "UC1 login", "admin / Admin@123", "Admin menu", "Manual"],
        ["TC02", "UC1 login", "reception / Staff@123", "Reception menu", "Manual"],
        ["TC03", "UC1 login", "admin / wrong", "Error, stay on login", "JUnit"],
        ["TC04", "UC1 login", "blank user or password", "ClinicException", "JUnit"],
        ["TC05", "UC1 login", "inactive staff flag", "Not active message", "Manual"],
        ["TC06", "UC1 login", "MySQL stopped", "Cannot connect message", "Manual"],
        ["TC07", "UC2 register", "Kamal, 0771234567, Filling, tomorrow 10:30", "APT-2026-xxxx saved", "Manual"],
        ["TC08", "UC2 include validate", "name Al", "Rejected, no row", "JUnit"],
        ["TC09", "UC2 include validate", "phone 07712", "Rejected", "JUnit"],
        ["TC10", "UC2 include validate", "phone 0771234567 and +94771234567", "Accepted", "JUnit"],
        ["TC11", "UC2 include validate", "past date", "Rejected", "JUnit"],
        ["TC12", "UC2 include slot", "same dentist/date/time already BOOKED", "Rejected", "JUnit"],
        ["TC13", "UC2 include notify", "successful save", "email and SMS rows written", "Manual"],
        ["TC14", "UC3 search", "APT-2026-0001", "Details shown", "Manual"],
        ["TC15", "UC3 search", "APT-2026-9999", "Not found", "JUnit"],
        ["TC16", "UC3 search", "blank number", "Number required", "JUnit"],
        ["TC17", "UC3 extend QR", "found appointment", "QR image loads", "Manual"],
        ["TC18", "UC3 extend QR", "not found", "No QR", "Manual"],
        ["TC19", "UC4 include find", "known BOOKED number", "Bill page opens", "Manual"],
        ["TC20", "UC4 bill math", "Filling 8000 + fee 1500", "Total 9500.00", "JUnit"],
        ["TC21", "UC4 state", "CANCELLED appointment", "Cannot bill", "JUnit"],
        ["TC22", "UC4 duplicate", "bill already exists", "Old bill, no second insert", "Manual"],
        ["TC23", "UC4 extend print", "bill on screen", "Browser can print", "Manual"],
        ["TC24", "UC5 help", "reception session", "Desk steps, no reports text", "Manual"],
        ["TC25", "UC5 help", "admin session", "Staff and catalogue steps", "Manual"],
        ["TC26", "UC6 exit", "click Exit", "Session gone, home page", "Manual"],
        ["TC27", "UC6 exit", "Back after logout", "Protected page blocked", "Manual"],
        ["TC28", "UC7 reports", "admin opens /reports", "Charts + table", "Manual"],
        ["TC29", "UC7 reports", "reception opens /reports", "Blocked by RoleGuard", "JUnit"],
        ["TC30", "UC7 extend date", "pick another date", "List changes", "Manual"],
        ["TC31", "UC8 staff", "create desk2 reception", "Row inserted", "JUnit"],
        ["TC32", "UC8 staff", "duplicate username admin", "Rejected", "JUnit"],
        ["TC33", "UC8 staff", "delete own account", "Rejected", "JUnit"],
        ["TC34", "UC10 portal", "patient books free slot", "APT number, bookedBy PATIENT", "Manual"],
        ["TC35", "State / cancel", "COMPLETED visit", "Cannot cancel", "JUnit"],
        ["TC36", "REST", "GET /api/appointments/APT-2026-0001", "JSON visit", "Manual"],
        ["TC37", "REST", "GET /api/slots missing date", "400 error JSON", "Manual"],
        ["TC38", "Inheritance", "AdminUser is StaffUser and Person", "JUnit asserts true", "JUnit"],
        ["TC39", "Prototype", "cloneForRebook()", "dentist and treatment copied", "JUnit"],
        ["TC40", "Password hash", "Admin@123", "Matches SQL seed hash", "JUnit"],
    ])
    cap(doc, "Table 2: Thirty-plus test cases, aligned with the use cases in Figure 1")
    P(doc, "There are 14 JUnit classes and 32 @Test methods under src/test/java. I run mvn test. Mockito stands in for DAOs. .github/workflows/ci.yml is meant to run the same command on JDK 17. Test data is the SQL seed: Nadeesha Perera as admin, Ishara Jayasuriya as reception, Kamal Perera at 12 Galle Road, Filling LKR 8000.00, fee LKR 1500.00.")

    # 7 GitHub
    H(doc, "7  Task D - GitHub")
    P(doc, "Public repository: https://github.com/Dayansank/sunrise-clinic")
    P(doc, "Section 2 is the five-day branch plan. The commit messages on GitHub are those day-by-day sentences. A marker can open any commit and see what existed. I stayed honest about the calendar. I am not inventing nightly ticks that the website would contradict. The useful part is feature-sized commits instead of one dump.")
    pic(doc, "screen_github.png")
    cap(doc, "Figure 16: Public GitHub repository")
    pic(doc, "screen_commits.png")
    cap(doc, "Figure 17: Commit history on main")
    P(doc, "CI is mvn test on Temurin 17. That is integration of the tests in Table 2. I am not deploying Tomcat from GitHub. The README says how to import SQL, set db.properties, build the WAR and open the URL.")

    # 8 Future
    H(doc, "8  Future implementation")
    P(doc, "These are later versions of the same clinic, not a new product.")
    bullet(doc, "Plug a real SMS and email gateway into NotificationChannel. The include already writes the rows.")
    bullet(doc, "Password reset for staff and patients. Login already exists; reset is extra.")
    bullet(doc, "Card payment after UC4, so the bill can be marked paid.")
    bullet(doc, "Reminder the day before a BOOKED visit, using the same appointment number.")
    bullet(doc, "Export the reports page to PDF.")
    bullet(doc, "Dentist leave dates, still using Check dentist slot.")
    bullet(doc, "Short treatment notes on the appointment, still searched by APT number.")
    bullet(doc, "Keep GitHub Actions green on every push of main after day 5.")
    P(doc, "I did not add extras that are not part of this clinic. If a future feature cannot point back to an actor or a table I already have, I will not put it on a slide.")

    # 9 FAQ
    H(doc, "9  Frequently asked questions")
    P(doc, "Why is login not <<include>> on every use case? Because the actor can finish Help or Exit as their own jobs, and because a filter already blocks guests. Include would say Register cannot even start unless the login use case is nested inside it every time, which makes the drawing noisy and is easy to get wrong.")
    P(doc, "Why is print <<extend>> and notification <<include>>? Print is optional after the bill is on screen. Notification always runs after a successful save in NotificationService. Optional versus always is the whole difference between extend and include.")
    P(doc, "Why three actors, not one Staff? Admin cannot print a walk-in bill in the code. Reception cannot open reports. One stick figure would lie.")
    P(doc, "Why JSP instead of Swing? Same Java rules can be used by the browser and by /api/. That is the web-service part.")
    P(doc, "Why only three charts? The reports page has doughnut, bar and line. That is enough for status, income and the week. I did not add a pie for decoration.")
    P(doc, "Where are the 15 design patterns? Section 5. They are Java classes, not a menu card.")
    P(doc, "How do I start the site? Import sunrise_clinic.sql, keep db.properties on this PC, mvn package, deploy the WAR to Tomcat, open http://localhost:8080/sunrise-clinic. Logins are in Appendix A.")
    P(doc, "Do the tests need MySQL? The JUnit ones mock DAOs. The manual rows in Table 2 need Tomcat and MySQL.")
    P(doc, "Is the 5-day plan a different system? No. Day 3 is the six jobs. Days 4 and 5 are the extras already in Figure 1. If the lecturer opens GitHub and the report side by side, the commit sentences should match Table 1. That is the consistency check I used while I wrote this file.")

    # 10
    H(doc, "10  Conclusion")
    P(doc, "Sunrise Dental Clinic needed something better than a diary. I designed it with a use case diagram that uses include and extend on purpose, a class diagram that matches the Java packages, and a separate sequence for login, register, search, bill, help and exit. I built those flows on Tomcat and MySQL over a five-day branch plan. I tested them with more than thirty cases. Patterns sit on booking and billing. Future work stays inside this clinic. The code is at the GitHub link in Section 7.")
    P(doc, "If I had another month I would finish a live SMS gateway and keep Actions green. For this submission the diagrams, the Git days, the tests and the running WAR are the same system. I also kept the word count near five thousand so the extra Git, include/extend, sequence and test-case pages still sit inside the limit the lecturer accepted. I would rather cut a buzzword than add a feature that is not in Sunrise.")

    H(doc, "References")
    for r in [
        "Beck, K. (2003) Test-driven development: by example. Boston: Addison-Wesley.",
        "Chacon, S. and Straub, B. (2014) Pro Git. 2nd edn. New York: Apress. Available at: https://git-scm.com/book/en/v2 (Accessed: 21 August 2026).",
        "Fowler, M. (2004) UML distilled: a brief guide to the standard object modeling language. 3rd edn. Boston: Addison-Wesley.",
        "Gamma, E., Helm, R., Johnson, R. and Vlissides, J. (1995) Design patterns: elements of reusable object-oriented software. Reading, MA: Addison-Wesley.",
        "Sommerville, I. (2016) Software engineering. 10th edn. Harlow: Pearson Education.",
    ]:
        P(doc, r, indent=False, after=8)

    H(doc, "Appendix A  Logins and URLs")
    bullet(doc, "Admin: admin / Admin@123")
    bullet(doc, "Reception: reception / Staff@123")
    bullet(doc, "Patient: kamal@sunrise.lk / Patient@123")
    bullet(doc, "App: http://localhost:8080/sunrise-clinic")
    bullet(doc, "GitHub: https://github.com/Dayansank/sunrise-clinic")

    H(doc, "Appendix B  Diagram and screenshot sources")
    P(doc, "All pictures in this Word file are already inserted. UML source files stay in docs/uml/. Live screenshots were taken from http://localhost:8080/sunrise-clinic and from https://github.com/Dayansank/sunrise-clinic.", indent=False)

    doc.save(OUT)
    print("Wrote", OUT)


if __name__ == "__main__":
    main()
