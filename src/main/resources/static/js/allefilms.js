"use strict";
import {byId, toon, verberg} from "./util.js";
const response = await fetch("films");
if (response.ok) {
    const films = await response.json();
    const filmsBody = byId("filmsBody");
    for(const film of films) {
        const tr = filmsBody.insertRow();
        tr.insertCell().textContent = film.id;
        tr.insertCell().textContent = film.titel;
        tr.insertCell().textContent = film.jaar;
        tr.insertCell().textContent = film.vrijePlaatsen
        const td = tr.insertCell();
        const button = document.createElement("button");
        td.appendChild(button);
        button.textContent = "verwijder";
        button.onclick = async () => {
            const response = await fetch(`films/${film.id}`, {method: "DELETE"});
            if (response.ok) {
                verberg("storing");
                tr.remove();
            } else {
                toon("storing");
            }
        }
    }
} else {
    toon("storing");
}