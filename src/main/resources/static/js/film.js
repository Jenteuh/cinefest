"use strict";
import {byId, toon, verberg, setText} from "./util.js";
byId("zoek").onclick = async () => {
    verberg("film", "zoekIdFout", "nietGevonden", "storing", "nieuweTitelFout", "conflict");
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
    verberg("nieuweTitelFout");
    const nieuweTitelInput = byId("nieuweTitel");
    if (! nieuweTitelInput.checkValidity()) {
        toon("nieuweTitelFout");
        nieuweTitelInput.focus();
        return;
    }
    updateTitel(nieuweTitelInput.value);
};

byId("reserveer").onclick = async () => {
    verberg("emailAdresFout", "plaatsenFout");
    const emailAdresInput = byId("emailAdres");
    if (! emailAdresInput.checkValidity()) {
        toon("emailAdresFout");
    }
    const plaatsenInput = byId("plaatsen");
    if (! plaatsenInput.checkValidity()) {
        toon("plaatsenFout");
    }
    const aantalFouten = document.querySelectorAll(".fout:not([hidden])").length;
    if (aantalFouten === 0) {
        const nieuweReservatie = {
            emailAdres: emailAdresInput.value,
            plaatsen: Number(plaatsenInput.value)
        };
        reserveer(nieuweReservatie);
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

async function updateTitel(titel) {
    const response = await fetch(`films/${byId("zoekId").value}/titel`,
        {
            method: "PUT",
            headers: {"content-type": "text/plain"},
            body: titel
        });
    if (response.ok) {
        setText("titel", titel);
    } else {
        toon("storing");
    }
}

async function reserveer(nieuweReservatie) {
    verberg("nietGevonden", "storing", "conflict");
    const response = await fetch(`films/${byId("zoekId").value}/reservaties`,
        {
            method: "POST",
            headers: {'Content-Type': "application/json"},
            body: JSON.stringify(nieuweReservatie)
        });
    if (response.ok) {
        window.location = "allefilms.html";
    } else {
        switch (response.status) {
            case 404:
                toon("nietGevonden");
                break;
            case 409:
                const responseBody = await response.json();
                setText("conflict", responseBody.message);
                toon("conflict");
                break;
            default:
                toon("storing");
        }
    }
}