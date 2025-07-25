const BASE = "http://localhost:8080/api";

/* ---------- low‑level helpers ---------- */
async function handle(res) {
  /* return JSON if possible, otherwise text */
  const ct = res.headers.get("content-type") || "";
  return ct.includes("application/json") ? res.json() : res.text();
}

/* GET /api/{path} */
export async function get(path) {
  const res = await fetch(`${BASE}/${path}`);
  return handle(res);
}

/* POST /api/{path} */
export async function post(path, data) {
  const res = await fetch(`${BASE}/${path}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      // send JWT if stored
      Authorization: localStorage.getItem("token")
        ? `Bearer ${localStorage.getItem("token")}`
        : undefined
    },
    body: JSON.stringify(data)
  });
  return handle(res);
}

/* PUT /api/{path}/{id} */
export async function put(path, id, data) {
  const res = await fetch(`${BASE}/${path}/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  });
  return handle(res);
}

/* DELETE /api/{path}/{id} */
export async function del(path, id) {
  return fetch(`${BASE}/${path}/${id}`, { method: "DELETE" });
}


export const BASE_URL = "http://localhost:8080";

/** light wrapper around fetch */
async function request(method, url, data) {
  const opts = {
    method,
    headers: { "Content-Type": "application/json" },
  };
  if (data) opts.body = JSON.stringify(data);

  const res = await fetch(`${BASE_URL}${url}`, opts);
  if (!res.ok) throw new Error(await res.text() || res.statusText);
  return res.status === 204 ? null : res.json();
}

export const get  = url => request("GET",  url);
export const post = (url, data) => request("POST", url, data);
export const put  = (url, data) => request("PUT",  url, data);
export const del  = url => request("DELETE", url);

