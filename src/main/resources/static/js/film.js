"use strict";
import {byId, toon, verberg, setText} from "./util.js";
byId("zoek").onclick = async () => {
    verberg("film", "zoekIdFout", "nietGevonden", "storing", "nieuweTitelFout");
    const zoekIdInput = byId("zoekId");
    if (!zoekIdInput.checkValidity()) {
        toon("zoekIdFout");
        zoekIdInput.focus();
        return;
    }
    findById(zoekIdInput.value);
};

byId("verwijder").onclick = async () => {
    const zoekIdInput = byId("zoekId");
    const response = await fetch(`films/${zoekIdInput.value}`, {method:"DELETE"});
    if (response.ok) {
        verberg("film", "zoekIdFout", "nietGevonden", "storing");
        zoekIdInput.value = "";
        zoekIdInput.focus();
    } else {
        toon("storing");
    }
};

byId("bewaar").onclick = async () => {
    const nieuweTitelInput = byId("nieuweTitel");
    if (nieuweTitelInput.checkValidity()) {
        verberg("nieuweTitelFout");
        updateTitel(nieuweTitelInput.value);
    } else {
        toon ("nieuweTitelFout");
        nieuweTitelInput.focus();
    }
};

async function findById(id) {
    const response = await fetch(`films/${id}`);
    if (response.ok) {
        const film = await response.json();
        toon("film");
        setText("titel", film.titel);
        setText("jaar", film.jaar);
        setText("vrijePlaatsen", film.vrijePlaatsen)
    } else {
        if (response.status === 404) {
            toon("nietGevonden");
        } else {
            toon("storing");
        }
    }
}

async function updateTitel(nieuweTitel) {
    const response = await fetch(`films/${byId("zoekId").value}/titel`,
        {
            method: "PUT",
            headers: {"content-type": "text/plain"},
            body: titel
        });
    if (response.ok) {
        setText("titel", nieuweTitel);
    } else {
        toon("storing");
    }
}