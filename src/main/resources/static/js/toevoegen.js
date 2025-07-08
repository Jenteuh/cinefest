"use strict";
import {byId, toon, verberg} from "./util.js";
byId("toevoegen").onclick = async () => {
    verberg("titelFout", "jaarFout", "storing");
    const titelInput = byId("titel");
    if (!titelInput.checkValidity()) {
        toon("titelFout");
    }
    const jaarInput = byId("jaar");
    if (!jaarInput.checkValidity()) {
        toon("jaarFout");
    }
    const aantalFouten = document.querySelectorAll(".fout:not([hidden])").length;
    if (aantalFouten === 0) {
    // JavaScript object maken dat een film met naam en prijs voorstelt:
        const film = {
            titel: titelInput.value,
            jaar: Number(jaarInput.value)
        };
        voegToe(film);
    }
};

async function voegToe(film) {
    const response = await fetch("films",
{
        method: "POST",
        headers: {'Content-Type': "application/json"},
        body: JSON.stringify(film)
    });
if (response.ok) {
    window.location = "allefilms.html";
} else {
    toon("storing");
}
}