document.addEventListener("DOMContentLoaded", () => {
  const eventsContainer = document.getElementById("events-container");
  const guestInfo = document.getElementById("guest-info");
  const logoutBtn = document.getElementById("logout-btn");

  const roomNumber = localStorage.getItem("roomNumber");
  const guestName = localStorage.getItem("guestName") || "Huésped";

  if (!roomNumber) {
    // No hay sesión, vuelve al login
    window.location.href = "guest-login.html";
    return;
  }

  guestInfo.textContent = `Habitación ${roomNumber} · ${guestName}`;

  logoutBtn.addEventListener("click", () => {
    localStorage.removeItem("jwt");
    localStorage.removeItem("roomNumber");
    localStorage.removeItem("guestName");
    window.location.href = "guest-login.html";
  });

  loadEvents();

  async function loadEvents() {
    try {
      const events = await apiRequest("/api/events");
      if (!events || events.length === 0) {
        eventsContainer.innerHTML =
          '<p class="card-subtitle">No hay eventos publicados por el momento.</p>';
        return;
      }

      const html = [
        '<div class="grid-cards">',
        ...events.map(renderEventCard),
        "</div>",
      ].join("");

      eventsContainer.innerHTML = html;

      // Asignar listeners a botones de "Unirme"
      document.querySelectorAll("[data-join-event]").forEach((btn) => {
        btn.addEventListener("click", async () => {
          const id = btn.getAttribute("data-join-event");
          await joinEvent(id);
        });
      });
    } catch (err) {
      console.error(err);
      eventsContainer.innerHTML =
        '<p class="error-message">No se pudieron cargar los eventos.</p>';
    }
  }

  function renderEventCard(event) {
    const start = new Date(event.startTime);
    const end = new Date(event.endTime);

    const dateStr = start.toLocaleDateString("es-ES", {
      day: "2-digit",
      month: "short",
    });
    const timeStr = `${start.toLocaleTimeString("es-ES", {
      hour: "2-digit",
      minute: "2-digit",
    })} - ${end.toLocaleTimeString("es-ES", {
      hour: "2-digit",
      minute: "2-digit",
    })}`;

    return `
      <article class="event-card">
        <h2 class="event-title">${event.title}</h2>
        <p class="event-meta">
          ${dateStr} · ${timeStr} · ${event.location || "Ubicación por definir"}
        </p>
        <p class="event-description">
          ${event.description}
        </p>
        <div class="event-footer">
          <span class="badge">
            ${event.occupiedSpots}/${event.capacity} plazas · ${event.status}
          </span>
          <button class="btn btn-primary" data-join-event="${event.id}">
            Unirme al evento
          </button>
        </div>
      </article>
    `;
  }

  async function joinEvent(eventId) {
    try {
      await apiRequest(`/api/events/${eventId}/join`, {
        method: "POST",
        body: JSON.stringify({
          roomNumber,
          guestName,
        }),
      });
      alert("Te has unido al evento.");
      // Volvemos a cargar para actualizar plazas
      loadEvents();
    } catch (err) {
      console.error(err);
      alert("No se pudo completar la acción: " + err.message);
    }
  }
});