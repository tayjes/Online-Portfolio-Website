// ---------------------------------------------------------------
// Atelier Nine — frontend logic
// Talks to the Spring Boot backend running on the same origin.
// ---------------------------------------------------------------

document.getElementById('year').textContent = new Date().getFullYear();

/* ---------- mobile nav toggle ---------- */
const navToggle = document.querySelector('.nav-toggle');
const mainNav = document.querySelector('.main-nav');
navToggle.addEventListener('click', () => {
  const isOpen = mainNav.classList.toggle('open');
  navToggle.setAttribute('aria-expanded', isOpen);
});
mainNav.querySelectorAll('a').forEach(link =>
  link.addEventListener('click', () => mainNav.classList.remove('open'))
);

/* ---------- scroll reveal ---------- */
const revealTargets = document.querySelectorAll('section:not(.hero)');
revealTargets.forEach(el => el.classList.add('reveal'));

const revealObserver = new IntersectionObserver((entries) => {
  entries.forEach(entry => {
    if (entry.isIntersecting) {
      entry.target.classList.add('is-visible');
      revealObserver.unobserve(entry.target);
    }
  });
}, { threshold: 0.12 });

revealTargets.forEach(el => revealObserver.observe(el));

/* ---------- helpers ---------- */
async function getJSON(url) {
  const res = await fetch(url);
  if (!res.ok) throw new Error(`Request to ${url} failed: ${res.status}`);
  return res.json();
}

function starString(rating) {
  const full = '★'.repeat(rating);
  const empty = '☆'.repeat(5 - rating);
  return full + empty;
}

/* ---------- render: services ---------- */
async function loadServices() {
  const grid = document.getElementById('services-grid');
  try {
    const services = await getJSON('/api/services');
    grid.innerHTML = services.map(s => `
      <div class="service-card">
        <div class="service-icon">${s.icon}</div>
        <h3>${s.title}</h3>
        <p>${s.description}</p>
      </div>
    `).join('');
  } catch (err) {
    grid.innerHTML = `<p class="loading-text">Couldn't load services right now.</p>`;
    console.error(err);
  }
}

/* ---------- render: portfolio projects ---------- */
async function loadProjects() {
  const grid = document.getElementById('portfolio-grid');
  try {
    const projects = await getJSON('/api/projects');
    grid.innerHTML = projects.map(p => `
      <a class="project-card" href="${p.projectUrl}">
        <div class="project-thumb">${p.initials}</div>
        <div class="project-body">
          <span class="project-category">${p.category}</span>
          <h3>${p.title}</h3>
          <p>${p.description}</p>
        </div>
      </a>
    `).join('');
  } catch (err) {
    grid.innerHTML = `<p class="loading-text">Couldn't load projects right now.</p>`;
    console.error(err);
  }
}

/* ---------- render: testimonials ---------- */
async function loadTestimonials() {
  const list = document.getElementById('testimonials-list');
  try {
    const testimonials = await getJSON('/api/testimonials');
    list.innerHTML = testimonials.map(t => `
      <div class="testimonial-card">
        <div class="stars">${starString(t.rating)}</div>
        <p class="quote">“${t.message}”</p>
        <p class="testimonial-meta"><strong>${t.clientName}</strong> — ${t.clientRole}</p>
      </div>
    `).join('');
  } catch (err) {
    list.innerHTML = `<p class="loading-text">Couldn't load testimonials right now.</p>`;
    console.error(err);
  }
}

/* ---------- contact form ---------- */
const contactForm = document.getElementById('contact-form');
const contactStatus = document.getElementById('contact-status');

contactForm.addEventListener('submit', async (e) => {
  e.preventDefault();
  const payload = {
    name: document.getElementById('name').value.trim(),
    email: document.getElementById('email').value.trim(),
    message: document.getElementById('message').value.trim(),
  };

  contactStatus.textContent = 'Sending…';
  contactStatus.className = 'contact-status';

  try {
    const res = await fetch('/api/contact', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    });
    if (!res.ok) throw new Error(`Server responded with ${res.status}`);
    const data = await res.json();
    contactStatus.textContent = data.message || 'Message sent!';
    contactStatus.classList.add('success');
    contactForm.reset();
  } catch (err) {
    contactStatus.textContent = 'Something went wrong — please try again.';
    contactStatus.classList.add('error');
    console.error(err);
  }
});

/* ---------- init ---------- */
loadServices();
loadProjects();
loadTestimonials();