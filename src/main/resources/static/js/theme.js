// theme.js - Load saved theme settings across pages

document.addEventListener("DOMContentLoaded", () => {
  // Apply dark mode if enabled
  if (localStorage.getItem("theme") === "dark") {
    document.body.classList.add("dark");
  }

  // Apply saved font size
  const fontSize = localStorage.getItem("fontSize") || "medium";
  document.body.style.fontSize =
    fontSize === "small" ? "14px" :
    fontSize === "large" ? "18px" :
    "16px"; // default medium
});
