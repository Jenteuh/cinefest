"use strict";
import {byId, toon, verberg} from "./util.js";
byId("zoek").onclick = async () => {
    verberg("medewerkersTable", "storing");
    const stukVoornaamInput = byId("stukVoornaam");
    const stukFamilienaamInput = byId("stukFamilienaam");
    findByStukVoornaamEnStukFamilienaam(stukVoornaamInput.value, stukFamilienaamInput.value);
};
async function findByStukVoornaamEnStukFamilienaam(stukVoornaam, stukFamilienaam) {
    var response = await fetch(
        `medewerkers?stukVoornaam=${stukVoornaam}&stukFamilienaam=${stukFamilienaam}`);
    if (response.ok) {
        const medewerkers = await response.json();
        toon("medewerkersTable");
        const filmsBody = byId("medewerkersBody");
        filmsBody.innerHTML = "";
        for (const medewerker of medewerkers) {
            const tr = medewerkersBody.insertRow();
            tr.insertCell().textContent = medewerker.id;
            tr.insertCell().textContent = medewerker.voornaam;
            tr.insertCell().textContent = medewerker.familienaam;
        }
    } else {
        toon("storing");
    }
}