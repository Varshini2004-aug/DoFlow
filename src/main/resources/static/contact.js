/* eslint-env browser */
import { post } from "./api.js";

const form = document.getElementById("contactForm");
const pop  = document.getElementById("contactMsg");

form.addEventListener("submit", async e => {
  e.preventDefault();
  const data = Object.fromEntries(new FormData(form));
  try {
    await post("contact", data);          // POST /api/contact
    pop.style.display = "block";
    form.reset();
    setTimeout(() => (pop.style.display = "none"), 3000);
  } catch {
    pop.textContent = "❌ Could not send. Try again.";
    pop.style.display = "block";
  }
});
