/* eslint-env browser */
export function toggleNav() {
  const nav   = document.getElementById("sideNavbar");
  const open  = nav.style.width && nav.style.width !== "0px";
  nav.style.width = open ? "0" : calcWidth();
}

function calcWidth() {
  const w = window.innerWidth;
  if (w <= 480) return "65%";
  if (w <= 768) return "45%";
  if (w <= 1024) return "30%";
  return "250px";
}

window.addEventListener("resize", () => {
  const nav = document.getElementById("sideNavbar");
  if (nav && nav.style.width !== "0px") nav.style.width = calcWidth();
});
