// LOGIN HUESPED
document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("guest-login-form");
  if (!form) return;

  const errorLabel = document.getElementById("login-error");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    errorLabel.style.display = "none";
    errorLabel.textContent = "";

    const roomNumber = document.getElementById("roomNumber").value.trim();
    const token = document.getElementById("token").value.trim();

    if (!roomNumber || !token) {
      errorLabel.textContent = "Por favor completa todos los campos.";
      errorLabel.style.display = "block";
      return;
    }

    try {
      const data = await apiRequest("/api/auth/guest/login", {
        method: "POST",
        body: JSON.stringify({
          roomNumber,
          accessToken: token    // <-- IMPORTANTE: NOMBRE IGUAL QUE DTO
        }),
      });

      // Aceptamos "jwt" o "token" por si acaso
      const jwt = data.token;

      localStorage.setItem("jwt", jwt);
      localStorage.setItem("roomNumber", data.roomNumber);
      if (data.guestName) {
        localStorage.setItem("guestName", data.guestName);
      }

      window.location.href = "guest-events.html";
    } catch (err) {
      console.error("Error en login huésped:", err);
      errorLabel.textContent =
        "No se pudo iniciar sesión. Verifica tus datos o intenta más tarde.";
      errorLabel.style.display = "block";
    }
  });
});