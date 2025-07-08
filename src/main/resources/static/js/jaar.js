"use strict";
import {byId, toon, verberg} from "./util.js";
byId("zoek").onclick = async () => {
    verberg("filmsTable", "jaarFout", "storing");
    const jaarInput = byId("jaar");
    if (!jaarInput.checkValidity()) {
        toon("jaarFout");
        jaarInput.focus();
        return;
    }
    findByJaar(jaarInput.value);
};
async function findByJaar(jaar) {
    const response = await fetch(`films?jaar=${jaar}`);
    if (response.ok) {
        const films = await response.json();
        toon("filmsTable");
        const filmsBody = byId("filmsBody");
        filmsBody.innerHTML = "";
        for (const film of films) {
            const tr = filmsBody.insertRow();
            tr.insertCell().textContent = film.id;
            tr.insertCell().textContent = film.titel;
            tr.insertCell().textContent = film.jaar;
            tr.insertCell().textContent = film.vrijePlaatsen;
        }
    } else {
        toon("storing");
    }
}