<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sunrise.clinic.pattern.ConsultationFeeConfig" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sunrise Dental Clinic · Colombo</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="landing">
<%@ include file="/WEB-INF/jspf/public-header.jspf" %>

<section class="slider" id="homeSlider" aria-label="Clinic highlights">
    <div class="slides">
        <article class="slide is-active" style="background-image:url('https://images.unsplash.com/photo-1606811841689-23dfddce3e95?auto=format&fit=crop&w=1800&q=80')">
            <div class="slide-copy">
                <p class="crumb">Home</p>
                <p class="kicker">Colombo 03</p>
                <h1>Sunrise Dental Clinic</h1>
                <p class="lead">Small private clinic. You can book online or just walk in at reception.</p>
                <div class="portal-row">
                    <a class="btn orange" href="${pageContext.request.contextPath}/patient-register.jsp">Book appointment</a>
                    <a class="btn ghost" href="${pageContext.request.contextPath}/patient-login">Patient login</a>
                </div>
            </div>
        </article>
        <article class="slide" style="background-image:url('https://images.unsplash.com/photo-1588776814546-1ffcf47267a5?auto=format&fit=crop&w=1800&q=80')">
            <div class="slide-copy">
                <p class="kicker">What we do</p>
                <h1>Check-ups, fillings and cleaning</h1>
                <p class="lead">Also root canal and whitening. Ask at the desk if you are not sure.</p>
                <div class="portal-row">
                    <a class="btn orange" href="${pageContext.request.contextPath}/patient-register.jsp">Book appointment</a>
                    <a class="btn ghost" href="${pageContext.request.contextPath}/index.jsp#services">View services</a>
                </div>
            </div>
        </article>
        <article class="slide" style="background-image:url('https://images.unsplash.com/photo-1629909613654-28e377c37b09?auto=format&fit=crop&w=1800&q=80')">
            <div class="slide-copy">
                <p class="kicker">Online booking</p>
                <h1>Pick a free time</h1>
                <p class="lead">Login, choose dentist and time. The system gives you a number like APT-2026-0001.</p>
                <div class="portal-row">
                    <a class="btn orange" href="${pageContext.request.contextPath}/patient-login">Patient login</a>
                    <a class="btn ghost" href="${pageContext.request.contextPath}/login.jsp">Staff portal</a>
                </div>
            </div>
        </article>
        <article class="slide" style="background-image:url('https://images.unsplash.com/photo-1609840114035-3c981b782dfe?auto=format&fit=crop&w=1800&q=80')">
            <div class="slide-copy">
                <p class="kicker">Opening hours</p>
                <h1>Mon to Sat, 9am to 5pm</h1>
                <p class="lead">12 Galle Road, Colombo 03. Consultation fee LKR <%= ConsultationFeeConfig.getInstance().getFee() %>.</p>
                <div class="portal-row">
                    <a class="btn orange" href="${pageContext.request.contextPath}/patient-register.jsp">Book appointment</a>
                    <a class="btn ghost" href="${pageContext.request.contextPath}/index.jsp#contact">Clinic hours</a>
                </div>
            </div>
        </article>
    </div>
    <canvas id="particlesNetwork" class="particles-canvas" aria-hidden="true"></canvas>
    <button class="slider-nav prev" type="button" aria-label="Previous slide">&#10094;</button>
    <button class="slider-nav next" type="button" aria-label="Next slide">&#10095;</button>
    <div class="slider-dots" role="tablist" aria-label="Choose slide"></div>
</section>

<section class="section light hours">
    <img src="https://images.unsplash.com/photo-1588776814546-1ffcf47267a5?auto=format&fit=crop&w=1200&q=80" alt="Dental treatment room">
    <div>
        <p class="eyebrow">About</p>
        <h2>About the clinic</h2>
        <p>Sunrise Dental Clinic is in Colombo 03. New patients can make an account on this site. If you dont want to book online, reception can still add you at the desk.</p>
        <p>Consultation is LKR <%= ConsultationFeeConfig.getInstance().getFee() %>. Two people cannot take the same dentist at the same time.</p>
    </div>
</section>

<section class="section tint" id="services">
    <h2>Services we offer</h2>
    <div class="grid-3">
        <article class="service-card">
            <div class="icon-badge">01</div>
            <h3>Check-up</h3>
            <p>Normal exam. X-ray if the dentist needs it.</p>
        </article>
        <article class="service-card">
            <div class="icon-badge">02</div>
            <h3>Cleaning</h3>
            <p>Scaling and polish.</p>
        </article>
        <article class="service-card">
            <div class="icon-badge">03</div>
            <h3>Fillings</h3>
            <p>White fillings for cavities.</p>
        </article>
        <article class="service-card">
            <div class="icon-badge">04</div>
            <h3>Root canal</h3>
            <p>If the tooth is infected we try to save it.</p>
        </article>
        <article class="service-card">
            <div class="icon-badge">05</div>
            <h3>Whitening</h3>
            <p>Teeth whitening at the clinic.</p>
        </article>
        <article class="service-card">
            <div class="icon-badge">06</div>
            <h3>Braces check</h3>
            <p>We look at alignment. For braces we send you to a specialist.</p>
        </article>
    </div>
</section>

<section class="section light" id="specialists">
    <h2>How this website works</h2>
    <div class="grid-3">
        <article class="feature">
            <div class="icon-badge">A</div>
            <h3>Book online</h3>
            <p>Make an account, pick dentist and a free time.</p>
        </article>
        <article class="feature">
            <div class="icon-badge">B</div>
            <h3>Your visits</h3>
            <p>After login you can see appointments, cancel one, and check bills.</p>
        </article>
        <article class="feature">
            <div class="icon-badge">C</div>
            <h3>Reception</h3>
            <p>Staff can add a walk-in, search the number and print the bill.</p>
        </article>
    </div>
</section>

<section class="cta-band">
    <div>
        <h2>Need an appointment?</h2>
        <p>Register if you are new. Login if you already made an account.</p>
    </div>
    <a class="btn orange" href="${pageContext.request.contextPath}/patient-register.jsp">Book appointment</a>
</section>

<section class="section light hours" id="contact">
    <div>
        <p class="eyebrow">Visit us</p>
        <h2>Clinic hours &amp; contact</h2>
        <p>Monday – Saturday · 09:00 – 17:00<br>Closed Sunday</p>
        <p>12 Galle Road, Colombo 03<br>+94 11 234 5678</p>
    </div>
    <img src="https://images.unsplash.com/photo-1629909613654-28e377c37b09?auto=format&fit=crop&w=1200&q=80" alt="Clinic reception">
</section>

<%@ include file="/WEB-INF/jspf/public-footer.jspf" %>
<script>
(function () {
    const root = document.getElementById("homeSlider");
    if (!root) return;
    const slides = Array.from(root.querySelectorAll(".slide"));
    const dotsWrap = root.querySelector(".slider-dots");
    let index = 0;
    let timer;

    slides.forEach((_, i) => {
        const dot = document.createElement("button");
        dot.type = "button";
        dot.className = "dot" + (i === 0 ? " is-active" : "");
        dot.setAttribute("aria-label", "Slide " + (i + 1));
        dot.addEventListener("click", () => go(i, true));
        dotsWrap.appendChild(dot);
    });
    const dots = Array.from(dotsWrap.querySelectorAll(".dot"));

    function go(next, pause) {
        slides[index].classList.remove("is-active");
        dots[index].classList.remove("is-active");
        index = (next + slides.length) % slides.length;
        slides[index].classList.add("is-active");
        dots[index].classList.add("is-active");
        if (pause) restart();
    }
    function restart() {
        clearInterval(timer);
        timer = setInterval(() => go(index + 1, false), 5500);
    }
    root.querySelector(".prev").addEventListener("click", () => go(index - 1, true));
    root.querySelector(".next").addEventListener("click", () => go(index + 1, true));
    root.addEventListener("mouseenter", () => clearInterval(timer));
    root.addEventListener("mouseleave", restart);
    restart();
})();

(function () {
    const canvas = document.getElementById("particlesNetwork");
    const slider = document.getElementById("homeSlider");
    if (!canvas || !slider || window.matchMedia("(prefers-reduced-motion: reduce)").matches) return;
    const ctx = canvas.getContext("2d");
    const mouse = { x: null, y: null };
    let width = 0;
    let height = 0;
    let particles = [];

    function spawn() {
        const speed = 2.4 + Math.random() * 3.4;
        const angle = Math.random() * Math.PI * 2;
        return {
            x: Math.random() * width,
            y: Math.random() * height,
            vx: Math.cos(angle) * speed,
            vy: Math.sin(angle) * speed,
            r: Math.random() * 2.2 + 0.8,
            life: Math.random() * 80,
            maxLife: 70 + Math.random() * 90
        };
    }

    function alphaFor(p) {
        const t = p.life / p.maxLife;
        if (t < 0.14) return t / 0.14;
        if (t > 0.52) return Math.max(0, 1 - (t - 0.52) / 0.48);
        return 1;
    }

    function resize() {
        width = canvas.width = slider.clientWidth;
        height = canvas.height = slider.clientHeight;
        const count = Math.max(55, Math.floor((width * height) / 11000));
        particles = Array.from({ length: Math.min(count, 140) }, spawn);
    }

    function draw() {
        ctx.clearRect(0, 0, width, height);
        for (let i = 0; i < particles.length; i++) {
            const p = particles[i];
            p.life += 1.8;
            if (p.life >= p.maxLife) {
                particles[i] = spawn();
                continue;
            }
            if (mouse.x != null) {
                const mdx = p.x - mouse.x;
                const mdy = p.y - mouse.y;
                const mdist = Math.hypot(mdx, mdy);
                if (mdist < 160 && mdist > 0.1) {
                    p.vx += (mdx / mdist) * 0.55;
                    p.vy += (mdy / mdist) * 0.55;
                    p.life += 2.4;
                }
            }
            p.x += p.vx;
            p.y += p.vy;
            if (p.x < 0 || p.x > width) p.vx *= -1;
            if (p.y < 0 || p.y > height) p.vy *= -1;

            const a = alphaFor(p);
            ctx.beginPath();
            ctx.arc(p.x, p.y, p.r * (0.65 + a * 0.55), 0, Math.PI * 2);
            ctx.fillStyle = "rgba(255,255,255," + (0.9 * a) + ")";
            ctx.fill();

            for (let j = i + 1; j < particles.length; j++) {
                const q = particles[j];
                const dx = p.x - q.x;
                const dy = p.y - q.y;
                const dist = Math.hypot(dx, dy);
                if (dist < 150) {
                    const qa = alphaFor(q);
                    const fade = (1 - dist / 150) * a * qa;
                    ctx.beginPath();
                    ctx.moveTo(p.x, p.y);
                    ctx.lineTo(q.x, q.y);
                    ctx.strokeStyle = dist < 80
                        ? "rgba(255,193,77," + (fade * 0.7) + ")"
                        : "rgba(255,255,255," + (fade * 0.32) + ")";
                    ctx.lineWidth = 1;
                    ctx.stroke();
                }
            }
        }
        requestAnimationFrame(draw);
    }

    slider.addEventListener("mousemove", (e) => {
        const box = slider.getBoundingClientRect();
        mouse.x = e.clientX - box.left;
        mouse.y = e.clientY - box.top;
    });
    slider.addEventListener("mouseleave", () => {
        mouse.x = null;
        mouse.y = null;
    });
    window.addEventListener("resize", resize);
    resize();
    draw();
})();
</script>
</body>
</html>
