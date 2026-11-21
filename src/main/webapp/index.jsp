<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  // Change "user" if your LoginServlet saves the session under a different name
  boolean isLoggedIn = (session.getAttribute("user") != null);
  String bookingUrl = isLoggedIn ? "Booking.jsp" : "login.jsp?msg=login_required";
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Indhira's Lake View Turf</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700&family=Russo+One&family=Bebas+Neue&display=swap" rel="stylesheet">
  <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">

  <style>
    body { font-family: 'Poppins', sans-serif; scroll-behavior: smooth; }

    /* Navbar */
    .navbar { background: #000; padding: 10px 15px; min-height: 65px; transition: all 0.3s ease-in-out; }
    .navbar.shrink { padding: 4px 12px; min-height: 50px; background: rgba(0,0,0,0.95); }
    .navbar-brand { display: flex; align-items: center; }
    .navbar-brand img { margin-right: 8px; transition: all 0.3s ease; }
    .navbar.shrink .navbar-brand img { width: 38px; height: 38px; }
    .brand-text { font-size: 1.3rem; font-weight: 600; color: #00e676; text-shadow: 0 0 3px rgba(0,255,136,0.5); transition: all 0.3s ease-in-out; }
    .navbar.shrink .brand-text { font-size: 1.1rem; }
    .brand-text:hover { color: #00cc66; text-shadow: 0 0 6px rgba(0,255,136,0.8); }

    .navbar-nav .nav-link { color: white !important; margin-left: 15px; font-weight: 500; font-size: 0.95rem; position: relative; display: flex; align-items: center; transition: all 0.3s ease-in-out; }
    .navbar-nav .nav-link i { margin-right: 6px; }
    .navbar-nav .nav-link:hover, .navbar-nav .nav-link.active { color: #00ff88 !important; }
    .navbar-nav .nav-link::after { content: ""; position: absolute; width: 0%; height: 2px; background: #00ff88; left: 0; bottom: -4px; transition: all 0.3s ease-in-out; border-radius: 2px; }
    .navbar-nav .nav-link:hover { transform: scale(1.03); text-shadow: 0 0 6px rgba(0,255,136,0.7); }
    .navbar-nav .nav-link:hover::after { width: 100%; }

    /* Hero */
    .hero { height: auto; min-height: 100vh; padding: 90px 15px 40px; background-size: cover; background-position: center; color: white; display: flex; justify-content: center; align-items: center; text-align: center; position: relative; transition: background-image 1s ease-in-out; overflow: hidden; }
    .hero::after { content: ""; position: absolute; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.55); }
    .hero-content { position: relative; z-index: 2; max-width: 900px; }
    .hero h1 { font-family: 'Russo One', sans-serif; font-size: 2.8rem; font-weight: 700; text-transform: uppercase; color: #5af08b; text-shadow: 0 0 4px rgba(0,255,136,0.6); transition: 0.3s; }
    .hero h1:hover { color: #00ff88; text-shadow: 0 0 10px #00ff88; }
    .hero p { font-size: 1.1rem; margin-bottom: 20px; font-family: 'Bebas Neue', sans-serif; letter-spacing: 1px; color: #f1f1f1; }
    .btn-book { background: #00ff88; border: none; padding: 10px 24px; font-size: 1rem; font-weight: 600; border-radius: 40px; transition: 0.3s; }
    .btn-book:hover { background: #00cc66; transform: scale(1.05); box-shadow: 0 0 10px #00ff88; }

    /* About */
    .about-section { position: relative; background: url("https://i.pinimg.com/1200x/01/bc/97/01bc9777630ce9802915849a49879f35.jpg") center/cover fixed no-repeat; padding: 100px 0; }
    .about-section .overlay { position: absolute; inset: 0; background: linear-gradient(135deg, rgba(0,0,0,0.85), rgba(0,255,136,0.25)); z-index: 1; opacity: 0; } /* opacity 0 keeps the About background looking as it does today; set to 1 for the dark overlay */
    .about-section .container { position: relative; z-index: 2; }
    .about-intro { color: #e6ffe6; font-size: 1.15rem; line-height: 1.8; text-shadow: 0 1px 4px rgba(0,0,0,0.7); }
    .highlight-text { font-weight: 700; font-size: 1.25rem; background: linear-gradient(90deg, #00ff88, #00cc66); -webkit-background-clip: text; -webkit-text-fill-color: transparent; position: relative; display: inline-block; padding-bottom: 4px; }
    .highlight-text::after { content: ""; position: absolute; left: 0; bottom: 0; width: 100%; height: 3px; background: #00ff88; border-radius: 2px; box-shadow: 0 0 6px rgba(0,255,136,0.8); animation: underlineGlow 2.5s infinite ease-in-out; }
    @keyframes underlineGlow { 0%, 100% { box-shadow: 0 0 6px rgba(0,255,136,0.6); } 50% { box-shadow: 0 0 15px rgb(216,240,229); } }
    .animate-glow { animation: glowPulse 2.5s infinite ease-in-out; }
    @keyframes glowPulse { 0%, 100% { text-shadow: 0 0 6px rgba(0,255,136,0.6), 0 0 12px rgba(0,255,136,0.4); } 50% { text-shadow: 0 0 12px #00ff88, 0 0 25px rgba(0,255,136,0.9); } }

    .about-card { background: #fff; border-radius: 15px; padding: 25px; color: #222; height: 100%; box-shadow: 0 6px 15px rgba(0,0,0,0.15); transition: transform 0.4s ease, box-shadow 0.4s ease; }
    .about-card:hover { transform: translateY(-12px) scale(1.05); box-shadow: 0 15px 25px rgba(0,255,136,0.5); }
    .about-icon { font-size: 50px; margin-bottom: 15px; color: #00b873; text-shadow: 0 0 6px rgba(0,255,136,0.7); animation: float 3s ease-in-out infinite; }
    @keyframes float { 0%, 100% { transform: translateY(0); } 50% { transform: translateY(-10px); } }

    .section-title { margin: 2rem 1rem 1rem; text-align: center; font-weight: 700; font-size: 1.8rem; color: #00d084; text-shadow: 0 0 6px rgba(0,255,136,0.6); position: relative; display: block; }
    .section-title::after { content: ""; display: block; width: 60px; height: 3px; background: #00d084; margin: 8px auto 0; border-radius: 2px; }
    .about-section p { color: #e8f9f0; font-size: 1.05rem; line-height: 1.6; }
    .about-card p { color: #444; }
    .about-card h4 { color: #111; font-weight: 600; margin-bottom: 10px; }
    .counter { font-size: 2.5rem; font-weight: 700; color: #00ff88; text-shadow: 0 0 6px rgba(0,255,136,0.7); }

    /* Footer */
    .footer { background: #111; color: #f0f0f0; padding: 50px 20px 20px; opacity: 0; transform: translateY(50px); transition: all 1s ease-in-out; }
    .footer.show { opacity: 1; transform: translateY(0); }
    .footer-container { display: flex; flex-wrap: wrap; justify-content: space-around; gap: 30px; margin-bottom: 20px; }
    .footer-section { opacity: 0; transform: translateY(20px); transition: all 0.8s ease-in-out; }
    .footer-section.show-section { opacity: 1; transform: translateY(0); }
    .footer-section h3, .footer-section h4 { color: #00ff88; margin-bottom: 15px; text-shadow: 0 0 4px #00ff88; }
    .footer-section a { color: #00ff88; text-decoration: none; display: flex; align-items: center; margin: 5px 0; transition: all 0.3s ease; position: relative; }
    .footer-section a i { margin-right: 8px; transition: transform 0.3s ease; }
    .footer-section a:hover { color: #00cc66; text-shadow: 0 0 8px #00ff88; }
    .footer-section a:hover i { transform: translateX(5px); }
    .footer-bottom { text-align: center; font-size: 0.9rem; border-top: 1px solid #333; padding-top: 10px; opacity: 0; transform: translateY(20px); transition: all 1s ease-in-out; }
    .footer-bottom.show-bottom { opacity: 1; transform: translateY(0); }
    .footer-bottom a { color: #00ff88; margin: 0 5px; text-decoration: none; transition: all 0.3s ease; }
    .footer-bottom a:hover { color: #00cc66; text-shadow: 0 0 6px #00ff88; }
    .footer-section.social a { display: inline-block; margin: 0 8px; font-size: 1.5rem; position: relative; transition: transform 0.4s ease, color 0.4s ease; }
    .footer-section.social a:hover { transform: translateY(-5px) rotate(-5deg); color: #00cc66; }
    .footer-section a.flow-hover { background: linear-gradient(90deg, #00ff88, #00cc66); -webkit-background-clip: text; -webkit-text-fill-color: transparent; }

    /* ===== Responsive layout ===== */
    @media (min-width: 1400px) {
      .hero h1 { font-size: 3.5rem; }
      .hero p { font-size: 1.4rem; }
    }
    @media (max-width: 991.98px) {
      .navbar-collapse { background: rgba(0,0,0,0.95); padding: 10px 15px; margin-top: 8px; border-radius: 8px; }
      .navbar-nav .nav-link { margin-left: 0; padding: 8px 0; }
      .about-section { background-attachment: scroll; padding: 70px 0; }
      .footer-container { justify-content: center; text-align: center; }
      .footer-section a { justify-content: center; }
    }
    @media (max-width: 767.98px) {
      .brand-text { font-size: 1rem; }
      .hero h1 { font-size: 2rem; }
      .hero p { font-size: 0.95rem; }
      .btn-book { padding: 8px 18px; font-size: 0.9rem; }
      .about-intro { font-size: 0.95rem; }
      .about-card h4 { font-size: 1.1rem; }
      .section-title { font-size: 1.5rem; }
      .counter { font-size: 2rem; }
      .footer-container { flex-direction: column; align-items: center; }
    }
    @media (max-width: 400px) {
      .hero h1 { font-size: 1.6rem; }
      .brand-text { font-size: 0.85rem; }
      .navbar-brand img { width: 36px; height: 36px; }
    }
  </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark fixed-top">
  <div class="container-fluid">
    <a class="navbar-brand" href="index.jsp">
      <img src="images/logo2.png" alt="Indhira's Lake View Turf Logo" width="46" height="46">
      <span class="brand-text">Indhira's Lake View Turf</span>
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
            data-bs-target="#mainNav" aria-controls="mainNav"
            aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="mainNav">
      <ul class="navbar-nav ms-auto align-items-lg-center">
        <li class="nav-item"><a class="nav-link active" href="index.jsp"><i class="bi bi-house-door-fill"></i> Home</a></li>
        <li class="nav-item"><a class="nav-link" href="#about"><i class="bi bi-info-circle-fill"></i> About</a></li>
        <li class="nav-item"><a class="nav-link" href="<%= bookingUrl %>"><i class="bi bi-calendar-check-fill"></i> Booking</a></li>
        <li class="nav-item"><a class="nav-link" href="pricing.html"><i class="bi bi-cash-stack"></i> Pricing</a></li>
        <li class="nav-item"><a class="nav-link" href="gallery.html"><i class="bi bi-image-fill"></i> Gallery</a></li>
        <li class="nav-item"><a class="nav-link" href="contact.html"><i class="bi bi-telephone-fill"></i> Contact</a></li>
        <li class="nav-item ms-lg-3 my-2 my-lg-0">
          <a href="register.html" target="_blank" class="btn btn-sm btn-outline-success me-2">Register</a>
          <a href="login.html" target="_blank" class="btn btn-sm btn-success">Login</a>
        </li>
        <li class="nav-item ms-lg-2">
          <a href="LogoutServlet" class="btn btn-sm btn-danger">Logout</a>
        </li>
      </ul>
    </div>
  </div>
</nav>

<!-- Hero -->
<section class="hero" id="hero">
  <div class="hero-content">
    <h1 data-aos="fade-up" data-aos-duration="1200">Welcome to Indhira's Lake View Turf</h1>
    <p data-aos="fade-up" data-aos-delay="300" data-aos-duration="1200">Book your favorite slot now and enjoy the game!</p>
    <button onclick="goToBooking()" class="btn btn-book" data-aos="zoom-in" data-aos-delay="500">Book Now</button>
  </div>
</section>

<!-- About -->
<section id="about" class="about-section text-white">
  <div class="overlay"></div>
  <div class="container">
    <h2 class="section-title" data-aos="fade-up">About Our Turf</h2>
    <p class="about-intro text-center mb-5" data-aos="fade-up" data-aos-delay="200">
      Welcome to
      <span class="highlight-text animate-glow"><i class="bi bi-trophy-fill"></i> Indhira's Lake View Turf</span>,
      the ultimate destination for football enthusiasts⚽.
      Situated by the serene lakeside🌅, our turf combines
      <b>world-class infrastructure</b> with <b>scenic beauty</b>.
      Whether you are a casual player, a team training for tournaments,
      or hosting an event, we provide the perfect pitch.
    </p>

    <div class="row g-4 justify-content-center">
      <div class="col-12 col-md-6 col-lg-4" data-aos="flip-left" data-aos-delay="100">
        <div class="about-card text-center">
          <div class="about-icon">🏟️</div>
          <h4>Our Journey</h4>
          <p>Since 2020, we’ve welcomed 2000+ players and hosted countless matches, leagues, and corporate tournaments. A place where memories are created every day.</p>
        </div>
      </div>
      <div class="col-12 col-md-6 col-lg-4" data-aos="flip-up" data-aos-delay="200">
        <div class="about-card text-center">
          <div class="about-icon">⚡</div>
          <h4>World-Class Facilities</h4>
          <p>Floodlights for night matches 🌙, secure parking 🚗, refreshments 🍹, and modern changing rooms 👕. Every detail is designed to enhance your play.</p>
        </div>
      </div>
      <div class="col-12 col-md-6 col-lg-4" data-aos="flip-right" data-aos-delay="300">
        <div class="about-card text-center">
          <div class="about-icon">📏</div>
          <h4>Premium Turf</h4>
          <p>7-a-side professional artificial turf 🌿 built with eco-friendly material, ensuring <b>durability</b>, <b>grip</b>, and <b>injury safety</b> for every game.</p>
        </div>
      </div>
    </div>

    <div class="row g-4 mt-1">
      <div class="col-12 col-md-6" data-aos="zoom-in" data-aos-delay="150">
        <div class="about-card text-center">
          <div class="about-icon">🎉</div>
          <h4>Events &amp; Community</h4>
          <p>We host football camps, kids’ training sessions, birthday turf parties, and inter-college leagues to build a thriving football community.</p>
        </div>
      </div>
      <div class="col-12 col-md-6" data-aos="zoom-in" data-aos-delay="250">
        <div class="about-card text-center">
          <div class="about-icon">🌱</div>
          <h4>Eco-Friendly</h4>
          <p>Our turf uses sustainable materials and water-efficient maintenance, ensuring we keep both players and the planet safe.</p>
        </div>
      </div>
    </div>

    <div class="row text-center mt-5">
      <div class="col-12 col-md-4 mb-3" data-aos="zoom-in" data-aos-delay="100">
        <h2 class="counter" data-target="500">0</h2>
        <p>Matches Played</p>
      </div>
      <div class="col-12 col-md-4 mb-3" data-aos="zoom-in" data-aos-delay="200">
        <h2 class="counter" data-target="2000">0</h2>
        <p>Registered Players</p>
      </div>
      <div class="col-12 col-md-4 mb-3" data-aos="zoom-in" data-aos-delay="300">
        <h2 class="counter" data-target="10000">0</h2>
        <p>Hours Played</p>
      </div>
    </div>
  </div>
</section>

<!-- Footer -->
<footer class="footer" id="footer">
  <div class="footer-container">
    <div class="footer-section company-info">
      <img src="images/logo1.png" alt="Lake View Turf Logo" style="width:180px; max-width:100%; height:auto; margin-bottom:10px;">
      <h3><i class="bi bi-trophy-fill"></i> Indhira's Lake View Turf</h3>
      <p>Professional 7-a-side turf by the lakeside. Book slots, tournaments &amp; events with ease!</p>
    </div>

    <div class="footer-section quick-links">
      <h4>Quick Links</h4>
      <a href="#hero" class="flow-hover"><i class="bi bi-house-fill"></i> Home</a>
      <a href="#about" class="flow-hover"><i class="bi bi-info-circle-fill"></i> About</a>
      <a href="<%= bookingUrl %>" class="flow-hover"><i class="bi bi-calendar-check-fill"></i> Booking</a>
      <a href="pricing.html" class="flow-hover"><i class="bi bi-cash-stack"></i> Pricing</a>
      <a href="gallery.html" class="flow-hover"><i class="bi bi-image-fill"></i> Gallery</a>
      <a href="contact.html" class="flow-hover"><i class="bi bi-telephone-fill"></i> Contact</a>
    </div>

    <div class="footer-section contact-info">
      <h4>Contact</h4>
      <a href="tel:+919788686714"><i class="bi bi-telephone-fill"></i> +91 9788686714</a>
      <a href="https://wa.me/919788686714" target="_blank"><i class="bi bi-whatsapp"></i> WhatsApp</a>
      <a href="https://mail.google.com/mail/?view=cm&amp;fs=1&amp;to=imcturf@gmail.com&amp;su=Booking%20Inquiry&amp;body=Hello,%20I%20want%20to%20book%20a%20slot" target="_blank">
        <i class="bi bi-envelope-fill"></i> info@lakeviewturf.com
      </a>
      <a href="#"><i class="bi bi-geo-alt-fill"></i> 123 Lakeview Street, City, India</a>
    </div>

    <div class="footer-section social">
      <h4>Follow Us</h4>
      <a href="https://www.facebook.com/imc_turf_trichy" target="_blank"><i class="bi bi-facebook"></i></a>
      <a href="https://www.instagram.com/imc_turf_trichy" target="_blank"><i class="bi bi-instagram"></i></a>
      <a href="#" target="_blank"><i class="bi bi-twitter"></i></a>
      <a href="#" target="_blank"><i class="bi bi-youtube"></i></a>
    </div>
  </div>

  <div class="footer-bottom">
    <p>© 2025 Indhira's Lake View Turf | All rights reserved</p>
    <p><a href="#">Privacy Policy</a> | <a href="#">Terms &amp; Conditions</a></p>
  </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
<script>
  AOS.init({ duration: 1200, easing: "ease-in-out", once: true, mirror: false });

  // Navbar shrink
  window.addEventListener("scroll", function () {
    document.querySelector(".navbar").classList.toggle("shrink", window.scrollY > 50);
  });

  // Hero background slider (plain string join, no dollar-brace, so JSP leaves it alone)
  const hero = document.getElementById("hero");
  const images = [
    "https://i.pinimg.com/736x/5e/07/8d/5e078de81d2b51af01dc31bde14541a5.jpg",
    "https://i.pinimg.com/736x/48/7e/a4/487ea4998ba41d602f00bd8d9e40c369.jpg",
    "https://i.pinimg.com/736x/86/f6/57/86f657f39a227079d1309c10f033c20e.jpg",
    "https://i.pinimg.com/736x/e5/f0/26/e5f026c2c9b2320305d95fb8f1c9c01a.jpg"
  ];
  let currentIndex = 0;
  function changeBackground() {
    hero.style.backgroundImage = "url('" + images[currentIndex] + "')";
    currentIndex = (currentIndex + 1) % images.length;
  }
  setInterval(changeBackground, 4000);
  changeBackground();

  // Counter animation
  const speed = 150;
  document.querySelectorAll(".counter").forEach(function (counter) {
    let started = false;
    function animate() {
      const target = +counter.getAttribute("data-target");
      const count = +counter.innerText.replace(/,/g, "");
      const increment = Math.ceil(target / speed);
      if (count < target) {
        counter.innerText = count + increment;
        setTimeout(animate, 30);
      } else {
        counter.innerText = target.toLocaleString();
      }
    }
    function check() {
      if (!started && counter.getBoundingClientRect().top < window.innerHeight) {
        started = true;
        animate();
      }
    }
    window.addEventListener("scroll", check);
    check();
  });

  function goToBooking() { window.location.href = "<%= bookingUrl %>"; }

  // Footer animation
  const footer = document.getElementById("footer");
  const observer = new IntersectionObserver(function (entries) {
    entries.forEach(function (entry) {
      if (entry.isIntersecting) {
        footer.classList.add("show");
        document.querySelectorAll(".footer-section").forEach(function (sec, i) {
          setTimeout(function () { sec.classList.add("show-section"); }, i * 150);
        });
        document.querySelector(".footer-bottom").classList.add("show-bottom");
      }
    });
  }, { threshold: 0.1 });
  observer.observe(footer);
</script>

</body>
</html>
