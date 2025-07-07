"use strict";
import {byId, toon} from "./util.js";
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
    }
} else {
    toon("storing");
}