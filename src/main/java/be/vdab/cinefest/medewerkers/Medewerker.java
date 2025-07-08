package be.vdab.cinefest.medewerkers;

public class Medewerker {

    private final long id;
    private final String voornaam;
    private final String familienaam;

    public Medewerker(long id, String voornaam, String familienaam) {
        this.id = id;
        this.voornaam = voornaam;
        this.familienaam = familienaam;
    }

    public long getId() {
        return id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getFamilienaam() {
        return familienaam;
    }
}
