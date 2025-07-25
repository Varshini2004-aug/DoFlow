import { post } from "./api.js";

/* ========== SIGN‑UP ========== */
const suForm = document.getElementById("signupForm");
if (suForm) {
  suForm.addEventListener("submit", async e => {
    e.preventDefault();
    const msg = document.getElementById("suMsg");
    msg.textContent = "Creating account…";

    try {
      const res = await post("auth/register", {
        username: suForm.suUser.value,
        password: suForm.suPass.value
      });
      msg.textContent = res;                // backend returns text
      if (res.includes("success")) {
        setTimeout(() => (location.href = "login.html"), 1000);
      }
    } catch (err) {
      console.error(err);
      msg.textContent = "Couldn’t register – see console.";
    }
  });
}

/* ========== LOGIN ========== */
const liForm = document.getElementById("loginForm");
if (liForm) {
  liForm.addEventListener("submit", async e => {
    e.preventDefault();
    const msg = document.getElementById("liMsg");
    msg.textContent = "Authenticating…";

    try {
      const res = await post("auth/login", {
        username: liForm.liUser.value,
        password: liForm.liPass.value
      });
      /* expect JSON: {token:"…", message:"Login successful."} */
      if (res.token) localStorage.setItem("token", res.token);
      localStorage.setItem("user", liForm.liUser.value);
      msg.textContent = res.message || "Logged in.";
      location.href = "addtask.html";
    } catch (err) {
      console.error(err);
      msg.textContent = "Login failed.";
    }
  });
}
