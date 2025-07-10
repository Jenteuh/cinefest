"use strict";
import {byId, toon, verberg} from "./util.js";
byId("zoek").onclick = async () => {
    verberg("reservatiesTable", "emailAdresFout", "storing");
    const emailAdresInput = byId("emailAdres");
    if (!emailAdresInput.checkValidity()) {
        toon("emailAdresFout");
        emailAdresInput.focus();
        return;
    }
    findByEmailAdres(emailAdresInput.value);
};
async function findByEmailAdres(emailAdres) {
    const response = await fetch(`reservaties?emailAdres=${emailAdres}`);
    if (response.ok) {
        const reservaties = await response.json();
        toon("reservatiesTable");
        const reservatiesBody = byId("reservatiesBody");
        reservatiesBody.innerHTML = "";
        for (const reservatie of reservaties) {
            const tr = reservatiesBody.insertRow();
            tr.insertCell().textContent = reservatie.id;
            tr.insertCell().textContent = reservatie.titel;
            tr.insertCell().textContent = reservatie.plaatsen;
            tr.insertCell().textContent =
                new Date(reservatie.besteld).toLocaleString("nl-BE");
        }
    } else {
        toon("storing");
    }
}