// Atelier Nine frontend — talks to the Spring Boot REST API on the same origin.

document.getElementById('year').textContent = new Date().getFullYear();

/* mobile nav */
const navToggle = document.querySelector('.nav-toggle');
const mainNav = document.getElementById('main-nav');
navToggle.addEventListener('click', () => {
  navToggle.setAttribute('aria-expanded', mainNav.classList.toggle('open'));
});
mainNav.querySelectorAll('a').forEach(a => a.addEventListener('click', () => mainNav.classList.remove('open')));

/* scroll reveal */
const revealObserver = new IntersectionObserver(entries => {
  entries.forEach(e => {
    if (e.isIntersecting) { e.target.classList.add('is-visible'); revealObserver.unobserve(e.target); }
  });
}, { threshold: 0.1 });
document.querySelectorAll('main > section:not(.hero)').forEach(el => {
  el.classList.add('reveal');
  revealObserver.observe(el);
});

async function getJSON(url) {
  const res = await fetch(url);
  if (!res.ok) throw new Error(`${url} -> ${res.status}`);
  return res.json();
}

const esc = s => String(s).replace(/[&<>"']/g, c => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' }[c]));

/* announcement banner (CI/CD demo) — only shows if /api/announcement exists */
async function loadAnnouncement() {
  try {
    const data = await getJSON('/api/announcement');
    if (data && data.message) {
      const el = document.getElementById('announcement');
      el.textContent = data.message;
      el.hidden = false;
    }
  } catch (_) { /* endpoint not deployed -> banner stays hidden */ }
}

/* projects + filters */
let allProjects = [];

function renderProjects(category) {
  const grid = document.getElementById('portfolio-grid');
  const list = category === 'All' ? allProjects : allProjects.filter(p => p.category === category);
  grid.innerHTML = list.map(p => `
    <a class="project-card" href="${esc(p.projectUrl)}">
      <div class="project-cover" style="background:linear-gradient(135deg, ${esc(p.colorA)}, ${esc(p.colorB)})">
        <span class="project-year">${esc(p.year)}</span>
        ${esc(p.initials)}
        <span class="project-view">View case study →</span>
      </div>
      <div class="project-meta">
        <h3>${esc(p.title)}</h3>
        <span class="project-category">${esc(p.category)}</span>
      </div>
      <p class="project-desc">${esc(p.description)}</p>
      <div class="tags">${(p.tags || []).map(t => `<span>${esc(t)}</span>`).join('')}</div>
    </a>
  `).join('');
}

async function loadProjects() {
  const grid = document.getElementById('portfolio-grid');
  try {
    allProjects = await getJSON('/api/projects');
    const cats = ['All', ...new Set(allProjects.map(p => p.category))];
    const filters = document.getElementById('filters');
    filters.innerHTML = cats.map((c, i) => `<button class="chip${i === 0 ? ' active' : ''}" data-cat="${esc(c)}">${esc(c)}</button>`).join('');
    filters.addEventListener('click', e => {
      const btn = e.target.closest('.chip');
      if (!btn) return;
      filters.querySelectorAll('.chip').forEach(c => c.classList.remove('active'));
      btn.classList.add('active');
      renderProjects(btn.dataset.cat);
    });
    renderProjects('All');
  } catch (err) {
    grid.innerHTML = '<p class="loading-text">Couldn\'t load projects right now.</p>';
    console.error(err);
  }
}

/* services */
async function loadServices() {
  const list = document.getElementById('services-list');
  try {
    const services = await getJSON('/api/services');
    list.innerHTML = services.map(s => `
      <div class="service-row">
        <div class="service-icon">${esc(s.icon)}</div>
        <h3>${esc(s.title)}</h3>
        <p>${esc(s.description)}</p>
      </div>`).join('');
  } catch (err) {
    list.innerHTML = '<p class="loading-text">Couldn\'t load services right now.</p>';
    console.error(err);
  }
}

/* testimonials */
async function loadTestimonials() {
  const list = document.getElementById('testimonials-list');
  try {
    const items = await getJSON('/api/testimonials');
    list.innerHTML = items.map(t => `
      <div class="testimonial-card">
        <div class="stars">${'★'.repeat(t.rating)}${'☆'.repeat(5 - t.rating)}</div>
        <p class="quote">“${esc(t.message)}”</p>
        <p class="testimonial-meta"><strong>${esc(t.clientName)}</strong> — ${esc(t.clientRole)}</p>
      </div>`).join('');
  } catch (err) {
    list.innerHTML = '<p class="loading-text">Couldn\'t load testimonials right now.</p>';
    console.error(err);
  }
}

/* stats (count-up) */
async function loadStats() {
  try {
    const stats = await getJSON('/api/stats');
    document.querySelectorAll('#stats b').forEach(el => {
      const target = stats[el.dataset.key] ?? 0;
      let n = 0;
      const step = Math.max(1, Math.ceil(target / 30));
      const t = setInterval(() => {
        n = Math.min(target, n + step);
        el.textContent = n + (el.dataset.key === 'clients' ? '+' : '');
        if (n >= target) clearInterval(t);
      }, 30);
    });
  } catch (err) { console.error(err); }
}

/* contact form */
const form = document.getElementById('contact-form');
const status = document.getElementById('contact-status');
form.addEventListener('submit', async e => {
  e.preventDefault();
  status.textContent = 'Sending…';
  status.className = 'contact-status';
  try {
    const res = await fetch('/api/contact', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        name: form.name.value.trim(),
        email: form.email.value.trim(),
        message: form.message.value.trim(),
      }),
    });
    if (!res.ok) throw new Error(res.status);
    const data = await res.json();
    status.textContent = data.message || 'Message sent!';
    status.classList.add('success');
    form.reset();
  } catch (err) {
    status.textContent = 'Message not sent — check the details and try again.';
    status.classList.add('error');
  }
});

loadAnnouncement();
loadProjects();
loadServices();
loadTestimonials();
loadStats();